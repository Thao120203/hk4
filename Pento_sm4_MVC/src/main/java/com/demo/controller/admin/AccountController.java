package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Role;
import com.demo.helpers.SecurityCodeHelper;
import com.demo.service.AccountService;
import com.demo.service.MailService;
import com.demo.service.RoleService;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "account", "account/" })
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MailService mailService;

	@Autowired
	private Environment environment;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAll());
		return "account/index";
	}

	// -------- ADD // for Admin
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		return "account/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			account.setStatus("0");
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/account/index";
	}

	// ------ Register // For User
	@GetMapping({ "register" })
	public String register(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		return "account/register";
	}

	@PostMapping({ "register" })
	public String register(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
		try {
			account.setStatus("0");
			account.setRole(new Role(3, "ROLE_MEMBER"));

			// Genereate Code
			String securityCode = SecurityCodeHelper.generate();
			account.setSecurityCode(securityCode);

			if (confirmPassword.matches(newPassword)) {
				account.setPassword(newPassword);
				if (accountService.save(account)) {
					// Generate Content
					String content = "Click here to Active Account";
					content += "<br> <a href='http://localhost:8085/account/verify?code=" + securityCode + "&email="
							+ account.getEmail() + "'>Click Me</a>";

					// Send Mail
					mailService.send(environment.getProperty("spring.mail.username"), account.getEmail(), "Verify",
							content);

					redirectAttributes.addFlashAttribute("msg", "Regsiter Success");
				} else {
					redirectAttributes.addFlashAttribute("msg", "Register Failed");
				}
			} else {
				redirectAttributes.addFlashAttribute("msg", "Password not match");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/account/index";
	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (accountService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/account/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("account", accountService.find(id));
		return "account/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			account.setStatus("0");
			account.setSecurityCode("");
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/account/index";
	}

	// ----------- LOGIN
	@GetMapping({ "login" })
	public String login(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		return "account/login";
	}

	@PostMapping({ "login" })
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, RedirectAttributes redirectAttributes, ModelMap modelMap) {

		Account account = accountService.findByEmail(email);
		try {
			if ( email == null || password == null ) {
				redirectAttributes.addFlashAttribute("msg", "Email Password not null");
				return "redirect:/account/login";
			}

			if ( !account.getStatus().equals("1") ) {
				redirectAttributes.addFlashAttribute("msg", "Email not Active");
				return "redirect:/account/login";
			}

			if ( accountService.login(email, password) == null ) {
				redirectAttributes.addFlashAttribute("msg", "Login Failed");
				return "redirect:/account/login";
			}

			session.setAttribute("email", email);
			modelMap.put("accounts", accountService.findAll());
			return "account/index";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Login Failed");
			return "redirect:/account/login";
		}
	}

	// ------------------ Change PassWord
	// Update Password
	@GetMapping({ "updatePassword/{id}" })
	public String updatePassword(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {
		Account account = accountService.find(id);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "Email not found");
		} else {
			modelMap.put("account", account);
			return "account/updatePassword";
		}
		return "redirect:/account/login";
	}

	@PostMapping({ "updatePassword" })
	public String updatePassword(@ModelAttribute("account") Account account,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {
		
		if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
		    redirectAttributes.addFlashAttribute("msg", "Password cannot be empty");
		    return "redirect:/account/updatePassword/" + account.getId();
		}

		if (!accountService.findPassword(account.getId()).equals(currentPassword)) {
		    redirectAttributes.addFlashAttribute("msg", "Current password is incorrect");
		    return "redirect:/account/updatePassword/" + account.getId();
		}

		if (!confirmPassword.equals(newPassword)) {
		    redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
		    return "redirect:/account/updatePassword/" + account.getId();
		}

		account.setPassword(newPassword);
		redirectAttributes.addFlashAttribute("msg", accountService.save(account) ? "Password changed successfully" : "Failed to change password");
		return "redirect:/account/index";
	}

	//
	@RequestMapping(value = "verify", method = RequestMethod.GET)
	public String verify(@RequestParam("email") String email, @RequestParam("code") String code,
			RedirectAttributes redirectAttributes) {
		Account account = accountService.findByEmail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "email not found");
		} else {
			if (code.equals(account.getSecurityCode())) {
				account.setStatus("1");
				if (accountService.save(account)) {
					redirectAttributes.addFlashAttribute("msg", "Actived");
				} else {
					redirectAttributes.addFlashAttribute("msg", "Failed");
				}
			} else {
				redirectAttributes.addFlashAttribute("msg", "Failed");
			}
		}
		return "redirect:/account/login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return "redirect:/account/login";
	}

	@RequestMapping(value = "forgetpassword", method = RequestMethod.GET)
	public String forgetpassword() {
		return "account/forgetpassword";
	}

	@RequestMapping(value = "forgetpassword", method = RequestMethod.POST)
	public String forgetpassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		Account account = accountService.findByEmail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "Email not found");
			return "redirect:/account/forgetpassword";
		} else {
			String securityCode = SecurityCodeHelper.generate();
			account.setSecurityCode(securityCode);
			if (accountService.save(account)) {
				String content = "Nhan vao <a href='http://localhost:8085/account/updatepassword?code=" + securityCode
						+ "&email=" + account.getEmail() + "'>day</a> de cap nhat password";
				mailService.send(environment.getProperty("spring.mail.email"), account.getEmail(), "Update Password",
						content);
			}
			return "redirect:/account/login";
		}
	}

}

package com.demo.controller.user;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping({ "", "/" })
public class LoginController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MailService mailService;

	@Autowired
	private Environment environment;
	
	@GetMapping({ "index" })
	public String index(ModelMap modelMap) {
		return "user/index";
	}
	
	// ------ Register // For User
	@GetMapping({ "register" })
	public String register(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		return "user/log/register";
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
					content += "<br> <a href='http://localhost:8085/verify?code=" + securityCode + "&email="
							+ account.getEmail() + "'>Click Me</a>";

					// Send Mail
					mailService.send(environment.getProperty("spring.mail.username"), account.getEmail(), "Verify",
							content);

					redirectAttributes.addFlashAttribute("msg", "Regsiter Success");
					return "redirect:/login";
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
		return "redirect:/register";
	}


	// ----------- LOGIN
	@GetMapping({ "login", "", "/"})
	public String login(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		return "user/log/login";
	}

	@PostMapping({ "login" })
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, RedirectAttributes redirectAttributes, ModelMap modelMap) {

		Account account = accountService.findByEmail(email);
		try {
			if ( email == null || password == null ) {
				redirectAttributes.addFlashAttribute("msg", "Email Password not null");
				return "redirect:/login";
			}

			if ( !account.getStatus().equals("1") ) {
				redirectAttributes.addFlashAttribute("msg", "Email not Active");
				return "redirect:/login";
			}

			if ( accountService.login(email, password) == null ) {
				redirectAttributes.addFlashAttribute("msg", "Login Failed");
				return "redirect:/login";
			}

			modelMap.put("id", account.getId());
			session.setAttribute("id", account.getId());
			session.setAttribute("email", email);
			return "user/index";
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Login Failed");
			return "redirect:/login";
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
			return "user/log/updatePassword";
		}
		return "redirect:/login";
	}

	@PostMapping({ "updatePassword" })
	public String updatePassword(@ModelAttribute("account") Account account,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {
		
		if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
		    redirectAttributes.addFlashAttribute("msg", "Password cannot be empty");
		    return "redirect:/updatePassword/" + account.getId();
		}

		if (!accountService.findPassword(account.getId()).equals(currentPassword)) {
		    redirectAttributes.addFlashAttribute("msg", "Current password is incorrect");
		    return "redirect:/updatePassword/" + account.getId();
		}

		if (!confirmPassword.equals(newPassword)) {
		    redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
		    return "redirect:/updatePassword/" + account.getId();
		}

		account.setPassword(newPassword);
		redirectAttributes.addFlashAttribute("msg", accountService.save(account) ? "Password changed successfully" : "Failed to change password");
		return "redirect:/index";
	}

	// Send Mail
	@GetMapping({ "passwordForgot/{id}" })
	public String passwordForgot(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {
		Account account = accountService.find(id);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "Email not found");
		} else {
			modelMap.put("account", account);
			return "user/log/passwordForgot";
		}
		return "redirect:/login";
	}

	@PostMapping({ "passwordForgot" })
	public String passwordForgot(@ModelAttribute("account") Account account,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {
		
		if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
		    redirectAttributes.addFlashAttribute("msg", "Password cannot be empty");
		    return "redirect:/passwordForgot/" + account.getId();
		}

		if (!confirmPassword.equals(newPassword)) {
		    redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
		    return "redirect:/passwordForgot/" + account.getId();
		}

		account.setPassword(newPassword);
		redirectAttributes.addFlashAttribute("msg", accountService.save(account) ? "Password changed successfully" : "Failed to change password");
		return "redirect:/login";
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
		return "redirect:/login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return "redirect:/login";
	}

	@RequestMapping(value = "forgetPassword", method = RequestMethod.GET)
	public String forgetpassword() {
		return "user/log/forgetpassword";
	}

	@RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
	public String forgetpassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		Account account = accountService.findByEmail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "Email not found");
			return "redirect:/forgetPassword";
		} else {
			String securityCode = SecurityCodeHelper.generate();
			account.setSecurityCode(securityCode);
			if (accountService.save(account)) {
				
				String content = "Nhan vao <a href='http://localhost:8085/passwordForgot/" + account.getId() + "'>day</a> de cap nhat password";
				mailService.send(environment.getProperty("spring.mail.username"), account.getEmail(), "Update Password",
						content);
			}
			return "redirect:/login";
		}
	}

}

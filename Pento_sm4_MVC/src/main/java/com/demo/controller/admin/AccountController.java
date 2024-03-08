package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.demo.service.BranchService;
import com.demo.service.MailService;
import com.demo.service.RoleService;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping({ "admin/account", "admin/account/" })
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private MailService mailService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private Environment environment;

	@GetMapping({ "index"})
	public String index(ModelMap modelMap) {
		modelMap.put("accounts", accountService.findAll());
		return "admin/account/index";
	}

	// -------- ADD // for Admin
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		modelMap.put("branchs", branchService.findAll());
		return "admin/account/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			account.setStatus("0");
			account.setPassword(encoder.encode(account.getPassword()));
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/account/index";
	}
	
	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (accountService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/account/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("account", accountService.find(id));
		modelMap.put("roles", roleService.findAll());
		modelMap.put("branchs", branchService.findAll());
		return "admin/account/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			String statusValue = account.getStatus();
			account.setStatus(statusValue != null && statusValue.equals("on") ? "1" : "0");
			if (accountService.save(account)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/account/index";
	}

	// ----------- LOGIN
	@GetMapping({ "login" })
	public String login(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		return "admin/account/login";
	}

	@PostMapping({ "login" })
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, RedirectAttributes redirectAttributes, ModelMap modelMap) {

		Account account = accountService.findByEmail(email);
		try {
			if ( email == null || password == null ) {
				redirectAttributes.addFlashAttribute("msg", "Email Password not null");
				return "redirect:/admin/account/login";
			}

			if ( !account.getStatus().equals("1") ) {
				redirectAttributes.addFlashAttribute("msg", "Email not Active");
				return "redirect:/admin/account/login";
			}

			if ( accountService.login(email, password) == null ) {
				redirectAttributes.addFlashAttribute("msg", "Login Failed");
				return "redirect:/admin/account/login";
			}

			session.setAttribute("email", email);
			modelMap.put("accounts", accountService.findAll());
			return "admin/account/index";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Login Failed");
			return "redirect:/admin/account/login";
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
			return "admin/account/updatePassword";
		}
		return "redirect:/admin/account/login";
	}

	@PostMapping({ "updatePassword" })
	public String updatePassword(@ModelAttribute("account") Account account,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {
		
		if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
		    redirectAttributes.addFlashAttribute("msg", "Password cannot be empty");
		    return "redirect:/admin/account/updatePassword/" + account.getId();
		}

		if (!accountService.findPassword(account.getId()).equals(currentPassword)) {
		    redirectAttributes.addFlashAttribute("msg", "Current password is incorrect");
		    return "redirect:/admin/account/updatePassword/" + account.getId();
		}

		if (!confirmPassword.equals(newPassword)) {
		    redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
		    return "redirect:/admin/account/updatePassword/" + account.getId();
		}
		account.setPassword(encoder.encode(newPassword));
		redirectAttributes.addFlashAttribute("msg", accountService.save(account) ? "Password changed successfully" : "Failed to change password");
		return "redirect:/admin/account/index";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return "redirect:/admin/account/login";
	}
	
	@RequestMapping(value = "welcome", method = RequestMethod.GET)
	public String welcome(Authentication authentication, ModelMap modelMap) {
		modelMap.put("email", authentication.getName());
		return "admin/welcome";
	}
	
	@RequestMapping(value = "accessDenied", method = RequestMethod.GET)
	public String accessDenied() {
		return "admin/accessDenied";
	}


}

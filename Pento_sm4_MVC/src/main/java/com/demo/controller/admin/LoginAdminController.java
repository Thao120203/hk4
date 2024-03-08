package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.demo.service.BranchService;
import com.demo.service.MailService;
import com.demo.service.RoleService;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping({ "admin/log/", "admin/log/" })
public class LoginAdminController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MailService mailService;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private Environment environment;

	// ----------- LOGIN
	@GetMapping({ "login", "login/", "", "/" })
	public String login(ModelMap modelMap) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		return "admin/login";
	}

	@PostMapping({ "login" })
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, RedirectAttributes redirectAttributes, ModelMap modelMap) {

		Account account = accountService.findByEmail(email);
		try {
			if (email == null || password == null) {
				redirectAttributes.addFlashAttribute("msg", "Email Password not null");
				return "redirect:/admin/login";
			}

			if (!account.getStatus().equals("1")) {
				redirectAttributes.addFlashAttribute("msg", "Email not Active");
				return "redirect:/admin/login";
			}

			if (accountService.login(email, password) == null) {
				redirectAttributes.addFlashAttribute("msg", "Login Failed");
				return "redirect:/admin/login";	
			}

			if(!encoder.matches(password, account.getPassword())) {
				redirectAttributes.addFlashAttribute("msg", "Password Not Match");
				return "redirect:/admin/login";
			}
			
			session.setAttribute("email", email);
			modelMap.put("accounts", accountService.findAll());
			return "admin/account/index";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Login Failed");
			return "redirect:/admin/login";
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
		return "redirect:/admin/login";
	}

	@PostMapping({ "updatePassword" })
	public String updatePassword(@ModelAttribute("account") Account account,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {

		if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", "Password cannot be empty");
			return "redirect:/admin/updatePassword/" + account.getId();
		}

		if (!accountService.findPassword(account.getId()).equals(currentPassword)) {
			redirectAttributes.addFlashAttribute("msg", "Current password is incorrect");
			return "redirect:/admin/updatePassword/" + account.getId();
		}

		if (!confirmPassword.equals(newPassword)) {
			redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
			return "redirect:/admin/updatePassword/" + account.getId();
		}

		account.setPassword(newPassword);
		redirectAttributes.addFlashAttribute("msg",
				accountService.save(account) ? "Password changed successfully" : "Failed to change password");
		return "redirect:/admin/account/index";
	}

	
	@RequestMapping(value = "forgetPassword", method = RequestMethod.GET)
	public String forgetpassword() {
		return "admin/forgetpassword";
	}

	@RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
	public String forgetpassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		Account account = accountService.findByEmail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("msg", "Email not found");
			return "redirect:/admin/forgetPassword";
		} else {
			if (account != null) {
				String contentForSa= "Account <i style='color: red'>" + account.getEmail() + "</i> is forget the password";
				mailService.send(account.getEmail(), environment.getProperty("spring.mail.username"), "Admin Foget Password",contentForSa);
				
				String contentForAdmin = "You have sent email Forget Password to <i class='color:red'>sa</i>, Please wait for minute to get the password form <i class='color:red'>sa</i>";
				mailService.send(environment.getProperty("spring.mail.username"),account.getEmail(), "FogetPass",contentForAdmin);
			}
			return "redirect:/admin/login";
		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return "redirect:/admin/login";
	}

}

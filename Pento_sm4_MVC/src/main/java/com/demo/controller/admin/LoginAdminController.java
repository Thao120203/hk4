package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import com.demo.entities.User;
import com.demo.helpers.SecurityCodeHelper;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.MailService;
import com.demo.service.RoleService;
import com.demo.service.UserService;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping({ "log/", "log/" })
public class LoginAdminController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;
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
	public String login(ModelMap modelMap, @RequestParam(value = "error" , required =  false ) String error, @RequestParam(value = "logout" , required =  false ) String logout) {
		Account account = new Account();
		modelMap.put("account", account);
		modelMap.put("roles", roleService.findAll());
		if(error != null) {
			modelMap.put("msg", 1);
		}
		
		if(logout != null) {
			modelMap.put("msg", 0);
		}
		return "admin/login";
	}

	@PostMapping({ "login" })
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, RedirectAttributes redirectAttributes, ModelMap modelMap,BindingResult bindingResult) {

		Account account = accountService.findByEmail(email);
		try {
			if (email == null || password == null) {
				redirectAttributes.addFlashAttribute("msg", "Email Password not null");
				return "redirect:/log/login";
			}

			if (!account.getStatus().equals("1")) {
				redirectAttributes.addFlashAttribute("msg", "Email not Active");
				return "redirect:/log/login";
			}

			if (accountService.login(email, password) == null) {
				redirectAttributes.addFlashAttribute("msg", "Login Failed");
				return "redirect:/log/login";
			}

			if (!encoder.matches(password, account.getPassword())) {
				redirectAttributes.addFlashAttribute("msg", "Password Not Match");
				return "redirect:/log/login";
			}

			session.setAttribute("email", email);
			modelMap.put("accounts", accountService.findAll());
			return "admin/account/index";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Login Failed");
			return "redirect:/log/login";
		}
	}

	// ------ Register // For User
	@GetMapping({ "register" })
	public String register(ModelMap modelMap) {
		Account account = new Account();
		User user = new User();
		modelMap.put("user", user);
		modelMap.put("account", account);
		return "admin/register";
	}

	@PostMapping({ "register" })
	public String register(@ModelAttribute("account") Account account,@ModelAttribute("user") User user, RedirectAttributes redirectAttributes,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
		try {
			account.setStatus("1");
			account.setRole(new Role(3, "ROLE_MEMBER"));

			// Genereate Code
			String securityCode = SecurityCodeHelper.generate();
			account.setSecurityCode(securityCode);

			if (confirmPassword.matches(newPassword)) {
				account.setPassword(encoder.encode(newPassword));

				if (!confirmPassword.equals(newPassword)) {
					redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
					return "redirect:/log/updatePassword/" + account.getId();
				}

				if (accountService.save(account)) {
					user.setAccount(account);
					userService.save(user);
					// Generate Content
					String content = "Click here to Active Account";
					content += "<br> <a href='http://localhost:8085/log/verify?code=" + securityCode + "&email="
							+ account.getEmail() + "'>Click Me</a>";

					// Send Mail
					mailService.send(environment.getProperty("spring.mail.username"), account.getEmail(), "Verify",
							content);

					redirectAttributes.addFlashAttribute("msg", 3);
					return "redirect:/log/login";
				} else {
					redirectAttributes.addFlashAttribute("msg", "Register Failed");
				}
			} else {
				redirectAttributes.addFlashAttribute("msg", 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/log/register";
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
			return "admin/updatePassword";
		}
		return "redirect:/log/login";
	}

	@PostMapping({ "updatePassword" })
	public String updatePassword(@ModelAttribute("account") Account account,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
			RedirectAttributes redirectAttributes) {

		if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", "Password cannot be empty");
			return "redirect:/log/updatePassword/" + account.getId();
		}

		if (!confirmPassword.equals(newPassword)) {
			redirectAttributes.addFlashAttribute("msg", "Confirm password does not match new password");
			return "redirect:/log/updatePassword/" + account.getId();
		}

		account.setPassword(encoder.encode(newPassword));
		redirectAttributes.addFlashAttribute("msg",
				accountService.save(account) ? "Password changed successfully" : "Failed to change password");
		return "redirect:/log/login";
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
			return "redirect:/log/forgetPassword";
		} else {
			if (account.getRole().toString().equals("ROLE_ADMIN")) {
				if (account != null) {
					String contentForSa = "Account <i style='color: red'>" + account.getEmail()
							+ "</i> is forget the password";
					mailService.send(account.getEmail(), environment.getProperty("spring.mail.username"),
							"Admin Foget Password", contentForSa);

					String contentForAdmin = "You have sent email Forget Password to <i class='color:red'>sa</i>, Please wait for minute to get the password form <i class='color:red'>sa</i>";
					mailService.send(environment.getProperty("spring.mail.username"), account.getEmail(), "FogetPass",
							contentForAdmin);
				}
				return "redirect:/log/login";
			} else {
				String content = "Nhan vao <a href='http://localhost:8085/log/updatePassword/" + account.getId()
						+ "'>day</a> de cap nhat password";
				mailService.send(environment.getProperty("spring.mail.username"), account.getEmail(), "Update Password",
						content);

			}
			return "redirect:/log/login";

		}
	}

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
		return "redirect:/log/login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		return "redirect:/log/login";
	}

}

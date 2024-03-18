package com.demo.controller.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Branchs;
import com.demo.entities.CategoryFood;
import com.demo.entities.Menu;
import com.demo.entities.User;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.CategoryFoodService;
import com.demo.service.MenuService;
import com.demo.service.RoleService;
import com.demo.service.UserService;

@Controller
@RequestMapping({ "account", "account/" })
public class AccountController {

	@Autowired
	private BranchService branchService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@GetMapping({ "edit" })
	public String edit(ModelMap modelMap, Authentication authentication) {
		Account account = accountService.findByEmail(authentication.getName());
		modelMap.put("account", accountService.find(account.getId()));
		modelMap.put("user", userService.findAccoutId(account.getId()));
		modelMap.put("branchs", branchService.findAll());
		return "user/account/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes,
			@ModelAttribute("user") User user, @RequestParam("id_user") int id_user, Authentication authentication) {
		try {
			Account account2 = accountService.findByEmail(authentication.getName());
			user.setId(id_user);
			if (accountService.save(account) && userService.save(user)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/home/index";
	}

	// ----------- LOGIN

}

package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.User;
import com.demo.service.AccountService;
import com.demo.service.UserService;

@Controller
@RequestMapping({ "admin/user", "admin/user/" })
public class UserAdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("users", userService.findAll());
		return "admin/user/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		User User = new User();
		modelMap.put("user", User);
		return "admin/user/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		try {
			if (userService.save(user)) {
				redirectAttributes.addFlashAttribute("msg", "Add Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Add Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/user/index";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(userService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/user/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap, Authentication authentication) {
		modelMap.put("user", userService.find(id));	
		modelMap.put("accounts", accountService.findByRoleAdminMember());
		modelMap.put("sa", accountService.findByEmail(authentication.getName()).getId());
		return "admin/user/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		try {
			
			if (userService.save(user)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/user/index";
	}
	
}

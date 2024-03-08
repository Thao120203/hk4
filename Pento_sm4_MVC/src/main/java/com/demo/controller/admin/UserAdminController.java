package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.User;
import com.demo.service.UserService;

@Controller
@RequestMapping({ "admin/user", "admin/user/" })
public class UserAdminController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("users", userService.findAll());
		return "user/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		User User = new User();
		modelMap.put("user", User);
		return "User/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("User") User User, RedirectAttributes redirectAttributes) {
		
		return "redirect:/user/index";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(userService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/user/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("user", userService.find(id));	
		return "User/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("user") User User, RedirectAttributes redirectAttributes) {
		
		return "redirect:user/index";
	}
	
}

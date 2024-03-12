package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.demo.entities.Branchs;
import com.demo.service.AccountService;
import com.demo.service.BranchService;

@Controller
@RequestMapping({ "admin/branch", "admin/branch/"})
public class BranchsController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap, Authentication authentication) {
		
		String email = accountService.findByEmail(authentication.getName()).getRole().getName();
		
		if(email.equals("ROLE_SUPER_ADMIN")){
			modelMap.put("branchs", branchService.findAll());					
		} else {
			modelMap.put("branchs", branchService.findBranchNamesByEmail(authentication.getName().toString()));
		}
		
		return "admin/branch/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Branchs Branch = new Branchs();
		modelMap.put("branch", Branch);
		return "admin/branch/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("branch") Branchs branch, RedirectAttributes redirectAttributes) {
		
		if(branchService.save(branch)) {
			return "redirect:/admin/branch/index";
		}
		return "redirect:/admin/branch/add";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(branchService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Success");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/branch/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("branch", branchService.find(id));	
		return "admin/branch/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("branch") Branchs branch, RedirectAttributes redirectAttributes) {
		if(branchService.save(branch)) {
			return "redirect:/branch/index";
		}
		return "redirect:/admin/branch/edit";
	}
	
}

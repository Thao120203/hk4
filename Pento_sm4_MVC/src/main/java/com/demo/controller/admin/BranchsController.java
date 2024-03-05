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


import com.demo.entities.Branchs;
import com.demo.service.BranchService;

@Controller
@RequestMapping({ "branch", "branch/"})
public class BranchsController {
	
	@Autowired
	private BranchService BranchService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("branchs", BranchService.findAll());
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
		
		if(BranchService.save(branch)) {
			return "redirect:/branch/index";
		}
		return "redirect:/branch/add";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(BranchService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Success");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/branch/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("branch", BranchService.find(id));	
		return "admin/branch/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("branch") Branchs branch, RedirectAttributes redirectAttributes) {
		if(BranchService.save(branch)) {
			return "redirect:/branch/index";
		}
		return "redirect:/branch/edit";
	}
	
}

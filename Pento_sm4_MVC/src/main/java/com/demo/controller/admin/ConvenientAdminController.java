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
import com.demo.entities.Convenient;
import com.demo.service.BranchService;
import com.demo.service.ConvenientService;

@Controller
@RequestMapping({ "admin/convenient", "admin/convenient/"})
public class ConvenientAdminController {

	@Autowired
	private BranchService branchService;
	@Autowired
	private ConvenientService convenientService;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("convenients", convenientService.findAll());

		return "admin/convenient/index";
	}

	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Convenient convenient = new Convenient();
		modelMap.put("branchs", branchService.findAll());
		modelMap.put("convenient", convenient);
		return "admin/convenient/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("convenient") Convenient convenient, @ModelAttribute("branch") Branchs branch,
			RedirectAttributes redirectAttributes) {
		if(convenientService.save(convenient)) {
			return "redirect:/convenient/index";
		}
		return "redirect:/convenient/add";
	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if (convenientService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Success");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/convenient/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("convenient", convenientService.find(id));
		modelMap.put("branch", branchService.findAll());
		return "admin/convenient/edit";
	}

	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("convenient") Convenient convenient, RedirectAttributes redirectAttributes) {
		if (convenientService.save(convenient)) {
			return "redirect:/convenient/index";
		}
		return "redirect:/convenient/edit";
	}

}

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

import com.demo.entities.Tables;
import com.demo.service.BranchService;
import com.demo.service.TablesService;

@Controller
@RequestMapping({ "table", "table/"})
public class TablesController {
	
	@Autowired
	private TablesService TablesService;
	@Autowired
	private BranchService branchService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("tables", TablesService.findAll());
		return "admin/table/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Tables Tables = new Tables();
		modelMap.put("branch", branchService.findAll());
		modelMap.put("table", Tables);
		return "admin/table/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("table") Tables table, RedirectAttributes redirectAttributes) {
		if(TablesService.save(table)) {
			return "redirect:/table/index";
		}
		return "redirect:/table/add";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(TablesService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/table/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("table", TablesService.find(id));	
		modelMap.put("branch", branchService.findAll());

		return "admin/table/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("table") Tables table, RedirectAttributes redirectAttributes) {
		if(TablesService.save(table)) {
			return "redirect:/table/index";
		}
		return "redirect:/table/edit";
	}
	
}

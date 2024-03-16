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

import com.demo.entities.Tables;
import com.demo.service.BranchService;
import com.demo.service.TablesService;

@Controller
@RequestMapping({ "admin/table", "admin/table/"})
public class TablesAdminController {
	
	@Autowired
	private TablesService tablesService;
	@Autowired
	private BranchService branchService;
	
	@GetMapping({ "index" })
	public String index(ModelMap modelMap) {
		modelMap.put("tables", tablesService.findAll());
		return "admin/table/index";
	}
	
	@GetMapping({ "find/{id}",})
	public String findTable(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("branchName", branchService.find(id).getName());
		modelMap.put("branchId", branchService.find(id).getId());
		modelMap.put("tables", tablesService.findTableNamesByBranchId(id));
		
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
	
	// ADD
		@GetMapping({ "add/{id}" })
		public String addForAdmin(ModelMap modelMap, @PathVariable("id") int id) {
			Tables Tables = new Tables();
			modelMap.put("branch", branchService.find(id));
			modelMap.put("table", Tables);
			return "admin/table/add";
		}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("table") Tables table, RedirectAttributes redirectAttributes) {
		table.setStatus("0");
		if(tablesService.save(table)) {
			return "redirect:/admin/table/index";
		}
		return "redirect:/admin/table/add";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(tablesService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/table/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("table", tablesService.find(id));	
		modelMap.put("branch", branchService.findAll());

		return "admin/table/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("table") Tables table, RedirectAttributes redirectAttributes) {
		String statusValue = table.getStatus();
		table.setStatus(statusValue != null && statusValue.equals("on") ? "1" : "0");
		
		if(tablesService.save(table)) {
			return "redirect:/admin/table/index";
		}
		return "redirect:/admin/table/edit";
	}
	
}

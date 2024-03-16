package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.demo.entities.Promotions;
import com.demo.service.BranchService;
import com.demo.service.PromotionsService;

@Controller
@RequestMapping({ "admin/promotions", "admin/promotions/"})
public class PromotionsAminController {
	
	@Autowired
	private PromotionsService promotionsService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("promotions", promotionsService.findAll());
		return "admin/promotion/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
			Promotions promotion = new Promotions();
		modelMap.put("promotions", promotion);
		return "admin/promotion/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("promotions") Promotions promotion, RedirectAttributes redirectAttributes) {
		try {
			
			if(promotionsService.save(promotion)) {
				return "redirect:/promotions/index";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return "redirect:/promotions/add";
		
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(promotionsService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Success");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/promotions/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("promotion", promotionsService.find(id));	
		return "admin/promotion/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("promotions") Promotions promotion, RedirectAttributes redirectAttributes) {
		if(promotionsService.save(promotion)) {
			return "redirect:/promotions/index";
		}
		return "redirect:/promotions/edit";
	}
	
}

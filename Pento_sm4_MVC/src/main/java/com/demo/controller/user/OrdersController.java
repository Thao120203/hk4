package com.demo.controller.user;

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

import com.demo.entities.Orders;
import com.demo.service.OrdersService;

@Controller
@RequestMapping({ "orders", "oders/"})
public class OrdersController {
	
	@Autowired
	private OrdersService OrdersService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("Orderss", OrdersService.findAll());
		return "oders/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Orders Orders = new Orders();
		modelMap.put("Orders", Orders);
		return "oders/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("Orders") Orders Orders, RedirectAttributes redirectAttributes) {
		
		return "redirect:/oders/index";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(OrdersService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/oders/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("Orders", OrdersService.find(id));	
		return "oders/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("Orders") Orders Orders, RedirectAttributes redirectAttributes) {
		
		return "redirect:oders/index";
	}
	
}

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

import com.demo.entities.OrderDetail;
import com.demo.service.OrderDetailService;

@Controller
@RequestMapping({ "orderDetail", "orderDetail/"})
public class OrderDetailController {
	
	@Autowired
	private OrderDetailService OrderDetailService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("OrderDetails", OrderDetailService.findAll());
		return "OrderDetail/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		OrderDetail OrderDetail = new OrderDetail();
		modelMap.put("OrderDetail", OrderDetail);
		return "OrderDetail/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("OrderDetail") OrderDetail OrderDetail, RedirectAttributes redirectAttributes) {
		
		return "redirect:/OrderDetail/index";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(OrderDetailService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/OrderDetail/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("OrderDetail", OrderDetailService.find(id));	
		return "OrderDetail/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("OrderDetail") OrderDetail OrderDetail, RedirectAttributes redirectAttributes) {
		
		return "redirect:OrderDetail/index";
	}
	
}

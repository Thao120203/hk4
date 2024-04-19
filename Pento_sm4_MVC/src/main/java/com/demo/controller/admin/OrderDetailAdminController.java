package com.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Menu;
import com.demo.entities.OrderDetail;

import com.demo.service.MenuService;
import com.demo.service.OrderDetailService;
import com.demo.service.OrdersService;
import com.demo.service.PromotionsService;

@Controller
@RequestMapping({ "admin/orderDetail", "admin/orderDetail/"})
public class OrderDetailAdminController {
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private PromotionsService promotionService;
	@GetMapping({ "index/{id}"})
	public String index(ModelMap modelMap,@PathVariable("id") int idOrder) {
		modelMap.put("orderDetails", orderDetailService.findByOrderId(idOrder));
		modelMap.put("idOrder", idOrder);
		
		
		return "admin/orderDetail/index";
	}
	
	// ADD
	@GetMapping({ "add/{id}" })
	public String add(ModelMap modelMap,@PathVariable("id") int idOrder) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrders(ordersService.find(idOrder));
		modelMap.put("orderDetail", orderDetail);
		
		modelMap.put("menus", menuService.findAll());	
		return "admin/orderDetail/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("orderDetail") OrderDetail orderDetail , RedirectAttributes redirectAttributes) {
		
		int id = orderDetail.getOrders().getId();
		Menu menu = menuService.find(orderDetail.getMenu().getId());
		orderDetail.setPrice(orderDetail.getQuantity() * menu.getPrice());
		if(orderDetailService.save(orderDetail)) {
			redirectAttributes.addFlashAttribute("msg", "Add Success");
			return "redirect:admin//orderDetail/index/"+ id;
		}
		redirectAttributes.addFlashAttribute("msg", "Add Failed");
		return "redirect:/admin/orderDetail/index/"+ id;
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(orderDetailService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/order/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{idOrder}/{id}"})
	public String edit(@PathVariable("idOrder") int idOrder,@PathVariable("id") int id, ModelMap modelMap) {	
 		OrderDetail detail = orderDetailService.find(id);
		detail.setOrders(ordersService.find(idOrder));
		Menu menu = menuService.find(detail.getMenu().getId());
		detail.setPrice(detail.getQuantity() * menu.getPrice());
		modelMap.put("orderDetail", detail);
		
		modelMap.put("menus", menuService.findAll());	
		return "admin/orderDetail/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("orderDetail") OrderDetail orderDetail, RedirectAttributes redirectAttributes) {
		int id = orderDetail.getOrders().getId();
		if(orderDetailService.save(orderDetail)) {
			redirectAttributes.addFlashAttribute("msg", "Edit Success");
			return "redirect:/admin/orderDetail/index/"+id;
		}
		redirectAttributes.addFlashAttribute("msg", "Edit Failed");
		return "redirect:/admin/orderDetail/index/"+id;
	}
	
}

package com.demo.controller.user;



import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Hours;
import com.demo.entities.Orders;
import com.demo.entities.Tables;
import com.demo.entities.Times;
import com.demo.service.BranchService;
import com.demo.service.HoursService;
import com.demo.service.MenuService;
import com.demo.service.OrdersService;
import com.demo.service.TimesService;


@Controller
@RequestMapping({ "user/orders" })
public class UserOrdersController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private TimesService timesService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private HoursService hoursService;
	
	
	@GetMapping({ "add/{id}" })
	public String add(ModelMap modelMap , @PathVariable("id") int id) {
		Orders order = new Orders();
		modelMap.put("hours", hoursService.findAll());
		
		modelMap.put("menus",menuService.findBybranch_Menu(id));
		
		modelMap.put("order", order);
		return "user/orders/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("order") Orders Orders,@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("time") int time, RedirectAttributes redirectAttributes) {
		Orders.setCreated(new Date());
		
		
		Hours hours = new Hours();
		String hourString = String.format("%02d:00", time); 
		hours.setName(hourString);
		hoursService.save(hours);
		
		Times times = new Times();
		
		times.setHours(hours);
		timesService.save(times);
		
		Orders.setTimes(times);
		if(ordersService.save(Orders)) {
			return "redirect:/user/home/index";
		}
		return "redirect:/user/home/index";
	}
	
	
	
	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}
	
}

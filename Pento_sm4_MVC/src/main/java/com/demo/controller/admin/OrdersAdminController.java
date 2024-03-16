package com.demo.controller.admin;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Branchs;
import com.demo.entities.CategoryFood;
import com.demo.entities.Days;
import com.demo.entities.Hours;
import com.demo.entities.Months;
import com.demo.entities.OrderDetail;
import com.demo.entities.Orders;
import com.demo.entities.Tables;
import com.demo.entities.Times;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.DaysService;
import com.demo.service.HoursService;
import com.demo.service.MonthsService;
import com.demo.service.OrdersService;
import com.demo.service.TablesService;
import com.demo.service.TimesService;
import com.demo.service.UserService;

@Controller
@RequestMapping({ "admin/order", "admin/order/",""})
public class OrdersAdminController {
	
	@Autowired
	private OrdersService OrdersService;
	@Autowired
	private TablesService tablesService;
	@Autowired
	private DaysService daysService;
	@Autowired
	private MonthsService monthsService;
	@Autowired
	private HoursService hoursService;
	@Autowired
	private TimesService timesService;
	@Autowired
	private UserService userservice;
	@Autowired
	private AccountService accountservice;
	@Autowired
	private BranchService branchService;

	
	@GetMapping({ "index" })
	public String index(ModelMap modelMap ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountservice.findByEmail(authentication.getName());
		List<Branchs> branchs = branchService.findBranchNamesByAccountId(account.getId());
		List<Tables> listTable =new ArrayList<>();
		for ( Branchs branch: branchs) {
			List<Tables> tables = tablesService.findTableNamesByBranchId(branch.getId());
			if(tables!= null) {
				listTable.addAll(tables);
			}
		}
		List<Orders> listorder = new ArrayList<>();
		List<Tables> newlist = new ArrayList<>();
		for ( Tables table: listTable) {
			List<Orders> orders = OrdersService.findByTableId(table.getId());
			for (Orders order : orders) {
				Tables newTable =  tablesService.find(order.getTables().getId());
				if(newTable!= null) {
					newlist.add(newTable);
				}
			}
			System.out.print(table.getBranchs().getName()+" ");
			if(orders!= null) {
				listorder.addAll(orders);
			}
		}
		
		modelMap.put("tables", newlist);
		modelMap.put("orders", listorder);
		List<Orders> listOrder = (List<Orders>) OrdersService.findAll();
		List<Times> listTimes = new ArrayList<>();
		for ( Orders order : listOrder) {
			Times time = timesService.find(order.getTimes().getId());
			if(time!= null) {
				listTimes.add(time);
			}
		}
		modelMap.put("times", listTimes);
		return "admin/order/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Orders Orders = new Orders();
		modelMap.put("order", Orders);
		modelMap.put("users", userservice.findAll()) ;
		modelMap.put("tables", tablesService.findAll());
		return "admin/order/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("order") Orders Orders,@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("time") int time, RedirectAttributes redirectAttributes) {
		Orders.setCreated(new Date());
		
		Days days = new Days();
		String dateString = String.valueOf(date.getDayOfMonth()); 
		days.setName(dateString);
		daysService.save(days);
		
		Months months = new Months();
		String monthString = String.valueOf(date.getMonthValue()); 
		months.setName(monthString);
		monthsService.save(months);
		
		Hours hours = new Hours();
		String hourString = String.format("%02d:00", time); 
		hours.setName(hourString);
		hoursService.save(hours);
		
		Times times = new Times();
		times.setDays(days);
		times.setMonths(months);
		times.setHours(hours);
		timesService.save(times);
		
		Orders.setTimes(times);
		Orders.setStatus("unpaid");
		if(OrdersService.save(Orders)) {
			return "redirect:/order/index";
		}
		
		return "redirect:/order/index";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(OrdersService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/order/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("order", OrdersService.find(id));	
		modelMap.put("users", userservice.findAll()) ;
		modelMap.put("tables", tablesService.findAll());
		Times time = timesService.find(OrdersService.find(id).getTimes().getId());
		modelMap.put("booked", time);
		return "admin/order/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("order") Orders Orders, RedirectAttributes redirectAttributes,@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("time") int time) {
		Orders.setCreated(new Date());
		
		Days days = new Days();
		String dateString = String.valueOf(date.getDayOfMonth()); 
		days.setName(dateString);
		daysService.save(days);
		
		Months months = new Months();
		String monthString = String.valueOf(date.getMonthValue()); 
		months.setName(monthString);
		monthsService.save(months);
		
		Hours hours = new Hours();
		String hourString = String.format("%02d:00", time); 
		hours.setName(hourString);
		hoursService.save(hours);
		
		Times times = new Times();
		times.setDays(days);
		times.setMonths(months);
		times.setHours(hours);
		timesService.save(times);
		
		Orders.setTimes(times);
		if(OrdersService.save(Orders)) {
			return "redirect:/order/index";
		}
		return "redirect:/order/edit";
	}
	 
}

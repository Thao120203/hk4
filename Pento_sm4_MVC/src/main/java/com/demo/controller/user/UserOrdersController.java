package com.demo.controller.user;



import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Days;
import com.demo.entities.Hours;
import com.demo.entities.Months;
import com.demo.entities.Orders;
import com.demo.entities.Tables;
import com.demo.entities.Times;
import com.demo.entities.User;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.DaysService;
import com.demo.service.HoursService;
import com.demo.service.MenuService;
import com.demo.service.MonthsService;
import com.demo.service.OrdersService;
import com.demo.service.TablesService;
import com.demo.service.TimesService;
import com.demo.service.UserService;


@Controller
@RequestMapping({ "orders" })
public class UserOrdersController {
	
	@Autowired
	private MenuService menuService;
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
	
	
	@GetMapping({ "add/{id}" })
	public String add(ModelMap modelMap , @PathVariable("id") int id) {
		Orders order = new Orders();
		modelMap.put("hours", hoursService.findAll());
		
		modelMap.put("menus",menuService.findBybranch_Menu(id));
		
		modelMap.put("order", order);
		return "user/orders/add";
	}

	@PostMapping({ "addoder" })
	String addoder(@ModelAttribute("order") Orders Orders,@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("time") int time, RedirectAttributes redirectAttributes, Authentication authentication) {
		Account account = accountservice.findByEmail(authentication.getName());
		User user = userservice.findAccoutId(account.getId());
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
		Orders.setUser(user);
		Orders.setTimes(times);
		Orders.setStatus("unpaid");
		if(OrdersService.save(Orders)) {
			return "redirect:/home/index";
		}
		
		return "redirect:/home/index";
	}
	
	
	
	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}
	
}

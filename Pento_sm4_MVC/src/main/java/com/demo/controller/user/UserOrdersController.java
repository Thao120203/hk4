package com.demo.controller.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.tags.form.RadioButtonsTag;

import com.demo.entities.Account;
import com.demo.entities.Branchs;
import com.demo.entities.Days;
import com.demo.entities.Hours;
import com.demo.entities.Menu;
import com.demo.entities.Months;
import com.demo.entities.OrderDetail;
import com.demo.entities.Orders;
import com.demo.entities.Tables;
import com.demo.entities.Times;
import com.demo.entities.Transaction;
import com.demo.entities.User;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.DaysService;
import com.demo.service.HoursService;
import com.demo.service.MenuService;
import com.demo.service.MonthsService;
import com.demo.service.OrderDetailService;
import com.demo.service.OrdersService;
import com.demo.service.TablesService;
import com.demo.service.TimesService;
import com.demo.service.TransactionService;
import com.demo.service.UserService;

@Controller
@RequestMapping({ "orders" })
public class UserOrdersController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private OrdersService OrdersService;
	@Autowired
	private OrderDetailService orderDetailService;
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
	@Autowired
	private TransactionService transactionService;

	@GetMapping({ "add/{id}" })
	public String add(ModelMap modelMap, @PathVariable("id") int id) {
		Orders order = new Orders();
		modelMap.put("hours", hoursService.findAll());
		Branchs branch = branchService.find(id);
		modelMap.put("menus", menuService.findBybranch_Menu(branch.getAccount().getId()));
		modelMap.put("tables", tablesService.findTableNamesByBranchId(id));
		modelMap.put("order", order);
		return "user/orders/add";
	}
	
	@GetMapping({ "details/{id}" })
	public String details(ModelMap modelMap, @PathVariable("id") int id, Authentication authentication) {
		Account account = accountservice.findByEmail(authentication.getName());
		modelMap.put("orderdetails", orderDetailService.findByIdOrder(id));
		modelMap.put("account", accountservice.find(account.getId()));
		modelMap.put("users", userservice.findAccoutId(account.getId()));
		return "user/orders/details";
	}

	@GetMapping({ "index" })
	public String index(ModelMap modelMap, Authentication authentication) {
		List<Transaction> transactions = new ArrayList<>();
		Account account = accountservice.findByEmail(authentication.getName());
		

		var user = userservice.findAccoutId(account.getId());
		for (var order : OrdersService.findByIdAccount(user.getId())) {
			transactions.add(transactionService.transactionAndOrder(order.getId()));
		}
		modelMap.put("account", accountservice.find(account.getId()));
		modelMap.put("users", userservice.findAccoutId(account.getId()));
		modelMap.put("transactions", transactions);
		return "user/orders/index";
	}

	@PostMapping({ "addoder" })
	String addoder(@ModelAttribute("order") Orders Orders,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			@RequestParam("time") int time, RedirectAttributes redirectAttributes, Authentication authentication,
			@RequestParam("q24_menu[][id]") List<Menu> data, @RequestParam("quantities") int[] quantities,
			@RequestParam("paypal-payment-method") String payment) {
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
			if (data != null) {
				if (OrdersService.save(Orders)) {
					OrderDetail orderdetail_id = new OrderDetail();
					for (Menu menu : data) {
						OrderDetail orderdetail = new OrderDetail();
						for (int quantity : quantities) {

							orderdetail.setQuantity(quantity);
							orderdetail.setPrice(quantity * menu.getPrice());
						}
						orderdetail.setOrders(Orders);
						orderdetail.setMenu(menu);

						orderDetailService.save(orderdetail);
						orderdetail_id = orderdetail;
					}
					if(payment.contains("paypal")){
						return "redirect:/transaction/add";
					}	else {
						Transaction transaction = new Transaction();
						transaction.setCreated(new Date());
						transaction.setOrders(Orders);
						transaction.setPaymentMethod(payment);
						transaction.setTotalAmount(String.valueOf(orderDetailService.sumorderdetail_idorder(Orders.getId())));
						if(transactionService.save(transaction)) {
							return "redirect:/orders/index";
						}
						
					}
					

				}
			} else {
				if (OrdersService.save(Orders)) {
					return "redirect:/home/index";
				}
			}
		

		return "redirect:/home/index";
	}

	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}

}

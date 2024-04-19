package com.demo.controller.user;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.OrderDetail;
import com.demo.entities.Orders;
import com.demo.entities.Promotions;
import com.demo.entities.Tables;
import com.demo.entities.Transaction;
import com.demo.paypal.PayPalConfig;
import com.demo.paypal.PayPalResult;
import com.demo.paypal.PayPalSucess;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.HoursService;
import com.demo.service.MenuService;
import com.demo.service.OrderDetailService;
import com.demo.service.OrdersService;
import com.demo.service.TransactionService;
import com.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping({ "transaction", "transaction/" })
public class TransactionController {
	@Autowired
	private Environment environment;
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userservice;
	@Autowired
	private OrdersService OrdersService;
	@Autowired
	private OrderDetailService orderDetailService;
	
	@GetMapping({ "add" })
	public String add(ModelMap modelMap, Authentication authentication) {
		Transaction transaction = new Transaction();
		modelMap.put("transaction", transaction);
		
		modelMap.put("posturl", environment.getProperty("paypal.posturl"));
		modelMap.put("returnurl", environment.getProperty("paypal.returnurl"));
		modelMap.put("business", environment.getProperty("paypal.business"));
		Account account = accountService.findByEmail(authentication.getName());

		modelMap.put("account", accountService.find(account.getId()));
		modelMap.put("users", userservice.findAccoutId(account.getId()));
		var user = userservice.findAccoutId(account.getId());
		Orders order = OrdersService.findByIdAccount(user.getId()).get(0);
	
		List<OrderDetail> listdetail = orderDetailService.findByIdOrder(order.getId());
		
		modelMap.put("orderDetails", listdetail);
		return "user/transaction/add";
	}
	@GetMapping({ "success" })
	public String success(@RequestParam("PayerID") String payerID, 
			HttpServletRequest request, Authentication authentication) {
		PayPalConfig payPalConfig = new PayPalConfig();
		payPalConfig.setAuthToken(environment.getProperty("paypal.authtoken"));
		payPalConfig.setBusiness(environment.getProperty("paypal.business"));
		payPalConfig.setPosturl(environment.getProperty("paypal.posturl"));
		payPalConfig.setReturnurl(environment.getProperty("paypal.returnurl"));
		PayPalSucess payPalSucess = new PayPalSucess();
		PayPalResult result = payPalSucess.getPayPal(request, payPalConfig);
		if (result == null) {
			System.out.println("Failed");
		} else {
			Account account = accountService.findByEmail(authentication.getName());
			var user = userservice.findAccoutId(account.getId());
			Orders order = OrdersService.findByIdAccount(user.getId()).get(0);
			order.setStatus("paid");
			List<OrderDetail> listdetail = orderDetailService.findByIdOrder(order.getId());
			double totalPrice = 0.0;
			for (OrderDetail detail : listdetail) {
			    totalPrice += detail.getPrice();
			}
			Transaction transaction = new Transaction();
			transaction.setOrders(order);
			transaction.setPaymentMethod("paypal");
			transaction.setTotalAmount(String.valueOf(totalPrice));
			transaction.setCreated(new Date());
			if(transactionService.save(transaction)) {
				OrdersService.save(order);
			}
		}
		return "redirect:/orders/index";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("transaction") Transaction transaction, RedirectAttributes redirectAttributes,ModelMap modelMap,Authentication authentication) {
		try {
			
			if(transactionService.save(transaction)) {
				return "redirect:/orders/index";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return "redirect:/transaction/add";
		
	}
	
	
	
	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}
	
}

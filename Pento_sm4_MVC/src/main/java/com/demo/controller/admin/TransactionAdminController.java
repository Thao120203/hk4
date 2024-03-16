package com.demo.controller.admin;

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

import com.demo.entities.Tables;
import com.demo.entities.Transaction;
import com.demo.service.BranchService;
import com.demo.service.OrdersService;
import com.demo.service.TablesService;
import com.demo.service.TransactionService;

@Controller
@RequestMapping({ "admin/transaction", "admin/transaction/"})
public class TransactionAdminController {
	
	@Autowired
	private TransactionService transactionService;
	
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("transactions", transactionService.findAll());
		return "admin/transaction/index";
	}
	
}

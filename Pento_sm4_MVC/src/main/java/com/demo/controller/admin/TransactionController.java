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
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private OrdersService ordersService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("transactions", transactionService.findAll());
		return "admin/transaction/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Transaction transaction = new Transaction();
		modelMap.put("order", ordersService.findAll());
		modelMap.put("transaction", transaction);
		return "admin/transaction/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("transaction") Transaction transaction, RedirectAttributes redirectAttributes) {
		if(transactionService.save(transaction)) {
			return "redirect:/transaction/index";
		}
		return "redirect:/transaction/add";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(transactionService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/transaction/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("transaction", transactionService.find(id));	
		modelMap.put("order", ordersService.findAll());

		return "admin/transaction/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("transaction") Transaction transaction, RedirectAttributes redirectAttributes) {
		if(transactionService.save(transaction)) {
			return "redirect:/transaction/index";
		}
		return "redirect:/transaction/edit";
	}
	
}

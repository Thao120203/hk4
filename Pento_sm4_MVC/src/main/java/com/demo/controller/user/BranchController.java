package com.demo.controller.user;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Orders;
import com.demo.entities.Tables;
import com.demo.service.BranchService;
import com.demo.service.HoursService;
import com.demo.service.MenuService;
import com.demo.service.OrdersService;


@Controller
@RequestMapping({ "branch", "branch/" })
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private HoursService hoursService;
	
	
	@GetMapping( "details/{id}")
	public String index(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("orders", new Orders());
		modelMap.put("hours", hoursService.findAll());
		var branch = branchService.find(id);
		modelMap.put("branch",branch);
		modelMap.put("menus_categorys", menuService.findBybranch_Category(branch.getAccount().getId()));
		modelMap.put("menus",menuService.findBybranch_Menu(branch.getAccount().getId()));
		return "user/branch/details";
	}
	
	
	
	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}
	
}

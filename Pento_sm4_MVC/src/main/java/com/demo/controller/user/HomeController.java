package com.demo.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.service.BranchService;
import com.demo.service.CategoryFoodService;

@Controller
@RequestMapping({ "home", "home/", "/", "" })
public class HomeController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private CategoryFoodService categoryService;
	
	
	
	@GetMapping({ "index", "index/", "", "/"})
	public String index(ModelMap modelMap) {
		modelMap.put("branchs", branchService.findAll());
		modelMap.put("categories", categoryService.findAll());

		return "user/home/index";
	}
	
	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}
	
}

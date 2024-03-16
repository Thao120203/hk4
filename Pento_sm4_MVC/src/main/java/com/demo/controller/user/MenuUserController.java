package com.demo.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.service.BranchService;
import com.demo.service.MenuService;


@Controller
@RequestMapping({ "menu", "menu/" })
public class MenuUserController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private MenuService menuService;
	
	
	
	@GetMapping({ "index",})
	public String index(ModelMap modelMap) {
		modelMap.put("menus", menuService.findAll());
		

		return "user/menu/index";
	}
	
	
	
	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}
	
}

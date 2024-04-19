package com.demo.controller.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.demo.entities.Branchs;
import com.demo.entities.CategoryFood;
import com.demo.entities.Menu;
import com.demo.entities.User;
import com.demo.service.BranchService;
import com.demo.service.CategoryFoodService;
import com.demo.service.MenuService;
import com.demo.service.UserService;

@Controller
@RequestMapping({ "category", "category/" })
public class CategoryController {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CategoryFoodService categoryService;
	
	@Autowired
	private BranchService branchService;
	
	
	
	@GetMapping( "index/{id}")
	public String index(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("category", categoryService.find(id));
		
		modelMap.put("branchs", branchService.listBranchbyCategory_Food(id));
		modelMap.put("countbranch", branchService.countListBranchbyCategory_Food(id));
		return "user/category/index";
	}
	
	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}
	
}

package com.demo.controller.user;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.demo.entities.Orders;
import com.demo.entities.Reviews;
import com.demo.entities.Tables;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.HoursService;
import com.demo.service.MenuService;
import com.demo.service.OrdersService;
import com.demo.service.ReviewService;


@Controller
@RequestMapping({ "branch", "branch/" })
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ReviewService reviewService;
	
	
	
	@GetMapping( "details/{id}")
	public String detais(ModelMap modelMap, @PathVariable("id") int id) {
		modelMap.put("orders", new Orders());
		var branch = branchService.find(id);
		modelMap.put("branch",branch);
		modelMap.put("menus_categorys", menuService.findBybranch_Category(branch.getAccount().getId()));
		modelMap.put("menus",menuService.findBybranch_Menu(branch.getAccount().getId()));
		
		modelMap.put("reviews", reviewService.findByIdBranch(id));
		
		return "user/branch/details";
	}
	

	@GetMapping( "index")
	public String index(ModelMap modelMap) {
		modelMap.put("branchs",branchService.findAll());
		return "user/branch/index";
		
	}
	
	@PostMapping( "search")
	public String search(ModelMap modelMap,@RequestParam("keyword") String keyword) {
		modelMap.put("branchs",branchService.findByKeyword(keyword));
		return "user/branch/index";
		
	}
	
	@PostMapping( "comment")
	public String comment(ModelMap modelMap,@RequestParam("rating") String rating, @RequestParam("comment") String comment, @RequestParam("idBybranch") int idBybranch, Authentication authentication) {
		Reviews reviews = new Reviews();
		reviews.setRating(rating);
		reviews.setComment(comment);
		var account = accountService.findByEmail(authentication.getName());
		reviews.setAccount(account);
		var br = branchService.find(idBybranch);
		reviews.setBranchs(br);
		reviews.setCreated(new Date());
		if(reviewService.save(reviews)) {
			System.out.println("oke");
		}
		
		return "redirect:/branch/details/"+idBybranch;
		
	}
	
	@GetMapping({ "accessDenied" })
	public String accessDenied(ModelMap modelMap) {
		return "user/accessDenied";
	}
	
}

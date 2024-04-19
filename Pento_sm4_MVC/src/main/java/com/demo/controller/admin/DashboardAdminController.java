package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Branchs;
import com.demo.entities.Orders;
import com.demo.entities.Role;
import com.demo.entities.Tables;
import com.demo.entities.Times;
import com.demo.helpers.SecurityCodeHelper;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.DaysService;
import com.demo.service.HoursService;
import com.demo.service.MailService;
import com.demo.service.MonthsService;
import com.demo.service.OrdersService;
import com.demo.service.RoleService;
import com.demo.service.TablesService;
import com.demo.service.TimesService;
import com.demo.service.UserService;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping({ "admin", "admin/" })
public class DashboardAdminController {
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

	@Autowired
	private AccountService accountService;

	@GetMapping({ "index", "", "/", "dashboard"})
	public String index(ModelMap modelMap, Authentication authentication) {
		Account account = accountservice.findByEmail(authentication.getName());
		if (account.getId() != 1) {
			List<Branchs> branchs = branchService.findBranchNamesByAccountId(account.getId());
			List<Tables> listTable = new ArrayList<>();
			for (Branchs branch : branchs) {
				List<Tables> tables = tablesService.findTableNamesByBranchId(branch.getId());
				if (tables != null) {
					listTable.addAll(tables);
				}
			}
			List<Orders> listorder = new ArrayList<>();
			List<Tables> newlist = new ArrayList<>();
			for (Tables table : listTable) {
				List<Orders> orders = OrdersService.findByTableId(table.getId());
				for (Orders order : orders) {
					Tables newTable = tablesService.find(order.getTables().getId());
					if (newTable != null) {
						newlist.add(newTable);
					}
				}
				System.out.print(table.getBranchs().getName() + " ");
				if (orders != null) {
					listorder.addAll(orders);
				}
			}

			modelMap.put("tables", newlist);
			modelMap.put("orders", listorder);
			List<Orders> listOrder = (List<Orders>) OrdersService.findAll();
			List<Times> listTimes = new ArrayList<>();
			for (Orders order : listOrder) {
				Times time = timesService.find(order.getTimes().getId());
				if (time != null) {
					listTimes.add(time);
				}
			}
			modelMap.put("times", listTimes);
		} else {
			Iterable<Branchs> branchs = branchService.findAll();
			List<Tables> listTable = new ArrayList<>();
			for (Branchs branch : branchs) {
				List<Tables> tables = tablesService.findTableNamesByBranchId(branch.getId());
				if (tables != null) {
					listTable.addAll(tables);
				}
			}
			List<Orders> listorder = new ArrayList<>();
			List<Tables> newlist = new ArrayList<>();
			for (Tables table : listTable) {
				List<Orders> orders = OrdersService.findByTableId(table.getId());
				for (Orders order : orders) {
					Tables newTable = tablesService.find(order.getTables().getId());
					if (newTable != null) {
						newlist.add(newTable);
					}
				}
				System.out.print(table.getBranchs().getName() + " ");
				if (orders != null) {
					listorder.addAll(orders);
				}
			}

			modelMap.put("tables", newlist);
			modelMap.put("orders", listorder);
			List<Orders> listOrder = (List<Orders>) OrdersService.findAll();
			List<Times> listTimes = new ArrayList<>();
			for (Orders order : listOrder) {
				Times time = timesService.find(order.getTimes().getId());
				if (time != null) {
					listTimes.add(time);
				}
			}
			modelMap.put("times", listTimes);
		}

		return "admin/dashboard/index";
	}

}

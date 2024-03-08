package com.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.demo.entities.Role;
import com.demo.helpers.SecurityCodeHelper;
import com.demo.service.AccountService;
import com.demo.service.BranchService;
import com.demo.service.MailService;
import com.demo.service.RoleService;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping({ "admin", "admin/" })
public class DashboardAdminController {

	@GetMapping({ "index", "", "/", "dashboard"})
	public String index(ModelMap modelMap) {
		return "admin/dashboard/index";
	}

}

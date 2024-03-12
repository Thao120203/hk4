package com.demo.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Contact;
import com.demo.service.AccountService;
import com.demo.service.ContactService;



@Controller
@RequestMapping({ "contact", "contact/" })
public class ContactController {

	@Autowired
	private ContactService contactService;
	@Autowired
	private AccountService accountService;

	
	// -------- ADD 
		@GetMapping({ "index", "add", "", "/"})
		public String add(ModelMap modelMap, Authentication authentication) {
			Account account = accountService.findByEmail(authentication.getName());
			Contact contact = new Contact();
			
			modelMap.put("contactEmail", account.getId());
			modelMap.put("contact", contact);
			return "user/contact/index";
		}

		@PostMapping({ "add" })
		public String add(@ModelAttribute("contact") Contact contact, Authentication authentication, RedirectAttributes redirectAttributes) {
			
			try {
				contact.setStatus("0");
				contact.setAccount(accountService.findByEmail(authentication.getName()));
				if(contactService.save(contact)) {
					return "redirect:/contact/index";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "redirect:/contact/index";
		}
		



}

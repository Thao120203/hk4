package com.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Contact;
import com.demo.service.ContactService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping({ "admin/contact", "admin/contact/" })
public class ContactAdminController {

	@Autowired
	private ContactService contactService;
	
	@GetMapping({ "index", "","/"})
	public String index(ModelMap modelMap) {
		modelMap.put("contacts", contactService.findAll());
		return "admin/contact/index";
	}

	// -------- ADD // for Admin

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id, HttpSession session) {
		if (contactService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", "Delete Sucess");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/contact/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("contact", contactService.find(id));
		return "admin/contact/edit";
	}

	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("contact") Contact contact, RedirectAttributes redirectAttributes) {
		try {
			String statusValue = contact.getStatus();
			contact.setStatus(statusValue != null && statusValue.equals("on") ? "1" : "0");
			if (contactService.save(contact)) {
				redirectAttributes.addFlashAttribute("msg", "Edit Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Edit Failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/contact/index";
	}


}

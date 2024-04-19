package com.demo.controller.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Account;
import com.demo.entities.Branchs;
import com.demo.helpers.FileHelper;
import com.demo.service.AccountService;
import com.demo.service.BranchService;

@Controller
@RequestMapping({ "admin/branch", "admin/branch/" })
public class BranchsAdminController {

	@Autowired
	private BranchService branchService;

	@Autowired
	private AccountService accountService;

	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap, Authentication authentication) {

		String email = accountService.findByEmail(authentication.getName()).getRole().getName();

		if (email.equals("ROLE_SUPER_ADMIN")) {
			modelMap.put("branchs", branchService.findAll());
		} else {
			modelMap.put("branchs", branchService
					.findBranchNamesByAccountId(accountService.findByEmail(authentication.getName()).getId()));
		}

		return "admin/branch/index";
	}

	@GetMapping({ "view/{id}" })
	public String viewForSA(ModelMap modelMap, Authentication authentication, @PathVariable("id") int id) {
		modelMap.put("accountName", accountService.find(id).getEmail());
		modelMap.put("branchs", branchService.findBranchNamesByAccountId(id));

		return "admin/branch/index";
	}

	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Branchs Branch = new Branchs();
		modelMap.put("branch", Branch);
		modelMap.put("accounts", accountService.findByRoleAdmin());
		return "admin/branch/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("branch") Branchs branch, RedirectAttributes redirectAttributes,
			BindingResult bindingResult, @RequestParam("file") MultipartFile file, Authentication authentication) {
		Account account = accountService.findByEmail(authentication.getName());
		branch.setAccount(account);
		String statusValue = branch.getStatus();
		branch.setStatus(statusValue != null && statusValue.equals("on") ? "1" : "0");
		try {
			if (file != null && !file.isEmpty()) {
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				File imagesFolder = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(imagesFolder.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				branch.setImage(fileName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (branchService.save(branch)) {
			redirectAttributes.addFlashAttribute("msg", 1);
		} else {
			redirectAttributes.addFlashAttribute("msg", 2);
			return "redirect:/admin/branch/add";
		}
		return "redirect:/admin/branch/index";

	}

	// DELETE
	@GetMapping({ "delete/{id}" })
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if (branchService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", 4);
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/branch/index";
	}

	// EDIT Information
	@GetMapping({ "edit/{id}" })
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("branch", branchService.find(id));
		return "admin/branch/edit";
	}

	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("branch") Branchs branch, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountService.findByEmail(authentication.getName());
		branch.setAccount(account);
		String statusValue = branch.getStatus();
		branch.setStatus(statusValue != null && statusValue.equals("on") ? "1" : "0");
		try {
			if (file != null && !file.isEmpty()) {

				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				File imagesFolder = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(imagesFolder.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				branch.setImage(fileName);
			} else {
				var branchs = branchService.find(branch.getId());
				branch.setImage(branchs.getImage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (branchService.save(branch)) {
			redirectAttributes.addFlashAttribute("msg", 3);

			return "redirect:/admin/branch/index";
		}
		return "redirect:/admin/branch/edit";
	}

}

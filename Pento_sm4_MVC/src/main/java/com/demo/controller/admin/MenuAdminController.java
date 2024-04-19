package com.demo.controller.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.demo.entities.Images;
import com.demo.entities.Menu;
import com.demo.helpers.FileHelper;
import com.demo.service.AccountService;
import com.demo.service.CategoryFoodService;
import com.demo.service.ImagesService;
import com.demo.service.MenuService;

@Controller
@RequestMapping({ "admin/menu", "admin/menu/"})
public class MenuAdminController {
	
	@Autowired
	private CategoryFoodService categoryService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private ImagesService imageService;
	@Autowired
	private AccountService accountservice;
	
	@GetMapping({ "index"})
	public String index(ModelMap modelMap, Authentication authentication) {
		Account account = accountservice.findByEmail(authentication.getName());
		
		try {
	        Path folderPath = Paths.get("static/images");
	        if (!Files.exists(folderPath)) {
	            Files.createDirectories(folderPath);
	        }
	        
	        if(account.getId() != 1) {
				modelMap.put("menus", menuService.findBybranch_Menu(account.getId()));
			} else {
				modelMap.put("menus", menuService.findAll());
			}

	        // Thực hiện các hoạt động khác nếu cần
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Xử lý ngoại lệ nếu có
	    }
		return "admin/menu/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Menu menu = new Menu();
		modelMap.put("menu", menu);
		modelMap.put("categories", categoryService.findAll());
		return "admin/menu/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("menu") Menu menu, RedirectAttributes redirectAttributes,Authentication authentication,@RequestParam("file") MultipartFile file) {
		
		 try {
		        Account account = accountservice.findByEmail(authentication.getName());

		        if (file != null && !file.isEmpty()) {
		            Images images = new Images();
		            String fileName = FileHelper.generateFileName(file.getOriginalFilename());
		            Path imagePath = Paths.get("static/images/" + fileName);
		            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
		            images.setName(fileName);
		            imageService.save(images); // Lưu đối tượng Images trước
		            menu.setImages(images);
		        } else {
		            Images defaultImage = new Images("noimage.png");
		            imageService.save(defaultImage); // Lưu đối tượng Images mặc định trước
		            menu.setImages(defaultImage);
		        }

		        menu.setAccount(account);
		        
		        if (menuService.save(menu)) {
		            redirectAttributes.addFlashAttribute("msg", 1);
		        } else {
		            redirectAttributes.addFlashAttribute("msg", "Failed");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        redirectAttributes.addFlashAttribute("msg", e.getMessage());
		    }
		    return "redirect:/admin/menu/index";
	}
	
	// DELETE
	@GetMapping({"delete/{id}"})
	public String delete(RedirectAttributes redirectAtributes, @PathVariable("id") int id) {
		if(menuService.delete(id)) {
			redirectAtributes.addFlashAttribute("msg", 4);
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/menu/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id,@ModelAttribute("menu") Menu menu, ModelMap modelMap) {
		modelMap.put("menu", menuService.find(id));	
		modelMap.put("categories", categoryService.findAll());
		modelMap.put("accounts", accountService.findAll());
	
		return "admin/menu/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("menu") Menu menu,RedirectAttributes redirectAttributes,Authentication authentication,@RequestParam("file") MultipartFile file) {
		 try {
		        Account account = accountservice.findByEmail(authentication.getName());

		        if (file != null && !file.isEmpty()) {
		        	imageService.delete(menu.getImages().getId());
		            Images images = new Images();
		            String fileName = FileHelper.generateFileName(file.getOriginalFilename());
		            Path imagePath = Paths.get("static/images/" + fileName);
		            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
		            images.setName(fileName);
		            
		            imageService.save(images); // Lưu đối tượng Images trước
		            menu.setImages(images);
		        } else {
		        	var menus = menuService.find(menu.getId());
					menu.setImages(menus.getImages());
		        	 if (menu.getImages() == null) {
		                 Images defaultImage = new Images("noimage.png");
		                 imageService.save(defaultImage); // Lưu đối tượng Images mặc định
		                 menu.setImages(defaultImage);
		             }
		        }
		        menu.setAccount(account);
		        
		        if (menuService.save(menu)) {
		            redirectAttributes.addFlashAttribute("msg", 3);
		        } else {
		            redirectAttributes.addFlashAttribute("msg", "Failed");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        redirectAttributes.addFlashAttribute("msg", e.getMessage());
		    }
		    return "redirect:/admin/menu/index";
		
	}
}

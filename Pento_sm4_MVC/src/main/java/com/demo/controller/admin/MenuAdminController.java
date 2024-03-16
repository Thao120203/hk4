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

import com.demo.entities.Images;
import com.demo.entities.Menu;
import com.demo.helpers.FileHelper;
import com.demo.service.CategoryFoodService;
import com.demo.service.ImagesService;
import com.demo.service.MenuService;

@Controller
@RequestMapping({ "admin/menu", "admin/menu/"})
public class MenuAdminController {
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private CategoryFoodService categoryFoodService;
	
	
	@Autowired
	private ImagesService imageService;
	
	@GetMapping({ "index", "", "/" })
	public String index(ModelMap modelMap) {
		modelMap.put("menus", menuService.findAll());
		return "admin/menu/index";
	}
	
	// ADD
	@GetMapping({ "add" })
	public String add(ModelMap modelMap) {
		Menu menu = new Menu();
		modelMap.put("categories", categoryFoodService.findAll());
		modelMap.put("menu", menu);
		return "admin/menu/add";
	}

	@PostMapping({ "add" })
	public String add(@ModelAttribute("menu") Menu menu, RedirectAttributes redirectAttributes,@RequestParam("file") MultipartFile file) {
		
		try {
			if (file != null && file.getSize() > 0) {
				File folderImage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderImage.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				menu.setImage(fileName);
			} else {
				menu.setImage("no-image.jpg");
			}
			if (menuService.save(menu)) {
				redirectAttributes.addFlashAttribute("msg", "Success");
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
			redirectAtributes.addFlashAttribute("msg", "Delete Success");
		} else {
			redirectAtributes.addFlashAttribute("msg", "Delete Failed");
		}
		return "redirect:/admin/menu/index";
	}
	
	// EDIT Information
	@GetMapping({"edit/{id}"})
	public String edit(@PathVariable("id") int id,@ModelAttribute("menu") Menu menu, ModelMap modelMap) {
		modelMap.put("menu", menuService.find(id));	
	
		return "admin/menu/edit";
	}
	
	@PostMapping({ "edit" })
	public String edit(@ModelAttribute("menu") Menu menu,RedirectAttributes redirectAttributes,@RequestParam("file") MultipartFile file) {
		try {
			Images images = new Images();
			if (file != null && file.getSize() > 0) {
				File folderImage = new File(new ClassPathResource(".").getFile().getPath() + "/static/images");
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderImage.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				images.setName(fileName);
				menu.setImages(images);
			} else {
				images.setName("no-image.jpg");
			}
			if (menuService.save(menu)) {
				redirectAttributes.addFlashAttribute("msg", "Success");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/menu/edit";
	}
	
}

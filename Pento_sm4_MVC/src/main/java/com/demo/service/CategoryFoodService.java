package com.demo.service;

import com.demo.entities.CategoryFood;
import com.demo.entities.Contact;

public interface CategoryFoodService {

	public Iterable<CategoryFood> findAll();
	public CategoryFood find(int id);
	
	// CRUD
	public boolean save(CategoryFood categoryFood);
	public boolean edit(int id);
	public boolean delete(int id);
	
	public CategoryFood saveid(CategoryFood category);
	
}

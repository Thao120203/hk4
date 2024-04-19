package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.CategoryFood;
import com.demo.repositories.CategoryFoodRepository;




@Service
public class CategoryFoodServiceImpl implements CategoryFoodService{
	@Autowired
	private CategoryFoodRepository categoryRepository;
	
	@Override
	public Iterable<CategoryFood> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public CategoryFood find(int id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public boolean save(CategoryFood contactFood) {
		try {
			categoryRepository.save(contactFood);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			categoryRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public CategoryFood saveid(CategoryFood category) {
		try {
			
			return categoryRepository.save(category);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CategoryFood> listAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAllNew();
	}

	

	
}

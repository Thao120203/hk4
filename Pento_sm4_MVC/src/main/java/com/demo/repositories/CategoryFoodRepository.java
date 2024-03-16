package com.demo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.CategoryFood;
import com.demo.entities.Contact;


public interface CategoryFoodRepository extends CrudRepository<CategoryFood, Integer>{
	
	@Query("from CategoryFood order by id desc")
	public List<CategoryFood> findAllNew();
	
}

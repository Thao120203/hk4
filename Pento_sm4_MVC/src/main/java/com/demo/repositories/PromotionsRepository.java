package com.demo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Branchs;
import com.demo.entities.Promotions;


public interface PromotionsRepository extends CrudRepository<Promotions, Integer>{
	
	@Query("from Promotions order by id desc")
	public List<Promotions> findAllNew();
	
}

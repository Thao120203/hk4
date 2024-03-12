package com.demo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Branchs;
import com.demo.entities.Convenient;


public interface ConvenientRepository extends CrudRepository<Convenient, Integer>{
	
	@Query("from Convenient order by id desc")
	public List<Branchs> findAllNew();
	
}

package com.demo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Branchs;


public interface BranchRepository extends CrudRepository<Branchs, Integer>{
	
	@Query("from Branchs order by id desc")
	public List<Branchs> findAllNew();
	
}

package com.demo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Branchs;


public interface BranchRepository extends CrudRepository<Branchs, Integer>{
	
	@Query("from Branchs where status != '999' order by id desc")
	public List<Branchs> findAllNew();
	
	@Query("SELECT b FROM Account a INNER JOIN Branchs b ON a.branchs.id = b.id WHERE a.email = :email")
	public List<Branchs> findBranchNamesByEmail(@Param("email") String email);
	
}

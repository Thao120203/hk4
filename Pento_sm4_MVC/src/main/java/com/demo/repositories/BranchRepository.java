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
	
	@Query("from Branchs where account.id = :id")
	public List<Branchs> findBranchNamesByAccountId(@Param("id") int id);
	
	@Query("from Branchs where account.id = :id_account")
	public List<Branchs> findByidaccount(@Param("id_account") int id_account);
	
	
	@Query("from Branchs where address like %:address")
	public List<Branchs> findByAddress(@Param("address") String address);
	
	@Query("from Branchs where name like %:keyword%")
	public List<Branchs> findByKeyword(@Param("keyword") String keyword);
}

package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Tables;

public interface TablesRepository extends CrudRepository<Tables, Integer>{
	
	@Query("from Tables order by id desc")
	public List<Tables> findAllNew();
	
	@Query("FROM Tables a WHERE a.branchs.id = :id")
	public List<Tables> findTableNamesByBranchId(@Param("id") int id);
	
}

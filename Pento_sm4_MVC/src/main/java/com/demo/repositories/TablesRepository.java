package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Tables;

public interface TablesRepository extends CrudRepository<Tables, Integer>{
	
	@Query("from Tables order by id desc")
	public List<Tables> findAllNew();
	
}

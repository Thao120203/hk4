package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Menu;

public interface MenuRepository extends CrudRepository<Menu, Integer>{
	
	@Query("from Menu order by id desc")
	public List<Menu> findAllNew();
	
}

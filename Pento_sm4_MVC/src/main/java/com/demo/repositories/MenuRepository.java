package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Menu;

public interface MenuRepository extends CrudRepository<Menu, Integer>{
	
	@Query("from Menu order by id desc")
	public List<Menu> findAllNew();
	
	@Query("from Menu where categoryFood.id = :id_category order by account.id")
	public List<Menu> findByKeyword(@Param("id_category") int id_category);
	@Query("from Menu where account.id = :id_account order by categoryFood.id")
	public List<Menu> findBybranch_Category(@Param("id_account") int id_account);
}

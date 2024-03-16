package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Times;

public interface TimesRepository extends CrudRepository<Times, Integer>{
	
	@Query("from Times order by id desc")
	public List<Times> findAllNew();
	
}

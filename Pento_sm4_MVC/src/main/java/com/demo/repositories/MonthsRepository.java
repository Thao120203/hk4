package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Months;

public interface MonthsRepository extends CrudRepository<Months, Integer>{
	
	@Query("from Months order by id desc")
	public List<Months> findAllNew();
	
}

package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Days;

public interface DaysRepository extends CrudRepository<Days, Integer>{
	
	@Query("from Days order by id desc")
	public List<Days> findAllNew();
	
}

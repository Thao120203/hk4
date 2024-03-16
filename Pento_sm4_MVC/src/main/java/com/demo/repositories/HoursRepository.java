package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Hours;

public interface HoursRepository extends CrudRepository<Hours, Integer>{
	
	@Query("from Hours order by id desc")
	public List<Hours> findAllNew();
	
}

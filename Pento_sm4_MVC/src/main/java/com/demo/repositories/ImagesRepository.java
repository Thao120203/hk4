package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Images;

public interface ImagesRepository extends CrudRepository<Images, Integer>{
	
	@Query("from Images order by id desc")
	public List<Images> findAllNew();
	
}

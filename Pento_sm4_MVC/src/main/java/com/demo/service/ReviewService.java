package com.demo.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.demo.entities.Images;
import com.demo.entities.Reviews; 

public interface ReviewService {

	public Iterable<Reviews> findAll();
	public List<Reviews> findByIdBranch(int id_branch);
	
	// CRUD
	public boolean save(Reviews reviews);

	
	
}

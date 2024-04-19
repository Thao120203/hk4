package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Images;
import com.demo.entities.Reviews;
import com.demo.entities.Times;

public interface ReviewRepository extends CrudRepository<Reviews, Integer>{
	
	@Query("from Reviews where branchs.id = :id_branch order by id desc")
	public List<Reviews> findByIdBranch(@Param("id_branch")int id_branch);
	
}

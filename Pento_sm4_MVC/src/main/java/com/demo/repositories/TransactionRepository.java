package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>{
	
	@Query("from Transaction order by id desc")
	public List<Transaction> findAllNew();
	
}

package com.demo.service;

import com.demo.entities.Transaction;

public interface TransactionService {

	public Iterable<Transaction> findAll();
	public Transaction find(int id);
	
	// CRUD
	public boolean save(Transaction transaction);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

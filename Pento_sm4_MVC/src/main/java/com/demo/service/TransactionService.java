package com.demo.service;

import java.util.List;

import com.demo.entities.Transaction;

public interface TransactionService {

	public Iterable<Transaction> findAll();
	public Transaction find(int id);
	
	// CRUD
	public boolean save(Transaction transaction);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
	public Transaction transactionAndOrder(int id_order);
	
	
}

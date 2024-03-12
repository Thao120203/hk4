package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Tables;
import com.demo.entities.Transaction;
import com.demo.repositories.TablesRepository;
import com.demo.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public Iterable<Transaction> findAll() {
		return transactionRepository.findAll();
	}
	
	@Override
	public Transaction find(int id) {
		return transactionRepository.findById(id).get();
	}


	@Override
	public boolean save(Transaction transaction) {
		try {
			transactionRepository.save(transaction);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			transactionRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}

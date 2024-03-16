package com.demo.service;

import com.demo.entities.Times;

public interface TimesService {

	public Iterable<Times> findAll();
	public Times find(int id);
	
	// CRUD
	public boolean save(Times Times);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

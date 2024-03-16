package com.demo.service;

import com.demo.entities.Months;

public interface MonthsService {

	public Iterable<Months> findAll();
	public Months find(int id);
	
	// CRUD
	public boolean save(Months Months);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

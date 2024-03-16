package com.demo.service;

import com.demo.entities.Days;

public interface DaysService {

	public Iterable<Days> findAll();
	public Days find(int id);
	
	// CRUD
	public boolean save(Days Days);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

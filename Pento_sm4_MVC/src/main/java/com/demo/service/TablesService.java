package com.demo.service;

import com.demo.entities.Tables;

public interface TablesService {

	public Iterable<Tables> findAll();
	public Tables find(int id);
	
	// CRUD
	public boolean save(Tables Tables);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

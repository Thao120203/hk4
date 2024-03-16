package com.demo.service;

import com.demo.entities.Hours;
import com.demo.entities.Tables;

public interface HoursService {

	public Iterable<Hours> findAll();
	public Hours find(int id);
	
	// CRUD
	public boolean save(Hours Hours);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

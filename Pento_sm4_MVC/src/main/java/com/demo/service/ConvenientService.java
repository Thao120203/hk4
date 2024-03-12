package com.demo.service;

import com.demo.entities.Convenient;

public interface ConvenientService {

	public Iterable<Convenient> findAll();
	public Convenient find(int id);
	
	// CRUD
	public boolean save(Convenient Branchs);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

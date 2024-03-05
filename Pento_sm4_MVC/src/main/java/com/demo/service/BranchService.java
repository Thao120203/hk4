package com.demo.service;

import com.demo.entities.Branchs;

public interface BranchService {

	public Iterable<Branchs> findAll();
	public Branchs find(int id);
	
	// CRUD
	public boolean save(Branchs Branchs);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

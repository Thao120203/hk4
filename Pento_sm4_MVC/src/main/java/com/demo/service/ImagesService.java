package com.demo.service;

import com.demo.entities.Images; 

public interface ImagesService {

	public Iterable<Images> findAll();
	public Images find(int id);
	
	// CRUD
	public boolean save(Images Images);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

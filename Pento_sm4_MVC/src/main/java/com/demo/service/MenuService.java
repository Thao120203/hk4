package com.demo.service;

import com.demo.entities.Menu; 

public interface MenuService {

	public Iterable<Menu> findAll();
	public Menu find(int id);
	
	// CRUD
	public boolean save(Menu Menu);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

package com.demo.service;

import java.util.List;

import com.demo.entities.Menu; 

public interface MenuService {

	public Iterable<Menu> findAll();
	public Menu find(int id);
	
	// CRUD
	public boolean save(Menu Menu);
	public boolean edit(int id);
	public boolean delete(int id);
	public List<Menu> findBybranch_Menu(int id_account);
	public List<Menu> findByKeyword(int id_category);
	public List<Menu> findBybranch_Category(int id_account);
}

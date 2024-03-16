package com.demo.service;

import java.util.List;

import com.demo.entities.Branchs;

public interface BranchService {

	public Iterable<Branchs> findAll();
	public Branchs find(int id);
	
	public List<Branchs> findBranchNamesByAccountId(int id);
	
	// CRUD
	public boolean save(Branchs Branchs);
	public boolean edit(int id);
	public boolean delete(int id);
	public Branchs saveid(Branchs Branchs);
	public List<Branchs> findbyidaccount(int id_account);
	
	public List<Branchs> listBranchbyCategory_Food(int id_category);
	public Integer countListBranchbyCategory_Food(int id_category);

	
}

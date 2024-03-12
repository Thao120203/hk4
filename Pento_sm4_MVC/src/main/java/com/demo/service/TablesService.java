package com.demo.service;

import com.demo.entities.Tables;
import java.util.List;


public interface TablesService {

	public Iterable<Tables> findAll();
	public Tables find(int id);
	public List<Tables> findTableNamesByBranchId(int id);
	
	// CRUD
	public boolean save(Tables Tables);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

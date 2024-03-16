package com.demo.service;

import java.util.List;
import com.demo.entities.Orders;

public interface OrdersService {

	public Iterable<Orders> findAll();
	public Orders find(int id);
	
	// CRUD
	public boolean save(Orders Orders);
	public boolean edit(int id);
	public boolean delete(int id);	
	public List<Orders> findByTableId(int id);
	
	
}

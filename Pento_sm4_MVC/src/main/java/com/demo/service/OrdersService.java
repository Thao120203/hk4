package com.demo.service;

import com.demo.entities.Orders;

public interface OrdersService {

	public Iterable<Orders> findAll();
	public Orders find(int id);
	
	// CRUD
	public boolean save(Orders Orders);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

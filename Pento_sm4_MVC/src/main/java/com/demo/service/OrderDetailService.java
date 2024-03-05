package com.demo.service;

import com.demo.entities.OrderDetail;

public interface OrderDetailService {

	public Iterable<OrderDetail> findAll();
	public OrderDetail find(int id);
	
	// CRUD
	public boolean save(OrderDetail OrderDetail);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

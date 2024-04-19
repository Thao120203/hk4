package com.demo.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.demo.entities.OrderDetail;

public interface OrderDetailService {

	public Iterable<OrderDetail> findAll();
	public OrderDetail find(int id);
	
	// CRUD
	public boolean save(OrderDetail OrderDetail);
	public boolean edit(int id);
	public boolean delete(int id);
	public double sumorderdetail_idorder(int id_order);
	public List<OrderDetail> findByIdOrder(int id_order);
	
}

package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Orders;

public interface OrdersRepository extends CrudRepository<Orders, Integer>{
	
	@Query("from Orders order by id desc")
	public List<Orders> findAllNew();
	
}
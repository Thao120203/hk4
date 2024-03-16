package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer>{
	
	@Query("from OrderDetail order by id desc")
	public List<OrderDetail> findAllNew();
	
	@Query("from OrderDetail where orders.id = :id")
	public List<OrderDetail> findByOrderId(@Param("id") int id);
}

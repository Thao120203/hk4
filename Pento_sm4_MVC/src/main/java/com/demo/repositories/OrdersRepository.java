package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;
import com.demo.entities.Orders;

public interface OrdersRepository extends CrudRepository<Orders, Integer>{
	
	@Query("from Orders order by id desc")
	public List<Orders> findAllNew();
	
	@Query("from Orders where tables.id = :id")
	public List<Orders> findByTableId(@Param("id") int id );

//	@Query("select distinct new Account(o.id, u.name, t.number, b.name) from Order o " +
//	        "join o.user u " +
//	        "join o.table t " +
//	        "join t.branch b " +
//	        "order by o.id desc")
//	public List<Account> findAllOrdersWithUserTableAndBranch();

}


package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.demo.entities.Orders;
import com.demo.repositories.OrdersRepository;

@Service
public class OrdersServiceImpl implements OrdersService{
	@Autowired
	private OrdersRepository OrdersRepository;
	
	@Override
	public Iterable<Orders> findAll() {
		return OrdersRepository.findAll();
	}

	@Override
	public Orders find(int id) {
		return OrdersRepository.findById(id).get();
	}

	@Override
	public boolean save(Orders Orders) {
		try {
			OrdersRepository.save(Orders);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			OrdersRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Async
	public List<Orders> findByIdAccount(int id_user) {
		// TODO Auto-generated method stub
		return OrdersRepository.findByIdAccount(id_user);
	}
	
	@Override
	public List<Orders> findByTableId(int id) {
		return OrdersRepository.findByTableId(id);
	}



}

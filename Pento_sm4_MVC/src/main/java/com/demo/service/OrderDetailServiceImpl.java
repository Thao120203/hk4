package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.OrderDetail;
import com.demo.repositories.OrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	@Autowired
	private OrderDetailRepository OrderDetailRepository;
	
	@Override
	public Iterable<OrderDetail> findAll() {
		return OrderDetailRepository.findAll();
	}

	@Override
	public OrderDetail find(int id) {
		return OrderDetailRepository.findById(id).get();
	}

	@Override
	public boolean save(OrderDetail OrderDetail) {
		try {
			OrderDetailRepository.save(OrderDetail);
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
			OrderDetailRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}

package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.demo.entities.Promotions;

import com.demo.repositories.PromotionsRepository;




@Service
public class PromotionsServiceImpl implements PromotionsService{
	@Autowired
	private PromotionsRepository promotionsRepository;
	
	@Override
	public Iterable<Promotions> findAll() {
		return promotionsRepository.findAll();
	}

	@Override
	public Promotions find(int id) {
		return promotionsRepository.findById(id).get();
	}

	@Override
	public boolean save(Promotions promotions) {
		try {
			promotionsRepository.save(promotions);
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
			promotionsRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}

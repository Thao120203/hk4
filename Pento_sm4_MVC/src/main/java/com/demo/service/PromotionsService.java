package com.demo.service;

import com.demo.entities.Promotions;

public interface PromotionsService {

	public Iterable<Promotions> findAll();
	public Promotions find(int id);
	
	// CRUD
	public boolean save(Promotions promotion);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}

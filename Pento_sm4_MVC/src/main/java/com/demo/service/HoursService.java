package com.demo.service;

import com.demo.entities.Hours;
import com.demo.entities.Tables;

public interface HoursService {

	public Iterable<Hours> findAll();
	
	public boolean save(Hours Hours);

}

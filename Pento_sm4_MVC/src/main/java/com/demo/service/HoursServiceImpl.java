package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Hours;
import com.demo.entities.Tables;
import com.demo.repositories.HoursRepository;
import com.demo.repositories.TablesRepository;

@Service
public class HoursServiceImpl implements HoursService{
	@Autowired
	private HoursRepository hoursRepository;
	
	@Override
	public Iterable<Hours> findAll() {
		return hoursRepository.findAll();
	}

	@Override
	public boolean save(Hours Hours) {
		try {
			hoursRepository.save(Hours);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

}

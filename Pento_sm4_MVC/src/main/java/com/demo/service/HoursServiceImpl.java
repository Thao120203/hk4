package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Hours;
import com.demo.repositories.HoursRepository;

@Service
public class HoursServiceImpl implements HoursService{
	@Autowired
	private HoursRepository hoursRepository;
	
	@Override
	public Iterable<Hours> findAll() {
		return hoursRepository.findAll();
	}

	@Override
	public Hours find(int id) {
		return hoursRepository.findById(id).get();
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

	@Override
	public boolean edit(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			hoursRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}

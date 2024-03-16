package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Days;
import com.demo.repositories.DaysRepository;

@Service
public class DaysServiceImpl implements DaysService{
	@Autowired
	private DaysRepository DaysRepository;
	
	@Override
	public Iterable<Days> findAll() {
		return DaysRepository.findAll();
	}

	@Override
	public Days find(int id) {
		return DaysRepository.findById(id).get();
	}

	@Override
	public boolean save(Days Days) {
		try {
			DaysRepository.save(Days);
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
			DaysRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}

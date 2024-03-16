package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Times;
import com.demo.repositories.TimesRepository;

@Service
public class TimesServiceImpl implements TimesService{
	@Autowired
	private TimesRepository TimesRepository;
	
	@Override
	public Iterable<Times> findAll() {
		return TimesRepository.findAll();
	}

	@Override
	public Times find(int id) {
		return TimesRepository.findById(id).get();
	}

	@Override
	public boolean save(Times Times) {
		try {
			TimesRepository.save(Times);
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
			TimesRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}

package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Months;
import com.demo.repositories.MonthsRepository;

@Service
public class MonthsServiceImpl implements MonthsService{
	@Autowired
	private MonthsRepository MonthsRepository;
	
	@Override
	public Iterable<Months> findAll() {
		return MonthsRepository.findAll();
	}

	@Override
	public Months find(int id) {
		return MonthsRepository.findById(id).get();
	}

	@Override
	public boolean save(Months Months) {
		try {
			MonthsRepository.save(Months);
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
			MonthsRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}

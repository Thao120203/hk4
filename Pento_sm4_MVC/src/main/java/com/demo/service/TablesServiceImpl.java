package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Tables;
import com.demo.repositories.TablesRepository;

@Service
public class TablesServiceImpl implements TablesService{
	@Autowired
	private TablesRepository TablesRepository;
	
	@Override
	public Iterable<Tables> findAll() {
		return TablesRepository.findAll();
	}

	@Override
	public Tables find(int id) {
		return TablesRepository.findById(id).get();
	}

	@Override
	public boolean save(Tables Tables) {
		try {
			TablesRepository.save(Tables);
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
			TablesRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}

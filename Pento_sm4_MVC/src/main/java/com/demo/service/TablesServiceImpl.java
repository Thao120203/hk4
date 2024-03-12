package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Tables;
import com.demo.repositories.TablesRepository;

@Service
public class TablesServiceImpl implements TablesService{
	@Autowired
	private TablesRepository tablesRepository;
	
	@Override
	public Iterable<Tables> findAll() {
		return tablesRepository.findAll();
	}

	@Override
	public Tables find(int id) {
		return tablesRepository.findById(id).get();
	}

	@Override
	public boolean save(Tables Tables) {
		try {
			tablesRepository.save(Tables);
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
			tablesRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Tables> findTableNamesByBranchId(int id) {
		return tablesRepository.findTableNamesByBranchId(id);
	}



}

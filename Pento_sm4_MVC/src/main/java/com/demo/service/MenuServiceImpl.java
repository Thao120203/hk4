package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Menu;
import com.demo.repositories.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService{
	@Autowired
	private MenuRepository MenuRepository;
	
	@Override
	public Iterable<Menu> findAll() {
		return MenuRepository.findAll();
	}

	@Override
	public Menu find(int id) {
		return MenuRepository.findById(id).get();
	}

	@Override
	public boolean save(Menu Menu) {
		try {
			MenuRepository.save(Menu);
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
			MenuRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}

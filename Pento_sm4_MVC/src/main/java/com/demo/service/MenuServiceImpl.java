package com.demo.service;

import java.util.ArrayList;
import java.util.List;

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
	@Override
	public List<Menu> findBybranch_Menu(int id_account) {
		return MenuRepository.findBybranch_Category(id_account);
	}
	
	@Override
	public List<Menu> findByKeyword(int id_category) {
		return MenuRepository.findByKeyword(id_category);
	}
	
	@Override
	public List<Menu> findBybranch_Category(int id_account) {
		var a= 0;
		List<Menu> menus = new ArrayList<>();
		
		for (Menu menu : MenuRepository.findBybranch_Category(id_account)) {
			if(a != menu.getCategoryFood().getId()) {
				menus.add(menu);
				a = menu.getCategoryFood().getId();
			}
		}
		return menus;
	}


}

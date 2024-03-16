package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Branchs;
import com.demo.entities.Menu;
import com.demo.entities.Branchs;
import com.demo.repositories.BranchRepository;




@Service
public class BranchServiceImpl implements BranchService{
	@Autowired
	private BranchRepository branchsRepository;
	@Autowired
	private MenuService menuService;
	
	
	@Override
	public Iterable<Branchs> findAll() {
		return branchsRepository.findAllNew();
	}

	@Override
	public Branchs find(int id) {
		return branchsRepository.findById(id).get();
	}

	@Override
	public boolean save(Branchs Branchs) {
		try {
			branchsRepository.save(Branchs);
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
			branchsRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Branchs saveid(Branchs Branchs) {
		try {
			
			return branchsRepository.save(Branchs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Branchs> findBranchNamesByAccountId(int id) {
		return branchsRepository.findBranchNamesByAccountId(id);
	}
	@Override
	public List<Branchs> findbyidaccount(int id_account) {
		
		return branchsRepository.findByidaccount(id_account);
	}

	@Override
	public List<Branchs> listBranchbyCategory_Food(int id_category) {
		List<Menu> menus = menuService.findByKeyword(id_category);
		List<Branchs> branchs = new ArrayList();
		var a = 0;
		for (Menu menu : menus) {
			if(a != menu.getAccount().getId()) {
				List<Branchs> brs = branchsRepository.findByidaccount(menu.getAccount().getId());
				for (Branchs branch : brs) {
					branchs.add(branch);			
				}
				a = 0+ menu.getAccount().getId();
			}
			
		}
		return branchs;
	}
	@Override
	public Integer countListBranchbyCategory_Food(int id_category) {
		List<Menu> menus = menuService.findByKeyword(id_category);
		List<Branchs> branchs = new ArrayList<>();
		var count =0;
		var a = 0;
		for (Menu menu : menus) {
			if(a != menu.getAccount().getId()) {
				List<Branchs> brs = branchsRepository.findByidaccount(menu.getAccount().getId());
				for (Branchs branch : brs) {
					branchs.add(branch);
					count++;
				}
				a = 0+ menu.getAccount().getId();
			}
			
		}
		return count;
	}
}

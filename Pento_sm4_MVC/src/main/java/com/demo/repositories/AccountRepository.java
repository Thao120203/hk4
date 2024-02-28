package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.entities.Account;

public interface AccountRepository extends CrudRepository<Account, Integer>{
	
	@Query("from Account where email = :email and password = :password and status = :status")
	public Account login(
			@Param("email") String email,
			@Param("password") String password, 
			@Param("status") int status);

	@Query("from Account order by id desc")
	public List<Account> findAllNew();

	@Query("from Account where email like %:email%")
	public Account findByEmail(@Param("email") String email);
	
	@Query("select password from Account where id =:id")
	public String findPassword(@Param("id") int id);
	
}

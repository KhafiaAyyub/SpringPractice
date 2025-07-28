package com.example.ExpenseProject.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ExpenseProject.Entities.UserInfo;

@Repository
public interface UserRepository  extends CrudRepository<UserInfo, Integer>{
	
	public UserInfo findbyUsername(String username);
}

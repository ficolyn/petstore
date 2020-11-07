package com.upemor.petsorerest.service;

import java.util.List;

import com.upemor.petsorerest.model.User;

public interface UserService {
	
	User findUserForLogin(String email, String password);
	
	List<User> listAllUsers();
	
	User findById(int id);
	
	boolean createUser(User user);
	
	User updateUser(int id, User user);
	
	void deleteUser(int id);
	
	boolean existsUserByEmail(String email);

}

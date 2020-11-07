package com.upemor.petsorerest.component;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upemor.petsorerest.model.User;
import com.upemor.petsorerest.service.UserService;

@Component
public class AdminCreator {
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void adminCreate() {
		if(!userService.existsUserByEmail("admin@localhost.com")) {
			User user = new User("admin","admin","admin","admin@localhost.com","admin",true,"ADMIN", null);
			userService.createUser(user);
		}
	}

}
package com.upemor.petsorerest.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upemor.petsorerest.exception.UserErrorException;
import com.upemor.petsorerest.model.User;
import com.upemor.petsorerest.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.listAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") final int id){
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<User>(
			new UserErrorException("User with id "
			+ id + " not found"), HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody final User user) {
		if(!userService.createUser(user)) {
			return new ResponseEntity<User>(new UserErrorException(
					"Unable to create new user. A User with name "
					+ user.getEmail() + " already exist."),HttpStatus.CONFLICT);
		}
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable("id") final int id, @RequestBody User user) {
		User currentuser = userService.findById(id);
		if (currentuser == null) {
			return new ResponseEntity<User>(
					new UserErrorException("Unable to upate. User with id "
					+ id + " not found."), HttpStatus.NOT_FOUND);
		}
		User updatedUser = userService.updateUser(id, user);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") final int id) {
		User currentuser = userService.findById(id);
		if (currentuser == null) {
			return new ResponseEntity<User>(
					new UserErrorException("Unable to delete User with id "
					+ id + " not found."), HttpStatus.NOT_FOUND);
		}
		userService.deleteUser(id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

}
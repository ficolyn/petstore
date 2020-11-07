package com.upemor.petsorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.upemor.petsorerest.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmailAndPassword(String email, String password);

	User findById(int id);

	User findByEmail(String email);

	boolean existsUserByEmail(String email);

	public User findByUsername(String username);
}

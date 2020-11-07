package com.upemor.petsorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upemor.petsorerest.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Category findById(int id);
}

package com.upemor.petsorerest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upemor.petsorerest.model.Category;
import com.upemor.petsorerest.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repository;

	@Override
	public List<Category> listAllCategories() {
		return repository.findAll();
	}

	@Override
	public Category findById(int id) {
		return repository.findById(id);
	}

	@Override
	public boolean createCategory(Category category) {
		Category categorySaved = repository.save(category);
		
		if (categorySaved == null)
			return false;
		
		return true;
	}

	@Override
	public Category updateCategory(int id, Category category) {
		Category categoryBD = repository.findById(id);
		
		if (categoryBD == null)
			return null;
		
		else {
			categoryBD.setName(category.getName());
			categoryBD.setPets(category.getPets());
			
			repository.saveAndFlush(categoryBD);
			
			return categoryBD;
		}
	}

	@Override
	public void deleteCategory(int id) {
		Category categoryBD = repository.findById(id);
		
		if (categoryBD != null)
			repository.delete(categoryBD);
	}

}

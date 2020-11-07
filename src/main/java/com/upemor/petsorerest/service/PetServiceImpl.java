package com.upemor.petsorerest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upemor.petsorerest.model.Category;
import com.upemor.petsorerest.model.Pet;
import com.upemor.petsorerest.model.Tag;
import com.upemor.petsorerest.repository.CategoryRepository;
import com.upemor.petsorerest.repository.PetRepository;
import com.upemor.petsorerest.repository.TagRepository;

@Service
public class PetServiceImpl implements PetService {
	@Autowired
	private PetRepository repository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private TagRepository tagRepository;

	@Override
	public List<Pet> listAllPets() {
		return repository.findAll();
	}

	@Override
	public Pet findById(int id) {
		return repository.findById(id);
	}

	@Override
	public boolean createPet(Pet pet) {
		
		if (pet.getCategory() != null) 
			pet.setCategory(setCategory(pet.getCategory().getId()));
		
		if (pet.getTag() != null) 
			pet.setTag(setTag(pet.getTag().getId()));
		
		Pet petSaved = repository.save(pet);
		
		if (petSaved == null)
			return false;
		
		return true;
	}

	@Override
	public Pet updatePet(int id, Pet petUpdated) {
		Pet pet = repository.findById(id);
		
		if (pet == null) 
			return null;
		else {
			pet.setName(petUpdated.getName());
			pet.setOrders(petUpdated.getOrders());
			pet.setPhotourl(petUpdated.getPhotourl());
			pet.setPrice(petUpdated.getPrice());
			pet.setStatus(petUpdated.isStatus());
			
			if (petUpdated.getCategory() != null) 
				pet.setCategory(setCategory(petUpdated.getCategory().getId()));
			
			if (petUpdated.getTag() != null) 
				pet.setTag(setTag(petUpdated.getTag().getId()));
			
			repository.saveAndFlush(pet);
			
			return pet;
		}
	}

	@Override
	public void deletePet(int id) {
		Pet pet = repository.findById(id);
		
		if (pet != null) 
			repository.delete(pet);
	}
	
	private Tag setTag(int id) {
		Tag tag = tagRepository.findById(id);
		
		if (tag == null) 
			return null;
		
		return tag;
	}
	
	private Category setCategory(int id) {
		Category category = categoryRepository.findById(id);
		
		if (category == null) 
			return null;
		
		return category;
	}

}

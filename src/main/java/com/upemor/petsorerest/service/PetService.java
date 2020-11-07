package com.upemor.petsorerest.service;

import java.util.List;

import com.upemor.petsorerest.model.Pet;

public interface PetService {
	List<Pet> listAllPets();

	Pet findById(int id);

	boolean createPet(Pet pet);

	Pet updatePet(int id, Pet pet);

	void deletePet(int id);
}

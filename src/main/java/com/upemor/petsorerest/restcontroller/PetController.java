package com.upemor.petsorerest.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.upemor.petsorerest.model.Pet;
import com.upemor.petsorerest.service.PetService;


@RestController
@RequestMapping("/api/pet")
public class PetController {
	
	@Autowired
	private PetService service;
	
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> listAllPets(){
		List<Pet> pets = service.listAllPets();
		
		Map<String, Object> response = new HashMap<>();
		
		if (pets.isEmpty()) {
			response.put("ok", false);
			response.put("message", "No se encontraron registros en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("ok", true);
		response.put("pets", pets);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findTag(@PathVariable("id") final int id) {
		Map<String, Object> response = new HashMap<>();
		
		Pet pet = service.findById(id);
		
		if (pet != null) {
			response.put("ok", true);
			response.put("pet", pet);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		} else {
			response.put("ok", false);
			response.put("message", "el pet con el id:" + id + " no se encuentra");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> createPet(@RequestBody final Pet pet) {
		Map<String, Object> response = new HashMap<>();
		
		if(service.createPet(pet)) {
			response.put("ok", true);
			response.put("pet", pet);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		} else {
			response.put("ok", false);
			response.put("message", "no se pudo crear el Pet en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updatePet(@PathVariable("id") final int id, @RequestBody Pet pet) {
		Pet currentPet = service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if (currentPet == null) {
			response.put("ok", false);
			response.put("message", "El Pet con el id: " + id + " no se encuentra en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		Pet updatePet = service.updatePet(id, pet);
		
		response.put("ok", true);
		response.put("tag", updatePet);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>>  deletePet(@PathVariable("id") final int id) {
		Map<String, Object> response = new HashMap<>();
		
		Pet currentPet = service.findById(id);
		
		if (currentPet == null) {
			response.put("ok", false);
			response.put("message", "El Pet con el id: " + id + " no se encuentra en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		service.deletePet(id);
		
		response.put("ok", true);
		response.put("message", "Tag eliminado correctamente");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}

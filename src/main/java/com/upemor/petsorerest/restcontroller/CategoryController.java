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

import com.upemor.petsorerest.model.Category;
import com.upemor.petsorerest.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> listAllCategories(){
		List<Category> categories = service.listAllCategories();
		
		Map<String, Object> response = new HashMap<>();
		
		if (categories.isEmpty()) {
			response.put("ok", false);
			response.put("message", "No se encontraron registros en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("ok", true);
		response.put("categories", categories);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findCategory(@PathVariable("id") final int id) {
		Map<String, Object> response = new HashMap<>();
		
		Category category = service.findById(id);
		
		if (category != null) {
			response.put("ok", true);
			response.put("category", category);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		} else {
			response.put("ok", false);
			response.put("message", "la cagegoria con el id:" + id + " no se encuentra");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> createCategory(@RequestBody final Category category) {
		Map<String, Object> response = new HashMap<>();
		
		if(service.createCategory(category)) {
			response.put("ok", true);
			response.put("category", category);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		} else {
			response.put("ok", false);
			response.put("message", "no se pudo crear la Category en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable("id") final int id, @RequestBody Category category) {
		Category currentCategory = service.findById(id);
		
		Map<String, Object> response = new HashMap<>();
		
		if (currentCategory == null) {
			response.put("ok", false);
			response.put("message", "Category con el id: " + id + " no se encuentra en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		Category updateCategory = service.updateCategory(id, category);
		
		response.put("ok", true);
		response.put("category", updateCategory);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>>  deleteCategory(@PathVariable("id") final int id) {
		Map<String, Object> response = new HashMap<>();
		
		Category currentCategory = service.findById(id);
		
		if (currentCategory == null) {
			response.put("ok", false);
			response.put("message", "Category con el id: " + id + " no se encuentra en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		service.deleteCategory(id);
		
		response.put("ok", true);
		response.put("message", "Category eliminado correctamente");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}

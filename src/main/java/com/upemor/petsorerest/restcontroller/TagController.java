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

import com.upemor.petsorerest.model.Tag;
import com.upemor.petsorerest.service.TagService;

@RestController
@RequestMapping("/api/tag")
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> listAllTags(){
		List<Tag> tags = tagService.listAllTags();
		Map<String, Object> response = new HashMap<>();
		
		if (tags.isEmpty()) {
			response.put("ok", false);
			response.put("message", "No se encontraron registros en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("ok", true);
		response.put("tags", tags);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findTag(@PathVariable("id") final int id) {
		Map<String, Object> response = new HashMap<>();
		
		Tag tag = tagService.findById(id);
		
		if (tag != null) {
			response.put("ok", true);
			response.put("tag", tag);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		} else {
			response.put("ok", false);
			response.put("message", "el tag con el id: " + id + " no se encuentra");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> createTag(@RequestBody final Tag tag) {
		Map<String, Object> response = new HashMap<>();
		
		if(tagService.createTag(tag)) {
			response.put("ok", true);
			response.put("tag", tag);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		} else {
			response.put("ok", false);
			response.put("message", "no se pudo crear el Tag en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updateTag(@PathVariable("id") final int id, @RequestBody Tag tag) {
		Tag currentTag = tagService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if (currentTag == null) {
			response.put("ok", false);
			response.put("message", "El tag con el id: " + id + " no se encuentra en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		Tag updatedTag = tagService.updateTag(id, tag);
		
		response.put("ok", true);
		response.put("tag", updatedTag);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>>  deleteTag(@PathVariable("id") final int id) {
		Map<String, Object> response = new HashMap<>();
		
		Tag currentTag = tagService.findById(id);
		
		if (currentTag == null) {
			response.put("ok", false);
			response.put("message", "El tag con el id: " + id + " no se encuentra en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		tagService.deleteTag(id);
		
		response.put("ok", true);
		response.put("message", "Tag eliminado correctamente");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}

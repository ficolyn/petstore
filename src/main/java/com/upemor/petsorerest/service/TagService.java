package com.upemor.petsorerest.service;

import java.util.List;

import com.upemor.petsorerest.model.Tag;

public interface TagService {
	
	//Operaciones crud en base de datos
	
	List<Tag> listAllTags();
	
	Tag findById(int id);
	
	boolean createTag(Tag tag);
	
	Tag updateTag(int id, Tag tag);
	
	void deleteTag(int id);

}
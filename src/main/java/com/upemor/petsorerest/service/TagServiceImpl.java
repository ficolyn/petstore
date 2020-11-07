package com.upemor.petsorerest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upemor.petsorerest.model.Tag;
import com.upemor.petsorerest.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {
	
	@Autowired
	private TagRepository tagRepository;

	public List<Tag> listAllTags() {
		List<Tag> tags = tagRepository.findAll();
		return tags;
	}

	public Tag findById(int id) {
		Tag tag = tagRepository.findById(id);
		return tag;
	}

	public boolean createTag(Tag tag) {	
		Tag newTag = tagRepository.save(tag);
		
		if (newTag != null) 
			return true;
		
		return false;
	}

	public Tag updateTag(int id, Tag tag) {
		Tag current = tagRepository.findById(id);
		current.setName(tag.getName());
		tagRepository.save(current);
		return current;
	}

	public void deleteTag(int id) {
		tagRepository.deleteById(id);
	}

}

package com.upemor.petsorerest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upemor.petsorerest.model.Orderpet;
import com.upemor.petsorerest.model.Pet;
import com.upemor.petsorerest.model.User;
import com.upemor.petsorerest.repository.OrderpetRepository;
import com.upemor.petsorerest.repository.PetRepository;
import com.upemor.petsorerest.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderpetRepository repository;
	@Autowired
	private PetRepository petRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Orderpet> listAllOrders() {
		return repository.findAll();
	}

	@Override
	public Orderpet findById(int id) {
		return repository.findById(id);
	}

	@Override
	public boolean createOrder(Orderpet order) {
		if (order.getPet() == null || order.getUser() == null)
			return false;
		
		order.setPet(getPetFromBD(order.getPet().getId()));
		order.setUser(getUserFromBD(order.getUser().getId()));

		Orderpet orderSaved = repository.save(order);
		
		
		if (orderSaved == null)
			return false;
		
		return true;
	}

	@Override
	public Orderpet updateOrder(int id, Orderpet newOrder) {
		if (newOrder.getPet() == null || newOrder.getUser() == null)
			return null;
		
		Orderpet order = repository.findById(id);
		
		if (order == null)
			return null;
		
		else {
			order.setPet(getPetFromBD(newOrder.getPet().getId()));
			order.setUser(getUserFromBD(newOrder.getUser().getId()));
		}
		
		repository.saveAndFlush(order);
		
		return order;
	}

	@Override
	public void deleteOrder(int id) {
		Orderpet order = repository.findById(id);
		
		if (order != null) 
			repository.delete(order);
	}
	
	private Pet getPetFromBD(int id) {
		Pet pet = petRepository.findById(id);
		return (pet != null) ? pet : null;
	}
	
	private User getUserFromBD(int id) {
		User user = userRepository.findById(id);
		return (user != null) ? user : null;
	}
}

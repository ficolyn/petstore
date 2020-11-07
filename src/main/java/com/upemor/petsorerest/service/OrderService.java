package com.upemor.petsorerest.service;

import java.util.List;

import com.upemor.petsorerest.model.Orderpet;

public interface OrderService {
	List<Orderpet> listAllOrders();

	Orderpet findById(int id);

	boolean createOrder(Orderpet order);

	Orderpet updateOrder(int id, Orderpet order);

	void deleteOrder(int id);
}

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

import com.upemor.petsorerest.model.Orderpet;
import com.upemor.petsorerest.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	@Autowired
	private OrderService service;
	
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> listAllCategories(){
		List<Orderpet> orders = service.listAllOrders();
		
		Map<String, Object> response = new HashMap<>();
		
		if (orders.isEmpty()) {
			response.put("ok", false);
			response.put("message", "No se encontraron registros en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("ok", true);
		response.put("orders", orders);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findOrder(@PathVariable("id") final int id) {
		Map<String, Object> response = new HashMap<>();
		
		Orderpet order = service.findById(id);
		
		if (order != null) {
			response.put("ok", true);
			response.put("order", order);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		} else {
			response.put("ok", false);
			response.put("message", "la orden con el id:" + id + " no se encuentra");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> createCategory(@RequestBody final Orderpet order) {
		Map<String, Object> response = new HashMap<>();
		
		if(service.createOrder(order)) {
			response.put("ok", true);
			response.put("order", order);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		} else {
			response.put("ok", false);
			response.put("message", "no se pudo crear la Orden en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable("id") final int id, @RequestBody Orderpet order) {
		Orderpet currentOrder = service.findById(id);
		
		Map<String, Object> response = new HashMap<>();
		
		if (currentOrder == null) {
			response.put("ok", false);
			response.put("message", "Order con el id: " + id + " no se encuentra en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		Orderpet updatedOrder = service.updateOrder(id, order);
		
		response.put("ok", true);
		response.put("order", updatedOrder);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>>  deleteCategory(@PathVariable("id") final int id) {
		Map<String, Object> response = new HashMap<>();
		
		Orderpet currentOrder = service.findById(id);
		
		if (currentOrder == null) {
			response.put("ok", false);
			response.put("message", "Order con el id: " + id + " no se encuentra en la BD");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		service.deleteOrder(id);
		
		response.put("ok", true);
		response.put("message", "Order eliminado correctamente");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}

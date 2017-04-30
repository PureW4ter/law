package com.destinesia.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.destinesia.entity.Customer;
import com.destinesia.entity.Order;
import com.destinesia.entity.Product;

public interface IOrderService {
	@Transactional
	public void addOrder(Order order, String token);
	public void addCustomer(Customer customer, String token);
	public List<Order> getOrdersByUserId(String token, int from, int pageSize);
	public List<Customer> getCustomersByUserId(String token, int from, int pageSize);
	public Order findOrderById(String id, String token);
	public void changeStatus(String orderId);
	public Product findProductById(String id);
	public List<Product> getProducts(int from, int pageSize);
}

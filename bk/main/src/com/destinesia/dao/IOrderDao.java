package com.destinesia.dao;

import java.util.List;

import com.destinesia.entity.Customer;
import com.destinesia.entity.Order;
import com.destinesia.entity.OrderDetail;
import com.destinesia.entity.Product;

public interface IOrderDao {
	public void addOrder(Order order);
	public void addOrderDetail(OrderDetail orderDetail);
	public void addCustomer(Customer customer);
	public List<Order> getOrdersByUserId(String userId, int from, int pageSize);
	public List<Customer> getCustomersByUserId(String userId, int from, int pageSize);
	public Order findOrderById(String id);
	public void changeStatus(String orderId);
	public Product findProductById(String id);
	public List<Product> getProducts(int from, int pageSize);
}

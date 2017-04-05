package com.destinesia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.destinesia.base.StatusMsg;
import com.destinesia.base.Utility;
import com.destinesia.dao.IOrderDao;
import com.destinesia.dao.IUserDao;
import com.destinesia.entity.Customer;
import com.destinesia.entity.Order;
import com.destinesia.entity.OrderDetail;
import com.destinesia.entity.Product;
import com.destinesia.entity.TokenInfo;
import com.destinesia.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService{

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IOrderDao orderDao;
	@Autowired 
	DataSourceTransactionManager transactionManager;
	
	@Override
	@Transactional  
	public void addOrder(Order order,  String token) {
		TokenInfo info = userDao.getTokenByToken(token);
		if(info ==null){
			return;
		}
		order.setUserId(info.getUserId());
		double totalPrice = 0;
		try{
			//添加预定详情
			for(OrderDetail orderDetail : order.getOrderDetails()){
				orderDetail.setId(Utility.generageId());
				orderDetail.setOrderId(order.getId());
				Product product = orderDao.findProductById(orderDetail.getProduct().getId());
				orderDetail.setPrice(product.getPrice());
				orderDetail.setTotalPrice(product.getPrice()*orderDetail.getCount());
				totalPrice = totalPrice+orderDetail.getPrice();
				orderDao.addOrderDetail(orderDetail);
			}
			order.setTotalPrice(totalPrice);
			orderDao.addOrder(order);
		}catch(Exception e){
			//可以通过捕获异常，发送自己的异常
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new StatusMsg(StatusMsg.CODE_DATABASE_ROLLBACK, "数据库操作出错，并且回滚");
			
		}
		
	}

	@Override
	public void addCustomer(Customer customer, String token) {
		TokenInfo info = userDao.getTokenByToken(token);
		if(info !=null){
			customer.setUserId(info.getUserId());
			orderDao.addCustomer(customer);
		}
	}

	@Override
	public List<Order> getOrdersByUserId(String token, int from, int pageSize) {
		TokenInfo info = userDao.getTokenByToken(token);
		if(info !=null){
			return orderDao.getOrdersByUserId(info.getUserId(), from, pageSize);
		}
		return null;
	}

	@Override
	public List<Customer> getCustomersByUserId(String token, int from,
			int pageSize) {
		TokenInfo info = userDao.getTokenByToken(token);
		if(info !=null){
			return orderDao.getCustomersByUserId(info.getUserId(), from, pageSize);
		}
		return null;
	}

	@Override
	public Order findOrderById(String id, String token) {
		Order order = orderDao.findOrderById(id);
		TokenInfo info = userDao.getTokenByToken(token);
		if(order!=null && info!=null && order.getUserId().equals(info.getUserId())){
			return order;
		}
		return null;
	}

	@Override
	public void changeStatus(String orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Product findProductById(String id) {
		return orderDao.findProductById(id);
	}

	@Override
	public List<Product> getProducts(int from, int pageSize) {
		return orderDao.getProducts(from, pageSize);
	}
	
}

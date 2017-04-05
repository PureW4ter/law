package com.destinesia.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.destinesia.dao.IOrderDao;
import com.destinesia.entity.Customer;
import com.destinesia.entity.Order;
import com.destinesia.entity.OrderDetail;
import com.destinesia.entity.Product;

@Repository
public class OrderDaoImpl extends SqlSessionDaoSupport implements IOrderDao{

	@Override
	public void addOrder(Order order) {
		this.getSqlSession().insert("OrderSpace.addOrder", order);
	}

	@Override
	public void addOrderDetail(OrderDetail orderDetail) {
		this.getSqlSession().insert("OrderSpace.addOrderDetail", orderDetail);
	}
	
	@Override
	public void addCustomer(Customer customer) {
		this.getSqlSession().insert("OrderSpace.addCustomer", customer);
	}

	@Override
	public List<Order> getOrdersByUserId(String userId, int from, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("OrderSpace.getOrdersByUserId", param);
	}

	@Override
	public Order findOrderById(String id) {
		return this.getSqlSession().selectOne("OrderSpace.findOrderById", id);
	}

	@Override
	public void changeStatus(String orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Product findProductById(String id) {
		return this.getSqlSession().selectOne("OrderSpace.findProductById", id);
	}

	@Override
	public List<Product> getProducts(int from, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("OrderSpace.getProducts", param);
	}
	
	@Override
	public List<Customer> getCustomersByUserId(String userId, int from,
			int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("OrderSpace.getCustomersByUserId", param);
	}
	@Resource  
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){  
        super.setSqlSessionFactory(sqlSessionFactory);  
    }
}

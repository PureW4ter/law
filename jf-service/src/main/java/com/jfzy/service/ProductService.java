package com.jfzy.service;

import java.util.List;

import com.jfzy.service.bo.ProductBo;

public interface ProductService {
	ProductBo getProduct(int id);
	List<ProductBo> getProducts();
}

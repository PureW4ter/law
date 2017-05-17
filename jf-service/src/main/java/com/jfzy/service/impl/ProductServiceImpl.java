package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.ProductService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.service.bo.ProductBo;
import com.jfzy.service.po.ProductPo;
import com.jfzy.service.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepo;
	
	@Override
	public ProductBo getProduct(int id) {
		ProductPo po = productRepo.getById(id);
		return po2Bo(po);
	}
	
	@Override
	public List<ProductBo> getProducts() {
		List<ProductPo> values = productRepo.findNotDeleted();
		List<ProductBo> results = new ArrayList<ProductBo>();
		values.forEach(po -> results.add(po2Bo(po)));
		return results;
	}
	
	private static ProductBo po2Bo(ProductPo po) {
		ProductBo bo = new ProductBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	private static ProductPo bo2Po(ProductBo bo) {
		ProductPo po = new ProductPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}
	
}

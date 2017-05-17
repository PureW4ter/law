package com.jfzy.mweb.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.SearchOrderVo;
import com.jfzy.service.OrderService;
import com.jfzy.service.ProductService;
import com.jfzy.service.UserService;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.bo.PayWayEnum;
import com.jfzy.service.bo.ProductBo;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.bo.UserBo;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@PostMapping(path="/order/screate",consumes =MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<Object> createSOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody SearchOrderVo vo) {
		OrderBo bo = svoToBo(vo);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		ProductBo pbo = productService.getProduct(vo.getProductId());
		UserBo ubo = userService.getUser(vo.getUserId());
		UserAccountBo uabo = userService.getUserAccountByUserId(vo.getUserId(), UserAccountTypeEnum.MOBILE.getId());
		bo.setCityId(0);//FIXME
		bo.setProductName(pbo.getName());
		bo.setRealPrice(pbo.getPrice());
		bo.setOriginPrice(pbo.getPrice());
		bo.setUserName(ubo.getRealName());
		bo.setUserPhoneNum(uabo.getValue());
		bo.setStatus(OrderStatusEnum.INIT.getId());
		bo.setPayWay(PayWayEnum.NO_PAY.getId());
		orderService.createOrder(bo);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	private static OrderBo svoToBo(SearchOrderVo vo) {
		OrderBo bo = new OrderBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}
}

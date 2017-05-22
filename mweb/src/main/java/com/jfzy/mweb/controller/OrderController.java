package com.jfzy.mweb.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.InvestOrderVo;
import com.jfzy.mweb.vo.OrderVo;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.SearchOrderVo;
import com.jfzy.mweb.vo.SimpleArticleVo;
import com.jfzy.service.OrderService;
import com.jfzy.service.ProductService;
import com.jfzy.service.UserService;
import com.jfzy.service.bo.ArticleBo;
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
	public ResponseVo<OrderBo> createSOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody SearchOrderVo vo) {
		OrderBo bo = svoToBo(vo);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		ProductBo pbo = productService.getProduct(vo.getProductId());
		UserBo ubo = userService.getUser(vo.getUserId());
		UserAccountBo uabo = userService.getUserAccountByUserId(vo.getUserId(), UserAccountTypeEnum.MOBILE.getId());
		bo.setProductName(pbo.getName());
		bo.setRealPrice(pbo.getPrice());
		bo.setOriginPrice(pbo.getPrice());
		bo.setUserName(ubo.getRealName());
		bo.setUserPhoneNum(uabo.getValue());
		OrderBo newBo = orderService.createSOrder(bo);
		return new ResponseVo<OrderBo>(ResponseStatusEnum.SUCCESS.getCode(), null, newBo);
	}
	
	@ResponseBody
	@PostMapping(path="/order/icreate",consumes =MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<OrderBo> createIOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody InvestOrderVo vo) {
		OrderBo bo = ivoToBo(vo);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		ProductBo pbo = productService.getProduct(vo.getProductId());
		UserBo ubo = userService.getUser(vo.getUserId());
		UserAccountBo uabo = userService.getUserAccountByUserId(vo.getUserId(), UserAccountTypeEnum.MOBILE.getId());
		bo.setProductName(pbo.getName());
		bo.setRealPrice(pbo.getPrice());
		bo.setOriginPrice(pbo.getPrice());
		bo.setUserName(ubo.getRealName());
		bo.setUserPhoneNum(uabo.getValue());
		OrderBo newBo = orderService.createIOrder(bo);
		return new ResponseVo<OrderBo>(ResponseStatusEnum.SUCCESS.getCode(), null, newBo);
	}
	
	@ResponseBody
	@GetMapping(value = "/order/pay")
	public ResponseVo<Object> pay(int id) {
		orderService.pay(id);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	@ResponseBody
	@GetMapping(value = "/order/cancel")
	public ResponseVo<Object> cancel(int id) {
		orderService.cancel(id);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	@ResponseBody
	@PostMapping(value = "/order/complete")
	public ResponseVo<Object> complete(@RequestParam(value = "id")int id, 
			@RequestParam(value = "comment")String comment, 
			@RequestParam(value = "picList[]") String[] picList) {
		orderService.complete(id, comment, picList);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	@ResponseBody
	@GetMapping("/order/listbyuser")
	public ResponseVo<List<OrderVo>> getOrders(int userId, int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderBo> values = orderService.getOrdersByUser(userId, new PageRequest(page, size, sort));
		List<OrderVo> result = new ArrayList<OrderVo>(values.size());
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}
	
	private static OrderBo svoToBo(SearchOrderVo vo) {
		OrderBo bo = new OrderBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}
	
	private static OrderBo ivoToBo(InvestOrderVo vo) {
		OrderBo bo = new OrderBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}
	
	private static OrderVo boToVo(OrderBo bo) {
		OrderVo vo = new OrderVo();
		BeanUtils.copyProperties(bo, vo);
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd");
		if(bo.getCreateTime()!=null)
			vo.setCreateTime(myFmt.format(bo.getCreateTime()));
		if(bo.getUpdateTime()!=null)
			vo.setUpdateTime(myFmt.format(bo.getUpdateTime()));
		if(bo.getStartTime()!=null)
			vo.setStartTime(myFmt.format(bo.getStartTime()));
		if(bo.getEndTime()!=null)
			vo.setEndTime(myFmt.format(bo.getEndTime()));
		if(bo.getStartTime()!=null && bo.getEndTime()!=null){
			if(new Date().getTime()>=bo.getEndTime().getTime() || bo.getStatus()==OrderStatusEnum.FINISHED.getId()){
				vo.setProcessPer("100%");
			}else if(new Date().getTime() > bo.getStartTime().getTime() && new Date().getTime()<bo.getEndTime().getTime()){
				vo.setProcessPer(
						Math.round((new Date().getTime() - bo.getStartTime().getTime())*100/(bo.getEndTime().getTime() - bo.getStartTime().getTime())) 
						+ "%");
			}else{
				vo.setProcessPer("0%");
			}
		}
		return vo;
	}
}

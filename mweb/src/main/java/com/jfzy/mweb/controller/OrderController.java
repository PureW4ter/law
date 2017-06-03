package com.jfzy.mweb.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.base.BaseController;
import com.jfzy.mweb.base.UserSession;
import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.InvestOrderVo;
import com.jfzy.mweb.vo.OrderCompleteVo;
import com.jfzy.mweb.vo.OrderVo;
import com.jfzy.mweb.vo.PrepayVo;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.SearchOrderVo;
import com.jfzy.service.OrderService;
import com.jfzy.service.ProductService;
import com.jfzy.service.UserService;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.bo.ProductBo;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.bo.UserBo;
import com.jfzy.service.bo.WxPayResponseDto;

@RestController
public class OrderController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@ResponseBody
	@PostMapping(path = "/api/order/screate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<OrderBo> createSOrder(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SearchOrderVo vo) {
		OrderBo bo = svoToBo(vo);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		ProductBo pbo = productService.getProduct(vo.getProductId());
		UserBo ubo = userService.getUser(vo.getUserId());
		UserAccountBo uabo = userService.getUserAccountByUserId(vo.getUserId(), UserAccountTypeEnum.MOBILE.getId());
		bo.setProductName(pbo.getName());
		bo.setProductCode(pbo.getCode());
		bo.setRealPrice(pbo.getPrice());
		bo.setOriginPrice(pbo.getPrice());
		bo.setUserName(ubo.getRealName());
		bo.setUserPhoneNum(uabo.getValue());
		OrderBo newBo = orderService.createSOrder(bo);
		return new ResponseVo<OrderBo>(ResponseStatusEnum.SUCCESS.getCode(), null, newBo);
	}

	@ResponseBody
	@PostMapping(path = "/api/order/icreate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<OrderBo> createIOrder(HttpServletRequest request, HttpServletResponse response,
			@RequestBody InvestOrderVo vo) {
		OrderBo bo = ivoToBo(vo);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		ProductBo pbo = productService.getProduct(vo.getProductId());
		UserBo ubo = userService.getUser(vo.getUserId());
		UserAccountBo uabo = userService.getUserAccountByUserId(vo.getUserId(), UserAccountTypeEnum.MOBILE.getId());
		bo.setProductName(pbo.getName());
		bo.setProductCode(pbo.getCode());
		bo.setRealPrice(pbo.getPrice());
		bo.setOriginPrice(pbo.getPrice());
		bo.setUserName(ubo.getRealName());
		bo.setUserPhoneNum(uabo.getValue());
		OrderBo newBo = orderService.createIOrder(bo);
		return new ResponseVo<OrderBo>(ResponseStatusEnum.SUCCESS.getCode(), null, newBo);
	}

	@ResponseBody
	@GetMapping(value = "/api/order/pay")
	public ResponseVo<PrepayVo> pay(int id) {
		UserSession session = getUserSession();

		if (session != null) {
			String openId = session.getOpenId();
			if (StringUtils.isNotBlank(openId) || UserSession.EMPTY_USER_ID != session.getUserId()) {
				WxPayResponseDto dto = orderService.pay(id, session.getUserId(), getClientIp(), openId);
				if (StringUtils.equals("SUCCESS", dto.getReturnCode())
						&& StringUtils.equals("SUCCESS", dto.getResultCode())) {
					PrepayVo vo = dtoToVo(dto);
					return new ResponseVo<PrepayVo>(ResponseStatusEnum.SUCCESS.getCode(), null, vo);
				} else {
					return new ResponseVo<PrepayVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), "微信支付单生成失败", null);
				}
			}
		}

		return new ResponseVo<PrepayVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), "未登录", null);

	}

	@ResponseBody
	@GetMapping(value = "/api/order/cancel")
	public ResponseVo<Object> cancel(int id) {
		orderService.cancel(id);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	@ResponseBody
	@PostMapping(value = "/api/order/complete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<Object> complete(HttpServletRequest request, HttpServletResponse response,
			@RequestBody OrderCompleteVo vo) {
		orderService.complete(vo.getId(), vo.getComment(), vo.getPicList());
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	@ResponseBody
	@GetMapping("/api/order/listbyuser")
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
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyyMMdd");
		if (bo.getCreateTime() != null)
			vo.setCreateTime(myFmt.format(bo.getCreateTime()));
		// 订单编号：时间+code+id
		vo.setOrderCode(myFmt2.format(bo.getCreateTime()) + bo.getProductCode() + bo.getId());
		if (bo.getUpdateTime() != null)
			vo.setUpdateTime(myFmt.format(bo.getUpdateTime()));
		if (bo.getStartTime() != null)
			vo.setStartTime(myFmt.format(bo.getStartTime()));
		if (bo.getEndTime() != null)
			vo.setEndTime(myFmt.format(bo.getEndTime()));
		if (bo.getStartTime() != null && bo.getEndTime() != null) {
			if (new Date().getTime() >= bo.getEndTime().getTime()
					|| bo.getStatus() == OrderStatusEnum.FINISHED.getId()) {
				if (bo.getPhoneEndTime() != null)
					vo.setPhoneEndTime(myFmt.format(bo.getPhoneEndTime()));
				if (bo.getStartTime() != null && bo.getEndTime() != null) {
					if (new Date().getTime() >= bo.getEndTime().getTime()
							|| bo.getStatus() == OrderStatusEnum.FINISHED.getId()) {
						vo.setProcessPer("100%");
					} else if (new Date().getTime() > bo.getStartTime().getTime()
							&& new Date().getTime() < bo.getEndTime().getTime()) {
						vo.setProcessPer(Math.round((new Date().getTime() - bo.getStartTime().getTime()) * 100
								/ (bo.getEndTime().getTime() - bo.getStartTime().getTime())) + "%");
					} else {
						vo.setProcessPer("0%");
					}
				}

			}
		}
		return vo;
	}

	private static PrepayVo dtoToVo(WxPayResponseDto dto) {
		PrepayVo vo = new PrepayVo();
		vo.setAppId(dto.getAppId());
		vo.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
		vo.setNonceStr(dto.getNonceStr());
		vo.setPaySign(dto.getSign());
		vo.setPaySign(String.format("prepaid_id=%s", dto.getPrepayId()));
		return vo;
	}
}
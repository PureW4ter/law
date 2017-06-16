package com.jfzy.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.service.OrderService;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.web.vo.OrderVo;
import com.jfzy.web.vo.PageResponseVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.SimpleResponseVo;

@RestController
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@ResponseBody
	@GetMapping("/api/order/find")
	public PageResponseVo<List<OrderVo>> getOrdersByCityIdAndStatus(int cityId, int status, int pageNo,
			int size) {
		Pageable page = new PageRequest(pageNo, size);
		Page<OrderBo> orders = orderService.getOrdersByCityIdAndStatus(cityId, status, page);
		List<OrderVo> vos = new ArrayList<OrderVo>();
		if (orders != null && orders.getContent() != null) {
			orders.getContent().forEach(bo -> vos.add(boToVo(bo)));
		}

		return new PageResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, vos, pageNo, size,
				orders.getTotalElements());
	}

	
	@ResponseBody
	@GetMapping("/api/order/slist")
	public ResponseVo<List<OrderVo>> getSearchOrders(int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderBo> values = orderService.getSearchOrders(new PageRequest(page, size, sort));
		List<OrderVo> result = new ArrayList<OrderVo>(values.size());
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}
	
	@ResponseBody
	@GetMapping("/api/order/ilist")
	public ResponseVo<List<OrderVo>> getInvestOrders(int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "phoneEndTime");
		List<OrderBo> values = orderService.getInvestOrders(new PageRequest(page, size, sort));
		List<OrderVo> result = new ArrayList<OrderVo>(values.size());
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}
	
	@ResponseBody
	@GetMapping("/api/order/lawyer")
	public ResponseVo<List<OrderVo>> getOrderByLawyer(int lawyerId, int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "phoneEndTime");
		Page<OrderBo> values = orderService.getOrdresByLawyer(lawyerId, new PageRequest(page, size, sort));
		List<OrderVo> result = new ArrayList<OrderVo>();
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}
	
	@ResponseBody
	@GetMapping("/api/order/assignment")
	public SimpleResponseVo assignOrder(int lawyerId, int orderId) {
		OssUserBo ossUser = getOssUser();
		if (ossUser != null) {
			orderService.assignOrder(orderId, lawyerId, ossUser.getId(), ossUser.getName());
		}
		//orderService.assignOrder(orderId, lawyerId, 101, "崔哥哥");
		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null);
	}

	private static OrderVo boToVo(OrderBo bo) {
		OrderVo vo = new OrderVo();
		BeanUtils.copyProperties(bo, vo);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat myFmt3 = new SimpleDateFormat("yyyyMMdd");
		if (bo.getCreateTime() != null)
			vo.setCreateTime(myFmt.format(bo.getCreateTime()));
		// 订单编号：时间+code+id
		vo.setOrderCode(myFmt3.format(bo.getCreateTime()) + bo.getProductCode() + bo.getId());
		if (bo.getUpdateTime() != null)
			vo.setUpdateTime(myFmt2.format(bo.getUpdateTime()));
		if (bo.getStartTime() != null)
			vo.setStartTime(myFmt2.format(bo.getStartTime()));
		if (bo.getEndTime() != null)
			vo.setEndTime(myFmt2.format(bo.getEndTime()));
		if (bo.getStartTime() != null && bo.getEndTime() != null) {
			if (new Date().getTime() >= bo.getEndTime().getTime()
					|| bo.getStatus() == OrderStatusEnum.FINISHED.getId()) {
				if (bo.getPhoneEndTime() != null)
					vo.setPhoneEndTime(myFmt2.format(bo.getPhoneEndTime()));
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

}

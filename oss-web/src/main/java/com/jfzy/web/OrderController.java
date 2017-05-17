package com.jfzy.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.service.OrderService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.web.vo.LawyerVo;
import com.jfzy.web.vo.PageResponseVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.SimpleOrderVo;
import com.jfzy.web.vo.SimpleResponseVo;

@RestController
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@ResponseBody
	@GetMapping("/order")
	public PageResponseVo<List<SimpleOrderVo>> getOrdersByCityIdAndStatus(int cityId, int status, int pageNo,
			int size) {
		Pageable page = new PageRequest(pageNo, size);
		Page<OrderBo> orders = orderService.getOrdersByCityIdAndStatus(cityId, status, page);
		List<SimpleOrderVo> vos = new ArrayList<SimpleOrderVo>();
		if (orders != null && orders.getContent() != null) {
			orders.getContent().forEach(bo -> vos.add(boToSimpleVo(bo)));
		}

		return new PageResponseVo<List<SimpleOrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, vos, pageNo, size,
				orders.getTotalElements());
	}

	@ResponseBody
	@PostMapping("/order/assignment")
	public SimpleResponseVo assignOrder(int lawyerId, int orderId) {
		OssUserBo ossUser = getOssUser();
		if (ossUser != null) {
			orderService.assignOrder(orderId, lawyerId, ossUser.getId(), ossUser.getName());
		}

		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null);
	}

	private static SimpleOrderVo boToSimpleVo(OrderBo bo) {
		SimpleOrderVo vo = new SimpleOrderVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}

	private static LawyerVo boToVo(LawyerBo bo) {
		LawyerVo vo = new LawyerVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}

	private static LawyerBo voToBo(LawyerVo vo) {
		LawyerBo bo = new LawyerBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}

}

package com.jfzy.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.service.LawyerReplyService;
import com.jfzy.service.OrderService;
import com.jfzy.service.bo.LawyerReplyBo;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderPhotoBo;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.web.vo.LawyerReplyVo;
import com.jfzy.web.vo.OrderPhotoVo;
import com.jfzy.web.vo.OrderVo;
import com.jfzy.web.vo.OrderWithReplyVo;
import com.jfzy.web.vo.PageResponseVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.SimpleResponseVo;

@RestController
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private LawyerReplyService lawyerReplyService;
	
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
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderBo> values = orderService.getInvestOrders(new PageRequest(page, size, sort));
		List<OrderVo> result = new ArrayList<OrderVo>(values.size());
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}
	
	@ResponseBody
	@GetMapping("/api/order/slistbyuser")
	public ResponseVo<List<OrderVo>> getSearchOrdersByUser(int page, int size, int userId) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderBo> values = orderService.getSearchOrdersByUser(new PageRequest(page, size, sort), userId);
		List<OrderVo> result = new ArrayList<OrderVo>(values.size());
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}
	
	@ResponseBody
	@GetMapping("/api/order/ilistbyuser")
	public ResponseVo<List<OrderVo>> getInvestOrdersByUser(int page, int size, int userId) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "phoneEndTime");
		List<OrderBo> values = orderService.getInvestOrdersByUser(new PageRequest(page, size, sort), userId);
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
		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null);
	}

	@ResponseBody
	@GetMapping(value = "/api/order/getreply")
	public ResponseVo<OrderWithReplyVo> getReply(int id) {
		LawyerReplyBo rbo = lawyerReplyService.getReply(id);
		OrderBo obo = orderService.getOrderById(id);
		List<OrderPhotoBo> orderPhotoList = orderService.getOrderPhotos(id);
		List<OrderPhotoBo> replyPhotoList = orderService.getReplyPhotos(id);
		OrderWithReplyVo vo = boToVo(obo, rbo, orderPhotoList, replyPhotoList);
		
		return new ResponseVo<OrderWithReplyVo>(ResponseStatusEnum.SUCCESS.getCode(), null, vo);
	}
	
	@ResponseBody
	@PostMapping(path = "/api/order/reply", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<Object> reply(HttpServletRequest request, HttpServletResponse response,
			@RequestBody LawyerReplyVo vo) {
		LawyerReplyBo bo = voToBo(vo);
		OrderBo obo = orderService.getOrderById(vo.getOrderId());
		bo.setProductCode(obo.getProductCode());
		bo.setLawyerId(obo.getLawyerId());
		if(bo.getId()>0){
			bo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		}else{
			bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		lawyerReplyService.createReply(bo);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	private static OrderPhotoVo boToVo(OrderPhotoBo bo) {
		OrderPhotoVo vo = new OrderPhotoVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
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
	
	private static LawyerReplyBo voToBo(LawyerReplyVo vo) {
		if(vo == null)
			return null;
		LawyerReplyBo bo = new LawyerReplyBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}
	
	private static LawyerReplyVo boToVo(LawyerReplyBo bo) {
		if(bo == null)
			return null;
		LawyerReplyVo vo = new LawyerReplyVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}
	
	private static OrderWithReplyVo boToVo(OrderBo obo, LawyerReplyBo rbo, 
			List<OrderPhotoBo> orderPhotoList, List<OrderPhotoBo> replyPhotoList) {
		OrderWithReplyVo vo = new OrderWithReplyVo();
		vo.setLawyerReplyVo(boToVo(rbo));
		vo.setOrderVo(boToVo(obo));
		if(orderPhotoList!=null){
			List<OrderPhotoVo> result = new ArrayList<OrderPhotoVo>(orderPhotoList.size());
			for (OrderPhotoBo bo : orderPhotoList) {
				result.add(boToVo(bo));
			}
			vo.setOrderPhotoList(result);
		}
		if(replyPhotoList!=null){
			List<OrderPhotoVo> result = new ArrayList<OrderPhotoVo>(replyPhotoList.size());
			for (OrderPhotoBo bo : replyPhotoList) {
				result.add(boToVo(bo));
			}
			vo.setReplyPhotoList(result);
		}
		return vo;
	}
}

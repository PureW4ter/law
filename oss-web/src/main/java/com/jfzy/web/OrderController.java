package com.jfzy.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.base.AuthCheck;
import com.jfzy.service.LawyerReplyService;
import com.jfzy.service.LawyerService;
import com.jfzy.service.NotificationService;
import com.jfzy.service.OrderService;
import com.jfzy.service.OssUserService;
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

@AuthCheck(privileges = { "admin", "csr" })
@RestController
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private LawyerService lawyerService;

	@Autowired
	private OssUserService userService;

	@Autowired
	private LawyerReplyService lawyerReplyService;

	@Autowired
	private NotificationService notifyService;

	@ResponseBody
	@GetMapping("/api/order/{orderNum}")
	public ResponseVo<List<OrderVo>> getOrderBySn(@PathVariable(name = "orderNum") String orderNum) {
		if (StringUtils.isBlank(orderNum)) {
			return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.BAD_REQUEST.getCode(), "订单号为空",
					new ArrayList<OrderVo>());
		} else {
			OrderBo bo = orderService.getOrderByOrderNum(orderNum);

			if (bo == null) {
				return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.BAD_REQUEST.getCode(), "无此订单",
						new ArrayList<OrderVo>());
			} else {
				List<OrderVo> list = new ArrayList<OrderVo>();
				list.add(boToVo(bo));
				return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, list);
			}
		}
	}

	@ResponseBody
	@GetMapping("/api/order/uncompleted")
	public PageResponseVo<List<OrderVo>> getUncompletedOrdersByCityIdAndStatus(int cityId, Integer status, int pageNo,
			int size) {
		Pageable page = new PageRequest(pageNo, size);

		Page<OrderBo> orders = orderService.getUncompletedOrdersByCityIdAndStatus(cityId, status, page);
		List<OrderVo> vos = new ArrayList<OrderVo>();
		if (orders != null && orders.getContent() != null) {
			orders.getContent().forEach(bo -> vos.add(boToVo(bo)));
		}

		return new PageResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, vos, pageNo, size,
				orders.getTotalElements());
	}

	@ResponseBody
	@GetMapping("/api/order/completed")
	public PageResponseVo<List<OrderVo>> getCompletedOrdersByCityIdAndStatus(int cityId, Integer status, int pageNo,
			int size) {
		Pageable page = new PageRequest(pageNo, size);

		Page<OrderBo> orders = orderService.getCompletedOrdersByCityIdAndStatus(cityId, status, page);
		List<OrderVo> vos = new ArrayList<OrderVo>();
		if (orders != null && orders.getContent() != null) {
			orders.getContent().forEach(bo -> vos.add(boToVo(bo)));
		}

		return new PageResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, vos, pageNo, size,
				orders.getTotalElements());
	}

	@ResponseBody
	@GetMapping("/api/order/find")
	public PageResponseVo<List<OrderVo>> getOrdersByCityIdAndStatus(int cityId, int status, int pageNo, int size) {
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
	@GetMapping("/api/order/slistbylawyer")
	public ResponseVo<List<OrderVo>> getSearchOrdersByLawyer(int page, int size, int lawyerId, int status) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderBo> values = orderService.getSearchOrdersByLawyer(new PageRequest(page, size, sort), lawyerId,
				status);
		List<OrderVo> result = new ArrayList<OrderVo>(values.size());
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}

	@ResponseBody
	@GetMapping("/api/order/ilistbylawyer")
	public ResponseVo<List<OrderVo>> getInvestOrdersByLawyer(int page, int size, int lawyerId, int status) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "phoneEndTime");
		List<OrderBo> values = orderService.getInvestOrdersByLawyer(new PageRequest(page, size, sort), lawyerId,
				status);
		List<OrderVo> result = new ArrayList<OrderVo>(values.size());
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}

	@GetMapping("/api/order/listbytype")
	public ResponseVo<List<OrderVo>> getOrdersByType(int page, int size, String productCode) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		Page<OrderBo> values = orderService.getOrdersByType(new PageRequest(page, size, sort), productCode);
		List<OrderVo> result = new ArrayList<OrderVo>();
		for (OrderBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<OrderVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}

	@AuthCheck(privileges = { "lawyer" })
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
	public SimpleResponseVo assignOrder(int lawyerId, int orderId, int opId) {
		OssUserBo ossUser = userService.getUserById(opId);
		if (ossUser != null) {
			orderService.assignOrder(orderId, lawyerId, ossUser.getId(), ossUser.getName());
		}
		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null);
	}

	@ResponseBody
	@GetMapping("/api/order/cancelass")
	public SimpleResponseVo cancelAssignment(int orderId, int opId) {
		OrderBo bo = orderService.getOrderById(orderId);
		OssUserBo ossUser = userService.getUserById(opId);
		if (ossUser != null) {
			orderService.assignOrder(orderId, 0, ossUser.getId(), ossUser.getName());
		}
		orderService.updateOrderStatus(orderId, OrderStatusEnum.NEED_DISPATCH.getId());
		lawyerService.updateOnProcessTask(-1, bo.getLawyerId());

		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null);
	}

	@ResponseBody
	@GetMapping("/api/order/confirm")
	public SimpleResponseVo confim(int orderId) {
		lawyerReplyService.confirmReply(orderId);
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
		if (bo.getId() > 0) {
			bo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		} else {
			bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		lawyerReplyService.createReply(bo, vo.isTemp());
		lawyerReplyService.addReplyPhotos(vo.getPicList(), vo.getOrderId());
		if (!vo.isNeedConfirm()) {
			orderService.updateOrderStatus(bo.getOrderId(), OrderStatusEnum.FINISHED.getId());
			notifyService.completeNotify(bo.getOrderId());
		}

		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	private static OrderPhotoVo boToVo(OrderPhotoBo bo) {
		OrderPhotoVo vo = new OrderPhotoVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}

	private OrderVo boToVo(OrderBo bo) {
		OrderVo vo = new OrderVo();
		BeanUtils.copyProperties(bo, vo);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
		if (bo.getCreateTime() != null)
			vo.setCreateTime(myFmt.format(bo.getCreateTime()));
		// 订单编号：时间+code+id
		vo.setOrderCode(bo.getOrderNum());
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

		if (!isAdmin()) {
			vo.setEmail("");
			vo.setUserPhoneNum("");
			vo.setUserName("");
			vo.setLawyerPhoneNum("");
		}
		return vo;
	}

	private static LawyerReplyBo voToBo(LawyerReplyVo vo) {
		if (vo == null)
			return null;
		LawyerReplyBo bo = new LawyerReplyBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}

	private static LawyerReplyVo boToVo(LawyerReplyBo bo) {
		if (bo == null)
			return null;
		LawyerReplyVo vo = new LawyerReplyVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}

	private OrderWithReplyVo boToVo(OrderBo obo, LawyerReplyBo rbo, List<OrderPhotoBo> orderPhotoList,
			List<OrderPhotoBo> replyPhotoList) {
		OrderWithReplyVo vo = new OrderWithReplyVo();
		vo.setLawyerReplyVo(boToVo(rbo));
		vo.setOrderVo(boToVo(obo));
		if (orderPhotoList != null) {
			List<OrderPhotoVo> result = new ArrayList<OrderPhotoVo>(orderPhotoList.size());
			for (OrderPhotoBo bo : orderPhotoList) {
				result.add(boToVo(bo));
			}
			vo.setOrderPhotoList(result);
		}
		if (replyPhotoList != null) {
			List<OrderPhotoVo> result = new ArrayList<OrderPhotoVo>(replyPhotoList.size());
			for (OrderPhotoBo bo : replyPhotoList) {
				result.add(boToVo(bo));
			}
			vo.setReplyPhotoList(result);
		}
		return vo;
	}
}

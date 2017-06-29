package com.jfzy.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.stereotype.Service;

import com.jfzf.core.Constants;
import com.jfzy.service.IdService;
import com.jfzy.service.LawyerService;
import com.jfzy.service.OrderService;
import com.jfzy.service.SmsService;
import com.jfzy.service.bo.IdTypeEnum;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.service.bo.LawyerStatusEnum;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderPayStatusEnum;
import com.jfzy.service.bo.OrderPhotoBo;
import com.jfzy.service.bo.OrderPhotoTypeEnum;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.bo.PayWayEnum;
import com.jfzy.service.bo.UserLevelEnum;
import com.jfzy.service.bo.WxPayEventBo;
import com.jfzy.service.bo.WxPayResponseDto;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.exception.JfErrorCodeRuntimeException;
import com.jfzy.service.po.OrderPhotoPo;
import com.jfzy.service.po.OrderPo;
import com.jfzy.service.repository.OrderPhotoRepository;
import com.jfzy.service.repository.OrderRepository;
import com.jfzy.service.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	private static final String TIME_FORMAT = "MM月dd日HH时mm分";

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private OrderPhotoRepository orderPhotoRepo;

	@Autowired
	private LawyerService lawyerSerivce;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private IdService idService;

	@Autowired
	private SmsService smsService;

	private int generateId(int page) {

		for (int i = 0; i < 3; ++i) {
			int id = idService.generateId(IdTypeEnum.ORDER.getId(), page);
			if (id > 0) {
				return id;
			}
		}

		throw new JfErrorCodeRuntimeException(400, "订单ID生成失败", "Failed in generated id");
	}

	private String generateOrderSN(String orderTypeStr, int city) {
		DateTime today = new DateTime();
		int page = today.getYear() * 10000 + today.getMonthOfYear() * 100 + today.getDayOfMonth();
		int id = generateId(page);

		return String.format("%s%s%s%s", page, orderTypeStr, city, id);
	}

	@Override
	public OrderBo createSOrder(OrderBo bo) {
		String sn = generateOrderSN(bo.getProductCode(), bo.getCityId());
		bo.setOrderNum(sn);

		if (Constants.PRODUCT_CODE_JIANDANWEN.equals(bo.getProductCode())) {
			bo.setStatus(OrderStatusEnum.NO_PAY.getId());
		} else {
			bo.setStatus(OrderStatusEnum.NO_PAY_NEED_COMPLETED.getId());
		}
		bo.setPayWay(PayWayEnum.NO_PAY.getId());
		OrderPo po = orderRepo.save(boToPo(bo));
		// 升级用户等级
		userRepo.updateLevel(UserLevelEnum.PAIED.getId(), bo.getUserId());
		return poToBo(po);
	}

	@Override
	public OrderBo createIOrder(OrderBo bo) {
		String sn = generateOrderSN(bo.getProductCode(), bo.getCityId());
		bo.setOrderNum(sn);

		if (Constants.PRODUCT_CODE_HUKOU.equals(bo.getProductCode())) {
			bo.setStatus(OrderStatusEnum.NO_PAY.getId());
		} else {
			bo.setStatus(OrderStatusEnum.NO_PAY_NEED_COMPLETED.getId());
		}
		bo.setPayWay(PayWayEnum.NO_PAY.getId());
		OrderPo po = orderRepo.save(boToPo(bo));
		// 升级用户等级
		userRepo.updateLevel(UserLevelEnum.PAIED.getId(), bo.getUserId());
		return poToBo(po);
	}

	@Override
	public WxPayResponseDto pay(int id, int userId, String ip, String openId) {
		// FIXED ME
		OrderPo po = orderRepo.findByUserIdAndId(userId, id);
		if (po == null) {
			throw new JfErrorCodeRuntimeException(400, "订单不存在",
					String.format("ORDER-PAY:Order not found : userId %s, orderId %s", userId, id));
		} else if (po.getPayStatus() == OrderPayStatusEnum.PAYED.getId()) {
			throw new JfErrorCodeRuntimeException(400, "订单已支付",
					String.format("ORDER-PAY:Order already payed : orderId %s", id));
		} else if (po.getPayStatus() == OrderPayStatusEnum.REFUND.getId()) {
			throw new JfErrorCodeRuntimeException(400, "订单已退款",
					String.format("ORDER-PAY:Order already refund : orderId %s", id));
		} else {
			return paymentService.unifiedOrder(poToBo(po), ip, openId);
		}
	}

	@Override
	public void cancel(int id) {
		orderRepo.updateStatus(OrderStatusEnum.CANCELED.getId(), new Timestamp(System.currentTimeMillis()), id);
	}

	@Override
	public void complete(int id, String memo, String[] picList) {
		OrderPhotoBo bo = new OrderPhotoBo();
		for (int i = 0; i < picList.length; i++) {
			bo.setOrderId(id);
			bo.setPhotoPath(picList[i]);
			bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			bo.setType(OrderPhotoTypeEnum.ORDER.getId());
			orderPhotoRepo.save(boToPo(bo));
		}
		orderRepo.updateMemo(memo, new Timestamp(System.currentTimeMillis()), id);
		orderRepo.updateStatus(OrderStatusEnum.NEED_DISPATCH.getId(), new Timestamp(System.currentTimeMillis()), id);

		OrderBo obo = getOrderById(id);
		if (Constants.PRODUCT_CODE_ZIXUNP.equals(obo.getProductCode())) {
			orderRepo.setStartAndEndTime(new Timestamp(System.currentTimeMillis()),
					new Timestamp(getNextWorkingDay(System.currentTimeMillis(), 24 * 60 * 60 * 1000)),
					new Timestamp(System.currentTimeMillis() + 2 * 60 * 60 * 1000),
					new Timestamp(System.currentTimeMillis()), id);
		} else {
			orderRepo.setStartAndEndTime(new Timestamp(System.currentTimeMillis()),
					new Timestamp(getNextWorkingDay(System.currentTimeMillis(), 24 * 60 * 60 * 1000)), null,
					new Timestamp(System.currentTimeMillis()), id);
		}
	}

	@Override
	public void assignOrder(int orderId, int lawyerId, int processorId, String processorName) {

		LawyerBo lawyer = lawyerSerivce.getLawyerById(lawyerId);
		if (lawyer != null && lawyer.getStatus() == LawyerStatusEnum.ACTIVE.getId()) {
			OrderPo po = orderRepo.findOne(orderId);
			if (po == null) {
				throw new JfErrorCodeRuntimeException(400, "无此订单",
						String.format("ASSIGN-ORDER: Order not found:%s", orderId));
			} else if (po.getCityId() != lawyer.getCityId()) {
				throw new JfErrorCodeRuntimeException(400, "城市不匹配",
						String.format("ASSIGN-ORDER: City not match. Order:%s,Lawyer:%s", orderId, lawyerId));
			} else if (po.getStatus() == OrderStatusEnum.NEED_DISPATCH.getId()
					|| po.getStatus() == OrderStatusEnum.DISPATCHED.getId()) {
				po.setLawyerId(lawyerId);
				po.setLawyerName(lawyer.getName());
				po.setLawyerPhoneNum(lawyer.getPhoneNum());
				po.setProcessorId(processorId);
				po.setProcessorName(processorName);
				po.setStatus(OrderStatusEnum.DISPATCHED.getId());
				po.setNotifyStatus(0);
				orderRepo.save(po);
				lawyerSerivce.updateOnProcessTask(1, lawyerId);
				notify(po);
			} else {
				throw new JfErrorCodeRuntimeException(400, "订单状态错误",
						String.format("ASSIGN-ORDER: Order wrong status.Order %s, Status %s", orderId, po.getStatus()));
			}
		} else {
			throw new JfErrorCodeRuntimeException(400, "无此律师或非启用律师",
					String.format("ASSIGN-ORDER: Active lawyer not found:%s", lawyerId));
		}
	}

	private void notify(OrderPo order) {
		String time = getTime(order.getPhoneEndTime(), order.getEndTime());
		String productName = Constants.PRODUCT_CODE_ZIXUNP.equals(order.getProductCode())
				|| Constants.PRODUCT_CODE_ZIXUN.equals(order.getProductCode())
				|| Constants.PRODUCT_CODE_JIANDANWEN.equals(order.getProductCode()) ? "咨询" : "调查";
		smsService.sendLawyerNotify(order.getLawyerPhoneNum(), productName, String.valueOf(order.getRealPrice()), time,
				"");
	}

	private static String getTime(Timestamp phoneEndTime, Timestamp endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		if (phoneEndTime != null) {
			return sdf.format(new Date(phoneEndTime.getTime()));
		} else if (endTime != null) {
			return sdf.format(new Date(endTime.getTime()));
		} else {
			throw new JfApplicationRuntimeException("end time is null");
		}
	}

	public static void main(String[] args) {
		System.out.println(getTime(new Timestamp(System.currentTimeMillis()), null));
	}

	@Override
	public OrderBo getOrderById(int id) {
		OrderPo po = orderRepo.findOne(id);
		if (po == null) {
			throw new JfErrorCodeRuntimeException(400, "此订单不存在", String.format("GET-ORDER: Order not found:%s", id));
		} else {
			return poToBo(po);
		}
	}

	@Override
	public OrderBo getOrderByOrderNum(String orderNum) {
		List<OrderPo> orderPos = orderRepo.findByOrderNum(orderNum);
		if (orderPos != null && orderPos.size() == 1) {
			return poToBo(orderPos.get(0));
		} else if (orderPos != null && orderPos.size() > 1) {
			throw new JfErrorCodeRuntimeException(400, "",
					String.format("GET-ORDER-BY-ORDERNUM:More than one order with orderNum:%s", orderNum));
		} else {
			return null;
		}
	}

	@Override
	public Page<OrderBo> getOrdersByCityIdAndStatus(int cityId, int status, Pageable page) {
		Page<OrderPo> poPage = orderRepo.findByCityIdAndStatus(cityId, status, page);
		return poPageToBoPage(poPage, page);
	}

	private static Page<OrderBo> poPageToBoPage(Page<OrderPo> poPage, Pageable page) {
		List<OrderBo> bos = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> bos.add(poToBo(po)));
		}
		Page<OrderBo> resultPage = new AggregatedPageImpl<OrderBo>(bos, page, poPage.getTotalElements());
		return resultPage;
	}

	@Override
	public List<OrderBo> getOrdersByUser(int userId, Pageable page) {
		Page<OrderPo> poPage = orderRepo.findByUserId(userId, page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public List<OrderBo> getSearchOrders(Pageable page) {
		Page<OrderPo> poPage = orderRepo.getSearchOrders(page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public List<OrderBo> getInvestOrders(Pageable page) {
		Page<OrderPo> poPage = orderRepo.getInvestOrders(page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public List<OrderBo> getSearchOrdersByUser(Pageable page, int userId) {
		Page<OrderPo> poPage = orderRepo.getSearchOrdersById(userId, page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public List<OrderBo> getInvestOrdersByUser(Pageable page, int userId) {
		Page<OrderPo> poPage = orderRepo.getInvestOrdersById(userId, page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public List<OrderBo> getSearchOrdersByLawyer(Pageable page, int lawyerId, int status) {
		Page<OrderPo> poPage = orderRepo.getSearchOrdersByLawyerId(lawyerId, status, page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public List<OrderBo> getInvestOrdersByLawyer(Pageable page, int lawyerId, int status) {
		Page<OrderPo> poPage = orderRepo.getInvestOrdersByLawyerId(lawyerId, status, page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public Page<OrderBo> getOrdresByLawyer(int lawyerId, Pageable page) {
		Page<OrderPo> poPage = orderRepo.findByLawyerId(lawyerId, page);
		List<OrderBo> bos = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> bos.add(poToBo(po)));
		}
		Page<OrderBo> resultPage = new AggregatedPageImpl<OrderBo>(bos, page, poPage.getTotalElements());

		return resultPage;
	}

	@Override
	public List<OrderBo> getUnprocessedOrdersByLawyer(int lawyerId, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void markPayed(WxPayEventBo bo, int userId) {
		List<OrderPo> pos = orderRepo.findByOrderNum(bo.getOutTradeNo());
		if (pos != null && pos.size() == 1) {
			OrderPo po = pos.get(0);
			if (po != null && userId == po.getUserId()) {
				if (po.getPayStatus() == OrderPayStatusEnum.NOT_PAYED.getId()) {

					if (po.getStatus() == OrderStatusEnum.NO_PAY_NEED_COMPLETED.getId()) {
						orderRepo.updatePayStatusAndStatus(OrderPayStatusEnum.PAYED.getId(),
								OrderStatusEnum.NOT_COMPLETED.getId(), po.getId());
					} else {
						orderRepo.updatePayStatusAndStatus(OrderPayStatusEnum.PAYED.getId(),
								OrderStatusEnum.NEED_DISPATCH.getId(), po.getId());
						orderRepo.setStartAndEndTime(new Timestamp(System.currentTimeMillis()),
								new Timestamp(getNextWorkingDay(System.currentTimeMillis(), 24 * 60 * 60 * 1000)), null,
								new Timestamp(System.currentTimeMillis()), po.getId());
					}
				}
			} else {
				logger.error(String.format("ORDER-MARKPAYED: Order user unable to match.Order_num:%s,userId:%s",
						bo.getOutTradeNo(), userId));
			}
		} else if (pos == null || pos.size() <= 0) {
			logger.error(String.format("ORDER-MARKPAYED: Order not found: %s", bo.getOutTradeNo()));
		} else {
			logger.error(String.format("ORDER-MARKPAYED: Multi order with order_num:%s", bo.getOutTradeNo()));
		}
	}

	@Override
	public void acceptorOrder(int lawyerId, int orderId) {
		LawyerBo lawyer = lawyerSerivce.getLawyerById(lawyerId);
		if (lawyer == null) {
			throw new JfErrorCodeRuntimeException(400, "律师不存在",
					String.format("ORDER-ACCEPTORDER:Lawyer not found: %s", lawyerId));
		}
		OrderPo order = orderRepo.findOne(orderId);
		if (order == null) {
			throw new JfErrorCodeRuntimeException(400, "订单不存在",
					String.format("ORDER-ACCEPTORDER:Order not found: %s", orderId));
		}
	}

	@Override
	public List<OrderPhotoBo> getOrderPhotos(int orderId) {
		List<OrderPhotoPo> pos = orderPhotoRepo.findByOrderIdAndType(orderId, OrderPhotoTypeEnum.ORDER.getId());
		List<OrderPhotoBo> results = new ArrayList<OrderPhotoBo>(pos.size());
		pos.forEach(po -> results.add(poToBo(po)));
		return results;
	}

	@Override
	public List<OrderPhotoBo> getReplyPhotos(int orderId) {
		List<OrderPhotoPo> pos = orderPhotoRepo.findByOrderIdAndType(orderId, OrderPhotoTypeEnum.REPLY.getId());
		List<OrderPhotoBo> results = new ArrayList<OrderPhotoBo>(pos.size());
		pos.forEach(po -> results.add(poToBo(po)));
		return results;
	}

	@Override
	public void updateOrderStatus(int orderId, int previousStatus, int newStatus) {
		orderRepo.updateStatus(orderId, previousStatus, newStatus);
	}

	@Override
	public void updateOrderStatus(int orderId, int newStatus) {
		orderRepo.updateStatus(orderId, newStatus);
	}

	@Override
	public List<OrderBo> getUnconfirmedOrders(int size) {
		PageRequest page = new PageRequest(1, size);
		Page<OrderPo> pos = orderRepo.findByStatus(OrderStatusEnum.DISPATCHED.getId(), page);

		List<OrderBo> results = new ArrayList<OrderBo>(20);
		if (pos != null) {
			pos.forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public int getNumbersOfUnAssignedOrdersByCity(int city) {
		return orderRepo.countByCityIdAndStatus(city, OrderStatusEnum.NEED_DISPATCH.getId());
	}

	@Override
	public int getTotal(int userId) {
		return orderRepo.getTotal(userId);
	}

	private static OrderPo boToPo(OrderBo bo) {
		OrderPo po = new OrderPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	private static OrderBo poToBo(OrderPo po) {
		OrderBo bo = new OrderBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	private static OrderPhotoPo boToPo(OrderPhotoBo bo) {
		OrderPhotoPo po = new OrderPhotoPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	private static OrderPhotoBo poToBo(OrderPhotoPo po) {
		OrderPhotoBo bo = new OrderPhotoBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	private static long getNextWorkingDay(long startTimeOfMs, long msToRoll) {
		DateTime date = new DateTime(startTimeOfMs);
		date = date.plus(msToRoll);
		if (date.getDayOfWeek() == 6) {
			date = date.plusDays(2);
		} else if (date.getDayOfWeek() == 7) {
			date = date.plusDays(1);
		}

		return date.getMillis();
	}

	@Override
	public Page<OrderBo> getUncompletedOrdersByCityIdAndStatus(int cityId, Integer status, Pageable page) {
		if (status != null) {
			Page<OrderPo> poPage = orderRepo.findUncompletedOrderByCityIdAndStatusOrderByAsc(cityId, status, page);
			return poPageToBoPage(poPage, page);
		} else {
			Page<OrderPo> poPage = orderRepo.findUncompletedOrdersByCityIdOrderByAsc(cityId, page);
			return poPageToBoPage(poPage, page);
		}
	}

	@Override
	public Page<OrderBo> getCompletedOrdersByCityIdAndStatus(int cityId, Integer status, Pageable page) {
		if (status != null) {
			Page<OrderPo> poPage = orderRepo.findCompletedByCityIdAndStatusOrderByDesc(cityId, status, page);
			return poPageToBoPage(poPage, page);
		} else {
			Page<OrderPo> poPage = orderRepo.findCompletedByCityIdOrderByDesc(cityId, page);
			return poPageToBoPage(poPage, page);
		}
	}

}

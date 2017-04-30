package com.destinesia.app.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.destinesia.base.LJConstants;
import com.destinesia.base.Utility;
import com.destinesia.entity.Customer;
import com.destinesia.entity.Order;
import com.destinesia.entity.dto.CustomerDTO;
import com.destinesia.entity.dto.OrderDTO;
import com.destinesia.service.IOrderService;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController{
	
	@Autowired
	private IOrderService orderService;
	
	/**
	 * @api {POST} /order/detail 获取订单详情
	 * @apiGroup order
	 * @apiVersion 1.0.0
	 * @apiDescription 根据订单ID获取订单详情
	 * @apiParam {String} orderId 订单ID
	 * @apiParam {String} token 登录后的认证
	 * @apiParamExample {json} 请求样例：
	 * 		{
	 *        "orderId":"",
	 *        "token":""
	 *      }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/detail", method=RequestMethod.POST)
	@ResponseBody
	public Object detail(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {	
		String id = request.getParameter("id");
		String token = request.getParameter("token");
		return orderService.findOrderById(id, token);
	}
	
	/**
	 * @api {POST} /order/create 创建订单
	 * @apiGroup order
	 * @apiVersion 1.0.0
	 * @apiDescription 创建订单
	 * @apiParam {String} id 订单ID
	 * @apiParam {String} token 登录后的认证
	 * @apiParamExample {json} 请求样例：
	 * 		{ 
	 * 		    "token": "690208343a974ba0a1146ccd81b7757d",
	 * 		    "customer": 
	 * 		    {
	 * 		        "id":"c6f1090312f1467a910cd3fd1294eba0"
	 * 		    },
	 * 		    "totalPrice": 55.66,
	 * 		    "orderDetails":[
	 * 		        {	
	 *                  "product":
     * 		             {
     *                       "id":"0a549f3950134df9b0bd6bd09aa8daf3"
     *                   },
     * 		  	        "count":1
     * 		       },
     * 		       {	
     *                 "product":
     *                  {
     *                       "id":"d63899b95bfb4f8c8894a544d2314e03"
     *                  },
     *                 "count":3
     *             }
     *         ]
   	 *      }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/create", method=RequestMethod.POST)
	@ResponseBody
	public void create(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderDTO orderDto)
			throws ServletRequestBindingException {	
		String token = orderDto.getToken();
		Order order = OrderDTO.convert(orderDto);
		if(order.getId() == null){
			order.setId(Utility.generageId());
		}
		orderService.addOrder(order, token);
	}
	
	/**
	 * @api {POST} /order/newcustomer 创建新的收货人信息
	 * @apiGroup order
	 * @apiVersion 1.0.0
	 * @apiDescription 创建新的收货人信息
	 * @apiParam {String} token 登录后的认证
	 * @apiParamExample {json} 请求样例：
	 * 		{
	 *          "token": "690208343a974ba0a1146ccd81b7757d",
     *          "name": "张三",
     *          "address": "上海市徐汇区",
     *          "addressCode": "200000",
     *          "phone": "15888888888"
	 *      }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/newcustomer", method=RequestMethod.POST)
	@ResponseBody
	public void newcustomer(HttpServletRequest request, HttpServletResponse response, @RequestBody CustomerDTO customerDto)
			throws ServletRequestBindingException {	
		String token = customerDto.getToken();
		Customer customer = CustomerDTO.convert(customerDto);
		if(customer.getId() == null){
			customer.setId(Utility.generageId());
		}
		orderService.addCustomer(customer, token);
	}

	/**
	 * @api {POST} /order/customers 获取收件人的列表，分页
	 * @apiGroup order
	 * @apiVersion 1.0.0
	 * @apiDescription 创建新的收货人信息
	 * @apiParam {String} token 登录后的认证
	 * @apiParamExample {json} 请求样例：
	 * 		{
	 *          "token": "690208343a974ba0a1146ccd81b7757d"
	 *      }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/customers", method=RequestMethod.POST)
	@ResponseBody
	public Object customers(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {	
		String token = request.getParameter("token");
		int from = request.getParameter("from")==null?0:Integer.parseInt(request.getParameter("from"));
		int size = request.getParameter("size")==null?LJConstants.pageSize:Integer.parseInt(request.getParameter("size"));
		return orderService.getCustomersByUserId(token, from, size);
	}
	/**
	 * @api {POST} /order/orders 获取用户的订单列表
	 * @apiGroup order
	 * @apiVersion 1.0.0
	 * @apiDescription 获取用户的订单列表
	 * @apiParam {String} token 登录后的认证
	 * @apiParam {int} from 第几页，从0开始
	 * @apiParam {int} size 每页数量，不传为30
	 * @apiParamExample {json} 请求样例：
	 * 		{
	 *        "token":"b729364b860348bea46d4ef5217e8bb6",
	 *        "from":"1",
	 *        "size":"25"
	 *      }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/orders", method=RequestMethod.POST)
	@ResponseBody
	public Object orders(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String token = request.getParameter("token");
		int from = request.getParameter("from")==null?0:Integer.parseInt(request.getParameter("from"));
		int size = request.getParameter("size")==null?LJConstants.pageSize:Integer.parseInt(request.getParameter("size"));
		return orderService.getOrdersByUserId(token, from, size);
	}
	
	/**
	 * @api {POST} /order/products 获取产品的列表
	 * @apiGroup order
	 * @apiVersion 1.0.0
	 * @apiDescription 获取产品的列表
	 * @apiParam {int} from 第几页，从0开始
	 * @apiParam {int} size 每页数量，不传为30
	 * @apiParamExample {json} 请求样例：
	 * 		{
	 *        "from":"1",
	 *        "size":"25"
	 *      }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/products", method=RequestMethod.POST)
	@ResponseBody
	public Object products(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		int from = request.getParameter("from")==null?0:Integer.parseInt(request.getParameter("from"));
		int size = request.getParameter("size")==null?LJConstants.pageSize:Integer.parseInt(request.getParameter("size"));
		return orderService.getProducts(from, size);
	}
	
	/**
	 * @api {POST} /order/productdetail 获取产品的详细信息
	 * @apiGroup order
	 * @apiVersion 1.0.0
	 * @apiDescription 获取产品的详细信息
	 * @apiParam {String} productId 产品的ID
	 * @apiParamExample {json} 请求样例：
	 * 		{
	 *        "productId":"d63899b95bfb4f8c8894a544d2314e03"
	 *      }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/productdetail", method=RequestMethod.POST)
	@ResponseBody
	public Object productdetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String productId = request.getParameter("productId");
		return orderService.findProductById(productId);
	}
	
	/**
	 * @api {POST} /order/pay 完成订单支付
	 * @apiGroup order
	 * @apiVersion 1.0.0
	 * @apiDescription 完成订单支付
	 * @apiParam {String} orderId 订单的ID
	 * @apiParam {String} token 登录后的认证
	 * @apiParamExample {json} 请求样例：
	 * 		{
	 *        "orderId":"d63899b95bfb4f8c8894a544d2314e03",
	 *        "token":"b729364b860348bea46d4ef5217e8bb6"
	 *      }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/pay", method=RequestMethod.POST)
	@ResponseBody
	public void pay(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String token = request.getParameter("token");
		//orderService.findOrderById(id, token);
	}
}
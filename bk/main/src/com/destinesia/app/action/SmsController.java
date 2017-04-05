package com.destinesia.app.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.destinesia.service.ISmsService;

@Controller
@RequestMapping(value = "/sms")
public class SmsController extends BaseController{
	@Autowired
	private ISmsService smsService;
	
	/**
	 * @api {POST} /sms/regist 获取注册验证码
	 * @apiGroup sms
	 * @apiVersion 1.0.0
	 * @apiDescription 用户获取注册验证码
	 * @apiParam {String} phone 注册用户手机号码
	 * @apiParamExample {json} 请求样例：
	 * 		{"phone":"15888888888"}
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/regist", method=RequestMethod.POST)
	@ResponseBody
	public void regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {	
		String phone = request.getParameter("phone");
		smsService.regist(phone);
	}
	
	/**
	 * @api {POST} /sms/login 获取登录验证码
	 * @apiGroup sms
	 * @apiVersion 1.0.0
	 * @apiDescription 用户获取登录验证码
	 * @apiParam {String} phone 登录用户手机号码
	 * @apiParamExample {json} 请求样例：
	 * 		{"phone":"15888888888"}
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {	
		String phone = request.getParameter("phone");
		smsService.login(phone);
	}
	
	/**
	 * @api {POST} /sms/reset 用户重置密码
	 * @apiGroup sms
	 * @apiVersion 1.0.0
	 * @apiDescription 用户重新设置密码，会发送新生成的密码给用户。
	 * @apiParam {String} phone 用户手机号码
	 * @apiParamExample {json} 请求样例：
	 * 		{"phone":"15888888888"}
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"passcode"}   
	 */
	@RequestMapping(value = "/reset", method=RequestMethod.POST)
	@ResponseBody
	public void reset(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {	
		String phone = request.getParameter("phone");
		smsService.reset(phone);
	}
	
	
	/**
	 * @api {POST} /sms/check 验证验证码正确性
	 * @apiGroup sms
	 * @apiVersion 1.0.0
	 * @apiDescription 验证验证码正确性，不正确就抛出异常
	 * @apiParam {String} phone 用户手机号码
	 * @apiParam {String} passcode 手机短信验证码
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "phone":"15899999999",
	 * 	  "passcode":"368734"
	 * }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"手机号码已经存在"}   
	 */
	@RequestMapping(value = "/check", method=RequestMethod.POST)
	@ResponseBody
	public void check(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {	
		String phone = request.getParameter("phone");
		String passcode = request.getParameter("passcode");
		smsService.valideCode(phone, passcode);
	}
}

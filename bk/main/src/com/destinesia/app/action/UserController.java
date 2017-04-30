package com.destinesia.app.action;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.destinesia.base.CVSExport;
import com.destinesia.base.StatusMsg;
import com.destinesia.base.Utility;
import com.destinesia.entity.Code;
import com.destinesia.entity.TokenInfo;
import com.destinesia.entity.User;
import com.destinesia.entity.dto.LoginReturnDTO;
import com.destinesia.entity.dto.WXUserDTO;
import com.destinesia.service.IUserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{
	@Autowired
	private IUserService userService;
	
	/**
	 * @api {POST} /user/recommend 邀请码验证
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 用于验证用户邀请码是否正确
	 * @apiParam {String} recommend 邀请码
	 * @apiParamExample {json} 请求样例：
	 * 		{"recommend":"wk68vn3d"}
	 * @apiSuccess (200) {int} grade 代表等级，1为1星，5为5星
	 * @apiSuccessExample {json} 返回样例: 
	 * 		{"grade":"3"}
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"邀请码为空"}   
	 */
	@RequestMapping(value = "/recommend", method=RequestMethod.POST)
	@ResponseBody
	public Object recommend(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {	
		String recommend = request.getParameter("recommend");
		int grade = userService.getGrade(recommend);
		if(grade>0){
			Map<String, String> result = new HashMap<String, String>();
			result.put("grade", grade+"");
			return result;
		}else{
			response.setStatus(400);
			StatusMsg msg = new StatusMsg(StatusMsg.CODE_NOT_FOUND, "邀请码似乎不正确，请尝试重新输入");
			throw msg;
		}
	}
	
	/**
	 * @api {POST} /user/regist 注册用户
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 用于注册用户
	 * @apiParam {String} nickName 昵称
	 * @apiParam {String} phone 手机号码*
	 * @apiParam {String} passcode 短信验证码*
	 * @apiParam {String} name 账号
	 * @apiParam {String} password 密码，MD5加密返回*
	 * @apiParam {String} mail 邮箱
	 * @apiParam {double} lat 维度
	 * @apiParam {double} lon 经度
	 * @apiParam {String} deviceId 硬件设备号
	 * @apiParam {String} recommend 推荐码
	 * @apiParam {String} inviteCode 邀请码
	 * @apiParam {int} plantform = 0  平台 0 IOS 1 Android
	 * @apiParamExample {json} 请求样例：
	 * {
	 *   "nickName": "love destinesia",
	 *   "name":"love_desti",
	 *   "phone":"15888888888",
	 *   "passcode":"340983",
	 *   "password": "e10adc3949ba59abbe56e057f20f883e",
	 *   "mail": "traval@destinesia.cn",
	 *   "deviceNO": "1FD69B7C03308FAF",
	 *   "recommend":"wk68vn3d",
	 *   "inviteCode":"113323",
	 *   "lat":114.190841674805,
	 *   "lon":121.476173400879
	 * }
	 * 
	 * @apiSuccess (200) {String} nickName 用户昵称
	 * @apiSuccess (200) {int} grade 代表等级，1为1星，5为5星
	 * @apiSuccess (200) {String} token 用户身份TOKEN
	 * @apiSuccessExample {json} 返回样例:
	 * {
	 *    "nickName":"love destinesia",
	 *    "grade":3,
	 *    "token":"ce94db3b1fd69b7c03308faf2cc912d8"
	 * }
	 * 
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"XX为空"} 
	 */
	@RequestMapping(value = "/regist")
	@ResponseBody
	public Object regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String nickName = request.getParameter("nickName");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String mail = request.getParameter("mail");
		String deviceNO = request.getParameter("deviceNO");
		String recommend = request.getParameter("recommend");
		String inviteCode = request.getParameter("inviteCode");
		String latStr = request.getParameter("lat");
		String lonStr = request.getParameter("lon");
		String passcode = request.getParameter("passcode");
		
		User user = new User();
		user.setId(Utility.generageId());
		user.setNickName(nickName);
		user.setName(name);
		user.setPhone(phone);
		user.setPassword(password);
		user.setMail(mail);
		user.setRecommend(recommend);
		user.setInviteCode(inviteCode);
		user.setDeviceNO(deviceNO);
		user.setLatitude(latStr==null?0:new Double(latStr));
		user.setLongitude(lonStr==null?0:new Double(lonStr));
		
		TokenInfo tokenInfo = userService.regist(user, passcode);
		LoginReturnDTO loginReturnDTO = new LoginReturnDTO();
		loginReturnDTO.setGrade(user.getGrade());
		loginReturnDTO.setNickName(user.getNickName());
		loginReturnDTO.setToken(tokenInfo.getValue());
		return loginReturnDTO;
	}

	 /**
	 * @api {POST} /user/login 用户登录
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 用于用户登录
	 * @apiParam {String} key 用户邮箱/用户手机号码
	 * @apiParam {String} password 密码，MD5加密
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "key":"traval@destinesia.cn",
	 * 	  "password":"e10adc3949ba59abbe56e057f20f883e"
	 * }
	 *
	 * @apiSuccess (200) {String} nickName 用户昵称
	 * @apiSuccess (200) {int} grade 代表等级，1为1星，5为5星
	 * @apiSuccess (200) {String} token 用户身份TOKEN
	 * @apiSuccessExample {json} 返回样例:
	 * {
	 *    "nickName":"love destinesia",
	 *    "grade":3,
	 *    "token":"ce94db3b1fd69b7c03308faf2cc912d8"
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"用户不存在"} 
	 */
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String key = request.getParameter("key");
		String password = request.getParameter("password");
		if(key == null || password==null){
			throw new StatusMsg(StatusMsg.CODE_BAD_REQUEST, "登录信息输入不全");
		}
		
		LoginReturnDTO loginReturnDTO = userService.login(key, password);
		if(loginReturnDTO == null){
			throw new StatusMsg(StatusMsg.CODE_BAD_REQUEST, "登录信息似乎不正确，请重试");
		}else{
			return loginReturnDTO;
		}
	}
	
	 /**
	 * @api {POST} /user/loginbyphone 用户短信验证码登录(暂时不用)
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 用于用户登录
	 * @apiParam {String} phone 用户手机号码
	 * @apiParam {String} passcode 手机短信验证码
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "phone":"15899999999",
	 * 	  "passcode":"368734"
	 * }
	 *
	 * @apiSuccess (200) {String} nickName 用户昵称
	 * @apiSuccess (200) {int} grade 代表等级，1为1星，5为5星
	 * @apiSuccess (200) {String} token 用户身份TOKEN
	 * @apiSuccess (200) {boolean} new 是否是新注册
	 * @apiSuccessExample {json} 返回样例:
	 * {
	 *    "nickName":"love destinesia",
	 *    "grade":3,
	 *    "token":"ce94db3b1fd69b7c03308faf2cc912d8",
	 *    "new":false
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1","message":"用户不存在"} 
	 */
	@RequestMapping(value = "/loginbyphone", method=RequestMethod.POST)
	@ResponseBody
	public Object loginbyphone(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String phone = request.getParameter("phone");
		String passcode = request.getParameter("passcode");
		if(phone == null || passcode==null){
			throw new StatusMsg(StatusMsg.CODE_BAD_REQUEST, "登录信息输入不全");
		}
		
		LoginReturnDTO loginReturnDTO = userService.loginbyphone(phone, passcode);
		if(loginReturnDTO == null){
			throw new StatusMsg(StatusMsg.CODE_BAD_REQUEST, "登录信息似乎不正确，请重试！");
		}else{
			return loginReturnDTO;
		}
	}
	
	/**
	 * @api {POST} /user/reset 获取/重置密码
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 帮助用户重置密码，并返回用户
	 * 
	 * @apiParam {String} phone 用户手机号码
	 * @apiParam {String} password 用户新密码
	 * @apiParam {String} passcode 短信验证码
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "phone":"15800888888",
	 *    "passcode":"398412",
	 *    "password":"e10adc3949ba59abbe56e057f20f883e"
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"用户不存在"} 
	 */
	@RequestMapping(value = "/reset", method=RequestMethod.POST)
	@ResponseBody
	public void resetPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String phone = request.getParameter("phone");
		String password= request.getParameter("password");
		String passcode = request.getParameter("passcode");
		userService.resetPassword(phone, password, passcode);
	}
	
	/**
	 * @api {GET} /user/generator 批量生成邀请码
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 批量生成邀请码
	 * @apiParam {String} secrete 秘钥，必须为 88b64c7bf77d440a8725e052d765c755
	 * @apiParam {String} count 创建数量
	 * @apiParam {String} grade 指定等级，默认为0
	 * @apiParam {String} identity 推荐人手机号码
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "secrete":"88b64c7bf77d440a8725e052d765c755",
	 * 	  "count":"1000",
	 * 	  "grade":"3",
	 *    "identity":"15888888888",
	 *    
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"创建失败"} 
	 */
	@RequestMapping(value = "/generator", method=RequestMethod.GET)
	@ResponseBody
	public Object generatCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		
		String secrete = request.getParameter("secrete");
		if(!"88b64c7bf77d440a8725e052d765c755".equals(secrete)){
			return "没有权限";
		}
		int count = Integer.parseInt(request.getParameter("count"));
		int grade = Integer.parseInt(request.getParameter("grade")!=null?request.getParameter("grade"):"0");
		String identity = request.getParameter("identity");
		List<Code> codes = userService.generateCode(count, grade, identity);
		
		try (OutputStream os = response.getOutputStream()) {
			CVSExport.responseSetProperties("code", response);
			CVSExport.doExport(codes, os);
		} catch (Exception e) {
		}
		return null;
		
	}
	/**
	 * @api {POST} /user/checknickname 检查昵称是否已经存在
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 检查昵称是否已经存在
	 * @apiParam {String} nickName 昵称
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "nickName":"love destinesia",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"nickName"} 
	 */
	@RequestMapping(value = "/checknickname", method=RequestMethod.POST)
	@ResponseBody
	public void checkNickname(HttpServletRequest request, HttpServletResponse response){
		String nickName =request.getParameter("nickName");
		User user = userService.findUserByNickName(nickName);
		if(user !=null){
			throw new StatusMsg(StatusMsg.CODE_ALREADY_EXISTS, "已存在关联此号码的账号，请尝试");
		}
	}
	/**
	 * @api {POST} /user/checkmail 检查邮箱是否存在
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 检查邮箱是否存在
	 * @apiParam {String} email 昵称
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "email":"love@destinesia.cn",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"mail"} 
	 */
	@RequestMapping(value = "/checkmail", method=RequestMethod.POST)
	@ResponseBody
	public void checkEmail(HttpServletRequest request, HttpServletResponse response){
		String email = request.getParameter("mail");
		User user = userService.findUserByEmail(email);
		if(user !=null){
			throw new StatusMsg(StatusMsg.CODE_ALREADY_EXISTS, "已存在关联此号码的账号，请尝试");
		}
	}
	
	/**
	 * @api {POST} /user/checkphone 检查号码是否存在
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 检查号码是否存在
	 * @apiParam {String} phone 电话号码
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "phone":"15888888888",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"phone"} 
	 */
	@RequestMapping(value = "/checkphone", method=RequestMethod.POST)
	@ResponseBody
	public void checkphone(HttpServletRequest request, HttpServletResponse response){
		String phone = request.getParameter("phone");
		User user = userService.findUserByPhone(phone);
		if(user !=null){
			throw new StatusMsg(StatusMsg.CODE_ALREADY_EXISTS, "已存在关联此号码的账号，请尝试");
		}
	}
	
	/**
	 * @api {POST} /user/list 罗列所有用户
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 罗列所有用户
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":""} 
	 */
	@RequestMapping(value = "/list", method=RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response){
		List<User> users = userService.list("");
		return users;
	}
	
	/**
	 * @api {POST} /user/enable 启用用户
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 启用用户
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":""} 
	 */
	@RequestMapping(value = "/enable", method=RequestMethod.POST)
	@ResponseBody
	public Object enable(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		userService.enableUser(id, "");
		return new ArrayList();
	}
	
	/**
	 * @api {POST} /user/disable 禁用用户
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 禁用用户
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":""} 
	 */
	@RequestMapping(value = "/disable", method=RequestMethod.POST)
	@ResponseBody
	public Object disable(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		userService.disableUser(id, "");
		return new ArrayList();
	}
	
	/**
	 * @api {POST} /user/wxregist 微信用户注册以及登录
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 微信用户注册以及登录
	 * 
	 * @apiParam {String} openid 用户的唯一微信ID
	 * @apiParam {String} nickname 用户的昵称
	 * @apiParam {int} sex 用户性别
	 * @apiParam {String} province 省
	 * @apiParam {String} city 市
	 * @apiParam {String} country 国家
	 * @apiParam {String} headimgurl 头像地址
	 * @apiParam {String} unionid 唯一ID
	 * @apiParam {double} lat 用户所在位置纬度
	 * @apiParam {double} lon 用户所在位置精度
	 * @apiParam {String} deviceNO 用户的手机硬件号
	 * 
	 * @apiSuccess (200) {String} nickName 用户昵称
	 * @apiSuccess (200) {int} grade 代表等级，1为1星，5为5星
	 * @apiSuccess (200) {String} token 用户身份TOKEN
	 * @apiSuccess (200) {boolean} new 是否是新注册
	 * @apiSuccessExample {json} 返回样例:
	 * {
	 *    "nickName":"love destinesia",
	 *    "grade":3,
	 *    "token":"ce94db3b1fd69b7c03308faf2cc912d8",
	 *    "new":false
	 * }
	 * 
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":""} 
	 */
	@RequestMapping(value = "/wxregist", method=RequestMethod.POST)
	@ResponseBody
	public Object wxregist(HttpServletRequest request, HttpServletResponse response,  @RequestBody WXUserDTO wxuserDto){
		User user = new User();
		user.setId(Utility.generageId());
		user.setHeadimg(wxuserDto.getHeadimgurl());
		user.setNickName(wxuserDto.getNickname());
		user.setDeviceNO(wxuserDto.getDeviceNO());
		user.setLatitude(wxuserDto.getLat());
		user.setLongitude(wxuserDto.getLon());
		user.setOpenid(wxuserDto.getOpenid());
		
		TokenInfo tokenInfo = userService.wxregist(user);
		LoginReturnDTO loginReturnDTO = new LoginReturnDTO();
		loginReturnDTO.setGrade(user.getGrade());
		loginReturnDTO.setNickName(user.getNickName());
		loginReturnDTO.setToken(tokenInfo.getValue());
		loginReturnDTO.setNew(tokenInfo.isNew());
		return loginReturnDTO;
	}
	
	/**
	 * @api {POST} /user/updategrade 根据邀请码，提升用户等级
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 根据邀请码，提升用户等级，如果用户等级比邀请码的高，保留用户原等级
	 * 
	 * @apiParam {String} token 用户登录的token
	 * @apiParam {String} recommend 邀请码
	 * 
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":""} 
	 */
	@RequestMapping(value = "/updategrade", method=RequestMethod.POST)
	@ResponseBody
	public int recommend(HttpServletRequest request, HttpServletResponse response,  @RequestBody WXUserDTO wxuserDto){
		String token = request.getParameter("token");
		String recommend = request.getParameter("recommend");
		return userService.updateUserGrade(token, recommend);
	}
	
	/**
	 * @api {POST} /user/updateheader 修改用户头像
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 用户修改头像
	 * 
	 * @apiParam {String} token 用户登录的token
	 * @apiParam {String} header 新头像图片地址
	 * 
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":""} 
	 */
	@RequestMapping(value = "/updateheader", method=RequestMethod.POST)
	@ResponseBody
	public Object updateheader(HttpServletRequest request, HttpServletResponse response,  @RequestBody WXUserDTO wxuserDto){
		String token = request.getParameter("token");
		String header = request.getParameter("header");
		userService.updateHeader(token, header);
		return "";
	}
	
	/**
	 * @api {POST} /user/albumcounts 获取用户专辑和杂志数量
	 * @apiGroup user
	 * @apiVersion 1.0.0
	 * @apiDescription 获取用户专辑和杂志数量
	 * 
	 * @apiParam {String} token 用户登录的token
     * @apiSuccess (200) {int} albumCount 用户专辑数量
	 * @apiSuccess (200) {int} magzineCount 用户杂志（打印专辑）数量
	 * @apiSuccessExample {json} 返回样例:
	 * {
	 *    "albumCount":"20",
	 *    "magzineCount":3,
	 * }
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":""} 
	 */
	@RequestMapping(value = "/albumcounts", method=RequestMethod.POST)
	@ResponseBody
	public Object albumCounts(HttpServletRequest request, HttpServletResponse response){
		String token = request.getParameter("token");
		return userService.albumCounts(token);
	}
}
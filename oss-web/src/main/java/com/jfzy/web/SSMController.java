package com.jfzy.web;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzf.core.Utils;
import com.jfzy.base.OssWebConstants;
import com.jfzy.service.SmsService;
import com.jfzy.service.repository.RedisRepository;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;

@RestController
public class SSMController extends BaseController {

	@Autowired
	private SmsService smsService;

	@Autowired
	private RedisRepository redisRepo;

	@ResponseBody
	@GetMapping("/api/ssm/code")
	public ResponseVo<String> getCode(HttpServletRequest request, String phoneNum) {
		String code = Utils.getSmsCode(4);

		redisRepo.setWithTimeout(String.format(OssWebConstants.REDIS_PREFIX_VERIFY_CODE, phoneNum), code,
				OssWebConstants.TIMEOUT_VERIFY_CODE);

		smsService.sendRegisterCode(phoneNum, code);
		return new ResponseVo<String>(ResponseStatusEnum.SUCCESS.getCode(), "发送成功", null);
	}
}
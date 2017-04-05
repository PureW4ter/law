package com.destinesia.app.action;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import com.destinesia.base.StatusMsg;

public class BaseController {
	private static Logger logger = Logger.getLogger(BaseController.class);  
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public StatusMsg handleException(Exception ex) {
		logger.error("Catch Exception: ",ex);
		StatusMsg s = null;
		if(ex instanceof DataAccessException){
			s = new StatusMsg(StatusMsg.CODE_DATABASE, "数据出错了");
		}else if(ex instanceof StatusMsg){
			s = (StatusMsg)ex;
		}else{
			s = new StatusMsg(StatusMsg.CODE_RUNTIME, "执行出错了");
		}
		return s;
	}
}

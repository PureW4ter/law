package com.destinesia.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.ModelAndView;
  
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;  
  
//@Component
public class BasicHandlerExceptionResolver implements HandlerExceptionResolver{  
    private static Logger logger = Logger.getLogger(BasicHandlerExceptionResolver.class);  
    
    @Override  
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
        logger.error("Catch Exception: ",ex);
        
        return null;
    }  
  
}  
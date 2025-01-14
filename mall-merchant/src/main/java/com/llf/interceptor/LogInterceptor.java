package com.llf.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.llf.controller.MerchantInfoController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@Component
public class LogInterceptor implements HandlerInterceptor {
	
	private static Logger logger = LogManager.getLogger(LogInterceptor.class);
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	logger.info("Request Method: {}", request.getMethod());
    	logger.info("Request URI: {}", request.getRequestURI());
    	logger.info("Request Headers: {}", request.getHeaderNames().hasMoreElements() ? request.getHeaderNames() : "none");
//    	logger.info("Request Body: {}", request.getReader());
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            System.out.println("返回参数: " + modelAndView.getModel());
        }
    }
}
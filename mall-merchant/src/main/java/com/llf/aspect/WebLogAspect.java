package com.llf.aspect;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 打印请求和响应信息
 */
@Aspect
@Component
public class WebLogAspect {

    //拿到日志对象
	//slf4j的日志对象
    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    
    @Pointcut("execution(public * com.llf.controller.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        //收到请求,记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        //记录请求url
        logger.info("URL: " + request.getRequestURL().toString());
        //记录请求方法
        logger.info("METHOD: " + request.getMethod());
        //记录请求IP
        logger.info("IP: " + request.getRemoteAddr());
        //获取类的信息
        logger.info("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //参数信息
        logger.info("请求报文: " + JSONObject.toJSONString(Arrays.asList(joinPoint.getArgs()).get(0)));
    }

    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //处理完请求，返回内容
    	logger.info("返回报文 : " + new ObjectMapper().writeValueAsString(res));
    }
}



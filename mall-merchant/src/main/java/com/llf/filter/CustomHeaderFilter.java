package com.llf.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpHeaders;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(-98)
//@Component
public class CustomHeaderFilter implements Filter {
 
	 @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	    }

	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpSession httpSession = req.getSession();
	        httpSession.setAttribute("name", "张三");
	        httpSession.setAttribute("age", "20");
	        String seesionId = httpSession.getId();
	        
	        System.out.println("sessionId："+seesionId);
	        
//	        // 1.获取的是数组类型的cookie
//	        Cookie[] cookies = req.getCookies();
//	        // 循环或者这么取都行
//	        if ("".equals(authorization) || authorization == null) {
//	            //如果get请求url中带有这个参数，则request中新增一个header
//	            requestWrapper.addHeader(HttpHeaders.AUTHORIZATION, "200");
//	            requestWrapper.addHeader("113", "200");
//	            requestWrapper.addHeader("2345", "200");
//	            rsp.addHeader("Authorization", "123");
////	            Cookie cookie = new Cookie("sessionId", seesionId);
////	            rsp.addCookie(cookie);
////	            HttpSession httpSession = req.getSession(true);
////	            httpSession.set
//	            // Goes to default servlet.
//	            chain.doFilter(requestWrapper, response);
//	            return;
//	        }
	        chain.doFilter(request, response);
	    }

	    @Override
	    public void destroy() {
	    }
}
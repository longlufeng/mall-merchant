package com.llf.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.stereotype.Component;

import java.util.*;
 
/**
 *  Filter请求拦截中添加header属性 
 *
 */
public class CustomRequestWrapper extends HttpServletRequestWrapper {
 
    private Map<String, String> headerMap = new HashMap<String, String>();
 
    public CustomRequestWrapper(HttpServletRequest request) {
        super(request);
    }
 
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }
 
    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        if (headerMap.containsKey(name)) {
            headerValue = headerMap.get(name);
        }
        return headerValue;
    }
 
    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        for (String name : headerMap.keySet()) {
            names.add(name);
        }
        return Collections.enumeration(names);
    }
 
    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            values.add(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }
}
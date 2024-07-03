package com.llf.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.llf.utils.ResultPackage;

/**
 * 拦截redisSession失效的请求,除了登录请求之外
 * @author longlufeng
 *
 */
public class RedisSessionInterceptor implements HandlerInterceptor {
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	// 路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    
    // 放行的url
    public static String[] urls = new String[]{"/login/login"};

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		return true;
		
//		String requestURI = request.getRequestURI();
//		
//		boolean check = check(requestURI, urls);
//		if(check){
//            return true;
//        }
//		
//		HttpSession session = request.getSession();
//		if (session.getAttribute("loginUserName") != null) {
//			try {
//				// 验证当前请求的session是否是已登录的session
//				String loginSessionId = redisTemplate.opsForValue()
//						.get("loginUserName:" + session.getAttribute("loginUserName"));
//				if (loginSessionId != null && loginSessionId.equals(session.getId())) {
//					return true;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		response401(response);
//
//		return false;
	}
	
	/**
	 * 路径匹配,检查本次请求是否放行
	 * 
	 * @param requetURI 从前端传回的请求
	 * @param urls      上面不用拦截的请求
	 * @return 进行对比返回
	 */
	public boolean check(String requetURI, String[] urls) {
		for (String url : urls) {
			boolean match = PATH_MATCHER.match(url, requetURI);// 使用上面定义的路径匹配器,进行比对
			if (match) {
				return true;// 匹配成功就返回true
			}
		}
		return false;// 一个也没匹配上,返回false
	}
	
	private void response401(HttpServletResponse response)
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
 
        try
        {
            response.getWriter().print(JSON.toJSONString(ResultPackage.failure("401", "用户未登录")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
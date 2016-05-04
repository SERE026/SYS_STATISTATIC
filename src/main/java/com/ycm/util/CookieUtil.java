package com.ycm.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
	
	/**
	 * 获取cookie
	 * @param req
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest req, String name) {
		Cookie[] cookies = req.getCookies();
		if(cookies == null || cookies.length == 0) {
			return null;
		}
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(name)) {
				
				return cookie;
			}
		}
		return null;
	}
	
	/*** 
	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP, 
	 * @param request 
	 * @return 
	 */  
	public static  String getClientIP(HttpServletRequest request) {  
	    //String fromSource = "X-Real-IP";  
	    String ip = request.getHeader("X-Real-IP");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("X-Forwarded-For");  
	        //fromSource = "X-Forwarded-For";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	        //fromSource = "Proxy-Client-IP";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	        //fromSource = "WL-Proxy-Client-IP";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	        //fromSource = "request.getRemoteAddr";  
	    }  
	    return ip;  
	}  
	
	
	
}

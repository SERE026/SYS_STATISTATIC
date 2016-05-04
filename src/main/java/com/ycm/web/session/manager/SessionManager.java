package com.ycm.web.session.manager;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

public class SessionManager {
	
	public static volatile Map<String,AppSession> sessionMap = new ConcurrentHashMap<String,AppSession>();
	
	public static volatile Map<String,HttpSession>  h_session = new ConcurrentHashMap<String,HttpSession>();
	
	public static void setAttribute(String key,Object value){
		AppSession session = new AppSession();
		session.setId(UUID.randomUUID().toString());
		session.setKey(key);
		session.setLastAccessedTime(new Date());
		session.setValue(value);
		HttpSession hs = getSession();
		if(hs!=null && hs.getMaxInactiveInterval() != 0){
			session.setMaxInactiveInterval(Long.valueOf(hs.getMaxInactiveInterval()));
		}else{
			session.setMaxInactiveInterval(15*60L);
		}
		sessionMap.put(key, session);
	}
	
	public static void removeAttribute(String key){
		sessionMap.remove(key);
	}
	
	public static Object getAttribute(String key){
		if(key==null || "".equals(key)) return null;
		AppSession session = sessionMap.get(key);
		if(session!=null) {
			session.setLastAccessedTime(new Date());
			sessionMap.put(key, session);
			return sessionMap.get(key).getValue();
		}
		else return null;
	}

	public static HttpSession getSession() {
		return h_session.get("session");
	}

	public static void setSession(HttpSession session) {
		h_session.put("session", session);
	}
	
}

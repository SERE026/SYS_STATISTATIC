package com.ycm.web.session.manager;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.ycm.util.GsonUtil;
import com.ycm.util.SpringContextHolder;

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
		
		JedisPool jedisPool = SpringContextHolder.getBean("jedisPool");
		Jedis jedis = jedisPool.getResource();
		jedis.set(key, GsonUtil.toJson(session));
		jedis.expire(key, 30*60);
		jedis.close();
		//sessionMap.put(key, session);
	}
	
	public static void removeAttribute(String key){
		JedisPool jedisPool = SpringContextHolder.getBean("jedisPool");
		Jedis jedis = jedisPool.getResource();
		jedis.del(key);
		jedis.close();
		//sessionMap.remove(key);
	}
	
	public static Object getAttribute(String key){
		if(key==null || "".equals(key)) return null;
		
		JedisPool jedisPool = SpringContextHolder.getBean("jedisPool");
		Jedis jedis = jedisPool.getResource();
		
		AppSession session = GsonUtil.parse2Object(jedis.get(key));
				//sessionMap.get(key);
		if(session!=null) {
			session.setLastAccessedTime(new Date());
			//sessionMap.put(key, session);
			//TODO 只要有访问，就认为是同一个用户
			jedis.expire(key, 30*60);
			return session.getValue();
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

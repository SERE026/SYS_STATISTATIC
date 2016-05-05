package com.ycm.web.session.manager;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ycm.util.GsonUtil;
import com.ycm.util.SpringContextHolder;

public class SessionManager {
	
	private static Logger LOG = LoggerFactory.getLogger(SessionManager.class);
	
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
		
		
		String json = jedis.get(key);
		LOG.info("Session 对象值为：{}",json);
		AppSession session = GsonUtil.fromJsonUnderScoreStyle(json, AppSession.class);
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
	
	public static void main(String[] args) {
		/*String json = "{\"id\":\"08736427-de8f-4171-94d1-105bf006e806\","
				+ "\"key\":\"2D4412DD-1790-42A5-8908-88C585612374\","
				+ "\"value\":\"2D4412DD-1790-42A5-8908-88C585612374\",\"creationTime\":\"May 4, 2016 6:01:27 PM\""
				+ ",\"lastAccessedTime\":\"May 4, 2016 6:01:27 PM\",\"maxInactiveInterval\":900}";
		
		Gson gson =  new GsonBuilder()
	            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	    AppSession session = gson.fromJson(json, AppSession.class);
	    
	    LOG.info("Session:{}",ToStringBuilder.reflectionToString(session));*/
	    
	    String str = "http://test.yinchengmall.com/#/billsFinancing?_k=qc3d49";
	    System.out.println(str.split("\\?")[0]);
	}
}

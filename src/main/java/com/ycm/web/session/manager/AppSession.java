package com.ycm.web.session.manager;

import java.util.Date;

import javax.servlet.http.HttpSession;

/**
 * 内部使用，无需序列化 
 * @author sere
 *
 */
public class AppSession {
	
	private String id;
	
	private String key;
	
	private Object value;
	
	private Date creationTime = new Date();
	
	private Date lastAccessedTime = new Date();
	
	private Long maxInactiveInterval; //存活秒数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(Date lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	public Long getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(Long maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
	
	
	
}

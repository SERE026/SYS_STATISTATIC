package com.ycm.dto;

public class BaseInfo implements Log{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8683259999458961941L;

	/**
	 * id 唯一标识
	 */
	private Long id;
	
	/**
	 * 访客的唯一标识
	 */
	private String tjUid;
	
	/**
	 * 访问URL
	 */
	private String url;
	
	/**
	 * 页面title
	 */
	private String title;
	
	/**
	 * 上一个地址
	 */
	private String referer;
	
	/**
	 * ip 地址
	 */
	private String ip;
	
	/**
	 * IP 所在的地域
	 */
	private String ipAddress;
	
	/**
	 * 网络运营商
	 */
	private String ISP;
	
	/**
	 * 客户端信息
	 */
	private ClientInfo client;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTjUid() {
		return tjUid;
	}

	public void setTjUid(String tjUid) {
		this.tjUid = tjUid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getISP() {
		return ISP;
	}

	public void setISP(String iSP) {
		ISP = iSP;
	}

	public ClientInfo getClient() {
		return client;
	}

	public void setClient(ClientInfo client) {
		this.client = client;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

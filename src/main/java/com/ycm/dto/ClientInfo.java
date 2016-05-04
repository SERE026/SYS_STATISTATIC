package com.ycm.dto;

/**
 * 客户端信息
 * @author sere
 *
 */
public class ClientInfo implements Log{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8684043795899081101L;

	
	/**
	 * 操作系统
	 */
	private String operSystem;
	
	/**
	 * 电脑屏幕分辨率
	 */
	private String screenResolution;
	
	/**
	 * 浏览器 信息
	 */
	private String browser;
	
	/**
	 * 浏览器完整信息
	 */
	private String allBrowser;
	
	/**
	 * 浏览器版本
	 */
	private String browserVersion;
	
	/**
	 * 是否支持java 1：支持 0：不支持
	 */
	private Integer isEnableJava;
	
	/**
	 * 是否支持flash 1：支持 0：不支持
	 */
	private Integer isEnableFlash;
	
	/**
	 * flash 版本号
	 */
	private String flashVersion;
	
	/**
	 * 显示屏的色彩位数
	 */
	private String colorBit;
	
	/**
	 * 客户端所使用的语言
	 */
	private String clanguage;
	
	public String getOperSystem() {
		return operSystem;
	}

	public void setOperSystem(String operSystem) {
		this.operSystem = operSystem;
	}

	public String getScreenResolution() {
		return screenResolution;
	}

	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String borowser) {
		this.browser = borowser;
	}


	public String getAllBrowser() {
		return allBrowser;
	}

	public void setAllBrowser(String allBrowser) {
		this.allBrowser = allBrowser;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public Integer getIsEnableJava() {
		return isEnableJava;
	}

	public void setIsEnableJava(Integer isEnableJava) {
		this.isEnableJava = isEnableJava;
	}

	public Integer getIsEnableFlash() {
		return isEnableFlash;
	}

	public void setIsEnableFlash(Integer isEnableFlash) {
		this.isEnableFlash = isEnableFlash;
	}

	public String getFlashVersion() {
		return flashVersion;
	}

	public void setFlashVersion(String flashVersion) {
		this.flashVersion = flashVersion;
	}

	public String getColorBit() {
		return colorBit;
	}

	public void setColorBit(String colorBit) {
		this.colorBit = colorBit;
	}

	public String getClanguage() {
		return clanguage;
	}

	public void setClanguage(String clanguage) {
		this.clanguage = clanguage;
	}

	
}

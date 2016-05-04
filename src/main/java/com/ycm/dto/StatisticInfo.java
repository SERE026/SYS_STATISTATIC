package com.ycm.dto;

import java.math.BigDecimal;

/**
 * 站点统计信息
 * @author sere
 *
 */
public class StatisticInfo implements Log {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1389042226451683894L;

	/**
	 * 页面浏览量
	 */
	private Integer pvCount;
	
	/**
	 * 网站独立访客数，如果一个用户多次访问该网站，只计算一个UV
	 */
	private Integer uvCount;
	
	/**
	 * 访问网站所有独立IP数
	 */
	private Integer ipCount;
	
	/**
	 * 网站被访问的次数
	 */
	private Integer visitCount;
	
	/**
	 * 访客在网站上平均停留的时长
	 */
	private Integer avrageStayTime;
	
	/**
	 * 访客访问的页面数量
	 */
	private Integer visitPageNumbers;
	
	/**
	 * 跳出率 只浏览了一个网页的次数  占 的访问次数的比率
	 */
	private BigDecimal outPageRate;
	
	/**
	 * 统计类型 HOUR、DAY、WEEK、MONTH、YEAR 
	 */
	private String type;

	public Integer getPvCount() {
		return pvCount;
	}

	public void setPvCount(Integer pvCount) {
		this.pvCount = pvCount;
	}

	public Integer getUvCount() {
		return uvCount;
	}

	public void setUvCount(Integer uvCount) {
		this.uvCount = uvCount;
	}

	public Integer getIpCount() {
		return ipCount;
	}

	public void setIpCount(Integer ipCount) {
		this.ipCount = ipCount;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Integer getAvrageStayTime() {
		return avrageStayTime;
	}

	public void setAvrageStayTime(Integer avrageStayTime) {
		this.avrageStayTime = avrageStayTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVisitPageNumbers() {
		return visitPageNumbers;
	}

	public void setVisitPageNumbers(Integer visitPageNumbers) {
		this.visitPageNumbers = visitPageNumbers;
	}

	public BigDecimal getOutPageRate() {
		return outPageRate;
	}

	public void setOutPageRate(BigDecimal outPageRate) {
		this.outPageRate = outPageRate;
	}

	
}

package com.ycm.dto;


/**
 * 访客信息
 * @author sere
 *
 */
public class VisitInfo extends BaseInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3487949476161270647L;
	
	/**
	 * 访问页面数
	 */
	private Integer pageNumber;
	
	/**
	 * 第一次访问时间
	 */
	private Long firstVisitTime;
	
	/**
	 * 最后访问时间
	 */
	private Long lastVisitTime;
	
	/**
	 * 访问时长
	 */
	private Long visitTimeLength;
	
	/**
	 * 是否是新访客  1是，0 不是
	 */
	private Integer isNewVisit;



	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Long getFirstVisitTime() {
		return firstVisitTime;
	}

	public void setFirstVisitTime(Long firstVisitTime) {
		this.firstVisitTime = firstVisitTime;
	}

	public Long getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Long lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	public Long getVisitTimeLength() {
		return visitTimeLength;
	}

	public void setVisitTimeLength(Long visitTimeLength) {
		this.visitTimeLength = visitTimeLength;
	}

	public Integer getIsNewVisit() {
		return isNewVisit;
	}

	public void setIsNewVisit(Integer isNewVisit) {
		this.isNewVisit = isNewVisit;
	}

}

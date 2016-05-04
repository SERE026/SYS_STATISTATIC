package com.ycm.dto;

/**
 * 页面信息
 * @author sere
 *
 */
public class PageVisitInfo extends BaseInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1389042226451683894L;

	/**
	 * 页面开始加载的时间
	 */
	private Long startLoadTime;
	
	/**
	 * 页面加载完毕的时间
	 */
	private Long endLoadTime;
	
	/**
	 * 离开页面的时间
	 */
	private Long leaveTime;
	
	/**
	 * 页面停留时间
	 */
	private Long stayTime;
	
	/**
	 * 该页面作为入口页面的次数  如果referer为空 不是入口
	 */
	private Integer inPageCount;
	
	/**
	 * 该页面作为出口页面的次数 
	 */
	private Integer outPageCount;
	

	public Long getStartLoadTime() {
		return startLoadTime;
	}

	public void setStartLoadTime(Long startLoadTime) {
		this.startLoadTime = startLoadTime;
	}

	public Long getEndLoadTime() {
		return endLoadTime;
	}

	public void setEndLoadTime(Long endLoadTime) {
		this.endLoadTime = endLoadTime;
	}

	public Long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Long getStayTime() {
		if(getLeaveTime()-getStartLoadTime()>0){
			return getLeaveTime()-getStartLoadTime();
		}
		return stayTime;
	}

	public void setStayTime(Long stayTime) {
		this.stayTime = stayTime;
	}

	public Integer getInPageCount() {
		return inPageCount;
	}

	public void setInPageCount(Integer inPageCount) {
		this.inPageCount = inPageCount;
	}

	public Integer getOutPageCount() {
		return outPageCount;
	}

	public void setOutPageCount(Integer outPageCount) {
		this.outPageCount = outPageCount;
	}

}

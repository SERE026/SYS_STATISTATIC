package com.ycm.dto;

import com.ycm.dto.Log;

/**
 * 页面加载信息
 * 元素信息参考 readme.docx
 * @author sere
 *
 */
public class PageLoadTime extends BaseInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6670706353732958263L;

	/**
	 * 从前一个页面unload到当前页面unload的时间
	 */
	private Long netAll;
	
	/**
	 * DNS 域名查询完成的时间，如果使用了本地缓存（即无 DNS 查询）或持久连接 
	 */
	private Long netDns;
	
	/**
	 * 建立Http、Tcp 连接的时间
	 */
	private Long netTcp;
	
	/**
	 * 开始接受响应到接受响应完成的时间
	 */
	private Long srv;
	
	/**
	 * 注意只是 DOM 树解析完成的时间
	 */
	private Long dom;
	
	/**
	 * click 、hover 事件的时间
	 */
	private Long loadEvent;
	
	/**
	 * 
	 */
	private String qid;
	
	/**
	 * dom 加载的时间
	 */
	private Long bdDom;
	
	/**
	 * 页面停留时间：执行统计代码的时间 TODO 
	 */
	private Long bdRun;
	
	/**
	 *  页面停留时间： 点击链接进入页面到该页面被卸载时的时间
	 */
	private Long bdDef;

	public Long getNetAll() {
		return netAll;
	}

	public void setNetAll(Long netAll) {
		this.netAll = netAll;
	}

	public Long getNetDns() {
		return netDns;
	}

	public void setNetDns(Long netDns) {
		this.netDns = netDns;
	}

	public Long getNetTcp() {
		return netTcp;
	}

	public void setNetTcp(Long netTcp) {
		this.netTcp = netTcp;
	}

	public Long getSrv() {
		return srv;
	}

	public void setSrv(Long srv) {
		this.srv = srv;
	}

	public Long getDom() {
		return dom;
	}

	public void setDom(Long dom) {
		this.dom = dom;
	}

	public Long getLoadEvent() {
		return loadEvent;
	}

	public void setLoadEvent(Long loadEvent) {
		this.loadEvent = loadEvent;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public Long getBdDom() {
		return bdDom;
	}

	public void setBdDom(Long bdDom) {
		this.bdDom = bdDom;
	}

	public Long getBdRun() {
		return bdRun;
	}

	public void setBdRun(Long bdRun) {
		this.bdRun = bdRun;
	}

	public Long getBdDef() {
		return bdDef;
	}

	public void setBdDef(Long bdDef) {
		this.bdDef = bdDef;
	}
	
	
	
}

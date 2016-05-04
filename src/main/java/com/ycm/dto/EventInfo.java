package com.ycm.dto;


/**
 * 事件信息
 * @author sere
 *
 */
public class EventInfo extends BaseInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8233736984898391406L;

	/**
	 * 唯一标识 自增
	 */
	private Long id;
	
	/**
	 * 事件发生的时间
	 */
	private Long time;
	
	/**
	 * 时间类型 click hover ...
	 */
	private String type;
	
	/**
	 * 距离上次时间发生的时间 
	 * click 为第二次点击的时间
	 * hover 则为停留在上面的时间
	 */
	private Long interval;
	
	/**
	 * 事件所作用的元素
	 */
	private ElementInfo element;
	
	/**
	 * 鼠标距离 元素的X轴像素
	 */
	private String offsetX;
	
	/**
	 * 鼠标距离 元素的Y轴像素
	 */
	private String offsetY;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public ElementInfo getElement() {
		return element;
	}

	public void setElement(ElementInfo element) {
		this.element = element;
	}

	public String getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(String offsetX) {
		this.offsetX = offsetX;
	}

	public String getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(String offsetY) {
		this.offsetY = offsetY;
	}
	
	
}

package com.ycm.dto;

/**
 * 页面元素信息
 * @author sere
 *
 */
public class ElementInfo implements Log{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2995300249663715225L;

	private Long id;
	
	/**
	 * 元素的ＩＤ标识
	 */
	private String tagId;
	
	/**
	 * 元素的name 值
	 */
	private String tagName;
	
	/**
	 * 元素的class 值
	 */
	private String tagClass;
	
	/**
	 * 元素 类型 div li a
	 */
	private String tag;
	
	/**
	 * 元素的src 属性
	 */
	private String src;
	
	/**
	 * 元素的href 属性
	 */
	private String href;
	
	/**
	 * 标签的text值
	 */
	private String text;
	
	/**
	 * 标签的value值
	 */
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagClass() {
		return tagClass;
	}

	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}

package com.ycm.parser;

import java.util.Map;

import com.ycm.dto.PageLoadTime;

/**
 * 页面停留时间日志解析器
 * @author sere
 *
 */
public class PageLoadParser extends CommonParser{

	@Override
	public PageLoadTime parse(Map<String,String> map) {
		
		PageLoadTime pageLoad = new PageLoadTime();
		super.setCommon(map, pageLoad);
		super.setPageLoad(map, pageLoad);
		return pageLoad;
	}

}

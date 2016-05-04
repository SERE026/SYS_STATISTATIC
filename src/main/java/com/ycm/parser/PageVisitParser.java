package com.ycm.parser;

import java.util.Map;

import com.ycm.dto.PageVisitInfo;

/**
 * 页面停留时间日志解析器
 * @author sere
 *
 */
public class PageVisitParser extends CommonParser{

	@Override
	public PageVisitInfo parse(Map<String,String> map) {
		
		PageVisitInfo pageVisit = new PageVisitInfo();
		super.setCommon(map, pageVisit);
		super.setPageVisit(map, pageVisit);
		return pageVisit;
	}

}

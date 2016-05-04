package com.ycm.parser;

import java.util.Map;

import com.ycm.dto.Log;
import com.ycm.dto.VisitInfo;

/**
 * 访问日志解析器
 * @author Administrator
 *
 */
public class VisitParser extends CommonParser {

	@Override
	public Log parse(Map<String,String> map) {
		VisitInfo visit = new VisitInfo();
		super.setCommon(map, visit);
		super.setVisit(map, visit);
		return visit;
	}

}

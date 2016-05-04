package com.ycm.parser;

import java.util.Map;

import com.ycm.dto.EventInfo;
import com.ycm.dto.Log;

/**
 * 页面点击日志解析器
 * @author sere
 *
 */
public class EventParser extends CommonParser {

	@Override
	public Log parse(Map<String,String> map) {		
		EventInfo event = new EventInfo();
		super.setCommon(map, event);
		super.setEvent(map, event);
		return event;
	}

}

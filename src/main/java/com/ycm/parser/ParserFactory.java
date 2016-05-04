package com.ycm.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析器工厂
 * @author sere
 *
 */
public abstract class ParserFactory {
	
	private static Logger LOG = LoggerFactory.getLogger(ParserFactory.class);
	
	/**
	 *  1 发送访客统计信息  
	 *  2 发送事件统计信息 
	 *  3 发送页面停留信息 
	 *  4 发送页面加载信息
	 * @param et
	 * @return
	 */
	public static CommonParser createParser(String et) {
		if("1".equals(et)) {
			return new VisitParser();
		} else if("2".equals(et)) {
			return new EventParser();
		} else if("3".equals(et)) {
			return new PageVisitParser();
		} else if("4".equals(et)) {
			return new PageLoadParser();
		} else {
			LOG.info("无法识别的日志类型，et：" + et);
			return null;
		}
	}
}

package com.ycm.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ycm.persitence.Page;

public interface StatisticService {

	/**
	 * 获取PV数、 UV数、  IP数、 新UV数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Map<String, Integer> visitRangeStat(Integer startTime,
			Integer endTime);

	List<Map<String, Integer>> getTimeRanges(Date formatStrToDate,
			Date formatStrToDate2);

	/**
	 *  访问量时间范围统计
	 * @param timeRanges
	 * @param spiltType 统计切分维度：小时、天、周、月、季度、年
	 * @return
	 */
	List<Map<String, Object>> visitRangeStat(List<Map<String, Integer>> timeRanges, int spiltType);

	/**
	 * 页面统计
	 * @param startTime
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return
	 */
	Page<Map<String, Object>> pageStat(Integer startTime, 
			Integer endTime, Integer offset, Integer limit);

	List<Map<String, Object>> browserStat(List<String> statPoints,
			int startTime, int endTime);

	List<Map<String, Object>> osStat(List<String> statPoints, int startTime,
			int endTime);

	int pageStat(Integer valueOf, String statPointPv, int startTime, int endTime);

	double pageStayTime(Integer valueOf, int startTime, int endTime);

	double pageLoadTime(Integer valueOf, int startTime, int endTime);

}

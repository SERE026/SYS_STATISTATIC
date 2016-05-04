package com.ycm.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ycm.api.StatisticService;
import com.ycm.constants.Constant;
import com.ycm.persitence.Page;
import com.ycm.persitence.PageResult;
import com.ycm.util.DateUtil;
import com.ycm.util.GsonUtil;

/**
 * 统计展示界面入口
 *
 */
@Controller
@RequestMapping("/stat")
public class StatisticController {
	
	private static Logger LOG = LoggerFactory.getLogger(StatisticController.class);
	
	@Autowired
	private StatisticService statisticService;
	
	
	/**
	 * 统计展示首页 最近30分钟的统计
	 * @return
	 */
	@RequestMapping(value="/pageStatIndex")
	public String pageStatIndex(HttpServletRequest request, HttpServletResponse response,Model model) {
		LOG.info("统计展示首页 ----------------------");
		
		Date date = new Date();
		
		Integer endTime =  DateUtil.parseStrToInt(date);
		Integer startTime = endTime -30 ; //TODO
		
		Map<String, Integer> currStat = statisticService.visitRangeStat(startTime,endTime);
		model.addAttribute("currStat", currStat);
		return "statistic/page_stat_index";
	}
	
	/**
	 * 统计展示首页
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, HttpServletResponse response,Model  model) {
		//统计的时间段类型：1：今日，2：昨日，3：最近7天，4：最近30天
		String statTimeType = request.getParameter("statTimeType");
		if(StringUtils.isEmpty(statTimeType)) {
			statTimeType = "1";
		}
		Integer startTime=0;
		Integer endTime=0;
		
		Date date1 = null;
		Date date2 =  null;
		if("1".equals(statTimeType)) {	//今日统计
			date1 = DateUtil.formatStrToDate(DateUtil.formatDateToString(new Date(), "MM/dd/yyyy") + " 00:00:00", "MM/dd/yyyy hh:mm:ss");
			date2 = DateUtil.formatStrToDate(DateUtil.formatDateToString(new Date(), "MM/dd/yyyy") + " 23:59:59", "MM/dd/yyyy hh:mm:ss");
		} else if("2".equals(statTimeType)) { //昨日统计
			Date yesterday = DateUtil.addDay(new Date(), -1);
			date1 = DateUtil.formatStrToDate(DateUtil.formatDateToString(yesterday, "MM/dd/yyyy") + " 00:00:00", "MM/dd/yyyy hh:mm:ss");
			date2 = DateUtil.formatStrToDate(DateUtil.formatDateToString(yesterday, "MM/dd/yyyy") + " 23:59:59", "MM/dd/yyyy hh:mm:ss");
		} else if("3".equals(statTimeType)) { //最近7天统计
			Date weekAgo = DateUtil.addDay(new Date(), -6);
			date1 = DateUtil.formatStrToDate(DateUtil.formatDateToString(weekAgo, "MM/dd/yyyy") + " 00:00:00", "MM/dd/yyyy hh:mm:ss");
			date2 = DateUtil.formatStrToDate(DateUtil.formatDateToString(new Date(), "MM/dd/yyyy") + " 23:59:59", "MM/dd/yyyy hh:mm:ss");
		
		} else if("4".equals(statTimeType)) { //最近30天统计
			Date monthAgo = DateUtil.addDay(new Date(), -29);
			date1 = DateUtil.formatStrToDate(DateUtil.formatDateToString(monthAgo, "MM/dd/yyyy") + " 00:00:00", "MM/dd/yyyy hh:mm:ss");
			date2 = DateUtil.formatStrToDate(DateUtil.formatDateToString(new Date(), "MM/dd/yyyy") + " 23:59:59", "MM/dd/yyyy hh:mm:ss");
		}
		
		startTime = DateUtil.parseStrToInt(date1);
		endTime =  DateUtil.parseStrToInt(date2);
		Map<String, Integer> currStat = statisticService.visitRangeStat(startTime, endTime);
		
		model.addAttribute("currStat", currStat);
		return "statistic/visit_stat_index";
	}
	
	
	
	/**
	 * 访问量统计
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/visitStat")
	public @ResponseBody String visitStat(HttpServletRequest request, HttpServletResponse response) {
		//统计指标：PV UV等
		String point = request.getParameter("point");
		//统计的时间范围,格式：yyyy/MM/dd-yyyy/MM/dd
		String timeRange = request.getParameter("timeRange");
		//统计切分维度：小时、天、周、月、季度、年
		String spiltTypeStr =  request.getParameter("spiltType");
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(point) || StringUtils.isEmpty(timeRange)) {
			result.put("state", false);
			result.put("msg", "确实必要参数");
			return GsonUtil.toJson(result);
		}
		
		//时间切片，每一个map是一个时间段		
		String[] times = timeRange.split("-");
		String startTime = times[0].trim();
		String endTime = times[1].trim();
		List<Map<String, Integer>> timeRanges = statisticService.getTimeRanges(DateUtil.formatStrToDate(startTime, "MM/dd/yyyy"), 
				DateUtil.formatStrToDate(endTime, "MM/dd/yyyy"));
		
		if(timeRanges == null || timeRanges.isEmpty()) {
			result.put("state", false);
			result.put("msg", "无法进行统计日期切分");
			return GsonUtil.toJson(result);
		}
		
		int spiltType = 0;
		if(StringUtils.isEmpty(spiltTypeStr)) {
			//没有切分维度，通过timeRanges进行判断，如果范围在48小时，按小时切的，否则都按天处理
			Map<String, Integer> m = timeRanges.get(0);
			if(m.get("endTime") - m.get("startTime") <= 48*3600) {
				spiltType = DateUtil.DATE_SPILT_TYPE_HOUR;
			} else {
				spiltType = DateUtil.DATE_SPILT_TYPE_DAY;
			}
		} else {
			spiltType = Integer.parseInt(spiltTypeStr);
		}
		
		List<String> statPoints = new ArrayList<String>();
		String[] strArr = point.split(",");
		for(String str : strArr) {
			statPoints.add(str);
		}
		List<Map<String, Object>> data = 
				statisticService.visitRangeStat(timeRanges, spiltType);
		result.put("state", true);
		result.put("data", data);
		return GsonUtil.toJson(result);
	}
	
	/**
	 * 页面统计
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/pageStat")
	public @ResponseBody String pageStat(HttpServletRequest request, HttpServletResponse response) {
		String limitStr = request.getParameter("limit");
		String offsetStr = request.getParameter("offset");
		int limit = 20;
		int offset = 0;
		if(StringUtils.isNotEmpty(limitStr)) {
			limit = Integer.parseInt(limitStr);
		}
		if(StringUtils.isNotEmpty(offsetStr)) {
			offset = Integer.parseInt(offsetStr);
		}
		
		//统计的时间范围,格式：yyyy/MM/dd-yyyy/MM/dd
		String timeRange = request.getParameter("timeRange");
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(timeRange)) {
			result.put("state", false);
			result.put("msg", "确实必要参数");
			return GsonUtil.toJson(result);
		}
		String[] times = timeRange.split("-");
		//TODO 
		int startTime = DateUtil.parseStrToInt(DateUtil.formatStrToDate(times[0].trim() + " 00:00:00", "MM/dd/yyyy hh:mm:ss"));
		int endTime = DateUtil.parseStrToInt(DateUtil.formatStrToDate(times[1].trim()+ " 23:59:59", "MM/dd/yyyy hh:mm:ss"));
		Page<Map<String, Object>> data = statisticService.pageStat(startTime, 
				endTime, offset, limit);
		
		PageResult<Map<String, Object>> pr = new PageResult<Map<String,Object>>();
		pr.setRows(data.getList());
		pr.setTotal(data.getTotalCount());
		return GsonUtil.toJson(pr);
	}
	
	/**
	 * 系统环境统计首页
	 * @return
	 */
	@RequestMapping(value="/sysEnvStatIndex")
	public String sysEnvStatIndex(HttpServletRequest request, HttpServletResponse response,ModelAndView model) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//System.out.println("--------------");
		//实时统计数据
		//Map<String, Integer> currStat = statisticService.visitCurrStat();
		//System.out.println(currStat);
		//request.setAttribute("currStat", currStat);
		//modelMap.put("currStat", currStat);
		return "statistic/sysenv_stat_index";
	}
	
	/**
	 * 浏览器访问统计
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/browserStat")
	public @ResponseBody String browserStat(HttpServletRequest request, HttpServletResponse response) {

		//统计的时间范围,格式：yyyy/MM/dd-yyyy/MM/dd
		String timeRange = request.getParameter("timeRange");
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(timeRange)) {
			result.put("state", false);
			result.put("msg", "确实必要参数");
			return GsonUtil.toJson(result);
		}
		
		//时间切片，每一个map是一个时间段		
		String[] times = timeRange.split("-");
		int startTime = DateUtil.parseDateToInt(DateUtil.formatStrToDate(times[0].trim() + " 00:00:00", "MM/dd/yyyy hh:mm:ss"));
		int endTime = DateUtil.parseDateToInt(DateUtil.formatStrToDate(times[1].trim()+ " 23:59:59", "MM/dd/yyyy hh:mm:ss"));
		List<String> statPoints = new ArrayList<String>();
		statPoints.add(Constant.STAT_POINT_PV);
		statPoints.add(Constant.STAT_POINT_UV);
		statPoints.add(Constant.STAT_POINT_NEW_UV);
		statPoints.add(Constant.STAT_POINT_IP);
		List<Map<String, Object>> data = 
				statisticService.browserStat(statPoints, startTime, endTime);
		result.put("state", true);
		result.put("data", data);
		return GsonUtil.toJson(result);
	}
	
	/**
	 * 操作系统访问统计
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/osStat")
	public @ResponseBody String osStat(HttpServletRequest request, HttpServletResponse response) {

		//统计的时间范围,格式：yyyy/MM/dd-yyyy/MM/dd
		String timeRange = request.getParameter("timeRange");
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(timeRange)) {
			result.put("state", false);
			result.put("msg", "确实必要参数");
			return GsonUtil.toJson(result);
		}
		
		//时间切片，每一个map是一个时间段		
		String[] times = timeRange.split("-");
		int startTime = DateUtil.parseDateToInt(DateUtil.formatStrToDate(times[0].trim() + " 00:00:00", "MM/dd/yyyy hh:mm:ss"));
		int endTime = DateUtil.parseDateToInt(DateUtil.formatStrToDate(times[1].trim()+ " 23:59:59", "MM/dd/yyyy hh:mm:ss"));
		List<String> statPoints = new ArrayList<String>();
		statPoints.add(Constant.STAT_POINT_PV);
		statPoints.add(Constant.STAT_POINT_UV);
		statPoints.add(Constant.STAT_POINT_NEW_UV);
		statPoints.add(Constant.STAT_POINT_IP);
		List<Map<String, Object>> data = 
				statisticService.osStat(statPoints, startTime, endTime);
		result.put("state", true);
		result.put("data", data);
		return GsonUtil.toJson(result);
	}
	
	/**
	 * 页面详情入口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pageDetail")
	public String pageDetail(HttpServletRequest request, HttpServletResponse response,ModelAndView model) {
		//进入页面详情统计界面，如果没有传pageId，则默认取访问量最大的页面
		/*String pageId = request.getParameter("pageId");
		VisitPage visitPage = null;
		if(StringUtils.isEmpty(pageId)) {
			visitPage = statisticService.findMaxPVVisitPage();
		} else {
			visitPage = statisticService.findVisitPageById(Integer.valueOf(pageId));
		}
		
		if(visitPage != null) {
			model.addObject("pageUrl", visitPage.getPageUrl());
			model.addObject("pageId", visitPage.getId());
		}*/
		return "statistic/page_detail";
	}
	
	/**
	 * 页面详情主要指标统计：PV、UV、IP、入口次数、跳出次数、加载时间、停留时间
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pageDetailMainPoint")
	public @ResponseBody String pageDetailMainPoint(HttpServletRequest request, HttpServletResponse response) {
		//统计的目标页面ID
		String pageId = request.getParameter("pageId");
		//统计的时间范围,格式：yyyy/MM/dd-yyyy/MM/dd
		String timeRange = request.getParameter("timeRange");
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(timeRange) || StringUtils.isEmpty(pageId)) {
			result.put("state", false);
			result.put("msg", "确实必要参数");
			return GsonUtil.toJson(result);
		}
		String[] times = timeRange.split("-");
		int startTime = DateUtil.parseDateToInt(
				DateUtil.formatStrToDate(times[0].trim() + " 00:00:00", "MM/dd/yyyy hh:mm:ss"));
		int endTime = DateUtil.parseDateToInt(
				DateUtil.formatStrToDate(times[1].trim()+ " 23:59:59", "MM/dd/yyyy hh:mm:ss"));
		int countPV = statisticService.pageStat(Integer.valueOf(pageId), 
				Constant.STAT_POINT_PV, startTime, endTime);
		int countUV = statisticService.pageStat(Integer.valueOf(pageId), 
				Constant.STAT_POINT_UV, startTime, endTime);
		int countIP = statisticService.pageStat(Integer.valueOf(pageId), 
				Constant.STAT_POINT_IP, startTime, endTime);
		//入口次数
		int countEnter = statisticService.pageStat(Integer.valueOf(pageId), 
				Constant.STAT_POINT_PAGE_ENTER, startTime, endTime);
		//入口率：入口次数/PV
		double countEnterPct = 0.0;
		if(countPV > 0) {
			countEnterPct = (Double.valueOf(countEnter)/countPV)*100;
		}
		//跳出次数
		int countExit = statisticService.pageStat(Integer.valueOf(pageId), 
				Constant.STAT_POINT_PAGE_EXIT, startTime, endTime);
		//跳出率
		double countExitPct = 0.0;
		if(countPV > 0) {
			countExitPct = (Double.valueOf(countExit)/countPV)*100;
		}
		//页面平均停留时长
		double stayTime = statisticService.pageStayTime(Integer.valueOf(pageId), startTime, endTime);
		//页面平均加载时长
		double loadTime = statisticService.pageLoadTime(Integer.valueOf(pageId), startTime, endTime);
		
		result.put(Constant.STAT_POINT_PV, countPV);
		result.put(Constant.STAT_POINT_UV, countUV);
		result.put(Constant.STAT_POINT_IP, countIP);
		result.put(Constant.STAT_POINT_PAGE_ENTER, countEnter);
		result.put(Constant.STAT_POINT_PAGE_ENTER_PCT, 
				new BigDecimal(countEnterPct).setScale(1).doubleValue());
		result.put(Constant.STAT_POINT_PAGE_EXIT, countExit);
		result.put(Constant.STAT_POINT_PAGE_EXIT_PCT, 
				new BigDecimal(countExitPct).setScale(1).doubleValue());
		result.put(Constant.STAT_POINT_PAGE_STAY_TIME, stayTime);
		result.put(Constant.STAT_POINT_PAGE_LOAD_TIME, loadTime);
		return GsonUtil.toJson(result);
	}
	
}

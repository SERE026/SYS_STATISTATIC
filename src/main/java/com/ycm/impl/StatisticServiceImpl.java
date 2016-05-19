package com.ycm.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.ycm.api.StatisticService;
import com.ycm.constants.Constant;
import com.ycm.persitence.Page;
import com.ycm.util.DateUtil;
import com.ycm.util.RedisKeyUtils;

@Service
public class StatisticServiceImpl implements StatisticService{
	
	private static Logger LOG = LoggerFactory.getLogger(StatisticServiceImpl.class);
	
	@Autowired
	private RedisSingleService redisService;
	
	@Override
	public Map<String, Integer> visitRangeStat(Integer startTime, Integer endTime) {
		
		Jedis jedis = redisService.getJedis();
		try{
			Map<String, Integer> data = new HashMap<String, Integer>();
			Long count = jedis.zcount(RedisKeyUtils.getUVKey(), startTime, endTime);
			data.put(Constant.STAT_POINT_UV, count.intValue());
			
			count = jedis.zcount(RedisKeyUtils.getPVKey(), startTime, endTime);
			data.put(Constant.STAT_POINT_PV, count.intValue());
			
			count = jedis.zcount(RedisKeyUtils.getNewUVKey(),startTime,endTime);
			data.put(Constant.STAT_POINT_NEW_UV, count.intValue());
			
			count = jedis.zcount(RedisKeyUtils.getIPKey(),startTime,endTime);
			data.put(Constant.STAT_POINT_IP, count.intValue());
				
			return data;
		}catch(Exception e){
			e.printStackTrace();
			
			redisService.close(jedis,true);
			return null;
		}finally{
			redisService.close(jedis,false);
		}
	}
	
	/**
	 * 访问量时间范围统计
	 * @param statPoint 统计指标：PV、UV、IP
	 * @param spiltType 统计切分维度：小时、天、周、月、季度、年
	 * @return
	 */
	@Override
	public List<Map<String, Object>> visitRangeStat(List<Map<String, Integer>> timeRanges,  int spiltType) {
		Jedis jedis = redisService.getJedis();
		try {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for (Map<String, Integer> m : timeRanges) {

				Integer ST = m.get("startTime");

				Integer startTime = DateUtil.parseStrToInt(DateUtil
						.unixTime2Date(m.get("startTime")));
				Integer endTime = DateUtil.parseStrToInt(DateUtil
						.unixTime2Date(m.get("endTime")));

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("timeText", getTimeText(ST, spiltType));

				Long count = jedis.zcount(RedisKeyUtils.getUVKey(),
						startTime, endTime);
				data.put(Constant.STAT_POINT_UV, count.intValue());

				count = jedis.zcount(RedisKeyUtils.getPVKey(),
						startTime, endTime);
				data.put(Constant.STAT_POINT_PV, count.intValue());

				count = jedis.zcount(RedisKeyUtils.getNewUVKey(),
						startTime, endTime);
				data.put(Constant.STAT_POINT_NEW_UV, count.intValue());

				count = jedis.zcount(RedisKeyUtils.getIPKey(),
						startTime, endTime);
				data.put(Constant.STAT_POINT_IP, count.intValue());

				result.add(data);
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			redisService.close(jedis,true);
			return null;
		} finally {
			redisService.close(jedis,false);
		}
	}
	
	/**
	 * 根据统计时间类型，获取时间范围
	 * @param statTimeType
	 * @return
	 */
	public List<Map<String, Integer>> getTimeRanges( int statTimeType) {
		if(Constant.STAT_TIME_TYPE_TODAY == statTimeType) {
			return DateUtil.split2Hous(new Date());
		} else if(Constant.STAT_TIME_TYPE_YESTERDAY == statTimeType) {
			Date yestDate =DateUtil.addDay(new Date(), 1*-1) ;
			return DateUtil.split2Hous(yestDate);
		} else if(Constant.STAT_TIME_TYPE_LASTEST_7 == statTimeType) {
			return DateUtil.split2Day(6);
		} else if(Constant.STAT_TIME_TYPE_LASTEST_30 == statTimeType) {
			return DateUtil.split2Day(29);
		} else {
			return Collections.emptyList();
		}
	}
	
	/**
	 * 切分时间片
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<Map<String, Integer>> getTimeRanges(Date startDate, Date endDate) {
		int unixStartDate = DateUtil.parseDateToInt(startDate);
		int unixEndDate = DateUtil.parseDateToInt(endDate);
		
		int spiltType = 0;		
		if(unixEndDate - unixStartDate <= 48*3600) {
			//如果开始时间~结束时间之间的范围在48小时内，按小时进行切分
			spiltType = DateUtil.DATE_SPILT_TYPE_HOUR;
		} else {
			//否则按天进行切分
			spiltType = DateUtil.DATE_SPILT_TYPE_DAY;
		}
		
		return DateUtil.spiltDate(startDate, endDate, spiltType);
	}
	
	/**
	 * 
	 * @param time
	 * @param spiltType
	 * @return
	 */
	private String getTimeText(int time, int spiltType) {
		if( DateUtil.DATE_SPILT_TYPE_HOUR == spiltType ) {
			return DateUtil.formatDateToString(DateUtil.unixTime2Date(time), "yyyy-MM-dd HH:mm");
		}  else if(DateUtil.DATE_SPILT_TYPE_DAY == spiltType ) {
			return DateUtil.formatDateToString(DateUtil.unixTime2Date(time), "yyyy-MM-dd");
		}  else {
			return "";
		}
	}
	
	
	@Override
	public Page<Map<String, Object>> pageStat(Integer startTime, 
			Integer endTime, Integer offset, Integer limit) {
		Jedis jedis = redisService.getJedis();
		try {

			Page<Map<String, Object>> pageResult = new Page<Map<String, Object>>();

			String key = RedisKeyUtils.getPVKey();

			Long total = jedis.zcount(key, 0,
					DateUtil.parseStrToInt(new Date()));
			pageResult.setTotalCount(total.intValue());
			if (total <= 0) {
				return pageResult;
			}

			Set<String> pages = jedis.zrange(key, offset, (offset+limit));
			if (pages == null || pages.isEmpty()) {
				return pageResult;
			}

			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

			for (String pg : pages) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("pageUrl", pg);
				
				String p = pg.split("\\?")[0];

				Long count = jedis.zcount(RedisKeyUtils.getUVByUrl(p),
						startTime, endTime);
				data.put(Constant.STAT_POINT_UV, count.intValue());

				count = jedis.zcount(RedisKeyUtils.getPVKey(p),
						startTime, endTime);
				data.put(Constant.STAT_POINT_PV, count.intValue());

				count = jedis.zcount(RedisKeyUtils.getIPByUrl(p),
						startTime, endTime);
				data.put(Constant.STAT_POINT_IP, count.intValue());

				result.add(data);
			}
			pageResult.setList(result);
			return pageResult;
		} catch (Exception e) {
			e.printStackTrace();
			redisService.close(jedis,true);
			return null;
		} finally {
			redisService.close(jedis,false);
		}
	}
	
	/**
	 * 页面指标统计
	 * @param pageId 页面id
	 * @param point 指标：UV、PV、IP、入口次数、跳出次数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public int pageStat(Integer pageId, String point, int startTime, int endTime) {
		int count = 0;
		//TODO 
		/*if(Constant.STAT_POINT_PV .equals(point)) {
			count = statisticDao.pageStatPV(pageId, startTime, endTime);
		} else if(Constant.STAT_POINT_UV.equals(point)) {
			count = statisticDao.pageStatUV(pageId, startTime, endTime);
		} else if(Constant.STAT_POINT_IP.equals(point)) {
			count = statisticDao.pageStatIP(pageId, startTime, endTime);
		} else if(Constant.STAT_POINT_PAGE_ENTER.equals(point)) {
			count = statisticDao.pageStatEnterCount(pageId, startTime, endTime);
		} else if(Constant.STAT_POINT_PAGE_EXIT.equals(point)) {
			count = statisticDao.pageStatOutCount(pageId, startTime, endTime);
		}*/
		return count;
	}
	
	/**
	 * 页面平均加载时间，单位：秒
	 * @param pageId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public double pageLoadTime(Integer pageId, int startTime, int endTime) {
		/*double result = statisticDao.pageAvgLoadTime(pageId, startTime, endTime);
		//转成成秒，保留3为小数
		double second = result/1000;
		BigDecimal b = new BigDecimal(second);
		return b.setScale(3,  BigDecimal.ROUND_HALF_UP).doubleValue();
		*/
		//TODO 
		return 0.0;
	}
	
	/**
	 * 页面平均加载时间，单位：秒
	 * @param pageId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public double pageStayTime(Integer pageId, int startTime, int endTime) {
		/*double result = statisticDao.pageAvgStayTime(pageId, startTime, endTime);
		//转成成秒，保留3为小数
		double second = result/1000;
		BigDecimal b = new BigDecimal(second);
		return b.setScale(3,  BigDecimal.ROUND_HALF_UP).doubleValue();*/
		//TODO 
		return 0.0;
		
	}
	
	/**
	 * 浏览器统计
	 * @param statPoints
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public List<Map<String, Object>> browserStat(List<String> statPoints, int startTime, int endTime) {
		/*List<String> browsers = statisticDao.findBrowsers(startTime, endTime);
		if(browsers == null || browsers.isEmpty()) {
			return Collections.emptyList();
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(String browser : browsers) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("browser", browser);
			for(String statPoint : statPoints) {
				int count = 0;
				if(Constant.STAT_POINT_PV .equals(statPoint)) {
					count = statisticDao.browserPV(startTime, endTime, browser);
				} else if(Constant.STAT_POINT_UV.equals(statPoint)) {
					count = statisticDao.browserUV(startTime, endTime, browser);
				} else if(Constant.STAT_POINT_IP.equals(statPoint)) {
					count = statisticDao.browserIP(startTime, endTime, browser);
				}
				m.put(statPoint,  count);
			}
			result.add(m);
		}*/
		//TODO
		return null;
	}
	
	/**
	 * 操作系统统计
	 * @param statPoints
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public List<Map<String, Object>> osStat(List<String> statPoints, int startTime, int endTime) {
		/*List<String> oses = statisticDao.findOS(startTime, endTime);
		if(oses == null || oses.isEmpty()) {
			return Collections.emptyList();
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(String os : oses) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("os", os);
			for(String statPoint : statPoints) {
				int count = 0;
				if(Constant.STAT_POINT_PV .equals(statPoint)) {
					count = statisticDao.osPV(startTime, endTime, os);
				} else if(Constant.STAT_POINT_UV.equals(statPoint)) {
					count = statisticDao.osUV(startTime, endTime, os);
				} else if(Constant.STAT_POINT_IP.equals(statPoint)) {
					count = statisticDao.osIP(startTime, endTime, os);
				}
				m.put(statPoint,  count);
			}
			result.add(m);
		}
*/	
		//TODO 
		return null;
	}
	
	
	private void close(Jedis jedis){
		
        /*if(jedis!=null && jedisPool!=null)
        	jedisPool.returnResource(jedis);*/
        /*if(jedis!=null)
        	jedis.close();
        
        LOG.debug(" Is Jedis connected " +jedis.isConnected());
        if(jedis!=null && jedis.isConnected())
            jedis.disconnect();
        LOG.debug(" After disconnecting: is redis connected  " +jedis.isConnected());*/
	}
}

package com.ycm.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.ycm.api.VisitService;
import com.ycm.constants.Constant;
import com.ycm.dto.EventInfo;
import com.ycm.dto.Log;
import com.ycm.dto.PageLoadTime;
import com.ycm.dto.PageVisitInfo;
import com.ycm.dto.VisitInfo;
import com.ycm.util.DateUtil;
import com.ycm.util.GsonUtil;
import com.ycm.util.RedisKeyUtils;
import com.ycm.util.SpringContextHolder;
import com.ycm.web.session.manager.SessionManager;

@Service
public class VisitServiceImpl implements VisitService{
	
	private static Logger LOG = LoggerFactory.getLogger(VisitServiceImpl.class);
	
	@Autowired
	private RedisSingleService redisService;
	
//	@Autowired
//    private JedisCluster redisService;
	
	@Override
	public void saveVisit(VisitInfo visit) {
		//TODO 
		/*Date date = new Date();
		//增加一条访客记录
		Long id = redisService.getIncrId("v");
		String key = RedisKeyUtils.getUVKey();
		visit.setId(id);
		LOG.info("访客KEY值：{} ---- 访客信息：{} ",key,ToStringBuilder.reflectionToString(visit));
		
		Integer score = DateUtil.parseStrToInt(date);
		
		//访客记录
		redisService.zadd(key,score,GsonUtil.toJson(visit));
		if(redisService.exists(key))
			redisService.lpushx(key, GsonUtil.toJson(visit));
		else 
			redisService.lpush(key, GsonUtil.toJson(visit));
		
		//是否为新访客
		Object obj = redisService.get(RedisKeyUtils.getNewUVKey(visit.getTjUid()));
		if(obj == null) //新访客数
			redisService.zadd(RedisKeyUtils.getNewUVKey(),score, GsonUtil.toJson(visit));
		
		//IP数 使用 Set
		redisService.zadd(RedisKeyUtils.getIPKey(),score, visit.getIp());
		
		//第一次访问的时间
		Map<String,String> map = new HashMap<String,String>();
		map.put("firstVisitTime", DateUtil.formatDateToString(date, "yyyyMMddHHmmssSSS"));
		
		//最后一次访问的时间
		if(visit.getLastVisitTime()!=null && visit.getLastVisitTime()>0){
			map.put("lastVisitTime", visit.getLastVisitTime().toString());
		}
		redisService.hmset(RedisKeyUtils.getNewUVKey(visit.getTjUid()), map);*/
	}
	
	@Override
	public void savePageVisit(PageVisitInfo page) {
		Jedis jedis = null;
		try{
			jedis = redisService.getJedis();
			Long id = jedis.incr("p");
			
			String p = page.getUrl().split("\\?")[0];
			
			String key = RedisKeyUtils.getPVKey(p);
			page.setId(id);
			LOG.info("访问页面 KEY值：{} ---- 页面访问信息：{} ", key,
					ToStringBuilder.reflectionToString(page));

			Date date = new Date();
			
			Integer score = DateUtil.parseStrToInt(date);
			
			String tag = DateUtil.formatDateToString(date, "yyyyMMdd");

			// PV 通过此统计 不允许重复
			jedis.zadd(key, score, GsonUtil.toJson(page));    //LOG 全纪录
			
			//PV总记录
			jedis.zadd(RedisKeyUtils.getPVKey(), score, page.getUrl()
					+ Constant.SEPERATOR + id);
			
			//TODO 渠道来源统计
			if(StringUtils.isNotBlank(page.getReferer())){
				jedis.zadd(RedisKeyUtils.getPVKey(page.getReferer()), score, page.getUrl()
						+ Constant.SEPERATOR + id);
			}

			//UV
			if (StringUtils.isNotBlank(page.getTjUid())) {
				// UV（访客数） 通过score 来区别
				Object tjUid = SessionManager.getAttribute(page.getTjUid());
				
				//每个页面的UV
				jedis.zadd(RedisKeyUtils.getUVByUrl(p), score,
						page.getTjUid() + Constant.SEPERATOR + tag);
				
				if(tjUid == null){
					//存储 给定一个30分钟过期
					SessionManager.setAttribute(page.getTjUid(), page.getTjUid());
					
					jedis.zadd(RedisKeyUtils.getUVKey(), score,
							page.getTjUid() + Constant.SEPERATOR + id);
					
					// NUV （新访客数：一天当中剔除重复访问的）
					String newKey = RedisKeyUtils.getNewUVKey();
					// 查询当天是否已经存在
					jedis.zadd(newKey, score, page.getTjUid() + Constant.SEPERATOR + tag);
				}
				// redisService.hset(RedisKeyUtils.getNewUVKey(),
				// page.getTjUid(), page.getTjUid());
			}

			// IP数 使用 SortSet 通过score 来查询
			if (StringUtils.isNotBlank(page.getIp())) {
				String newKey = RedisKeyUtils.getIPKey();
				
				jedis.zadd(newKey, score, page.getIp() + Constant.SEPERATOR + tag);
				//每个页面的IP数
				jedis.zadd(RedisKeyUtils.getIPByUrl(p), score, page.getIp() + Constant.SEPERATOR + tag);
			}
		} catch (Exception e) {
			redisService.close(jedis,true);
			e.printStackTrace();
		} finally {
			redisService.close(jedis,false);
		}
	}

	@Override
	public void saveEvent(EventInfo event) {
		//TODO 待需求挖掘
		/*Long id = redisService.getIncrId("e");
		String key = RedisKeyUtils.getEventKey();
		event.setId(id);
		LOG.info("事件KEY值：{} ---- 事件信息：{} ",key,ToStringBuilder.reflectionToString(event));
		//redisService.set(key, event);
		if(redisService.exists(key))
			redisService.lpushx(key, GsonUtil.toJson(event));
		else
			redisService.lpush(key, GsonUtil.toJson(event));*/
	}

	@Override
	public void savePageLoad(PageLoadTime loadTime) {
		//TODO
		/*Long id = redisService.getIncrId("l");
		String key = RedisKeyUtils.getLoadKey();
		loadTime.setId(id);
		LOG.info("页面加载 KEY值：{} ---- 页面加载信息：{} ",key,ToStringBuilder.reflectionToString(loadTime));
		//redisService.set(key, loadTime);
		if(redisService.exists(key))
			redisService.lpushx(key, GsonUtil.toJson(loadTime));
		else 
			redisService.lpush(key, GsonUtil.toJson(loadTime));*/
	}

	@Override
	public void save(Log log) {
		if(log instanceof VisitInfo){
			saveVisit((VisitInfo) log);
		}else if(log instanceof EventInfo){
			saveEvent((EventInfo) log);
		}else if(log instanceof PageVisitInfo){
			savePageVisit((PageVisitInfo) log);
		}else if(log instanceof PageLoadTime){
			savePageLoad((PageLoadTime) log);
		}
	}


	private void close(Jedis jedis,boolean f){
       /* if(jedis!=null)
        	jedis.close();
        
        LOG.debug(" Is Jedis connected " +jedis.isConnected());
        if(jedis!=null && jedis.isConnected())
            jedis.disconnect();
        LOG.debug(" After disconnecting: is redis connected  " +jedis.isConnected());*/
	}
	
}

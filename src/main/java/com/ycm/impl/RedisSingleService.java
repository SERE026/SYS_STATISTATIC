package com.ycm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisSingleService {

	private static Logger LOG = LoggerFactory.getLogger(RedisSingleService.class);
	
	@Autowired
	private JedisPool jedisPool/* = SpringContextHolder.getBean("jedisPool")*/;
	
	public synchronized Jedis getJedis(){
		if(jedisPool == null)
			return null;
		return jedisPool.getResource();
	}
	
	
	public synchronized void close(Jedis jedis,boolean f){
		if(jedis!=null && jedisPool!=null){
			if(f){
					jedisPool.returnBrokenResource(jedis);
			}else {
					jedisPool.returnResource(jedis);
			}
		}
	}


	/*@Override
	public Long getIncrId(String key) {
		
		return getJedis().incr(key);
	}
	
	@Override
	public Long incrBy(String key,Integer increment) {
		return getJedis().incrBy(key,increment);
	}


	@Override
	public Object get(String key) {
		return getJedis().get(SerializationUtil.serialize(key));
	}

	@Override
	public void set(String key, Object value) {
		getJedis().set(SerializationUtil.serialize(key),
				SerializationUtil.serialize(value));
	}
	
	@Override
	public void set(String key, Object value,Integer seconds) {
		getJedis().set(SerializationUtil.serialize(key),
				SerializationUtil.serialize(value));
		getJedis().expire(SerializationUtil.serialize(key), seconds);
	}

	@Override
	public Map hmget(String key) {
		  // map key的个数
		  LOG.info("map的key的个数:{}" ,getJedis().hlen(key));
		  // map value
		  LOG.info("map的value-->{}", getJedis().hvals(key));
		  
		  // map key
		  Set<String> keys = getJedis().hkeys(key);
		  LOG.info("map的key-->{}" , getJedis().hkeys(key));
		  
		  Map map = new HashMap();
		  for(String k : keys){
			  List<String> value = getJedis().hmget(key, k);
			  if(value != null && value.size() > 0){
				  map.put(k, value.get(0));
			  }
		  }
		  
		  // (String key, String... fields)返回值是一个list
		   String[] keyArgs = (String[]) keys.toArray();
		  List<String> list = getJedis().hmget(key, keyArgs);
		  LOG.info("redis中key的各个 fields值：{}, 长度：{}",list, list.size());
		 
		  // 删除map中的某一个键 的值 password 当然 (key, fields) 也可以是多个fields
		  getJedis().hdel(key, "password");
		  System.out.println("删除后map的key" + getJedis().hkeys(key));
		
		return map;
	}

	@Override
	public void hmset(String key, Map value) {
		getJedis().hmset(key, value);
	}
	
	@Override
	public String hget(String key, String fieldKey) {
		List<String> value = getJedis().hmget(key, fieldKey);
		if (value != null && value.size() > 0) {
			return value.toString();
		}
		return "";
	}

	@Override
	public void hset(String key, String fieldKey, String value) {
		getJedis().hset(key,fieldKey, value);
	}

	@Override
	public List lrange(String key,Integer start,Integer end) {
		//再取出所有数据jedis.lrange是按范围取出，  
		// 第一个是key，第二个是起始位置，第三个是结束位置，getJedis().llen获取长度 -1表示取得所有  
		return getJedis().lrange(key, start, end);
	}
	
	

	@Override
	public void lpush(String key, String... value) {
		getJedis().lpush(key,value);
		 //删除list中的数据
//		 getJedis().del(key);
//		 getJedis().rpush(key,v1); // 删除List中值为v1的值
//		 getJedis().rpush(key,v2);  
//		 getJedis().rpush(key,v3);   
	}
	
	@Override
	public void lpushx(String key, String... value) {
		getJedis().lpushx(key,value);
	}
	
	@Override
	public void linsert(String key,LIST_POSITION where,String pivot, String value) {
		getJedis().linsert(key, LIST_POSITION.AFTER, pivot, value);
	}
	

	@Override
	public void sadd(String key, String... value) {
		getJedis().sadd(key, value);
	}

	@Override
	public Set smembers(String key) {
		return getJedis().smembers(key);
	}

	@Override
	public void del(String key) {
		getJedis().del(key);
	}

	@Override
	public void expire(String key,Integer seconds) {
		getJedis().expire(key, seconds);
	}

	@Override
	public long zcount(String key, double min, double max) {
		Long len = getJedis().zcount(key.getBytes(), min, max);
		return len;
	}

	@Override
	public boolean exists(String key) {
		return getJedis().exists(key);
	}

	@Override
	public void zadd(String key, Integer score, String value) {
		getJedis().zadd(key, score, value);
	}

	@Override
	public Long llen(String key) {
		return getJedis().llen(key);
	}
	

	*//**
     * 全局扫描hset
     *
     * @param match field匹配模式
     * @return
     *//*
    public List<Map.Entry<String, String>> scanHSet(String key, String match) {
        int cursor = 0;
        ScanParams scanParams = new ScanParams();
        scanParams.match(match);
       
        ScanResult<Map.Entry<String, String>> scanResult;
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>();
        do {
            scanResult = getJedis().hscan(key, String.valueOf(cursor), scanParams);
            list.addAll(scanResult.getResult());
            cursor = Integer.parseInt(scanResult.getStringCursor());
        } while (cursor > 0);
        return list;
    }

	@Override
	public Set<String> zrange(String key, Integer start, Integer end) {
		return getJedis().zrange(key, start, end);
	}
	
	@Override
	public Set<String> zrangeByScore(String key, Integer startScore, Integer endScore) {
		return getJedis().zrangeByScore(key, startScore, endScore);
	}

	@Override
	public Set<String> getKeys(String key) {
		Set<String> keys = getJedis().keys("*");
		//Set<String> keys = jedis.keys("user.userid.*");
		return keys;
	}*/

}

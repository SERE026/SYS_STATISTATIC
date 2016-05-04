/*package com.ycm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.ycm.api.RedisService;
import com.ycm.util.GsonUtil;
import com.ycm.util.SpringContextHolder;

@Service("RedisOneService")
public class RedisServiceImpl implements RedisService{

	@Autowired
	private JedisPool jedisPool = SpringContextHolder.getBean("jedisPool");
	
	public Jedis getJedis(){
		return jedisPool.getResource();
	}
	
	@Override
	public Long getIncrId(String key) {
		Jedis jedis = jedisPool.getResource();
		return jedis.incr(key);
	}

	@Override
	public Long incrBy(String key, Integer increment) {
		Jedis jedis = jedisPool.getResource();
		return jedis.incrBy(key, increment);
	}

	@Override
	public Object get(String key) {
		Jedis jedis = jedisPool.getResource();
		return jedis.get(key);
	}

	@Override
	public void set(String key, Object value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		jedis.set(key, GsonUtil.toJson(value));
	}

	@Override
	public void set(String key, Object value, Integer seconds) {
		Jedis jedis = jedisPool.getResource();
		jedis.set(key, GsonUtil.toJson(value));
		jedis.expire(key, seconds);
	}

	@Override
	public Map hmget(String key) {
		Jedis jedis = jedisPool.getResource();
		Set<String> keys = jedis.hkeys(key);
		Map map = new HashMap();
		for (String k : keys) {
			List<String> value = jedis.hmget(key, k);
			if (value != null && value.size() > 0) {
				map.put(k, value.get(0));
			}
		}

		return map;
	}

	@Override
	public String hget(String key, String filedKey) {
		Jedis jedis = jedisPool.getResource();
		return jedis.hget(key, filedKey);
	}

	@Override
	public void hmset(String key, Map value) {
		Jedis jedis = jedisPool.getResource();
		jedis.hmset(key, value);
	}

	@Override
	public void hset(String key, String fieldKey, String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.hset(key, fieldKey, value);
	}

	@Override
	public Long llen(String key) {
		Jedis jedis = jedisPool.getResource();
		return jedis.llen(key);
	}

	@Override
	public List lrange(String key, Integer start, Integer end) {
		Jedis jedis = jedisPool.getResource();
		return jedis.lrange(key, start, end);
	}

	@Override
	public void lpush(String key, String... value) {
		Jedis jedis = jedisPool.getResource();
		jedis.lpush(key, value);
	}

	@Override
	public void lpushx(String key, String... value) {
		Jedis jedis = jedisPool.getResource();
		jedis.lpushx(key, value);
	}

	@Override
	public void linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.linsert(key, where, pivot, value);
	}

	@Override
	public void sadd(String key, String... value) {
		Jedis jedis = jedisPool.getResource();
		jedis.sadd(key, value);
	}

	@Override
	public Set smembers(String key) {
		Jedis jedis = jedisPool.getResource();		
		return jedis.smembers(key);
	}

	@Override
	public void del(String key) {
		Jedis jedis = jedisPool.getResource();	
		jedis.del(key);
	}

	@Override
	public void expire(String key, Integer seconds) {
		Jedis jedis = jedisPool.getResource();
		jedis.expire(key, seconds);
	}

	@Override
	public long zcount(String key, double min, double max) {
		Jedis jedis = jedisPool.getResource();
		return jedis.zcount(key, min, max);
	}

	@Override
	public boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		return jedis.exists(key);
	}

	@Override
	public void zadd(String key, Integer score, String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.zadd(key, score,value);
	}

	@Override
	public Set<String> zrange(String key, Integer start, Integer end) {
		Jedis jedis = jedisPool.getResource();
		return jedis.zrange(key, start, end);
	}

	@Override
	public Set<String> getKeys(String key) {
		Jedis jedis = jedisPool.getResource();
		return jedis.keys(key);
	}

	@Override
	public Set<String> zrangeByScore(String key, Integer startScore,
			Integer endScore) {
		Jedis jedis = jedisPool.getResource();
		return jedis.zrangeByScore(key, startScore, endScore);
	}
	

}
*/
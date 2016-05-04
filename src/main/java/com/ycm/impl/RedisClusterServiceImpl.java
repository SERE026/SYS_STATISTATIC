/*package com.ycm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Transaction;

import com.ycm.api.RedisService;
import com.ycm.util.SerializationUtil;

@Service("RedisClusterService")
public class RedisClusterServiceImpl implements RedisService{
	
	private static Logger LOG = LoggerFactory.getLogger(RedisClusterServiceImpl.class);

	@Autowired
    private JedisCluster jedisCluster;
	
	@Override
	public Long getIncrId(String key) {
		return jedisCluster.incr(key);
	}
	
	@Override
	public Long incrBy(String key,Integer increment) {
		return jedisCluster.incrBy(key,increment);
	}


	@Override
	public Object get(String key) {
		return jedisCluster.get(SerializationUtil.serialize(key));
	}

	@Override
	public void set(String key, Object value) {
		jedisCluster.set(SerializationUtil.serialize(key),
				SerializationUtil.serialize(value));
	}
	
	@Override
	public void set(String key, Object value,Integer seconds) {
		jedisCluster.set(SerializationUtil.serialize(key),
				SerializationUtil.serialize(value));
		jedisCluster.expire(SerializationUtil.serialize(key), seconds);
	}

	@Override
	public Map hmget(String key) {
		  // map key的个数
		  LOG.info("map的key的个数:{}" ,jedisCluster.hlen(key));
		  // map value
		  LOG.info("map的value-->{}", jedisCluster.hvals(key));
		  
		  // map key
		  Set<String> keys = jedisCluster.hkeys(key);
		  LOG.info("map的key-->{}" , jedisCluster.hkeys(key));
		  
		  Map map = new HashMap();
		  for(String k : keys){
			  List<String> value = jedisCluster.hmget(key, k);
			  if(value != null && value.size() > 0){
				  map.put(k, value.get(0));
			  }
		  }
		  
		  // (String key, String... fields)返回值是一个list
		   String[] keyArgs = (String[]) keys.toArray();
		  List<String> list = jedisCluster.hmget(key, keyArgs);
		  LOG.info("redis中key的各个 fields值：{}, 长度：{}",list, list.size());
		 
		  // 删除map中的某一个键 的值 password 当然 (key, fields) 也可以是多个fields
		  jedisCluster.hdel(key, "password");
		  System.out.println("删除后map的key" + jedisCluster.hkeys(key));
		
		return map;
	}

	@Override
	public void hmset(String key, Map value) {
		jedisCluster.hmset(key, value);
	}
	
	@Override
	public String hget(String key, String fieldKey) {
		List<String> value = jedisCluster.hmget(key, fieldKey);
		if (value != null && value.size() > 0) {
			return value.toString();
		}
		return "";
	}

	@Override
	public void hset(String key, String fieldKey, String value) {
		jedisCluster.hset(key,fieldKey, value);
	}

	@Override
	public List lrange(String key,Integer start,Integer end) {
		//再取出所有数据jedis.lrange是按范围取出，  
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedisCluster.llen获取长度 -1表示取得所有  
		return jedisCluster.lrange(key, start, end);
	}
	
	

	@Override
	public void lpush(String key, String... value) {
		jedisCluster.lpush(key,value);
		 //删除list中的数据
		 jedisCluster.del(key);
		 jedisCluster.rpush(key,v1); // 删除List中值为v1的值
		 jedisCluster.rpush(key,v2);  
		 jedisCluster.rpush(key,v3);   

	}
	
	@Override
	public void lpushx(String key, String... value) {
		jedisCluster.lpushx(key,value);
	}
	
	@Override
	public void linsert(String key,LIST_POSITION where,String pivot, String value) {
		jedisCluster.linsert(key, LIST_POSITION.AFTER, pivot, value);
	}
	

	@Override
	public void sadd(String key, String... value) {
		jedisCluster.sadd(key, value);
	}

	@Override
	public Set smembers(String key) {
		return jedisCluster.smembers(key);
	}

	@Override
	public void del(String key) {
		jedisCluster.del(key);
	}

	@Override
	public void expire(String key,Integer seconds) {
		jedisCluster.expire(key, seconds);
	}

	@Override
	public long zcount(String key, double min, double max) {
		Long len = jedisCluster.zcount(key.getBytes(), min, max);
		return len;
	}

	@Override
	public boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public void zadd(String key, Integer score, String value) {
		jedisCluster.zadd(key, score, value);
	}

	@Override
	public Long llen(String key) {
		return jedisCluster.llen(key);
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
            scanResult = jedisCluster.hscan(key, String.valueOf(cursor), scanParams);
            list.addAll(scanResult.getResult());
            cursor = Integer.parseInt(scanResult.getStringCursor());
        } while (cursor > 0);
        return list;
    }

	@Override
	public Set<String> zrange(String key, Integer start, Integer end) {
		return jedisCluster.zrange(key, start, end);
	}
	
	@Override
	public Set<String> zrangeByScore(String key, Integer startScore, Integer endScore) {
		return jedisCluster.zrangeByScore(key, startScore, endScore);
	}

	@Override
	public Set<String> getKeys(String key) {
		// TODO Auto-generated method stub
		//Set<String> keys = jedis.keys("*");
		//Set<String> keys = jedis.keys("user.userid.*");
		return jedisCluster.hkeys(key);
	}

}
*/
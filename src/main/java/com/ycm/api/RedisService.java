package com.ycm.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;

public interface RedisService extends BaseService{

	/**
	 * 获取自增id
	 * @param key
	 * @return
	 */
	public Long getIncrId(String key);
	
	/**
	 * 将key对应的数字加decrement。如果key不存在，操作之前，key就会被置为0。
	 * 如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误。这个操作最多支持64位有符号的正型数字。
	 * @param key
	 * @param increment
	 * @return
	 */
	Long incrBy(String key, Integer increment);
	
	/**
	 * 通用的get 方法，可以存放任何对象
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * 通用的set方法，可以存放任何对象
	 * @param key
	 * @param value
	 */
	public void set(String key,Object value) ;
	
	/**
	 * 通用的set方法，可以存放任何对象
	 * @param key
	 * @param value
	 */
	public void set(String key,Object value,Integer seconds) ;
	
	/**
	 * 获取一个map对象
	 * @param key
	 * @return
	 */
	public Map hmget(String key);
	
	/**
	 * 获取map对象 
	 * @param key
	 * @param fieldKey
	 * @return
	 */
	public String hget(String key,String filedKey);
	
	/**
	 * 存放一个map对象
	 * @param key
	 * @param value
	 */
	public void hmset(String key,Map value) ;
	
	/**
	 * 修改一个map对象 中指定的field的值
	 * @param key
	 * @param fieldKey
	 * @param value
	 */
	public void hset(String key,String fieldKey,String value) ;
	
	/**
	 * 获取list的长度
	 * @param key
	 * @return
	 */
	public Long llen(String key);
	
	/**
	 * 获取一个List对象
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List lrange(String key,Integer start,Integer end);
	
	/**
	 * 存放一个List对象
	 * @param key
	 * @param value
	 */
	public void lpush(String key,String... value) ;
	
	/**
	 * 存放一个List对象
	 * @param key
	 * @param value
	 */
	public void lpushx(String key,String... value) ;
	
	/**
	 * 往List 中插入一条数据
	 * @param key
	 * @param where 条件 before after
	 * @param pivot 位置，如在world位置之前或者之后插入
	 * @param value
	 */
	public void linsert(String key,  LIST_POSITION where, String pivot, String value);
	
	/**
	 * 存放Set 集合
	 * @param key
	 * @param value
	 */
	public void sadd(String key,String... value);
	
	/**
	 * 获取Set 集合
	 * @param key
	 * @return
	 */
	public Set smembers(String key);
	
	/**
	 * 删除对应的key值
	 * @param key
	 */
	public void del(String key);
	
	/**
	 * 更新key的有效时间
	 * @param key
	 * @param seconds
	 */
	public void expire(String key,Integer seconds);

	/**
	 * 获取指定权重区间内集合的数量
	 * @param String key
	 * @param double min 最小排序位置
	 * @param double max 最大排序位置
	 */
	public long zcount(String key, double min, double max);

	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key);

	/**
	 * sorted set 添加
	 * @param key
	 * @param score 分数，默认用日期值 查询比较方便
	 * @param value value
	 */
	public void zadd(String key, Integer score, String value);

	/**
	 * 分页去sorted Set 的值
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrange(String key, Integer start, Integer end);
	
	
	/**
	 * 对key的模糊查询：
	 * @param key
	 * @return
	 */
	public Set<String> getKeys(String key);
	
	/**
	 * 查询处于startCore 和 endScore中的元素
	 * @param key  
	 * @param startScore  
	 * @param endScore 通过时间设计
	 * @return
	 */
	public Set<String> zrangeByScore(String key, Integer startScore, Integer endScore);

}

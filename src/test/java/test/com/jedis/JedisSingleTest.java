package test.com.jedis;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisSingleTest {

	private ApplicationContext applicationContext;

	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext*.xml");
	}
	
	@Test
	public void test1(){
		JedisPool pool = (JedisPool) applicationContext.getBean("jedisPool");
		Jedis jedis = null;
		try {
			jedis = pool.getResource();

			jedis.set("name", "lisi");
			String name = jedis.get("name");
			System.out.println(name);
			// jedis.set("foo", "bar");
			// String foobar = jedis.get("foo");
			// jedis.zadd("sose", 0, "car");
			// jedis.zadd("sose", 0, "bike");
			// Set<String> sose = jedis.zrange("sose", 0, -1);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//关闭连接
			jedis.close();
//			if (pool != null && pool.isClosed()==false) {
//				System.out.println(pool.isClosed());
				//连接池销毁
//				pool.destroy();
//				System.out.println(pool.isClosed());
//			}
		}
	}
}

/*package com.ycm.pointcut;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.ycm.web.filter.XssHttpServletRequestWrapper;

@Component
@Aspect
public class JedisPointCut {

	private static Logger LOG = LoggerFactory
			.getLogger(JedisPointCut.class);

	*//**
	 * 定义Pointcut，Pointcut的名称 就是simplePointcut，此方法不能有返回值，该方法只是一个标示 execution(*
	 *//*
	@Pointcut("execution(* com.ycm.impl.RedisSingleService.*(..))")
	public void respPoint() {
	}

	*//**
	 * 前置通知，放在方法头上。
	 *//*
	
	 * @Before("respPoint()") public void before(JoinPoint jp) { String
	 * className = jp.getThis().toString(); String methodName =
	 * jp.getSignature().getName(); // 获得方法名 LOG.info("aop回调----位于：" + className
	 * + "调用" + methodName + "()方法-开始！"); Object[] args = jp.getArgs(); //
	 * 获得参数列表 }
	 

	*//**
	 * 后置【finally】通知，放在方法头上。
	 * 
	 * @param jp
	 *//*
	@After("respPoint()")
	public void after(JoinPoint jp) {

		String methodName = jp.getSignature().getName();
		LOG.info("==================AOP==" + methodName + "()方法-开始！");

		
		 * Object[] args = jp.getArgs(); // 获得参数列表 if (args.length <= 0) {
		 * LOG.info("====" + methodName + "方法没有参数"); } else { for (int i = 0; i
		 * < args.length; i++) { LOG.info("====AOP参数  " + (i + 1) + "：" +
		 * args[i]); } }
		 
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("==================AOP==" + jp.getSignature().getName()
				+ "()方法-结束！");
	}

	*//**
	 * 后置【try】通知，放在方法头上，使用returning来引用方法返回值。
	 *//*
	
	 * @AfterReturning(pointcut = "respPoint() ", returning = "result") public
	 * void afterReturning(JoinPoint jp,String result) { LOG.info("AOP后处理成功 ");
	 * }
	 

	*//**
	 * 后置【catch】通知，放在方法头上，使用throwing来引用抛出的异常。
	 * 
	 * @param jp
	 * @return
	 * @throws Throwable
	 *//*
	
	 * @AfterThrowing(pointcut = "respPoint()",throwing="e") public void
	 * doException(JoinPoint jp,Exception e){ if(e!=null){
	 * System.out.println("执行异常:" + e.getMessage()); //TODO 发送告警信息 } }
	 

	*//**
	 * 环绕通知，放在方法头上，这个方法要决定真实的方法是否执行，而且必须有返回值。
	 * 
	 * @param jp
	 * @return
	 * @throws Throwable
	 *//*
	
	 * @Around("respPoint()") public Object around(ProceedingJoinPoint jp)
	 * throws Throwable { LOG.info("正常运行"); return jp.proceed(); }
	 

}

*/
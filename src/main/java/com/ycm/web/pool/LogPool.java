package com.ycm.web.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ycm.api.VisitService;
import com.ycm.dto.Log;
import com.ycm.util.SpringContextHolder;

/**
 * 日志缓冲池
 *
 */
public class LogPool {
	
	private static LogPool logPool = null;
	
	@Autowired
	private LogQueue queue;
	
	private  ExecutorService executor = Executors.newCachedThreadPool();
	
	private LogPool() {
		//System.out.println("日志缓存队列开始启动");
		if(queue!=null){
			Log log = (Log) queue.take();
			if(log != null)
				executor.execute(new LogThread(log));
		}
		
        //System.out.println("日志缓存队列开始启动完毕");
	}
	
	public static synchronized LogPool getInstance() {
		if(logPool == null) {
			logPool = new LogPool();
		}
		return logPool;
	}
	
	/**
	 * 将日志丢进缓冲池
	 * @param log
	 */
	public void push(Log log) {
		queue.put(log);
	}
	
	class LogThread implements Runnable{
		
		private Log log;
		
		private VisitService visitService = SpringContextHolder.getBean(VisitService.class);
		
		public LogThread(){
		}
		
		public LogThread(Log log){
			this.log = log;
		}

		@Override
		public void run() {
			visitService.save(log);
		}
		
	}
}


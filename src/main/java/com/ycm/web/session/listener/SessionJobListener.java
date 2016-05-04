package com.ycm.web.session.listener;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ycm.web.session.task.UserOnlineTimerTask;


public class SessionJobListener extends HttpServlet implements ServletContextListener {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = LoggerFactory.getLogger(SessionJobListener.class);
	
	
	Timer timer = new Timer();  


	@Override
	public void contextInitialized(ServletContextEvent e) {
		// TODO Auto-generated method stub
		LOG.info("initial context....");  
		
		timer.schedule(new UserOnlineTimerTask(), 0, 10000);  
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// TODO Auto-generated method stub
		LOG.info("destory context....");  
		  
		timer.cancel();  
	}



	
}

package com.ycm.web.session.task;

import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ycm.web.session.manager.AppSession;
import com.ycm.web.session.manager.SessionManager;


public class UserOnlineTimerTask extends TimerTask {
	
	private static Logger LOG = LoggerFactory.getLogger(UserOnlineTimerTask.class);
	
	@Override
	public void run() {
		for(Object key : SessionManager.sessionMap.keySet()){
			
			AppSession session = (AppSession) SessionManager.sessionMap.get(key);
			
//			LOG.info("key=[{}] 最后访问时间："+session.getLastAccessedTime().getTime()+"-----当前时间："+(new Date()).getTime(),key);
			if((session.getLastAccessedTime().getTime()+session.getMaxInactiveInterval()*1000L)<(new Date()).getTime()){
				SessionManager.removeAttribute(session.getKey());
			}
		}
	}
	
}

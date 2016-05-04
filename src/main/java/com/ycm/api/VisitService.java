package com.ycm.api;

import com.ycm.dto.EventInfo;
import com.ycm.dto.Log;
import com.ycm.dto.PageLoadTime;
import com.ycm.dto.PageVisitInfo;
import com.ycm.dto.VisitInfo;

public interface VisitService {

	public void saveVisit(VisitInfo visit);
	
	public void savePageVisit(PageVisitInfo page);
	
	public void saveEvent(EventInfo event);
	
	public void savePageLoad(PageLoadTime loadTime);

	public void save(Log log);
}

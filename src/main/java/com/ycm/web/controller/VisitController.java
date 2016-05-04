package com.ycm.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycm.api.VisitService;
import com.ycm.constants.Constant;
import com.ycm.dto.Log;
import com.ycm.parser.CommonParser;
import com.ycm.parser.ParserFactory;
import com.ycm.util.ConvertUtils;
import com.ycm.util.CookieUtil;

import eu.bitwalker.useragentutils.UserAgent;
@Controller
@RequestMapping("/visit")
public class VisitController extends BaseController {
	
	private static Logger LOG = LoggerFactory.getLogger(VisitController.class);
	
	@Autowired
	private VisitService visitService;
	
	@RequestMapping("/send")
	public void send(HttpServletRequest request, HttpServletResponse response) {
		
		//writeImg(response);
		
		//参数map处理
		Map<String,String> param = ConvertUtils.reqToMap(request, true);
		
		String tj_et = request.getParameter("tj_et");	//操作类型 TODO
		
		if(StringUtils.isBlank(param.get("tj_url"))){
			String uri = request.getRequestURI();
			String uriPrefix = request.getContextPath();
			param.put("tj_url", Constant.TARGET_SITE_DOMAIN+uri+uriPrefix);
		}
		
		//根据et，判断日志类型
		if(StringUtils.isEmpty(tj_et)) {
			LOG.info("日志时间类型为空，跳过");
			return;
		}
		
		String ua = request.getHeader("User-Agent");
		Cookie cookie = CookieUtil.getCookie(request, Constant.YCM_TONGJI_SID);
		if(cookie != null) {
			cookie.getValue();
		}
		UserAgent userAgent = new UserAgent(ua);
		
		param.put("tj_ip", CookieUtil.getClientIP(request));
		param.put("tj_os", userAgent.getOperatingSystem().getName());
		param.put("tj_abr", ua);
		param.put("tj_br", userAgent.getBrowser().getName());
		param.put("tj_bv", userAgent.getBrowserVersion().getVersion());
		
		//创建日志解析器
		CommonParser parser = ParserFactory.createParser(tj_et);
		if(parser == null) {
			LOG.info("无法识别的日志类型:{}，跳过",tj_et);
			return;
		}
		
		//解析日志
		Log log = parser.parse(param);
		//将日志丢进缓冲池
		LOG.info("record LOG：{}",ToStringBuilder.reflectionToString(log));
		visitService.save(log);
		return;
	}
	
	
	private void writeImg(HttpServletResponse response){
		try {
			String path = this.getClass().getClassLoader().getResource("/").getPath();
			File file = new File(path+"/images/hm.gif");
			BufferedImage imge = ImageIO.read(file);
			ImageIO.write(imge, "gif", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

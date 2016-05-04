package com.ycm.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ycm.constants.Constant;
import com.ycm.dto.BaseInfo;
import com.ycm.dto.ClientInfo;
import com.ycm.dto.ElementInfo;
import com.ycm.dto.EventInfo;
import com.ycm.dto.IPAddressInfo;
import com.ycm.dto.Log;
import com.ycm.dto.PageLoadTime;
import com.ycm.dto.PageVisitInfo;
import com.ycm.dto.VisitInfo;
import com.ycm.util.ConvertUtils;
import com.ycm.util.DateUtil;
import com.ycm.util.GsonUtil;

public abstract class CommonParser {
	
	private static Logger LOG = LoggerFactory.getLogger(CommonParser.class);
	
	abstract public Log parse(Map<String,String> reqParam);
	
	protected void setCommon(Map<String,String> map, BaseInfo base) {
		
		String ip = map.get("tj_ip");
		base.setIp(ip); //设置IP
		base.setIpAddress(getAddressByIP(ip)); //获取IP地址
		
		//基本信息
		String tj_uid = map.get("tj_uid");	//标识是否为同一访客
		base.setTjUid(tj_uid);
		
		String tj_url = map.get("tj_url");	//访问的Url
		base.setUrl(tj_url);
		
		String referer = map.get("referrer");  //上一个地址
		base.setReferer(referer);
		
		String title = map.get("tj_tt");    //页面title
		base.setTitle(title);
		
		//客户端信息;
		ClientInfo client = new ClientInfo();
		setClient(map, client);
		base.setClient(client);
	}
	
	/**
	 * 访客信息
	 * @param map
	 * @param visit
	 */
	public static void setVisit(Map<String,String> map, VisitInfo visit){
		//第一次访问时间  String tj_fvt = map.get("tj_fvt");	
		visit.setFirstVisitTime(Long.valueOf(DateUtil.parseDateToInt(new Date())));
		if(StringUtils.isNotBlank(visit.getTjUid())){
			visit.setIsNewVisit(Constant.IS_NEW_VISIT_NO);
		}else{
			visit.setIsNewVisit(Constant.IS_NEW_VISIT_YES);
		}
	}
	
	/**
	 * 事件信息
	 * @param map
	 * @param event
	 */
	public static void setEvent(Map<String,String> map, EventInfo event){
		//事件信息
		String tj_lt = map.get("tj_lt"); 	//事件发生时间
		String tj_tp = trim(map.get("tj_tp"));	//事件类型 	Click hover 整理一份，按照键盘key值定义
		String tj_intv = trim(map.get("tj_intv"));	//距离上次时间发生的时间 click 为第二次点击的时间 hover 则为停留在上面的时间
		String tj_x	= map.get("tj_x"); 		//鼠标距离 元素的X轴像素
		String tj_y	= map.get("tj_y"); 		//鼠标距离 元素的Y轴像素
		
		event.setTime(Long.valueOf(tj_lt));
		event.setType(tj_tp);
		event.setInterval(Long.valueOf(tj_intv));
		event.setOffsetX(tj_x);
		event.setOffsetY(tj_y);
		// 元素信息
		ElementInfo ele = new ElementInfo();
		setElement(map, ele);
		event.setElement(ele);
	}
	
	/**
	 * 元素信息
	 * @param map
	 * @param event
	 */
	public static void setElement(Map<String,String> map, ElementInfo element){
		String tj_tg = map.get("tj_tg");		//事件触发的标签
		element.setTag(tj_tg);
		String tj_tg_i = map.get("tj_tg_i");	//标签的id
		element.setTagId(tj_tg_i);
		String tj_tg_n	= map.get("tj_tg_n"); //	标签的name
		element.setTagName(tj_tg_n);
		String tj_tg_c	= map.get("tj_tg_c"); //	标签的class
		element.setTagClass(tj_tg_c);
		String tj_tg_s	= map.get("tj_tg_s"); //	标签的src
		element.setSrc(tj_tg_s);
		String tj_tg_h	= map.get("tj_tg_h"); //	标签的href
		element.setHref(tj_tg_h);
		String tj_tg_v	= map.get("tj_tg_v"); //	标签的value
		element.setValue(tj_tg_v);
		String tj_tg_t	= map.get("tj_tg_t"); //	标签的text
		element.setTag(tj_tg_t);
	}
	
	/**
	 * 页面停留信息
	 * @param map
	 * @param pageVisit
	 */
	public static void setPageVisit(Map<String,String> map, PageVisitInfo pageVisit){
		String tj_tsl	= trim(map.get("tj_tsl"));	//页面开始加载的时间
		pageVisit.setStartLoadTime(Long.valueOf(tj_tsl));
		String tj_tel	= trim(map.get("tj_tel"));	//页面加载完成的时间
		pageVisit.setEndLoadTime(Long.valueOf(tj_tel));
		String tj_tll	= trim(map.get("tj_tll"));	//页面离开的时间
		pageVisit.setLeaveTime(Long.valueOf(tj_tll));
		String tj_stt	= trim(map.get("tj_stt"));	//页面停留的时间
		pageVisit.setStayTime(Long.valueOf(tj_stt));
	}
	
	/**
	 * 页面加载信息
	 * @param map
	 * @param pageLoad
	 */
	public static void setPageLoad(Map<String,String> map, PageLoadTime pageLoad){
		String tj_net_all	= trim(map.get("tj_net_all"));		 	//从前一个页面unload到当前页面unload的时间
		pageLoad.setNetAll(Long.valueOf(tj_net_all));
		String tj_net_dns	= trim(map.get("tj_net_dns"));			//DNS 域名查询完成的时间，如果使用了本地缓存（即无 DNS 查询）或持久连接
		pageLoad.setNetDns(Long.valueOf(tj_net_dns));
		String tj_net_tcp	= trim(map.get("tj_net_tcp"));			//建立Http、Tcp 连接的时间
		pageLoad.setNetTcp(Long.valueOf(tj_net_tcp));
		String tj_srv		= trim(map.get("tj_srv"));				//开始接受响应到接受响应完成的时间
		pageLoad.setSrv(Long.valueOf(tj_srv));
		String tj_dom		= trim(map.get("tj_dom"));		 		//开始接受响应到接受响应完成的时间
		pageLoad.setDom(Long.valueOf(tj_dom));
		String tj_load_event= trim(map.get("tj_load_event"));		//click 、hover 事件的时间
		pageLoad.setLoadEvent(Long.valueOf(tj_load_event));
		String tj_bd_dom	= trim(map.get("tj_bd_dom"));			//dom 加载的时间
		pageLoad.setDom(Long.valueOf(tj_bd_dom));
		String tj_bd_run	= trim(map.get("tj_bd_run"));			//页面停留时间：执行统计代码的时间
		pageLoad.setBdRun(Long.valueOf(tj_bd_run));
		String tj_bd_def	= trim(map.get("tj_bd_def"));			//页面停留时间： 点击链接进入页面到该页面被卸载时的时间
		pageLoad.setBdDef(Long.valueOf(tj_bd_def));
	}
	
	public static void setClient(Map<String,String> map, ClientInfo client){
		//客户端信息
		String tj_os = map.get("tj_os");			//操作系统
		client.setOperSystem(tj_os);
		String tj_sr = map.get("tj_sr");			//电脑屏幕分辨率
		client.setScreenResolution(tj_sr);
		String tj_ja = trim(map.get("tj_ja"));			//是否支持java 1：支持 0：不支持
		client.setIsEnableJava(Integer.valueOf(tj_ja));
		String tj_fa = trim(map.get("tj_fa"));			//是否支持flash 1：支持 0：不支持
		client.setIsEnableFlash(Integer.valueOf(tj_fa));
		String tj_fv = map.get("tj_fv");			//flash 版本号
		client.setFlashVersion(tj_fv);
		String tj_cb = map.get("tj_cb");			//显示屏的色彩位数
		client.setColorBit(tj_cb);
		String tj_cl = map.get("tj_cl");			//客户端所使用的语言
		client.setClanguage(tj_cl);

		//TODO 浏览器信息
		String browser = map.get("tj_br");          //浏览器名称
		client.setBrowser(browser);
		String tj_bv = map.get("tj_bv");			//浏览器版本
		client.setBrowserVersion(tj_bv);
		String tj_abr = map.get("tj_abr");			//浏览器完整信息
		client.setAllBrowser(tj_abr);
	}
	
	/**
	 * http://ip.qq.com/cgi-bin/searchip?searchip1= //腾讯
	 * http://ip.taobao.com/service/getIpInfo.php?ip= //淘宝
	 */
	public static String getAddressByIP(String strIP) {
		try {
			String ipUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=";
			URL url = new URL(ipUrl + strIP);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line = null;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			LOG.info("IP接口调用返回结果：{}",result);
			JSONObject json = (JSONObject) JSONObject.parse(result.toString());
			 
			IPAddressInfo ipai = new IPAddressInfo();
			try {
				ipai = (IPAddressInfo) ConvertUtils.mapToObject((Map<String, Object>) json.get("data"), IPAddressInfo.class);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
			
			LOG.info("IP相关信息：{}",ToStringBuilder.reflectionToString(ipai));
			
			return ipai.getIPAddress();
		} catch (IOException e) {
			return "读取失败";
		}
	}
	
	public static String trim(String value){
		if(StringUtils.isBlank(value) || "undefined".equals(value)){
			return "-1";
		}
		return value;
	}
	
	public static void main(String[] args) {
		getAddressByIP("124.126.206.87");
	}
}

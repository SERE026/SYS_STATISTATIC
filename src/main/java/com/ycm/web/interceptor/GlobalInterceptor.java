
package com.ycm.web.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ycm.constants.Constant;
import com.ycm.util.CookieUtil;


/**
 * 系统拦截器
 */
public class GlobalInterceptor implements HandlerInterceptor {

	private static Logger LOG = LoggerFactory
			.getLogger(GlobalInterceptor.class);



	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		request.setCharacterEncoding("UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		
		/*String visitUrl = "*"; //TODO 此处从数据库或者配置文件取
		
		//设置访问白名单Access-Control-Allow-Origin
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");  
        response.setHeader("Access-Control-Max-Age", "3456000");  
        response.setHeader("Access-Control-Allow-Headers", "version,token,codetype,signtype,codeType,signType,IMSI,IMEI,browsor,imei,imsi,DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type"); 
        response.setHeader("Content-Type", "text/plain charset=UTF-8");

		*/
		String uri = request.getRequestURI();
		String uriPrefix = request.getContextPath();
		
		//判断请求cookie是否存在JF_TONGJI_SID值，如果不存在，认为是新访客，分配一个32位的uuid
		LOG.info("请求地址：{}，参数为：{}",uri+uriPrefix,getParam(request));
		
		
		Cookie cookie = CookieUtil.getCookie(request, Constant.YCM_TONGJI_SID);
		if(cookie == null) {
			System.out.println("新访客");
			Cookie newCookie = new Cookie(Constant.YCM_TONGJI_SID, UUID.randomUUID().toString());
			newCookie.setMaxAge(Integer.MAX_VALUE);
			newCookie.setPath("/");
			response.addCookie(newCookie);
		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
	
	 /**
     * 获取请求参数
     * @param map
     * @return
     */
	@SuppressWarnings("rawtypes")
	public static String getParam(HttpServletRequest request){
        // 参数Map
		Map properties = request.getParameterMap();
        // 返回值String
        String resultStr = "";
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            resultStr = resultStr + name + "=" + value + "&";
        }
        
        return resultStr;
	}

}

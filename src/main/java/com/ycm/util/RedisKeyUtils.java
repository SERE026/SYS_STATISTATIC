package com.ycm.util;

import java.util.Date;


public class RedisKeyUtils {
    
    public static String getUVKey(){
        return "UV:";
    }

	/*public static String getPageVisitKey(String url) {
		return "P:" + url +":"+DateUtil.formatDateToString(new Date(), "yyyyMMddHHmmss");
	}*/
	
    public static String getPVKey() {
		return "PV:" ;
	}
    
	public static String getPVKey(String item) {
		return "PV:" + item ;
	}

	public static String getEventKey() {
		return "E:"/* +DateUtil.formatDateToString(new Date(), "yyyyMMddHHmmss")*/;
	}

	public static String getLoadKey() {
		return "L:" /*+ DateUtil.formatDateToString(new Date(), "yyyyMMddHHmmss")*/;
	}

	public static String getNewUVKey(){
		return "UV:N";
	}
	
	public static String getNewUVKey(String token){
		return "UV:N:"+token;
	}

	public static String getUVByUrl(String url){
		return "UV:URL:"+url;
	}
	
	public static String getUVKey(String itemId){
		return "UV:"+DateUtil.formatDateToString(new Date(), "yyyyMMddHHmmss");
	}

	public static String getIPKey() {
		return "IP:";
	}
	public static String getIPByUrl(String url) {
		return "IP:URL:"+url;
	}
	

}

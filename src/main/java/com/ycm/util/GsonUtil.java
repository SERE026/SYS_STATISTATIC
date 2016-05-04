package com.ycm.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	
	private static Gson gson = new Gson();
	
    public static <T> T fronJson2Map(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
    }
	
	public static <T> T parse2Object(String json) {
		if(StringUtils.isEmpty(json)) {
			return null;
		}
		return gson.fromJson(json, new TypeToken<T>() {
        }.getType());
	}
	
	public static <T> T parse2Object(String json,TypeToken<T> token) {
		if(StringUtils.isEmpty(json)) {
			return null;
		}
		return gson.fromJson(json, token.getType());
	}
	
	public static String toJson(Object o) {
		return gson.toJson(o);
	}
	
}

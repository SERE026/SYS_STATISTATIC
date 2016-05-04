package com.ycm.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ConvertUtils {


	/**
	 * 使用Introspector进行转换
	 * @param map 
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject_i(Map<String, Object> map,
			Class<?> beanClass) throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			Method setter = property.getWriteMethod();
			if (setter != null) {
				setter.invoke(obj, map.get(property.getName()));
			}
		}

		return obj;
	}
	
	public static Map<String, Object> objectToMap_i(Object obj)
			throws Exception {
		if (obj == null)
			return null;

		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (key.compareToIgnoreCase("class") == 0) {
				continue;
			}
			Method getter = property.getReadMethod();
			Object value = getter != null ? getter.invoke(obj) : null;
			map.put(key, value);
		}

		return map;
	}

	/** 使用reflect进行转换 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)
			throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}

			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}

		return obj;
	}

	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}

		return map;
	}

	/**
	 * 将map 转换成String
	 * @param map
	 * @return
	 */
	public static String map2String(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}
	
	/**
	 * request 转map
	 * @param request 
	 * @param isDecode 是否需要decode 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> reqToMap(HttpServletRequest request,
			boolean isDecode) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map<String,String> returnMap = new HashMap<String,String>();
		Iterator entries = properties.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String name = (String) entry.getKey();
			
			String value = "";
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			//参数进行decode处理
			if(isDecode && StringUtils.isNotBlank(value)){
				try {
					value = URLDecoder.decode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
package com.redplus.view.utils;


import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.i5le.util.GsonHelper;

public class GsonUtil {

	public static <T> T transferByStr(String data, Type type) {
		Gson gson = GsonHelper.create();
		return gson.fromJson(data, type);
	}

	/**
	 * json תʵ��
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 */
	public static <T> T transferByStr(String data, Class<T> clazz) {
		Gson gson = GsonHelper.create();
		return gson.fromJson(data, clazz);
	}

	public static <T> List<T> transferBYList(String data, Class<T> clazz) {
		Gson gson = GsonHelper.create();
		List<T> eList = gson.fromJson(data, new TypeToken<List<T>>() {
		}.getType());
		return eList;
	}

	/**
	 * ʵ��װjson
	 * 
	 * @param obj
	 * @return
	 */
	public static String transferByEntity(Object obj) {

		Gson gson = GsonHelper.create();
		return gson.toJson(obj);

	}

}

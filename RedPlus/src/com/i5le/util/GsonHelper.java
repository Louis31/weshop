package com.i5le.util;

import java.lang.reflect.Modifier;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

public class GsonHelper {

	public static Gson createExp() {

		Gson gson = new GsonBuilder().excludeFieldsWithModifiers(
				Modifier.PROTECTED) // <---
				.create();
		return gson;
	}

	public static Gson create() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		return gsonBuilder.create();
	}

	public String[] toStringArray(String data) {
		String rex = "\"\\w*\":";
		String s2 = data.replaceAll(rex, "");
		System.out.println(data);

		System.out.println(s2);
		String rex1 = "[\\{\\}\"]";
		String s3 = s2.replaceAll(rex1, "");
		System.out.println(rex + rex1);
		String s[] = s3.split(",");

		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
		}
		return s;
	}

}

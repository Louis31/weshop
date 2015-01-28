package com.i5le.redplus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HttpPostUtils {
	


	public static void callAll(String phone, String msg) {
		String path = "http://api-dx.panzhi.net/http1.php";
		Map<String, String> responseData = new HashMap<String, String>();
		responseData.put("act", "send");
		responseData.put("orgid", "26027");
		responseData.put("username", "amududu");
		responseData.put("passwd", "amududu123");
		responseData.put("destnumbers", phone);

		responseData.put("msg", msg + " ! 【广东独一网络有限公司】");

		 System.out.println(httpPost(path, responseData));

	}

	public static void main(String[] args) {

		String tel = "18520113963";
		String path = "http://api-dx.panzhi.net/http1.php";

		// String
		// str="	http://api-dx.panzhi.net/http.php?act=send&orgid=26027&username=amududu&passwd=amududu123&msg=祝你生日快乐【广东独一网络有限公司】&destnumbers=18520113963";

		Map<String, String> responseData = new HashMap<String, String>();
		responseData.put("act", "send");

		responseData.put("orgid", "26027");
		responseData.put("username", "amududu");
		responseData.put("passwd", "amududu123");
		responseData.put("destnumbers", tel);
		
		responseData.put("msg", "test 【广东独一网络有限公司】");
		System.out.println(httpPost(path, responseData));
	}

	public static String httpPost(String urlAddress,
			Map<String, String> paramMap) {
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		String[] params = new String[paramMap.size()];
		int i = 0;
		for (String paramKey : paramMap.keySet()) {
			String param = paramKey + "=" + paramMap.get(paramKey);
			params[i] = param;
			i++;
		}
		return httpPost(urlAddress, params);
	}

	public static String httpPost(String urlAddress, List<String> paramList) {
		if (paramList == null) {
			paramList = new ArrayList<String>();
		}
		return httpPost(urlAddress, paramList.toArray(new String[0]));
	}

	public static String httpPost(String urlAddress, String[] params) {
		URL url = null;
		HttpURLConnection con = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {

			String paramsTemp = "";
			for (int i = 0; i < params.length; i++) {

				paramsTemp += (i == 0 ? "?" : "&") + params[i];
			}
			// for (String param : params) {
			// if (param != null && !"".equals(param.trim())) {
			// paramsTemp += "&" + param;
			// }
			// }
			url = new URL(urlAddress );
			
			con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			byte[] b = paramsTemp.getBytes("");
			con.getOutputStream().write(b, 0, b.length);
			con.getOutputStream().flush();
			con.getOutputStream().close();
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				} else {
					result.append(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (con != null) {
					con.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}
}

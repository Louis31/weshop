/**
 *     all right reserved
 * @author:Mr.Yellow
 *     2014年6月30日
 */
package com.i5le.framwork.core.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

/**
 * UrlConnect 2014年6月30日
 */
public final class UrlConnect {
	private UrlConnect() {
	}

	public static final String POST = "POST";
	public static final String GET = "GET";

	public static final String CTYPE = "application/x-www-form-urlencoded;charset=utf-8";

	public static HttpURLConnection getConnection(URL url, String method,
			String ctype) throws IOException {
		HttpURLConnection conn = null;
		if ("https".equals(url.getProtocol())) {
			SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0],
						new TrustManager[] { new DefaultTrustManager() },
						new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url
					.openConnection();
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
				
					return true;
				}

			});
			conn = connHttps;

		} else {
			conn = (HttpURLConnection) url.openConnection();
		}

		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "uop-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);

		return conn;
	}

	public static HttpURLConnection getConnection(URL url, String Method)
			throws IOException {
		return getConnection(url, Method, CTYPE);

	}

	public static HttpURLConnection getPostConnection(URL url)
			throws IOException {
		return getConnection(url, POST);

	}

	public static HttpURLConnection getGetConnection(URL url)
			throws IOException {
		return getConnection(url, GET);

	}

}

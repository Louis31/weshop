
package com.i5le.redplus.util;  
  
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;  
import java.util.Map.Entry;  
import java.util.Set;
  
public class HttpUtil {  
	
	
	public static void sendMsg(String tel, String msg){
		
		String path = "http://api-dx.panzhi.net/http1.php";
		Map<String, String> responseData = new HashMap<String, String>();
		responseData.put("act", "send");

		responseData.put("orgid", "26027");
		responseData.put("username", "amududu1");
		responseData.put("passwd", "amududu1123123");
		responseData.put("destnumbers", tel);
		responseData.put("msg", msg + "【验证码】");
		System.out.println(HttpUtil.http(path, responseData));
	}
	public static void main(String[] args) {
		String tel = "18613142052";
		String path = "http://api-dx.panzhi.net/http1.php";
        String msg = "收：020AELLA 发：88888888详情：C1,E1, ! 【验证码】";
		// String
		// str="	http://api-dx.panzhi.net/http.php?act=send&orgid=26027&username=amududu&passwd=amududu123&msg=祝你生日快乐【广东独一网络有限公司】&destnumbers=18520113963";

		Map<String, String> responseData = new HashMap<String, String>();
		responseData.put("act", "send");

		responseData.put("orgid", "26027");
		responseData.put("username", "amududu1");
		responseData.put("passwd", "amududu1123123");
		responseData.put("destnumbers", tel);
		
		responseData.put("msg", "客户：18665654216"+msg);
		
		String sw = HttpUtil.http(path, responseData);
		System.out.println(sw);
		
	}
  
    public static String http(String url, Map<String, String> params) {  
        URL u = null;  
        HttpURLConnection con = null;  
        // 构建请求参数  
        StringBuffer sb = new StringBuffer();  
        if (params != null) {  
        	Set<Entry<String, String>> set = params.entrySet();
            for (Entry<String, String> e : set) {  
                sb.append(URLEncoder.encode(e.getKey()));  
                sb.append("=");  
                sb.append(URLEncoder.encode(e.getValue())); 
                sb.append("&");  
            }  
            sb.substring(0, sb.length() - 1);  
        }  
        System.out.println("send_url:" + url);  
        System.out.println("send_data:" + sb.toString());  
        // 尝试发送请求  
        try {  
            u = new URL(url);  
            con = (HttpURLConnection) u.openConnection();  
            //// POST 只能为大写，严格限制，post会不识别  
            con.setRequestMethod("POST");  
            con.setDoOutput(true);  
            con.setDoInput(true);  
            con.setUseCaches(false);  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");  
            osw.write(sb.toString());  
            osw.flush();  
            osw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (con != null) {  
                con.disconnect();  
            }  
        }  
  
        // 读取返回内容  
        StringBuffer buffer = new StringBuffer();  
        try {  
            //一定要有返回值，否则无法把请求发送给server端。  
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                buffer.append(temp);  
                buffer.append("\n");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return buffer.toString();  
    }  
  
}  
package com.i5le.redplus.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.i5le.redplus.util.HttpUtil;



@Controller
@RequestMapping(value = "/wechat")
public class WeChatAuth2COntroller  {
        
	//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa125df0217c39917&redirect_uri=http://192.168.169.101:8080/RedPlus/wechat/dowechatbc&response_type=code&scope=snsapi_base&state=1#wechat_redirect
	private String get_access_token_url="https://api.weixin.qq.com/sns/oauth2/access_token?" +
	        "appid=APPID" +
	        "&secret=SECRET&" +
	        "code=CODE&grant_type=authorization_code";
	private String get_userinfo="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	private String tk="K8NYKD36rTT51veqf8wIsYU1n4-A3gF946djXZqiFjkRa8-OybF0Im05icqU8O9F0Qngg8y6haDrO_6K56Z8FPPC2249Sw3bK50nL4OeWxg";
	@RequestMapping(value = "/dowechat")
	public void dowechat(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
	}
	
	@RequestMapping(value = "/dowechatbc")
	public void dowechatbc(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		 // 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
        String code=request.getParameter("code");
        String openid1=request.getParameter("openid");
        
        get_access_token_url=get_access_token_url.replace("APPID", "wxa125df0217c39917");
        get_access_token_url=get_access_token_url.replace("SECRET", "33ff52e40e6894b875923bd2d125cbfb");
        get_access_token_url=get_access_token_url.replace("CODE", code);
        
        String json=HttpUtil.http(get_access_token_url, null);
        //String json=HttpUtil.getUrl(get_access_token_url);
        
        
        JSONObject jsonObject=JSONObject.fromObject(json);
        String access_token=jsonObject.getString("access_token");
        String openid=jsonObject.getString("openid");
        
        get_userinfo=get_userinfo.replace("ACCESS_TOKEN", access_token);
        get_userinfo=get_userinfo.replace("OPENID", openid);
        
        String userInfoJson=HttpUtil.http(get_userinfo,null);
        
        JSONObject userInfoJO=JSONObject.fromObject(userInfoJson);
        
        String user_openid=userInfoJO.getString("openid");
        String user_nickname=userInfoJO.getString("nickname");
        String user_sex=userInfoJO.getString("sex");
        String user_province=userInfoJO.getString("province");
        String user_city=userInfoJO.getString("city");
        String user_country=userInfoJO.getString("country");
        String user_headimgurl=userInfoJO.getString("headimgurl");
        
    //    UserInfo_weixin userInfo=new UserInfo_weixin(user_openid, user_nickname, user_sex, user_province, user_city, user_country, user_headimgurl);
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("");
        out.println("");
        out.println("  ");
        out.println("  ");
        out.print("    This is ");
        out.print(this.getClass());
        out.println(", using the POST method \n");
        out.println("openid:"+user_openid+"\n\n");        
        out.println("nickname:"+user_nickname+"\n\n");        
        out.println("sex:"+user_sex+"\n\n");        
        out.println("province:"+user_province+"\n\n");        
        out.println("city:"+user_city+"\n\n");        
        out.println("country:"+user_country+"\n\n");
        out.println(">");
        out.println("  ");
        out.println("");
        out.flush();
        out.close();
	}
	
}

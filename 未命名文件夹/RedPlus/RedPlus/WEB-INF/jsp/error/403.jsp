<%@ page pageEncoding="UTF-8" %>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="com.i5le.framwork.core.web.MediaTypes"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String accept = request.getHeader("Accept");
	String contentType = MediaTypes.TEXT_HTML_UTF_8;
	String msg = "您没有权限进行此操作或登录超时！";
	if (accept.indexOf("application/json") != -1) {
		response.setHeader("Content-Type", MediaTypes.JSON_UTF_8);
		String json = "{\"success\":false,\"msg\":\""+msg+
			"\",\"hasError\":true,\"error\":\""+msg+"\"}";
		out.print(json);
	} else {
		response.setHeader("Content-Type", MediaTypes.TEXT_HTML_UTF_8);
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>403 - 您没有权限进行此操作</title>
</head>

<body>
	<h2>403 - 您没有权限进行此操作.</h2>
	<p>
		<a href="${ctx}/admin/login"/>返回登录页</a>
	</p>
</body>
</html>
<%
	}
%>







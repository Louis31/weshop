<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.cxstock.biz.power.dto.UserDTO;"%>
<%@include file="/common/import-tags.jsp"%>
<%@include file="/common/import-static.jsp"%>
<%
  UserDTO userInfo=(UserDTO)session.getAttribute("userInfo");
%> 
<html>
  <head>
  	<title></title>
    <script type="text/javascript">
	     window.log_id="<%=userInfo.getUserid()%>";
	     window.log_name="<%=userInfo.getUsername()%>";
	</script>
    <script type="text/javascript" src="${ctx}/static/js/Clock.js"></script>
    <script type="text/javascript" src="${ctx}/static/weshop/main/index.js"></script>
    <script type="text/javascript" src="${ctx}/static/weshop/main/App.js"></script>
  </head>
  <body>
  </body>
</html>
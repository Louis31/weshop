<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
  <% 
String path = request.getContextPath(); 
// 获得本项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
// 将 "项目路径basePath" 放入pageContext中，待以后用EL表达式读出。 
pageContext.setAttribute("basePath",basePath); 
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<html>       
<head>       
     
<title>带有图片预览功能的上传表单webjx.com</title>       
<script>       
function viewmypic(mypic,imgfile) {        
if (imgfile.value){        
mypic.src=imgfile.value;        
mypic.style.display="";        
mypic.border=1;        
}        
}        
</script>       
</head>       
<body>       
<center>       
<form  action="<%=basePath%>userImg/upload"  method="post" enctype="multipart/form-data">       
<input type="submit"  value="提交"/>          
</form>       
    
<br />       



<div style="display:none">       
</div>       
</center>       
</body>       
</html>
</body>
</html>
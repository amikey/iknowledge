<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'filedemo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript">
function doSubmit(){
    documnt.form[0].action="import"
    documnt.form[0].submit;
}
</script>
  </head>
  
  <body>
    
    用户名：${requestScope.usename } <br/>
    文件：${requestScope.file1 }<br/>
    ${requestScope.file2 }<br/>
    <form action="importWords" method="post">
    <input type="button" onclick="doSubmit()" value="导入">
    </form>
    <!-- 把上传的图片显示出来 -->
    <img alt="go" src="upload/<%=(String)request.getAttribute("file1")%> " />
    
    
  </body>
  
</html>

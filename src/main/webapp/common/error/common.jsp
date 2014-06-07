<%@ page language="java" pageEncoding="UTF-8"%><%
String path = request.getContextPath();
String basePath = new StringBuffer(request.getScheme()).append("://").append(request.getServerName()).
append(":").append(request.getServerPort()).append(path).toString();

//fixed for web app deploy to root server context ; at 2011-04-08 by zhadiw
//如果应用部署的站点根目录，则 path 值为空，导致部分URL跳转失效
if(null== path || "".equals(path)){
	path = basePath;
}


request.setAttribute("app.path",path);
request.setAttribute("app.base",basePath);

%><!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>system</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/error/style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.js?v=1.7.2"></script>

<script type="text/javascript">
<!--//-->//><!--
var app = {};
app.path    = '<%=path%>';
app.base    = '<%=basePath%>';
app.success = '1';
app.failed  = '0';

function app_title(title){ document.title= title +  ' - ' +  document.title;  }
function app_alert(msg,callback){
	alert(msg);
}
//--><!
</script>
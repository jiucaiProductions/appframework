<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.exception.ExceptionUtils"%>
<%@ include file="/common/error/common.jsp"%>
<script type="text/javascript">
<!--//-->//><!--

	$(function() {
		app_title('服务异常');
	
	});

//--><!
</script>
</head>
 
<body>
<div class="wrapper">
 	
	<!--主内容区开始-->
	
	<div class="wrap_center">
	
		<div class="error_title">
	    	<h2>HTTP 500 服务异常</h2>
		</div>
	
		<div class="error_detail">

			<%
			String currentTime="";
			String errMsg="";
			String requestUrl="";
			String requestQueryString="";
			try{
			
				Exception ex = (Exception)request.getAttribute("exception"); 
				requestUrl = (String)request.getAttribute("javax.servlet.forward.request_uri");
				requestQueryString = (String)request.getAttribute("javax.servlet.forward.query_string");
				
				
				requestUrl = requestUrl == null ? request.getRequestURI() : requestUrl;
				requestQueryString = requestQueryString == null ? "" : requestQueryString;
				
				String errStackTrace =ExceptionUtils.getFullStackTrace(ex);
			
				errMsg = ExceptionUtils.getRootCauseMessage(ex);
				if(null != errMsg && errMsg.length()>2 ){
					String[] msgs = errMsg.split(":");
					if(null != msgs && msgs.length >1 ){
						errMsg = msgs[1].trim();
					}
				}
				currentTime = new Date().toLocaleString();
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			%>
			请联系站点管理员报告异常，异常信息: <%=errMsg %><br/><br/>
			
			请求时间: <%=currentTime %><br/>
			请求地址: <%=requestUrl %><br/>
			请求参数: <%=requestQueryString %>

		</div>
	
		<div class="error_goback">
			<span class="btn ico goback">
			<a href="${pageContext.request.contextPath}/">返回首页</a>
			</span>
		</div>
		                    
	         
	      
	</div>
	<!--主内容区结束-->

</div>

</body>
</html>



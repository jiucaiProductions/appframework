<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/error/common.jsp"%>
<script type="text/javascript">
<!--//-->//><!--

	$(function() {
		app_title('请求的页面不存在');
	
	});

//--><!
</script>
</head>
 
<body>
<div class="wrapper">
 	
	<!--主内容区开始-->
	
	<div class="wrap_center">
	
		<div class="error_title">
	    	<h2>HTTP 404 请求的页面不存在</h2>
		</div>
	
		<div class="error_detail">
		<%
			String reqUrl = (String) request.getAttribute("javax.servlet.forward.request_uri");
			String reqParam = request.getQueryString();
			if( null== reqUrl){
				reqUrl= request.getRequestURI();
			}
		%>
	
		请检查请求的路径 <%=reqUrl %> 是否正确
		
		<% if( null != reqParam && !"".equals(reqParam) ){ %>
		<br/>
		请求参数: <%=reqParam %>
		
		<% } %>
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
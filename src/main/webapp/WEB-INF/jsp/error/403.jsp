<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/error/common.jsp"%>
<script type="text/javascript">
<!--//-->//><!--

	$(function() {
		app_title('您没有权限访问当前页面');
	
	});

//--><!
</script>
</head>
 
<body>
<div class="wrapper">
 	
	<!--主内容区开始-->
	
	<div class="wrap_center">
	
		<div class="error_title">
	    	<h2>HTTP 403 您没有权限访问当前页面</h2>
		</div>
	
		<div class="error_detail">
			请确认您是否有权限访问 <%=request.getRequestURI() %>
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

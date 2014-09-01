<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="Base.jsp" %>

<%@page import="com.gogo.domain.User"%>
<%@page import="com.gogo.helper.CommonConstant"%>

<% 
	User user = (User)request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	
 %>
<html>
<head>
<script type="text/javascript">	
	$(document).ready(function(){
		$("#backMain").click(function(){
			location.href = '<%=basePath %>user/main'; 
		});
		
		$("#doLogOut").click(function(){
			location.href = '<%=basePath %>login/doLogout'; 
		});
	});
</script>
</head>
<body onload="onLoad()">

<%=user.getAlisName() %>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<button id="backMain">返回主页</button>
&nbsp;
<button id="searchActBtn" name="searchActBtn" >查找用户信息</button>
&nbsp;
<button id="searchUserBtn" name="searchActBtn" >查找附近活动</button>
&nbsp;
<button id="doLogOut" name="doLogOut" >退出登录</button>
<br/><br/>
<hr style="float:left;width:10000px;"/>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="Base.jsp" %>

<%@page import="com.gogo.domain.User"%>
<%@page import="com.gogo.helper.CommonConstant"%>

<% 
	Object user4obj = request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	User user = null;
	if(user4obj != null){
		user = (User)user4obj;
	}
 %>
<html>
<head>
<script type="text/javascript">	
	$(document).ready(function(){
		$("#backMain").click(function(){
			location.href = '<%=basePath %>'; 
		});
		
		$("#backUserMain").click(function(){
			location.href = '<%=basePath %>user/main'; 
		});
		
		$("#doLogOut").click(function(){
			location.href = '<%=basePath %>login/doLogout'; 
		});

		$("#searchActBtn").click(function(){
			location.href = '<%=basePath %>activity/toShowAllPage'; 
		});
		
	});
</script>
</head>
<body>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<button id="backMain">返回主页</button>
&nbsp;
<button id="searchActBtn" >查找附近活动</button>
&nbsp;
<%  
if(user!= null){
%>
<button id="backUserMain"><%=user.getAlisName()%>的主页</button>
&nbsp;
<button id="searchUserBtn" >查找用户信息</button>
&nbsp;
<button id="doLogOut" name="doLogOut" >退出登录</button>
<%
}
%>


<br/><br/>
<hr style="float:left;width:10000px;"/>
</body>
</html>
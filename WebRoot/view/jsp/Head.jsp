<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="Base.jsp" %>

<%@page import="com.gogo.domain.User"%>
<%@page import="com.gogo.helper.CommonConstant"%>

<% 

 %>
<html>
<head>
<script type="text/javascript">	
	$(document).ready(function(){
		$("#backMain").click(function(){
			location.href = '<%=basePath %>'; 
		});
		
		$("#backUserMain").click(function(){
			location.href = '<%=basePath %>user/forwoardMain?access_token=<%=tokenId%>'; 
		});
		
		$("#doLogOut").click(function(){
			location.href = '<%=basePath %>login/doLogout?access_token=<%=tokenId%>'; 
		});

		$("#searchActBtn").click(function(){
			location.href = '<%=basePath %>activity/toShowAllPage?access_token=<%=tokenId%>'; 
		});
		
		$("#searchGroupBtn").click(function(){
			location.href = '<%=basePath %>group/toShowAllPage?access_token=<%=tokenId%>'; 
		});
		
		$("#searchUserBtn").click(function(){
			location.href = '<%=basePath %>friend/toShowAllPage?access_token=<%=tokenId%>'; 
		});
		
		$("#setterBtn").click(function(){
			location.href = '<%=basePath %>user/toShowSetterPage?access_token=<%=tokenId%>'; 
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



<button id="searchUserBtn" >附近的人</button>

<button id="searchGroupBtn" >附近的小组</button>


&nbsp;
<button id="setterBtn" >个人设置</button>
&nbsp;

<button id="doLogOut" name="doLogOut" >退出登录</button>

&nbsp;
<div id="backUserMain" style="float:right;display:inline;"> <img height="44" width="44">主页 </div>


<br/><br/>
<hr style="float:left;width:10000px;"/>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="view/jsp/Head.jsp" %>
<%@ page import="com.gogo.helper.*"%>

<html>
<head>
<title>GOGO</title>

<script type="text/javascript">
	$(document).ready(function() {
		$("#login4jsonBtn").click(function() {
			var f_userName = $("#u_userName").val();
			var f_password = $("#u_password").val();
			var params = {
				name : f_userName,
				password : f_password
			};
			var actionInfo = 'login/doLogin4json';
			var success = function(result) {
				if (result != null) {
					location.href = '<%=basePath%>';
				}
			};
			
			var failed = function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    };

			send4Json(params, actionInfo, success, failed);
		});
	});
</script>
</head>

<body>

	${errorMsg}

	

<%
	if(user == null){
		
	
%>

	 <div>JsonLogin - test
	<form id="login4jsonForm">
		name:<input id="u_userName" type="text" /> <br /> passwod:<input
			id="u_password" type="password" /><br /> <input id="login4jsonBtn"
			value="登录" type="button"><br />
	</form>
	<br />
	</div> 

<div>


</div>
<!-- 
<div>
	<form id="loginForm" action="login/doLogin" method="post">
		用户名：<input id="userName" name='userName' type="text" /> 
		密码：<input id="userPassword" name='userPassword' type="password" />
		<input id="loginBtn" value="登录" type="submit">
	</form>
</div> -->
	<form action="login/doRegister" method="post">
		<input id="registBtn" value="注册" type="submit"><br />
	</form>

<br/><br/>
<hr style="float:left;width:10000px;"/>

<%} %>

</body>
</html>

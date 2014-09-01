<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="view/jsp/Base.jsp"%>
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
				userName : f_userName,
				password : f_password
			};
			var actionInfo = 'login/doLogin4json';
			var success = function(result) {
				if (result == true) {
					location.href = '<%=basePath%>user/main';
				} else {
					alert(result);
				}
			};

			send4Json(params, actionInfo, success, null);
		});
	});
</script>
</head>

<body>

	${errorMsg}

	<div>用户登录</div>
	<form id="loginForm" action="login/doLogin" method="post">
		name:<input id="userName" name='userName' type="text" /> <br />
		passwod:<input id="password" name='password' type="password" /><br />
		<input id="loginBtn" value="登录" type="submit"><br />
	</form>
	<br />



	<div>JsonLogin - test</div>
	<form id="login4jsonForm">
		name:<input id="u_userName" type="text" /> <br /> passwod:<input
			id="u_password" type="password" /><br /> <input id="login4jsonBtn"
			value="登录" type="button"><br />
	</form>
	<br />

	<form action="login/doRegister" method="post">
		<input id="registBtn" value="注册" type="submit"><br />
	</form>

</body>
</html>

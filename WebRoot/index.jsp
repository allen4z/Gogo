<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="view/jsp/Base.jsp"%>
<%@ page import="com.gogo.helper.*"%>

<%
	Object obj = session.getAttribute(CommonConstant.USER_CONTEXT);

%>

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

	



	<!-- <div>JsonLogin - test
	<form id="login4jsonForm">
		name:<input id="u_userName" type="text" /> <br /> passwod:<input
			id="u_password" type="password" /><br /> <input id="login4jsonBtn"
			value="登录" type="button"><br />
	</form>
	<br />
	</div> -->

<div>


</div>

<%
	if(obj == null){
		%>
	<div>
	<form id="loginForm" action="login/doLogin" method="post">
		用户名：<input id="userName" name='userName' type="text" /> 
		密码：<input id="password" name='password' type="password" />
		<input id="loginBtn" value="登录" type="submit">
	</form>
	</div>
	<form action="login/doRegister" method="post">
		<input id="registBtn" value="注册" type="submit"><br />
	</form>
		<%
	}else{
		%>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button id="backMain">返回主页</button>
	&nbsp;
	<button id="searchActBtn" name="searchActBtn" >查找用户信息</button>
	&nbsp;
	<button id="searchUserBtn" name="searchActBtn" >查找附近活动</button>
	&nbsp;
	<button id="doLogOut" name="doLogOut" >退出登录</button>
		<%
	}
%>

<div>
	
	
	
</div>
<br/><br/>
<hr style="float:left;width:10000px;"/>

</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.gogo.helper.*"%>
<%@include file="view/jsp/Base.jsp" %>

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
					location.href = '<%=basePath%>user/forwoardMain?access_token=2c9ba3814a1317db014a132d4cba0000';
				}
			};
			
			var failed = function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    };

		    postWithOutToken(params, actionInfo, success, failed);
		});
	});
</script>
</head>

<body>
	${errorMsg}
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
	<form action="login/doRegister" method="post">
		<input id="registBtn" value="注册" type="submit"><br />
	</form>

<br/><br/>
<hr style="float:left;width:10000px;"/>

<a href="http://127.0.0.1:8080/Gogo/user/forwoardMain?access_token=2c9ba38149e639ce0149e63aa80b0000">AAAA</a>
<a href="http://127.0.0.1:8080/Gogo/user/forwoardMain?access_token=2c9ba3814a1317db014a132d4cba0000">BBBB</a>
<a href="http://127.0.0.1:8080/Gogo/user/forwoardMain?access_token=2c9ba38149e639ce0149e63aa80b0000">CCCC</a>

</body>
</html>

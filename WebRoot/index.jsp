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
					setTokenId(result.id);
					location.href = '<%=basePath%>user/forwoardMain?access_token='+result.id;
				}
			};
			
			var failed = function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    };

		    postWithOutToken(params, actionInfo, success, failed);
		});
		
		/* $("#getDate").click(function() {
			 var success = function(result){
				alert(result.buyNum);
			};
			
			var failed = function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    };
		    
			var url='http://192.168.20.222:8090/wxportal/wx?type=invoicing4year&orgCode=11310001&date=2014-11-27&isSum=true';
			$.ajax({
				dataType:'jsonp',
				jsonp:'jsonpCallback',
				url:url,
				success:success
			}); 
			
			//get4Json(null, url, success, failed);
			
			$.ajax({
				type : "get",
				async:false,
				url : "http://192.168.20.222:8090/wxportal/wx?type=invoicing4year&orgCode=11310001&date=2014-11-27&isSum=true",
				dataType : "jsonp",
				jsonp: "jsonpCallback",//服务端用于接收callback调用的function名的参数
				jsonpCallback:"success_jsonpCallback",	//callback的function名称
				success : function(json){
					alert('参数:'+json.buyNum);
				},
				error:function(){
					alert('fail');
					}
				});
			
		}); */
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

<!-- <input type="button" id="getDate" name="getDate" value="GETDATE"> -->
</body>
</html>

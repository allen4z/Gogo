<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Base.jsp" %>

<html>
  <head>
    <title>GOGO-用户注册</title>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#registerBtn").click(function(){
			var f_userName = $("#u_userName").val();
			var f_password = $("#u_password").val();
			var f_alisName = $("#u_alisName").val();
			var params = {userName:f_userName,userPassword:f_password,alisName:f_alisName};
			var actionInfo = 'user/doRegister';
			var success = function(result) { 
					if(result == true){
						alert('用户注册成功');
	        			location.href = '<%=basePath%>';		        			
					}
		        };
		    var failed = function(XMLHttpRequest,textStatus, errorThrown){
		    	// alert(XMLHttpRequest.status);
                // alert(XMLHttpRequest.readyState);
		         alert(XMLHttpRequest.responseText);
		        };
		        
		    send4Json(params,actionInfo,success,failed);
		});
		
	});
	</script>
  </head>
  
  <body>
  <div>用户注册：</div>
   <form id="registerForm" >
   	name:<input id="u_userName" type="text" /> <br/>
   
   	passwod:<input id="u_password" type="password"/><br/>
   	 
   	alisname：<input id="u_alisName" type="text" /><br/>
   	
   	<input id="registerBtn" value="注册" type="button"><br/>
   </form>
   <br/>
   
  </body>
</html>

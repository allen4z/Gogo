<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>

<html>
<head>
<title>Gogo-Main</title>
<script src="view/js/ajaxfileupload.js"></script>


<script type="text/javascript">

	$(document).ready(function(){
		$("#addGroupBtn").click(function(){
			var actionInfo = 'group/saveGroup?pn=1';	
			var params = {
				name : $("#group_name").val(),
				nickName:$("#group_nickname").val(),
				name : $("#group_content").val()
			};
			
			var success = function(id){
				location.href = '<%=basePath %>user/forwoardMain?access_token=<%=tokenId%>'; 
			};
			
			var failed = function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    };
			
			post4Json(params,actionInfo,success,failed);
		});
		
	});
	
	
</script>
</head>
<body>

<br/>
<br/>
<form>
球队名称：<input id="group_name" name="group_name" type="text"/>	<br/>
简称：<input id="group_nickname" name="group_nickname" type="text"/>	<br/>

简介：<input id="group_content" name="group_content" type="text"/>	<br/>

logo:

<input id="addGroupBtn" name="addGroupBtn"  type="button" value="保存"/>
</form>
</body>
</html>
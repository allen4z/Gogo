<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>

<html>
<head>
<title>Gogo-Main</title>
<script src="view/js/ajaxfileupload.js"></script>


<script type="text/javascript">

	$(document).ready(function(){
		$("#addGroupBtn").click(function(){
			var actionInfo = 'group/saveGroup';	
			var params = {
				name : $("#group_name").val()
			};
			
			var success = function(id){
				location.href = '<%=basePath %>user/main'; 
			};
			
			var failed = function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    };
			
			send4Json(params,actionInfo,success,failed);
		});
		
	});
	
	
</script>
</head>
<body>

<br/>
<br/>
<form>
小组名称：<input id="group_name" name="group_name" type="text"/>	<br/>
<input id="addGroupBtn" name="addGroupBtn"  type="button" value="保存"/>
</form>
</body>
</html>
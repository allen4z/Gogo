<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>

<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#addActBtn").click(function(){
		
			var actionInfo = 'activity/saveAct/<%=user.getUserId()%>';	
			var params = {
				actName : $("#act_name").val(),
				actContent : $("#act_content").val(),
				actStartTime : $("#act_starttime").val(),
				actEndTime : $("#act_endtime").val(),
				actSignTime : $("#act_signtime").val()
			};
			
			var success = function(id){
				//跳转到展示页面
				location.href = "showPage/"+id;
			};
			
			send4Json(params,actionInfo,success,function(){
				alert(error);
			});
		});
	});
</script>
</head>
<body>

<br/>
<br/>
<form>
活动名称：<input id="act_name" name="act_name" type="text"/>	
活动内容：<input id="act_content" name="act_content" type="text"/>
开始时间：<input id="act_starttime" name="act_starttime" type="date"/>
结束时间：<input id="act_endtime" name="act_endtime" type="date"/>
报名时间：<input id="act_signtime" name="act_signtime" type="date"/>

<input id="addActBtn" name="addActBtn"  type="button" value="保存"/>


<input id="registerBtn" value="注册" type="button"><br/>
 	
</form>
</body>
</html>
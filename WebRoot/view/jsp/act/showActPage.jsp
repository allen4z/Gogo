<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>

<% 
	int actId = (Integer)request.getAttribute("actId");
 %>
<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
	function onLoad(){
		$("#actInfo").hide();	
		var action = 'activities/loadAct/<%=actId%>';
		var success = function(act){
			$("#actInfo").show();			
		};
		send4Json(null,action,success,null);
	}
</script>
</head>
<body onload="onLoad()">

<br/>
<br/>
<div id="actInfo">
<form>
活动名称：<input id="act_name" name="act_name" type="text"/>	<br/>
活动内容：<input id="act_content" name="act_content" type="text"/> <br/>
开始时间：<input id="act_starttime" name="act_starttime" type="date"/> <br/>
结束时间：<input id="act_endtime" name="act_endtime" type="date"/> <br/>
报名时间：<input id="act_signtime" name="act_signtime" type="date"/> <br/>
<!-- <input id="addActBtn" name="addActBtn"  type="button" value="保存"/> -->
</form>
</div>
</body>
</html>
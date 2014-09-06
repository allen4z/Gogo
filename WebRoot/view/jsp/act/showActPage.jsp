<%@page import="com.gogo.domain.Role"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>

<% 
	Activity act = (Activity)request.getAttribute("act");
	Role role = (Role)request.getAttribute("role");
 %>
<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
	function onLoad(){
		$("#actInfo").hide();	
		var action = 'activities/loadAct/<%=act.getActId()%>';
		var success = function(act){
			$("#actInfo").show();			
		};
		send4Json(null,action,success,null);
	}
</script>
</head>
<body>

<br/>
<br/>
<div id="actInfo">
<form>
活动名称：<input id="act_name" name="act_name" type="text" value="<%=act.getActName() %>"/>	<br/>
活动内容：<input id="act_content" name="act_content" type="text"  value="<%=act.getActContent() %>"/> <br/>
开始时间：<input id="act_starttime" name="act_starttime" type="date"  value="<%=act.getActStartTime() %>"/> <br/>
结束时间：<input id="act_endtime" name="act_endtime" type="date"  value="<%=act.getActEndTime() %>"/> <br/>
报名时间：<input id="act_signtime" name="act_signtime" type="date"  value="<%=act.getActSignTime() %>"/> <br/>
<%
	if(role != null && role.getRoleCode().equals("01")){
%>
	<input id="addActBtn" name="addActBtn"  type="button" value="删除"/>
	<input id="addActBtn" name="addActBtn"  type="button" value="保存"/>
<%
	}
%>

<!-- <input id="addActBtn" name="addActBtn"  type="button" value="保存"/> -->
</form>
</div>
</body>
</html>
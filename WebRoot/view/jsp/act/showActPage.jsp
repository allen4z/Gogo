<%@page import="com.gogo.domain.Role"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>
<%
	String actId = (String)request.getAttribute("actId");
%>

<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">

function getAct(actId){
	var action = 'activity/loadActByActId/'+actId;
	var success = function(act){
		templatefill(act);
	};
	var failed = function(XMLHttpRequest,textStatus, errorThrown){
         alert(XMLHttpRequest.responseText);
    };
	send4Json(null,action,success,failed);
}

function templatefill(data){
	var html = template('dataTemplate', data);	
	document.getElementById('content').innerHTML = html;
	
}



</script>
</head>

<body onload="getAct('<%=actId%>')">

<div id="content" style="float:lift;"></div>

<script id="dataTemplate" type="text/html">

		<div  id="contentDiv" style="width:800px;height:300px;border:1px solid red;text-align:center;padding-top:20px;">
		<h3>{{actName}}<h3>
		<a>{{actContent}}</a>
		<br/>
		<a href="activity/signUp/{{actId}}">报名</a>
		</div>
		
</script>

</body>
</html>
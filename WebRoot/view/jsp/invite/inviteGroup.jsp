<%@page import="com.gogo.domain.enums.UserAndActState"%>
<%@page import="com.gogo.domain.helper.DomainStateHelper"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>
<%
	String actId = (String)request.getAttribute("actId");

	Integer uarState = (Integer)request.getAttribute("uarState");
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
		<h3>{{name}}<h3>
		<a>{{content}}</a>
		<br/>

		<%
			if(uarState == -1){
				
		%>
		<a href="activity/visitor/{{id}}">加入活动小组</a><br/>
		<a href="activity/join/{{id}}">报名参加活动</a>
		<%
			}else if(uarState != UserAndActState.CANCEL.ordinal() ){
				
				%>
				&nbsp;&nbsp;<a href="activity/cancelJoin/{{id}}">取消报名</a>
				&nbsp;&nbsp;<a href='activity/toShowSpecialActUserPage/<%=DomainStateHelper.USER_AND_ACT_JOIN%>/{{id}}'>查看参加用户</a>
				&nbsp;&nbsp;<a href="activity/toShowSpecialActUserPage/<%=DomainStateHelper.USER_AND_ACT_QUEUE%>/{{id}}">查看排队用户</a>
 				<br/><a href="activity/toShowActAllUserPage/{{id}}">查看活动小组所有用户</a><br/>
				<%
			}else{
				%>
				  <a href="activity/join/{{id}}">报名参加活动</a><br/>
 				<br/><a href="activity/toShowActAllUserPage/{{id}}">查看活动小组所有用户</a><br/>
				<%
			}
		%>
		</div>
		
</script>

</body>
</html>

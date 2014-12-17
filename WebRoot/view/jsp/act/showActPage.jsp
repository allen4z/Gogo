<%@page import="com.gogo.domain.enums.UserAndActState"%>
<%@page import="com.gogo.domain.helper.DomainStateHelper"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>
<%
	String actId = (String)request.getAttribute("actId");

	UserAndActState uarState = (UserAndActState)request.getAttribute("uarState");
	
	boolean isOwnUser = (Boolean)request.getAttribute("isOwnUser");
%>

<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#editAct").click(function(){
		location.href = '<%=basePath %>activity/toEditActPage?actId=<%=actId%>&access_token=<%=tokenId%>'; 
	});
});

function getAct(actId){
	var action = 'activity/loadActByActId/'+actId+'?flag=0';
	var success = function(act){
		templatefill(act);
	};
	var failed = function(XMLHttpRequest,textStatus, errorThrown){
         alert(XMLHttpRequest.responseText);
    };
	get4Json(null,action,success,failed);
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
			if(uarState ==UserAndActState.CANCEL){
				
		%>
				<a href="activity/join/{{id}}?access_token=<%=tokenId%>">报名参加活动</a>
		<%
			}else if(uarState !=UserAndActState.CANCEL ){
				
				%>
				&nbsp;&nbsp;<a href="activity/cancelJoin/{{id}}?access_token=<%=tokenId%>">取消报名</a>
				&nbsp;&nbsp;<a href='activity/toShowSpecialActUserPage?state=<%=UserAndActState.JOIN%>&actId={{id}}&access_token=<%=tokenId%>'>查看参加用户</a>
				&nbsp;&nbsp;<a href="activity/toShowSpecialActUserPage?state=<%=UserAndActState.QUEUE%>&actId={{id}}&access_token=<%=tokenId%>">查看排队用户</a>
 				<br/><a href="activity/toShowActAllUserPage?actId={{id}}&access_token=<%=tokenId%>">查看活动小组所有用户</a><br/>
				<%
			}else{
				%>
				 <a href="activity/join/{{id}}?access_token=<%=tokenId%>">报名参加活动</a>
 				<br/><a href="activity/toShowActAllUserPage?actId={{id}}&access_token=<%=tokenId%>">查看活动小组所有用户</a><br/>
				<%
			}
		%>
		</div>
		
</script>
<%if(isOwnUser){
	%>
	<button id="editAct" name="editAct">修改活动</button>
	<%
}%>


</body>
</html>

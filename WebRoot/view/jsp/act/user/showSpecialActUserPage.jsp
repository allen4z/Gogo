
<%@page import="com.gogo.domain.enums.UserAndActState"%>
<%@page import="com.gogo.domain.helper.RoleHelper"%>
<%@page import="com.gogo.page.Page"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="pagenav" uri="/view/tld/commons.tld"%>
<%@include file="../../Head.jsp" %>
<%

String actId = (String)request.getAttribute("actId");
UserAndActState state = (UserAndActState)request.getAttribute("state");


%>

<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
function getActAllUser(pn,state){
	var action;
	if(state==<%=UserAndActState.JOIN.ordinal()%>){
		action = 'activity/loadJoinUserFromAct/<%=actId%>?pn='+pn;
	}else if(state==<%=UserAndActState.QUEUE.ordinal()%>){
		action = 'activity/loadQueueUserFromAct/<%=actId%>?pn='+pn;
	}
	
	var success = function(page){
		if(!checkResult(page)){
			return ;
		}
		var users = page.items;
		templatefill(users);
	};
	var failed = function(XMLHttpRequest,textStatus, errorThrown){
         alert(XMLHttpRequest.responseText);
    };
	get4Json(null,action,success,failed);
}

function templatefill(users){
	var data = {users:users};
	var html = template('users', data);	
	document.getElementById('content').innerHTML = html;
	
}

</script>
</head>
<body onload="getActAllUser(1,<%=state.ordinal()%>)">
<%
if(state == UserAndActState.JOIN){
	%><h1>参与活动用户</h1><%
}else if(state == UserAndActState.QUEUE){
	%><h1>活动排队用户</h1><%
}
%>

<div id="content" style="float:lift;"></div>

<script id="users" type="text/html">
{{each users as user index}}
		<div  id="contentDiv" style="width:400px;height:300px;border:1px solid red;text-align:center;padding-top:20px;">
		<h3><a href="friend/toShowFriendPage/1/{{user.id}}">{{user.aliasName}}</a><h3>
		<a>{{user.aliasName}}</a>
		<br/>
		<br/>
		<a href="friend/friendRequest/{{user.id}}">添加</a>
		</div>
{{/each}}
</script>

</body>
</html>

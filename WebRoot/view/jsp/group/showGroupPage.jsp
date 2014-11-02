<%@page import="com.gogo.domain.helper.DomainStateHelper"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>
<%
	String groupId = (String)request.getAttribute("groupId");
%>

<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">

function getAct(groupId){
	var action = 'group/loadGroupById/'+groupId;
	var success = function(group){
		templatefill(group);
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

<body onload="getAct('<%=groupId%>')">

<div id="content" style="float:lift;"></div>

<script id="dataTemplate" type="text/html">

		<div  id="contentDiv" style="width:800px;height:300px;border:1px solid red;text-align:center;padding-top:20px;">
		<h3>{{name}}<h3>
		<br/>
		
		{{each joinUser as value i}}
			<li>用户名：{{value.user.aliasName}}</li>
		{{/each}}
		
		</div>
		
</script>

</body>
</html>
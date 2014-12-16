<%@page import="com.gogo.page.Page"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="pagenav" uri="/view/tld/commons.tld"%>
<%@include file="../Head.jsp" %>


<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
function getNearAct(pn){
	var action = 'activity/loadActByPlace';
	
	var params = {
		pn:pn
	};
	
	var success = function(page){
		var acts = page.items;
		templatefill(acts);
	};
	var failed = function(XMLHttpRequest,textStatus, errorThrown){
         alert(XMLHttpRequest.responseText);
    };
    get4Json(params,action,success,failed);
}

function templatefill(acts){
	var data = {acts:acts};
	var html = template('acts', data);	
	document.getElementById('content').innerHTML = html;
	
}

</script>
</head>
<body onload="getNearAct(1)">

<div id="content" style="float:lift;"></div>

<script id="acts" type="text/html">
{{each acts as act index}}
		<div  id="contentDiv" style="width:400px;height:300px;border:1px solid red;text-align:center;padding-top:20px;">
		<h3><a href="activity/toShowActPage?actId={{act.id}}&access_token=<%=tokenId%>">{{act.name}}</a><h3>
		<a>{{act.content}}</a>
		
		<br/>
		<br/>
		<br/>
		<a href="activity/toShowActPage?actId={{act.id}}&access_token=<%=tokenId%>">查看活动详情</a>
		</div>
{{/each}}
</script>


</body>
</html>

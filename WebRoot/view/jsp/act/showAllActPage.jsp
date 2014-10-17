<%@page import="com.gogo.page.Page"%>
<%@page import="com.gogo.domain.Role"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="pagenav" uri="/view/tld/commons.tld"%>
<%@include file="../Head.jsp" %>

<%-- <% 
	Page<Activity> allAct =(Page)request.getAttribute("page");
 %> --%>
<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
function getNearAct(pn){
	var action = 'activity/loadActByPlace?pn='+pn;
	var success = function(page){
		var acts = page.items;
		templatefill(acts);
	};
	var failed = function(XMLHttpRequest,textStatus, errorThrown){
         alert(XMLHttpRequest.responseText);
    };
	send4Json(null,action,success,failed);
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
		<h3><a href="activity/toShowActPage/1/{{act.actId}}">{{act.actName}}</a><h3>
		<a>{{act.actContent}}</a>
		
		<br/>

		{{if act.needActor}}
			该项目需要招募参与者
		{{/if}}
		<br/>
		<br/>
		<a href="activity/toShowActPage/1/{{act.actId}}">加入该活动组织</a>
		</div>
{{/each}}
</script>


</body>
</html>

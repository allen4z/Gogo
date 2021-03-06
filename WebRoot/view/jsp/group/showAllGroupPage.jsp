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
	var action = 'group/loadAllGroup?pn='+pn;
	var success = function(page){
		if(!checkResult(page)){
			return ;
		}
		
		var groups = page.items;
		templatefill(groups);
	};
	var failed = function(XMLHttpRequest,textStatus, errorThrown){
         alert(XMLHttpRequest.responseText);
    };
    get4Json(null,action,success,failed);
}

function templatefill(groups){
	var data = {groups:groups};
	var html = template('groups', data);	
	document.getElementById('content').innerHTML = html;
	
}

</script>
</head>
<body onload="getNearAct(1)">

<div id="content" style="float:lift;"></div>

<script id="groups" type="text/html">
{{each groups as group index}}
		<div  id="contentDiv" style="width:400px;height:300px;border:1px solid red;text-align:center;padding-top:20px;">
		<h3>{{group.name}}<h3>
		<br/>
		<br/>
		<a href="group/applyJoin/{{group.id}}?access_token=<%=tokenId%>">加入小组</a>
		<a href="group/toShowGroupPage?groupId={{group.id}}&access_token=<%=tokenId%>">查看小组人员</a>
		<a href="matches/toAddInviteMatches/{{group.id}}?access_token=<%=tokenId%>">约</a>
		</div>
{{/each}}
</script>


</body>
</html>

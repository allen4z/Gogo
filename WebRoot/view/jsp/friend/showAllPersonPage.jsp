<%@page import="com.gogo.page.Page"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="pagenav" uri="/view/tld/commons.tld"%>
<%@include file="../Head.jsp" %>

<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
function getNearFriend(pn){
	var action = 'friend/loadFriendByPlace?pn='+pn;
	var success = function(page){
		var users = page.items;
		templatefill(users);
	};
	var failed = function(XMLHttpRequest,textStatus, errorThrown){
         alert(XMLHttpRequest.responseText);
    };
	send4Json(null,action,success,failed);
}

function templatefill(users){
	var data = {users:users};
	var html = template('users', data);	
	document.getElementById('content').innerHTML = html;
	
}

</script>
</head>
<body onload="getNearFriend(1)">

<div id="content" style="float:lift;"></div>

<script id="users" type="text/html">
{{each users as user index}}
		<div  id="contentDiv" style="width:400px;height:300px;border:1px solid red;text-align:center;padding-top:20px;">
		<h3><a href="friend/toShowFriendPage/1/{{user.id}}">{{user.aliasName}}</a><h3>
		<a>{{user.aliasName}}</a>
		<br/>
		<br/>
		<a href="friend/friendRequest/{{user.id}}?access_token=2c9ba38149e639ce0149e63aa80b0000">添加</a>
		</div>
{{/each}}
</script>

</body>
</html>

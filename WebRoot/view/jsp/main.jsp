<%@page import="com.gogo.page.Page"%>
<%@page import="com.gogo.ctrl.model.UserMainModel"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="Head.jsp" %>
<%@page import="com.gogo.domain.Activity"%>
<%@ taglib prefix="pagenav" uri="/view/tld/commons.tld"%>

<%
	Page<Activity> ownAct =(Page)request.getAttribute("page");
%>
<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
	
	$(document).ready(function(){
		$("#searchUserBtn").click(function(){
			send4Json(null,'user/load/1',function(user){
				alert(user.userName);
			},null);
		});
	});
</script>
</head>
<body>
<br/>
<br/>
<form action="activity/addPage" method="post">
<input id="addActBtn" name="addActBtn"  type="submit" value="新增活動"></input>
</form>

<br/>

<div>
	<form id='addActForm'>
	
	</form>
</div>

<div id="ownActTable">
<table border="1">
	<tr >
		<td colspan="2" align="center">拥有活动</td>
	</tr>
	<tr>
		<td>活动名称</td>
		<td>活动内容</td>
	</tr>
	
	<%
			List<Activity> list = ownAct.getItems();
			for(Activity act : list){
		%>
		<tr>
			<td><a href="activity/showPage/<%=act.getActId()%>"><%=act.getActName() %></a></td>
			<td><%=act.getActContent() %></td>
		</tr>
		<%
	}
	
	%>
	
</table>
</div><%-- 
<pagenav:page url="user/main"></pagenav:page> --%>
<pagenav:pageV2 url="user/main"></pagenav:pageV2>
<div id="joinActTable">
<!-- <table>
	<tr>
		<td colspan="2" align="center">参加活动</td>
	</tr>
	<tr>
		<td>活动名称</td>
		<td>活动内容</td>
	</tr>
</table>-->
</div> 
</body>
</html>
<%@page import="com.gogo.ctrl.model.UserMainModel"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="Head.jsp" %>
<%@page import="com.gogo.domain.Activity"%>
<%
	UserMainModel umm =(UserMainModel)request.getAttribute("userMainModel");
%>
<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#searchActBtn").click(function(){
			location.href = "view/jsp/main.jsp";
		});
	});
	
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
			List<Activity> list = umm.getOwnActivity();
			for(Activity act : list){
		%>
		<tr>
			<td><%=act.getActName() %></td>
			<td><%=act.getActContent() %></td>
		</tr>
		<%
	}
	
	%>
	
</table>
</div>
<div id="joinActTable">
<table>
	<tr>
		<td colspan="2" align="center">参加活动</td>
	</tr>
	<tr>
		<td>活动名称</td>
		<td>活动内容</td>
	</tr>
</table>
</div>
</body>
</html>
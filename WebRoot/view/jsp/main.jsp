<%@page import="com.gogo.page.Page"%>
<%@page import="com.gogo.ctrl.model.UserMainModel"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="Head.jsp" %>
<%@page import="com.gogo.domain.Activity"%>


<%
	Page<Activity> ownAct =(Page)request.getAttribute("page");

	Page<Activity> joinAct =(Page)request.getAttribute("joinpage");
	
	List<String> payInfo = (List)request.getAttribute("payinfo");
	
	List<User> friends = (List)request.getAttribute("friends");
	
	
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
<form action="activity/toAddActPage" method="post">
<input id="addActBtn" name="addActBtn"  type="submit" value="新增活動"></input>
</form>

<br/>
<button id="searchUserBtn" >查找用户信息</button>
&nbsp;
<div>
	<form id='addActForm'>
	
	</form>
</div>

<div>
<table border="1">
<tr >
		<td colspan="2" align="center">好友列表</td>
	</tr>
<%
	if(friends!= null && friends.size()>0){
		for(User friend : friends){
			%><tr><td><%=friend.getAlisName()
			
			%></td><%
			
			%><td><a href="#">发送消息</a></td></tr><%
		}
	}
	%>
</table>

<table border="1">
	<tr >
		<td colspan="2" align="center">待支付列表</td>
	</tr>
	<%
	if(payInfo!= null && payInfo.size()>0){
		for(String msg : payInfo){
			%><tr><td><%
			out.println(msg);
			%></td><%
			
			%><td><a href="#">支付</a></td></tr><%
		}
	}
	%>
	
	
</table>
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
			<td><a href="activity/toShowActPage/0/<%=act.getActId()%>"><%=act.getActName() %></a></td>
			<td><%=act.getActContent() %></td>
		</tr>
		<%
	}
	
	%>
	
</table>
<pagenav:pageV2 url="user/main"/>


<table border="1">
	<tr >
		<td colspan="2" align="center">加入活动</td>
	</tr>
	<tr>
		<td>活动名称</td>
		<td>活动内容</td>
	</tr>
	
	<%
			List<Activity> jlist = joinAct.getItems();
			for(Activity act : jlist){
		%>
		<tr>
			<td><a href="activity/toShowActPage/2/<%=act.getActId()%>"><%=act.getActName() %></a></td>
			<td><%=act.getActContent() %></td>
		</tr>
		<%
	}
	
	%>
	
</table>
<pagenav:pageV2 url="user/main"/>

</div>

</body>
</html>
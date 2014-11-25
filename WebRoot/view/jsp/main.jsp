<%@page import="com.gogo.domain.GroupApplyInfo"%>
<%@page import="com.gogo.domain.Group"%>
<%@page import="com.gogo.page.Page"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="Head.jsp" %>
<%@page import="com.gogo.domain.Activity"%>


<%
	Page<Activity> ownAct =(Page)request.getAttribute("page");

	Page<Activity> joinAct =(Page)request.getAttribute("joinpage");
	
	List<String> payInfo = (List)request.getAttribute("payinfo");
	
	List<User> friends = (List)request.getAttribute("friends");
	
	List<User> requestFriend = (List)request.getAttribute("requestFriend");
	
	Page<Group> myGroups = (Page)request.getAttribute("myGroup");
	
	List<GroupApplyInfo> groupApplys = (List)request.getAttribute("groupApplys");
	
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

<form action="group/toAddGroupPage" method="post">
<input id="addGroupBtn" name="addGroupBtn"  type="submit" value="新增小组"></input>
</form>

<br/>
&nbsp;
<div>
	<form id='addActForm'>
	
	</form>
</div>

<div>

<table border="1">
<tr >
		<td colspan="2" align="center">好友请求列表</td>
	</tr>
<%
	if(requestFriend!= null && requestFriend.size()>0){
		for(User rfInfo : requestFriend){
			%><tr><td><%=rfInfo.getAliasName()
			
			%></td><%
			
			%><td><a href="friend/agreeApply/<%=rfInfo.getId() %>">同意请求</a></td></tr><%
		}
	}
	%>
</table>


<table border="1">
<tr >
		<td colspan="2" align="center">请求加入小组信息</td>
	</tr>
<%
	if(groupApplys!= null && groupApplys.size()>0){
		for(GroupApplyInfo gai : groupApplys){
			%><tr>
			<td>
			用户：<%=gai.getUser().getAliasName()%>请求加入小组：<%=gai.getGroup().getName() %>
			</td><%
			%><td><a href="group/passApply/<%=gai.getId() %>">同意请求</a></td></tr><%
		}
	}
	%>
</table>

<table border="1">
<tr >
		<td colspan="4" align="center">好友列表</td>
	</tr>
<%
	if(friends!= null && friends.size()>0){
		for(User friend : friends){
			%><tr><td><%=friend.getAliasName()
			
			%></td><%
			
			%>
			<td><a href="#">发送消息</a></td>
			<td>邀请加入活动小组<br/>
				<%
			
					List<Group> glist = myGroups.getItems();
					for(Group group : glist){
					%>
						<a href="group/inviteJoinGroup/<%=friend.getId()%>/<%=group.getId()%>"><%=group.getName() %></a><br/>		
						
					<%
					}
				%>
				
			</td>
			<td>邀请加入活动<br/>
				<%
				
				List<Activity> list = ownAct.getItems();
				for(Activity act : list){
				%>
					<a href="activity/inviteJoinAct/<%=friend.getId()%>/<%=act.getId()%>"><%=act.getName() %></a><br/>
				<%
				}
				
				List<Activity> jlist = joinAct.getItems();
				for(Activity act : jlist){
				%>
					<a href="activity/inviteJoinAct/<%=friend.getId()%>/<%=act.getId()%>"><%=act.getName() %></a><br/>
				<%
				}
				%>
			</td>
			</tr>
			<%
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
			<td><a href="activity/toShowActPage/<%=act.getId()%>"><%=act.getName() %></a></td>
			<td><%=act.getContent() %></td>
		</tr>
		<%
	}
	
	%>
	
</table>
<pagenav:pageV2 url="user/main"/>


<table border="1">
	<tr >
		<td colspan="2" align="center">正在参加或排队的活动</td>
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
			<td><a href="activity/toShowActPage/<%=act.getId()%>"><%=act.getName() %></a></td>
			<td><%=act.getContent() %></td>
		</tr>
		<%
	}
	
	%>
	
</table>


<table border="1">
	<tr >
		<td align="center">我的小组</td>
	</tr>
	<tr>
		<td>小组名称</td>
	</tr>
	
	<%
			List<Group> glist = myGroups.getItems();
			for(Group group : glist){
		%>
		<tr>
			<td><a href="group/toShowGroupPage/<%=group.getId()%>"><%=group.getName() %></a></td>
			
		</tr>
		<%
	}
	
	%>
	
</table>
<pagenav:pageV2 url="user/main"/>

</div>

</body>
</html>
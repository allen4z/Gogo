<%@page import="com.gogo.domain.Group"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>
<%
	Group otherGroup = (Group)request.getAttribute("otherGroup");
%>

<html>
<head>
<title>Gogo-Main</title>
<script src="view/js/ajaxfileupload.js"></script>


<script type="text/javascript">

	$(document).ready(function(){
		$("#addMatchList").click(function(){
			var actionInfo = 'matches/saveMatches?flag=0';	
			
			var params = {
				matchDate : $("#match_date").val(),
				otherGroup:{id:$("#match_otherGroup").val()},
				matchPlace:{
					longitude:$("#match_jd").val(),
					latitude:$("#match_wd").val()
					}
			};
			
			var success = function(result){
				if(!checkResult(result)){
					return ;
				}
				location.href = '<%=basePath %>user/forwoardMain?access_token=<%=tokenId%>'; 
			};
			
			var failed = function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    };
			
			post4Json(params,actionInfo,success,failed);
		});
		
	});
	
	
</script>
</head>
<body>

<br/>
<br/>
<form>

约战【<%=otherGroup.getName() %>】<br>

<input type="hidden" id="match_otherGroup" name="match_otherGroup" value="<%=otherGroup.getId()%>"/>

时间：<input id="match_date" name="match_date" type="text"/>	<br/>
地点：
<br>
经度：<input id="match_jd" name="match_jd" type="text"/>	<br/>
纬度：<input id="match_wd" name="match_wd" type="text"/>  <br>	

<input id="addMatchList" name="addMatchList"  type="button" value="约"/>
</form>
</body>
</html>
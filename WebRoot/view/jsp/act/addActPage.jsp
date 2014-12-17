<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>

<%
	boolean isAdd = false;
	String state = (String)request.getAttribute("state");
	Activity act = null;
	if(state.equals("edit")){
		isAdd = false;
		act = (Activity)request.getAttribute("act");
	}else if(state.equals("add")){
		isAdd = true;
	}
%>
<html>
<head>
<title>Gogo-Main</title>
<script src="view/js/ajaxfileupload.js"></script>


<script type="text/javascript">

	function ajaxFileUpload(){
		$.ajaxFileUpload({
			url:'activity/upload',
			secureuri:false,
			fileElementId:'file',
			dataType: 'json',
			beforeSend:function()
			{
				alert(2);
				$("#loading").show();
			},
			complete:function()
			{
				$("#loading").hide();
			},				
			success: function (data, status)
			{
				if(typeof(data.error) != 'undefined')
				{
					if(data.error != '')
					{
						alert(data.error);
					}else
					{
						alert(data.msg);
					}
				}
			},
			error: function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    }
		})
		return false;
	}

	$(document).ready(function(){
		
		var success = function(id){
			location.href = '<%=basePath %>user/forwoardMain?access_token=<%=tokenId%>'; 
		};
		
		var failed = function(XMLHttpRequest,textStatus, errorThrown){
	         alert(XMLHttpRequest.responseText);
	    };
	    
	    function getParams(){
	    	var params = {
	    			id:$("#actId").val(),
	    			name : $("#act_name").val(),
	    			startTime : $("#act_starttime").val(),
	    			open:$("#act_isOpen").attr("checked"),
	    			joinNeedPay:$("#act_joinNeedPay").val(),
	    			minJoin : $("#act_minJoin").val(),
	    			maxJoin : $("#act_maxJoin").val()
	    		};
	    	return params;
	    }
			
		
		$("#addActBtn").click(function(){
			var actionInfo = 'activity/saveAct?flag=0';	
			var params = getParams();
			post4Json(params,actionInfo,success,failed);
		});

		$("#editActBtn").click(function(){
			var actionInfo = 'activity/updateAct?flag=0';	
			var params = getParams();
			post4Json(params,actionInfo,success,failed);
		});
	});
	
	
</script>
</head>
<body>

<br/>
<br/>
<form>
<h1>
<%
	if(isAdd){
		%>新增活动<%
	}else{
		%>修改活动<%
	}
%>
</h1>
<input type="hidden" id="token" name="token" value="${token} "/>
<%if(!isAdd){
	%><input type="hidden" id="actId" name="actid" value="<%=act.getId()%>"/><%
} %>

活动名称：<input id="act_name" name="act_name" type="text" <%if(!isAdd){%>value="<%=act.getName()%>"<%} %>/>	<br/>
所在地：<br/>
是否对外开放：<input id="act_isOpen" name="act_isOpen" type="checkbox" <%if(!isAdd){if(act.isOpen()){%>checked="checked"<%}}else{%>checked="checked"<%} %>/><br/>
最少参加人数：<input id="act_minJoin" name="act_minJoin" type="text" <%if(!isAdd){%>value="<%=act.getMinJoin()%>"<%} %>/>&nbsp;
最多参加人数：<input id="act_maxJoin" name="act_maxJoin" type="text"<%if(!isAdd){%>value="<%=act.getMaxJoin()%>"<%} %>/>	<br/>
每个参与者需要缴费：<input id="act_joinNeedPay" name="act_joinNeedPay" type="text" <%if(!isAdd){%>value="<%=act.getJoinNeedPay()%>"<%} %>/><br/>
开始时间：<input id="act_starttime" name="act_starttime" type="datetime"  <%if(!isAdd){%>value="<%=act.getStartTime()%>"<%} %>/><br/>

<img id="loading" src="view/images/loading.gif" style="display:none;">
<%if(isAdd){
	%><input id="addActBtn" name="addActBtn"  type="button" value="保存"/><%
}else{
	%><input id="editActBtn" name="editActBtn"  type="button" value="修改"/><%
}%>


 	
</form>

活动图片：
<img id="loading" src="/images/loading.gif" style="display:none;">

<form name="form" action="upload/uploadimage" method="POST" enctype="multipart/form-data">
<input id="file" type="file" size="45" name="file" class="input">
<input id="type" type="hidden" size="45" name="type" value="0">
<input type="submit" value="上传"/>
<!-- <button class="button" id="buttonUpload" onclick="ajaxFileUpload();">Upload</button> -->
</form>
</body>
</html>
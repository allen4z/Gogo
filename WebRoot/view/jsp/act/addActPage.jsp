<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>

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
		    	// alert(XMLHttpRequest.status);
                // alert(XMLHttpRequest.readyState);
		         alert(XMLHttpRequest.responseText);
		    }
		})
		return false;
	}

	$(document).ready(function(){
		$("#addActBtn").click(function(){
		
			var actionInfo = 'activity/saveAct';	
			var params = {
				actName : $("#act_name").val(),
				actContent : $("#act_content").val(),
				actStartTime : $("#act_starttime").val(),
				actEndTime : $("#act_endtime").val(),
				actSignTime : $("#act_signtime").val()
			};
			
			var success = function(id){
				//alert(id);
				//跳转到展示页面
				//location.href = "showPage/"+id;
				location.href = '<%=basePath %>user/main'; 
			};
			
			var failed = function(XMLHttpRequest,textStatus, errorThrown){
		         alert(XMLHttpRequest.responseText);
		    };
			
			send4Json(params,actionInfo,success,failed);
		});
		
	});
	
	
</script>
</head>
<body>

<br/>
<br/>
<form>
活动名称：<input id="act_name" name="act_name" type="text"/>	
活动内容：<input id="act_content" name="act_content" type="text"/>
开始时间：<input id="act_starttime" name="act_starttime" type="date"/>
结束时间：<input id="act_endtime" name="act_endtime" type="date"/>
报名时间：<input id="act_signtime" name="act_signtime" type="date"/>

<img id="loading" src="view/images/loading.gif" style="display:none;">
<input id="addActBtn" name="addActBtn"  type="button" value="保存"/>
 	
</form>

上传图片：
<img id="loading" src="/images/loading.gif" style="display:none;">

<form name="form" action="activity/upload" method="POST" enctype="multipart/form-data">
<input id="file" type="file" size="45" name="file" class="input">
<input type="submit" value="上传"/>
<!-- <button class="button" id="buttonUpload" onclick="ajaxFileUpload();">Upload</button> -->
</form>
</body>
</html>
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
				actSignTime : $("#act_signtime").val(),
				needInvest:$("#act_needInvest").attr("checked"),
				needUndertake:$("#act_needUndertake").attr("checked"),
				needActor:$("#act_needActor").attr("checked"),
				needOpen:$("#act_isOpen").attr("checked")
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
活动名称：<input id="act_name" name="act_name" type="text"/>	<br/>
活动内容：<textarea id="act_content" name ="act_content" rows="10" cols="50"></textarea> <br/>
是否需要投资：<input id="act_needInvest" name="act_needInvest" type="checkbox"/><br/>
是否需要承办：<input id="act_needUndertake" name="act_needUndertake" type="checkbox"/><br/>
是否应聘参与人：<input id="act_needActor" name="act_needActor" type="checkbox"/><br/>
是否对外开放：<input id="act_isOpen" name="act_isOpen" type="checkbox"/><br/>
<!-- 开始时间：<input id="act_starttime" name="act_starttime" type="date"/><br/>
结束时间：<input id="act_endtime" name="act_endtime" type="date"/><br/>
报名时间：<input id="act_signtime" name="act_signtime" type="date"/><br/> -->

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
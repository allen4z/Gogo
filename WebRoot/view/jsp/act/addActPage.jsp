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
			//alert($("#act_isLoop").attr("checked"));
			var actionInfo = 'activity/saveAct';	
			var params = {
				name : $("#act_name").val(),
				content : $("#act_content").val(),
				startTime : $("#act_starttime").val(),
				endTime : $("#act_endtime").val(),
				signTime : $("#act_signtime").val(),
				open:$("#act_isOpen").attr("checked"),
				amount:$("#act_needAmount").val(),
				split:$("act_needSplit").attr("checked"),	
				joinNeedPay:$("#act_joinNeedPay").val(),
				minJoin : $("#act_minJoin").val(),
				maxJoin : $("#act_maxJoin").val(),
				loop:$("#act_isLoop").attr("checked")
				
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
所在地：<br/>

是否对外开放：<input id="act_isOpen" name="act_isOpen" type="checkbox"/><br/>

最少参加人数：<input id="act_minJoin" name="act_minJoin" type="text"/>&nbsp;
最多参加人数：<input id="act_maxJoin" name="act_maxJoin" type="text"/>	<br/>

活动所需总费用：<input id="act_needAmount" name="act_needAmount" type="text"/>	<br/>
费用是否平摊：<input id="act_needSplit" name="act_needSplit" type="checkbox"/>	<br/>
每个参与者需要缴费：<input id="act_joinNeedPay" name="act_joinNeedPay" type="text"/>	-- 平摊费用此项隐藏，报名后自动计算<br/>

是否为循环任务：<input id="act_isLoop" name="act_isLoop" type="checkbox"/><br/>
开始时间：<input id="act_starttime" name="act_starttime" type="date"/><br/>
结束时间：<input id="act_endtime" name="act_endtime" type="date"/><br/>
报名时间：<input id="act_signtime" name="act_signtime" type="date"/><br/>

<img id="loading" src="view/images/loading.gif" style="display:none;">
<input id="addActBtn" name="addActBtn"  type="button" value="保存"/>
 	
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
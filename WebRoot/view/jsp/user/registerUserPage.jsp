<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Base.jsp" %>

<%
	String userhead = (String)request.getAttribute("userhead");
	String imgPath= request.getContextPath()+userhead;
%>
<html>
  <head>
    <title>GOGO-用户注册</title>
    
	<script src="view/js/ajaxfileupload.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function(){

		$("#registerBtn").click(function(){
			var f_userName = $("#u_userName").val();
			var f_password = $("#u_password").val();
			var f_aliasName = $("#u_aliasName").val();
			var f_phone = $("#u_phone").val();
			var f_email = $("#u_email").val();
			var f_userhead = $("#u_userhead")[0].src;
			
			var params = {name:f_userName,password:f_password,aliasName:f_aliasName,phoneNum:f_phone,email:f_email,imageUrl:f_userhead};
			var actionInfo = 'user/doRegister';
			var success = function(result) { 
				if(!checkResult(result)){
					return ;
				}
					if(result == true){
						alert('用户注册成功');
	        			location.href = '<%=basePath%>';		        			
					}
		        };
		    var failed = function(XMLHttpRequest,textStatus, errorThrown){
		    	// alert(XMLHttpRequest.status);
                // alert(XMLHttpRequest.readyState);
		         alert(XMLHttpRequest.responseText);
		        };
		        
		    post4Json(params,actionInfo,success,failed);
		});
	});
	
	
	function ajaxFileUpload(){
		    //开始上传文件时显示一个图片,文件上传完成将图片隐藏
		    //$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});
		    //执行上传文件操作的函数
		
			var upload_url = getBasePathInfo()+'/upload/uploadUserHead'
			var params= {type:"0"}
		    $.ajaxFileUpload({
		        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
		        //url:'${pageContext.request.contextPath}/test/fileUpload?uname=玄玉',
				url:upload_url,
				//data:params,
		        secureuri:false,                       //是否启用安全提交,默认为false
		        fileElementId:'myBlogImage',           //文件选择框的id属性
		        dataType:'text',                       //服务器返回的格式,可以是json或xml等
		        success:function(data, status){        //服务器响应成功时的处理函数
		           if(data!='faild'){
		                $("img[id='u_userhead']").attr("src", data);
		                $('#result').html("图片上传成功<br/>");
		            }else{
		                $('#result').html('图片上传失败，请重试！！');
		            }
		        },
		        error:function(data, status, e){ //服务器响应失败时的处理函数
		            $('#result').html('图片上传失败，请重试！！');
		        }
		    });
		}
	
	</script>
  </head>
  
  <body>
  <div>
  <h1>用户注册</h1>  
 <div><img id="u_userhead" alt="用户头像" src="<%=userhead%>"></div> 
 <div>
 <div id="result"></div>
 <input type="file" id="myBlogImage" name="userHeadFile"/>
<input type="button" value="更换头像" onclick="ajaxFileUpload()"/>
</div>

   <form id="registerForm" >
   	name:<input id="u_userName" type="text" /> <br/>
   
   	passwod:<input id="u_password" type="password"/><br/>
   	 
   	aliasname：<input id="u_aliasName" type="text" /><br/>
   	
   	phone：<input id="u_phone" type="text" /><br/>
   	
   	email：<input id="u_email" type="text" /><br/>	
   		
   	
   	<input id="registerBtn" value="注册" type="button"><br/>
   </form>
   <br/>
   </div>
  </body>
</html>

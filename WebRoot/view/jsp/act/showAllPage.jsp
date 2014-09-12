<%@page import="com.gogo.page.Page"%>
<%@page import="com.gogo.domain.Role"%>
<%@page import="com.gogo.domain.Activity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../Head.jsp" %>

<%-- <% 
	Page<Activity> allAct =(Page)request.getAttribute("page");
 %> --%>
<html>
<head>
<title>Gogo-Main</title>
<script type="text/javascript">
function onLoad(){
	$("#actInfo").hide();	
	var action = 'activity/loadActByPlace';
	var success = function(page){
		var acts = page.items;
		createTable(acts) ;
	};
	
	var faild = function(error){
		alert('error '+error);
	}
	send4Json(null,action,success,faild);
}

function createTable(acts) {
	if(acts.length>0){
		var t = document.createElement('table');
	    var b = document.createElement('tbody');
	    
	    var r = document.createElement('tr');
	    for (var j = 0; j < 2; j++) {	
			 var c = document.createElement('td');
			 var m ;
			 switch(j){
			 case 0:
				 m = document.createTextNode('活动名称');
				 break;
			 case 1:
				 m = document.createTextNode('活动内容');
				 break;
			 }
		    
		     c.appendChild(m);
		     r.appendChild(c);
		}
	    b.appendChild(r);
	    
	    
		for(var i=0;i<acts.length;i++){
			
			 var r = document.createElement('tr');
			 for (var j = 0; j < 2; j++) {	
					 var c = document.createElement('td');
					 var m ;
					 var url;
					 switch(j){
					 case 0:
						 m.url = "activity/showPage/"+acts[i].actId;
						 m = document.createTextNode("<a href='activity/showPage/"+acts[i].actId+"'>"+acts[i].actName+"</a>");
						 break;
					 case 1:
						 m = document.createTextNode(acts[i].actContent);
						 break;
					 }
					 
				     c.appendChild(m);
				     r.appendChild(c);
				
			 }
			 b.appendChild(r);
		}
		t.appendChild(b);
		document.getElementById('allAct').appendChild(t);
		t.setAttribute('border', '1');
		
	   
	}
}
</script>
</head>
<body onload="onLoad()">

<div id="allAct"></div>

</body>
</html>

<%@ taglib prefix="pagenav" uri="/view/tld/commons.tld"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String tokenId = (String)request.getAttribute("tokenId");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script src="view/js/json2.js"></script>
	<script src="view/js/jquery.js"></script>
	<script src="view/js/template.js"></script>
	<script src="view/js/go_ajax.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			setTokenId('<%=tokenId%>');
		});
	</script>
  </head>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
1
<form:form commandName="user">
	<form:errors path="*" cssStyle="color:red"></form:errors><br/>
</form:form>

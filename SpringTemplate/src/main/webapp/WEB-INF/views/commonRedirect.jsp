<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
  
<c:choose>
	<c:when test="${requestUrl != null and requestUrl != ''}">
		<%
			response.sendRedirect(""+request.getAttribute("requestUrl"));
		%>
	</c:when>
	<c:otherwise>
		<%
			response.sendRedirect("/login.do");	
		
		%>
	</c:otherwise>
</c:choose>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <c:choose>
<c:when test="${exception.errorMsg != null}">
	<div id="emsg" data-noty-options='{"text":"${exception.errorMsg}","layout":"${exception.layoutType}","type":"${exception.errorClass}"}'>
		<script type="text/javascript">showMessage('${exception.errorMsg}');</script>
	</div>	
</c:when>
</c:choose> --%>

<c:choose>
<c:when test="${exception.errorMsg != null}">
	<div class="alert ${exception.errorClass}">${exception.errorMsg}</div>
</c:when>
</c:choose>
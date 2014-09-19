<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
 	<jsp:include page="COMMONINCLUDE/header.jsp"></jsp:include>
  </head>
  <body id="login">
   <!-- loading start -->
	<div id="ajax_loader" style="text-align: center;">
			<img style="vertical-align: middle;" alt="loading..." src="images/loading2.gif" height="35"
				/> &nbsp; Processing...Please wait...
	</div>
	<!-- loading end --> 
    <div class="container">
    <form:form action="login.do" commandName="loginVO" method="post" cssClass="form-signin" >
    	<div><a class="brand" href="javascript:void(0);"><img src="images/boesc_logo.png" /></a></div>	
        <h4 class="form-signin-heading"><spring:message code="label.signIn"/></h4>
        <c:if test="${ successMsg != null}"><div class="alert alert-error">${successMsg}</div></c:if>
        
        <c:if test="${responseMessage !=null}">
        		<div class="alert ${responseMessageClass}">${responseMessage}</div>
        </c:if>
        
        <c:set var="domainNameErrors"><form:errors cssClass="error" path="*"/></c:set>
	    <c:if test="${not empty domainNameErrors}">
				<div class="alert alert-error">${domainNameErrors} </div>
	    </c:if>
       	<form:input  path="userName" placeholder="User Name" cssClass="input-block-level"/>
       	<form:password path="password" placeholder="Password"  cssClass="input-block-level"/>
        <button class="btn btn-primary" type="submit"><spring:message code="label.loginBtn"/></button>
        <label>
       		 <br/>
         	<a  href="javascript:void(0);" onclick="displayIframe();" data-toggle="modal"><spring:message code="label.forgotPassword"/></a>
        </label>
        <label>
       		 <spring:message code="label.signUpMessage"/>&nbsp;<a href="register.do" class="btn btn-success"><spring:message code="label.createAccountBtn"/></a> 
        </label>
      </form:form>
		<jsp:include page="COMMONINCLUDE/footer.jsp"></jsp:include>
    </div> <!-- /container -->
    
    <div id="dialog" title="Forgot Password?">
	    <div id="ajax_loader" style="text-align: center;">
				<img style="vertical-align: middle;" alt="loading..." src="images/loading2.gif" height="35"
					/> &nbsp; Processing...Please wait...
		</div>
	</div>
	<script>
	$(function() {
		$( "#dialog").dialog({
			autoOpen: false,
			resizable: false,
			width: 600,
		});

	});
	</script>
    <script type="text/javascript" src="developerjs/application.js"></script>
    <script type="text/javascript" src="developerjs/request.js"></script> 
  </body>
</html>

    
    

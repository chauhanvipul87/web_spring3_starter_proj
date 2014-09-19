<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="COMMONINCLUDE/header.jsp"></jsp:include>
</head>
  <body>
  	<jsp:include page="COMMONINCLUDE/topmenu.jsp"></jsp:include>
         <div class="container-fluid">
            <div class="row-fluid">
            	<%-- <jsp:include page="COMMONINCLUDE/sidebar.jsp"></jsp:include> --%>
                <!--/span-->
                <div class="span12" id="content">
                    <div class="row-fluid">
                        <div class="span12">
                            <!-- block -->
                            <div class="block">
                                <div class="navbar navbar-inner block-header">
                                    <div class="muted pull-left">Dashboard</div>
                                </div>
                                <div class="block-content collapse in">
                                    <div class="span12">
                                       <h4>Welcome to IANA's Bad Order Equipment Status Service</h4>
                                       <div id="resDiv"></div>
                                    </div>
                                </div>
                            </div>
                            <!-- /block -->
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="COMMONINCLUDE/footer.jsp"></jsp:include>
           	<script type="text/javascript" src="developerjs/application.js"></script>
            <script type="text/javascript" src="developerjs/request.js"></script>
           
			<c:choose>
				<c:when test="${sessionScope.session_userTypeId == 1}">
					 <script type="text/javascript">
						$( document ).ready(function() {
							$("#dashboardMenu").addClass( "active" );
							getStatistics();
						});
					</script> 
				</c:when>
				<c:otherwise>
					 <script type="text/javascript">
						$( document ).ready(function() {
							$("#dashboardMenu").addClass( "active" );
						});
					</script> 
				</c:otherwise>
			</c:choose>
			
        </div>
        <!--/.fluid-container-->
    </body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	 <title><tiles:getAsString name="title" /></title>
  	 <tiles:insertAttribute name="header" />
  	 
</head>
  <body>
  	<tiles:insertAttribute name="topmenu" />
  
         <div class="container-fluid">
            <div class="row-fluid">
            	<tiles:insertAttribute name="sidebar" />
                <div class="span9" id="content"><tiles:insertAttribute name="body" /></div>
            </div>
             <tiles:insertAttribute name="footer" />
        </div>
        <!--/.fluid-container-->
    </body>
</html>

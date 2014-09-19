<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
  	 <tiles:insertAttribute name="header" />
  	 <title><tiles:getAsString name="title" /></title>
</head>
  <body>
         <div class="container-fluid">
            <div class="row-fluid">
                 <tiles:insertAttribute name="body" />
            </div>
             <tiles:insertAttribute name="footer" />
        </div>
        <!--/.fluid-container-->
    </body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<noscript>
		<div class="alert alert-block span9">
			<h4 class="alert-heading">Warning!</h4>
			<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
		</div>
</noscript>   
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
        	<div style="display:inline;">
				 <a class="brand" href="javascript:void(0);"><img src="images/gea_logo.png" style="width:197px;height:57px" /></a>
				 <div style="float:right;margin-top:20px;color:#878787"> 
					<h4>	GEA Process Engineering (India) Pvt. Ltd. </h4>
					<span style="float:right;font-weight:bold;color: #993300;">
					Vipul Chauhan asfasfdf </span>
               	<br/>
				</div>
			 </div>
			 <br/>			
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
            </a>
		   <div style="display:inline;clear:both;"></div>
               <div class="nav-collapse collapse" style="margin-top: 65px;">
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> ${displayName} <i class="caret"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a tabindex="-1" href="logout.do">Logout</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav">
							<li id="dashboardMenu">
	                      			<a href="home.do"><img src="images/home.png" width="30" height="30"/>Home</a>
	                  		</li>
						  <li id="profileMenuHeader" class="dropdown">
                         			<a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"><img src="images/profile.png" width="30" height="30"/>Profile <i class="caret"></i></a>
	                         <ul class="dropdown-menu">
		                        	 <li>
		                                 <a tabindex="-1" href="editCompanyProfile.do">Update Profile Info </a>
		                             </li>
		                        	 <li>
	                                 	<a tabindex="-1" href="changepwd.do">Change Password</a>
	                             	</li>
	                         </ul>
                    	 </li>	
						  <li class="dropdown" id="settingsMenuHeader">
                         			<a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"><img src="images/settings.png" width="30" height="30"/>Settings <i class="caret"></i></a>
	                         <ul class="dropdown-menu">
		                        	 <li>
		                                 <a tabindex="-1" href="manageUserConfiguration.do">Manage Configuration </a>
		                             </li>
		                        	 <li>
	                                 	<a tabindex="-1" href="manageOptOutDefIndex.do">Manage Opt-Out Of Defects</a>
	                             	</li>
	                         </ul>
                    	 </li>	
						  <li id="usersMenuHeader" class="dropdown">
                         			<a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"><img src="images/users.png" width="30" height="30"/>Users <i class="caret"></i></a>
	                         <ul class="dropdown-menu">
	                         	
		                        	 <li>
	                                 	<a tabindex="-1" href="suIndex.do">Manage Secondary Users</a>
	                             	</li>
		                        	 <li>
	                                 	<a tabindex="-1" href="suFileUploadIndex.do">Bulk Registrations</a>
	                             	</li>
	                         </ul>
                    	 </li>	
                       	 <li id="bosMenuHeader">
                                <a href="badOrderSearchIndex.do"><img src="images/search.png" width="30" height="30"/>Bad Order Search </a>
                            </li>
						  <li id="mappingMenuHeader" class="dropdown">
                         			<a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"><img src="images/location.png" width="30" height="30"/>Mapping <i class="caret"></i></a>
	                         <ul class="dropdown-menu">
		                        	 <li>
	                                 	<a tabindex="-1" href="foMappingIndex.do">FO Mapping</a>
	                             	</li>
		                        	 <li>
	                                 	<a tabindex="-1" href="mrvMappingIndex.do">MRV Mapping</a>
	                             	</li>
	                         </ul>
                    	 </li>	
							<li id="reportMenuHeader">
	                      		<a href="iepReportsIndex.do"><img src="images/report.png" width="30" height="30"/>Reports</a>
	                  		</li>
				     <li>
				         <a href="logout.do"><img src="images/logout.png" width="30" height="30"/>Logout</a>
				     </li>
                </ul>                              
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>
 <!-- loading start -->
<div id="ajax_loader" style="text-align: center;">
		<img style="vertical-align: middle;" alt="loading..." src="images/loading2.gif" height="35"
			/> &nbsp; Processing...Please wait...
</div>
<!-- loading end -->
 <!-- error Div start -->
<div id="errorDiv"></div> 

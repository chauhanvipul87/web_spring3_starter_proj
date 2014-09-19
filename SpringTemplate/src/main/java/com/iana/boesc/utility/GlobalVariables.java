package com.iana.boesc.utility;

import java.util.Properties;

import javax.mail.internet.InternetAddress;

public class GlobalVariables {
	
	public static String ZEROSTR = "0";
	public static String ONESTR = "1";
	
	public static int ZEROINT = 0;
	public static int ONEINT = 1;
	
	//default record list size
	public static int offset = 0;
	public static int record = 10;
	public static int currentPage = 1;
	
	public static final String OWNER = "612";
	public static final String GROUP = "610";

	//error messsages class starts
	public static final String ERROR_CLASS						  = "alert-error";
	public static final String SUCCESS_CLASS 					  = "alert-success";
	public static final String INFO_CLASS 						  = "alert-info";
	public static final String DANGER_CLASS 					  = "alert-danger";
	public static final String BlOCK_CLASS 						  = "alert-block";
	public static final String LAYOUT_TYPE 					 	  = "bottomLeft";
	
	public static final String YES    = "Yes";
	public static final String NO     = "No";
	
	public static final String TRANS_TYPE_BOESC    = "BOESC";
	public static final String TRANS_TYPE_DVIR     = "DVIR";
	public static final String TRANS_TYPE_DVER     = "DVER";
	public static final String N_A     = "Not Available";
	
	
	public static String ERROR_MSG ="Something went wrong while processing your request";
	

	public static final int USER_TYPE_ID_IEP   = 1;
	public static final int USER_TYPE_ID_MRV   = 2;
	public static final int USER_TYPE_ID_FO    = 3;
	public static final int USER_TYPE_ID_ADMIN = 4;
	public static final int USER_TYPE_ID_SEC   = 5;
	
	public static final String USER_TYPE_ADMIN = "Admin";
	public static final String USER_TYPE_IEP   = "IEP";
	public static final String USER_TYPE_MRV   = "MRV";
	public static final String USER_TYPE_FO    = "FO";
	public static final String USER_TYPE_SEC   = "SEC";
	public static final String USER_TYPE_SEC_LABEL   = "INTERNAL USER";
	
	public static final String COUNTRYNAME_USA    = "USA";
	public static final String COUNTRYNAME_CAN    = "CAN";
	public static final String COUNTRYNAME_MAX    = "MAX";
	
	public static final String NOTIFICATION_MODE_322 = "322";
	public static final String NOTIFICATION_MODE_XML = "XML";
	public static final String NOTIFICATION_MODE_EMAIL = "EMAIL";
	
	
	public static final int COUNTRY_ID_USA    = 1;
	public static final int COUNTRY_ID_CAN    = 2;
	public static final int COUNTRY_ID_MAX    = 3;
	
	public static final int CODE_ID_ISO    = 2;
	public static final int CODE_ID_AAR    = 1;
	
	public static final int  DEFAULT_FTP_PORT = 21;
	
	public static final String CODENAME_ISO    = "ISO";
	public static final String CODENAME_AAR    = "AAR";
	
	public static final String FTP_TYPE_PULL = "PULL";
	public static final String FTP_TYPE_PUSH = "PUSH";
	
	public static String SU_FILE_UPLOAD_DEST; //cofigured from AppServletContext 
	public static String IEP_ROOT_DIR; //cofigured from AppServletContext
	public static String MRV_ROOT_DIR; //cofigured from AppServletContext
	public static String FO_ROOT_DIR; //cofigured from AppServletContext
	
	public static Properties EMAIL_PROPS = null;
	public static String 	 FROM_EMAIL  = null;
	public static String 	 FROM_EMAIL_PASSWORD  = null;
	public static InternetAddress[]   TO_EMAILS   = null;
	
	
	public static final String STATUS_REJECTED  = "REJECTED";
	public static final String STATUS_PENDING   = "Pending";
	public static final String STATUS_PROCESSED   = "PROCESSED";
	public static final String STATUS_REPAIRED  = "Repaired";
	public static final String STATUS_RELEASED  = "RELEASED";
	public static final String INBOUND_JOB 		= "INBOUND";
	public static final String OUTBOUND_JOB 	= "OUTBOUND";
	public static final String SUBJECT_LINE_EXCE 	= "BOESC-Exception";
	public static final String SUBJECT_LINE_BULK_SU_REG	= "Bulk User Registration";
	public static final String EMPTY = "";
	public static final String BLANK = "";
	
	public static final String FILESEPERATOR = System.getProperty("file.separator");
	public static final String TEMPLATE_SAMPLE_DIR = "templates"; 
	
	
}

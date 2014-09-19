package com.iana.boesc.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
 
public class AppServletContextListener implements ServletContextListener{
 
	Logger log = Logger.getLogger(AppServletContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}
 
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener started");	
		//load menu items from properties file
		arg0.getServletContext().setAttribute("menuItems",getMenuPropValues());
		readConfigurationFile();
		// loading configuration file for file upload
		
	}
	
	public void readConfigurationFile(){
		
		Properties prop = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        try {
			prop.load(inputStream);
			setEmailConfigurationDetails(prop); //Configure Email address details.
			
		} catch (IOException e) {
			e.printStackTrace();
			EmailUtility.prepareAndSendExceptionEmail(null,e,null);
			log.error( "Excpetion Raised in readConfigurationFile: ", e);
		}
        GlobalVariables.SU_FILE_UPLOAD_DEST =  prop.getProperty("suFileUploadPath");
        GlobalVariables.IEP_ROOT_DIR =  prop.getProperty("rootPathIEP");
        GlobalVariables.MRV_ROOT_DIR =  prop.getProperty("rootPathMRV");
        GlobalVariables.FO_ROOT_DIR =  prop.getProperty("rootPathFO");
      

	}
	
	
	private void setEmailConfigurationDetails(Properties prop) {
		try{
			 Properties emailProps 	= new Properties();
			 emailProps.put("mail.smtp.auth"			, prop.getProperty("mail.smtp.auth"));
	 		 emailProps.put("mail.smtp.starttls.enable" , prop.getProperty("mail.smtp.starttls.enable"));
	 		 emailProps.put("mail.smtp.host"		    , prop.getProperty("mail.smtp.host"));
	 		 emailProps.put("mail.smtp.port"			, prop.getProperty("mail.smtp.port"));
	 		 emailProps.put("mail.smtp.ssl.trust"		, prop.getProperty("mail.smtp.host"));
	 		 GlobalVariables.FROM_EMAIL          	 = prop.getProperty("mail.smtp.from.email");
	 		 GlobalVariables.FROM_EMAIL_PASSWORD     = prop.getProperty("mail.smtp.from.password");
			 GlobalVariables.EMAIL_PROPS = emailProps;
			 
			 String toEmails = prop.getProperty("mail.smtp.to.emails");
			 String [] toEmailAddress;
			 InternetAddress[] internetAdress = null;
			 if(toEmails.contains(";")){
				 toEmailAddress = toEmails.split(";");
				 internetAdress = new InternetAddress[toEmailAddress.length];
				 for(int i =0 ;i <toEmailAddress.length; i++){
					 internetAdress [i] = new InternetAddress(toEmailAddress[i]);
				 }
			 }else{
				 internetAdress = new InternetAddress[1];
				 internetAdress [0] = new InternetAddress(toEmails);
			 }
			 GlobalVariables.TO_EMAILS  = internetAdress;
		}catch(AddressException e){
			e.printStackTrace();
			EmailUtility.prepareAndSendExceptionEmail(null,e,null);
			log.error( "Excpetion Raised in setEmailConfigurationDetails: ", e);
		}
			 
	}

	public List<String> getMenuPropValues() {
   	 
        Properties prop = new Properties();
        String propFileName = "menus.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			EmailUtility.prepareAndSendExceptionEmail(null,e,null);
		}
        // get the property value and store into array list
        List<String> menuItems = new ArrayList<String>(7);
        menuItems.add("Index Not Used.");
        menuItems.add(GlobalVariables.USER_TYPE_ID_IEP  ,prop.getProperty("IEP"));
        menuItems.add(GlobalVariables.USER_TYPE_ID_MRV	,prop.getProperty("MRV"));
        menuItems.add(GlobalVariables.USER_TYPE_ID_FO   ,prop.getProperty("FO"));
        menuItems.add(GlobalVariables.USER_TYPE_ID_ADMIN,prop.getProperty("ADMIN"));
        menuItems.add(GlobalVariables.USER_TYPE_ID_SEC  ,prop.getProperty("SEC"));
        log.info("menuItems ::"+menuItems);
        return menuItems;
    }
}
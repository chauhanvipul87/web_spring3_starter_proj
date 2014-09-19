package com.iana.boesc.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.iana.boesc.vo.BoescSecurityObj;

public class EmailUtility {
	
	static Logger  log =  Logger.getLogger(EmailUtility.class);
	private Session getSession(){
		log.error("GlobalVariables.EMAIL_PROPS ::"+GlobalVariables.EMAIL_PROPS);
		log.error("GlobalVariables.FROM_EMAIL_PASSWORD ::"+GlobalVariables.FROM_EMAIL_PASSWORD);
		log.error("GlobalVariables.FROM_EMAIL::"+GlobalVariables.FROM_EMAIL);
		
		 Session session = Session.getInstance(GlobalVariables.EMAIL_PROPS, new javax.mail.Authenticator() {
		        @Override
		        protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(GlobalVariables.FROM_EMAIL, GlobalVariables.FROM_EMAIL_PASSWORD);
		        }
		      });
		 return session;
	}
	
	public static boolean sendEmail(final String emailFrom, final String subject, final InternetAddress[] addressesTo,boolean sendHtmlEmail, List<String> bodyContentList,  
			boolean requiredAttachment,final File attachment) {
		try {
			Session session  = new EmailUtility().getSession();
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			InternetAddress addressFrom = new InternetAddress(emailFrom);
			message.setFrom(addressFrom);

			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO, addressesTo);

			// Set Subject: header field
			message.setSubject(subject);

			// Create the message part
			BodyPart messageBodyPart = new javax.mail.internet.MimeBodyPart();

			// Fill the message
			if(sendHtmlEmail)
				setMessageBodyContent(messageBodyPart,bodyContentList,true);
			else
				setMessageBodyContent(messageBodyPart,bodyContentList,false);


			// Create a multi part message
			Multipart multipart = new javax.mail.internet.MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			if(requiredAttachment && attachment != null){

				// Part two is attachment
				messageBodyPart = new javax.mail.internet.MimeBodyPart();
				DataSource source = new FileDataSource(attachment);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(attachment.getName());
				multipart.addBodyPart(messageBodyPart);
			}


			// Send the complete message parts
			message.setContent(multipart);
			// Send message
			Transport.send(message);
			System.out.println("Mail Sent....");
			return true;
		} catch (Exception ex) {
			log.error( "Excpetion Raised in sendEmail: ", ex);
			ex.printStackTrace();
			return false;
		}
	}

	private static void setMessageBodyContent(BodyPart messageBodyPart, List<String> bodyContentList, boolean htmlEmail) throws MessagingException {
		if(htmlEmail){
			String str ="";
			for(int i =0 ;i <bodyContentList.size() ; i++){
				str =str + bodyContentList.get(i);
			}
			messageBodyPart.setContent(str, "text/html");
		}else{
			String str ="";
			for(int i =0 ;i <bodyContentList.size() ; i++){
				str =str + bodyContentList.get(i) +"\n";
			}
			messageBodyPart.setText(str);
		}
	}

	public static boolean sendBulkUpdateFailureNotice(final String msgBody) {

	    Session session = Session.getInstance(GlobalVariables.EMAIL_PROPS, new javax.mail.Authenticator() {
	      @Override
	      protected PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication("", "");
	      }
	    });
	    try {
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(GlobalVariables.FROM_EMAIL));
	      message.addRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(
	    		  GlobalVariables.FROM_EMAIL) });
	      message.setSubject("Bulk DVER Update via XLS failure notice:");
	      message.setText(msgBody);
	      message.setContent(msgBody, "text/html");
	      Transport.send(message);
	      return true;
	    } catch (MessagingException e) {
	      e.getMessage();
	  	log.error( "Excpetion Raised in sendBulkUpdateFailureNotice: ", e );
	      return false;
	    }
	  }

	  public static boolean sendExceptionEmails(final String exceptionMessage) {

		    Session session = Session.getInstance(GlobalVariables.EMAIL_PROPS, new javax.mail.Authenticator() {
		      @Override
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication("", "");
		      }
		    });
		    try {
		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress(GlobalVariables.FROM_EMAIL));
		      message.setText(exceptionMessage);
		      Transport.send(message);
		      return true;
		    } catch (MessagingException e) {
		      e.getMessage();
		      log.error( "Excpetion Raised in sendExceptionEmails: ", e );
		      return false;
		    }
		  }

	public static void prepareAndSendExceptionEmail(HttpServletRequest request,Exception ex,Map<String,Object> extraFields) {

		List<String> lst = new ArrayList<String>();
		if(request !=null){
			HttpSession session =  request.getSession();
			if(session !=null){
				BoescSecurityObj securityObj = (BoescSecurityObj) session.getAttribute("securityObj");
					if(securityObj !=null){
							lst.add("<p><string>SecurityObj ::</strong> "+(BoescSecurityObj) session.getAttribute("securityObj")+"</p>");
					}
		     }
		}
		lst.add("<p><string>Exception caused by ::</strong> "+BOESCUtil.exceptionToString(ex)+"</p>");
		
		if(extraFields !=null && extraFields.size() > 0){
			 Iterator<Entry<String, Object>> entries = extraFields.entrySet().iterator();
			 while (entries.hasNext()) {
			   Entry<String, Object> thisEntry = (Entry<String, Object>) entries.next();
			   Object key = thisEntry.getKey();
			   Object value = thisEntry.getValue();
			   lst.add("<p><string>"+key+" ::</strong> "+value+"</p>");
			 }
		 }
		// EmailUtility.sendEmail(GlobalVariables.FROM_EMAIL, GlobalVariables.SUBJECT_LINE_EXCE, GlobalVariables.TO_EMAILS, true, lst, false, null);
		
	}
	
	
}

/*
 * Copyright (c) 2013 IANA. All Rights Reserved. THE AUTHOR MAKES NO
 * REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. THE
 * AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT
 * OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.iana.boesc.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.CurrencyValidator;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.PercentValidator;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class BOESCUtil {

  private final static String LEADING_ZERO_REGEX = "^0+(?!$)";
  static Logger log   = Logger.getLogger(BOESCUtil.class);
  
  /**
   * 
   * @param list
   * @param outputStream
   * @throws DocumentException
   * @throws IOException
   */
  public static void doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, outputStream);
    document.open();
    PdfContentByte cb = writer.getDirectContent();

    for (InputStream in : list) {
      PdfReader reader = new PdfReader(in);
      for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        document.newPage();
        // import the page from source pdf
        PdfImportedPage page = writer.getImportedPage(reader, i);
        // add the page to the destination pdf
        cb.addTemplate(page, 0, 0);
      }
    }

    outputStream.flush();
    document.close();
    outputStream.close();
  }

  /**
   * 
   * @param fileContents
   * @return
   */
  public static byte[] concatenateFileContent(List<byte[]> fileContents) {
    int length = 0;

    for (byte[] content : fileContents) {
      length += content.length;
    }

    byte[] mergedFileContents = new byte[length];

    int destPos = 0;

    for (byte[] fileContent : fileContents) {
      System.arraycopy(fileContent, 0, mergedFileContents, destPos, fileContent.length);
      destPos += fileContent.length;
    }

    return mergedFileContents;
  }


  public static String trimLeadingZeros(String inputString) {
    return inputString.replaceFirst(LEADING_ZERO_REGEX, "");
  }
  
  public static final ModelAndView setResponseMessage(ModelAndView model,String message,Integer errorFlag, String className){
		if(message ==null || message.trim().equals("")){
			model.addObject("responseMessage",GlobalVariables.ERROR_MSG);
		}else{
			model.addObject("responseMessage",message);
		}
		if(message ==null || message.equals("")  || errorFlag == 1 ){
			model.addObject("responseMessageClass",GlobalVariables.ERROR_CLASS);
		}else{
			model.addObject("responseMessageClass",className);
		}
		return model; 
	}
  
  
  public static final RedirectAttributes setFlashResponseMessage(RedirectAttributes redirectAttributes,String message,Integer errorFlag, String className){
		if(message ==null || message.trim().equals("")){
			redirectAttributes.addFlashAttribute("responseMessage",GlobalVariables.ERROR_MSG);
		}else{
			redirectAttributes.addFlashAttribute("responseMessage",message);
		}
		if(message ==null || message.equals("")  || errorFlag == 1 ){
			redirectAttributes.addFlashAttribute("responseMessageClass",GlobalVariables.ERROR_CLASS);
		}else{
			redirectAttributes.addFlashAttribute("responseMessageClass",className);
		}
		return redirectAttributes; 
	}
  
  
  
  public static int getUserTypeId(String userType){
	  
	  if(userType.equalsIgnoreCase(GlobalVariables.USER_TYPE_IEP))
		  return GlobalVariables.USER_TYPE_ID_IEP;
	  if(userType.equalsIgnoreCase(GlobalVariables.USER_TYPE_MRV))
		  return GlobalVariables.USER_TYPE_ID_MRV;
	  if(userType.equalsIgnoreCase(GlobalVariables.USER_TYPE_FO))
		  return GlobalVariables.USER_TYPE_ID_FO;
	  if(userType.equalsIgnoreCase(GlobalVariables.USER_TYPE_ADMIN))
		  return GlobalVariables.USER_TYPE_ID_ADMIN;
	  if(userType.equalsIgnoreCase(GlobalVariables.USER_TYPE_SEC))
		  return GlobalVariables.USER_TYPE_ID_SEC;
	  
	  return 0;
  }
  
  /**
	  * this method change first character in upper case & return modified string
	  * 
	  * @param str
	  * @return modified str
	  */
	public final String initCap(String rowStr) {
		StringBuffer stringbf = new StringBuffer();
		Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(rowStr);
		while (m.find()) {
			m.appendReplacement(stringbf, m.group(1).toUpperCase()+ m.group(2).toLowerCase());
		}
		return stringbf.toString();
	}
	
	/**
	  * Check if provided String is validate or not
	  * 
	  * @param str
	  * @return valid String (Blank if null or null)
	  */
	public static final String validateString(String str){
		if(str ==null || str.trim().equals("") || str.trim().equalsIgnoreCase("NULL")){
			return "";
		}else{
			return str.trim();
		}
	}
	
	
	/**
	  * Check if provided Object of String is validate or not
	  * 
	  * @param str
	  * @return valid Object of String (Blank if null or null)
	  */
	public static final String validateObject(Object str){
		if(str ==null || str.toString().trim().equals("")){
			return "";
		}else{
			return str.toString().trim();
		}
	}
	
	/**
	  * Check if provided String is Number
	  * 
	  * @param str
	  * @return
	  */
	 public static boolean checkIsNumeric(String str) {
	  if (str == null || str.trim().equals("")){
		  return false;
	  }
	  return str.trim().matches("-?\\d+(.\\d+)?");
	 }

	 /**
	  * Check if given String provided is double
	  * 
	  * @param str
	  * @return
	  */
	 public static boolean checkIfDouble(String str) {
	  if (str == null || str.trim().equals("")){
		  return false;
	  }
	  try {
	       Double.parseDouble(str);
	  } catch (NumberFormatException nfe) {
	      return false;
	  }
	  return true;
	 }

	 /**
	  * Validates whether provided string is date field or not
	  * 
	  * @param date
	  * @param format
	  *            defaultDate format
	  * @return
	  */
	 public static boolean validateDate(String date) {
	  if (date == null || date.trim().equals("")){
		  return false;
	  }
	  String format = "MM/dd/yyyy";
	  DateValidator validator = DateValidator.getInstance();
	  Date dateVal = validator.validate(date, format);
	  if (dateVal == null) {
	   return false;
	  }
	  return true;
	 }

	 /**
	  * Validates whether provided string is date field or not
	  * 
	  * @param date
	  * @param format
	  * @return boolean status of whether given data is valid or not
	  */
	 public static boolean validateDate(String date, String format) {
	   if (date == null || date.trim().equals("")){
		  return false;
	   }
	   if (format == null || format.trim().equals("")){
	  		  return false;
	   }
	  DateValidator validator = DateValidator.getInstance();

	  Date dateVal = validator.validate(date, format);
	  if (dateVal == null) {
	   return false;
	  }
	  return true;
	 }

	 /**
	  * Formats the given date as according to given formatter
	  * 
	  * @param date
	  * @param format
	  * @return
	  */
	 public static String formatDate(String date, String format) {
	  if (date == null || date.trim().equals("")){
		  return null;
	   }
	   if (format == null || format.trim().equals("")){
	  		  return null;
	   }
	  DateValidator validator = DateValidator.getInstance();

	  String dateVal = null;
	  try {
	   dateVal = validator.format(date, format);
	  } catch (IllegalArgumentException e) {
	   System.out.println("Bad date:" + date + ": cannot be formatted");
	  }
	  if (dateVal == null) {
	   return null;
	  }
	  return dateVal;
	 }

	 /**
	  * Validates whether clients data is Integer or not
	  * 
	  * @param integer
	  * @return
	  */
	 public static boolean IntegerValidator(String integer) {
	  if (integer == null || integer.trim().equals("")){
	  		  return false;
	  }
	  IntegerValidator validator = IntegerValidator.getInstance();
	  Integer integerVal = validator.validate(integer, "#,##0.00");
	  if (integerVal == null) {
	   return false;
	  }
	  return true;
	 }

	 /**
	  * validates whether data is currency of not
	  * 
	  * @param currency
	  * @param loc
	  * @return
	  */
	 public static boolean currencyValidator(String currency, Locale loc) {
	  BigDecimalValidator validator = CurrencyValidator.getInstance();
	  if (loc == null) {
	   loc = Locale.US;
	  }
	  BigDecimal amount = validator.validate(currency, loc);
	  if (amount == null) {
	   return false;
	  }
	  return true;
	 }

	 /**
	  * Validates whether data provided is in percentage or not
	  * 
	  * @param percentVal
	  * @return
	  */
	 public static boolean percentValidator(String percentVal) {
	  BigDecimalValidator validator = PercentValidator.getInstance();
	  boolean valid = false;
	  BigDecimal Percent = validator.validate(percentVal, Locale.US);
	  if (Percent == null) {
	   valid = false;
	  }
	  // Check the percent is between 0% and 100%
	  if (validator.isInRange(Percent, 0, 1)) {
	   valid = true;
	  } else {
	   valid = false;
	  }
	  return valid;
	 }

	 /**
	  * validates correct email address
	  * 
	  * @param email
	  * @return
	  */
	 public static boolean emailValidator(String email) {
		 if (email == null || email.trim().equals("")){
	  		  return false;
	  	  } 
		  EmailValidator validator = EmailValidator.getInstance();
		  boolean isAddressValid = validator.isValid(email);
		  return isAddressValid;
	 }
	 
	 public static boolean isvalidLengh(Object str, int  min, int max,boolean enableTrim){
		if (str == null){
	  		  return false;
	  	}
		if(enableTrim){
			if(str.toString().trim().equals("")){
				return false;
			}
		}
		int strLength = str.toString().length();
		if(strLength < min || strLength > max){
			return false;
		}
		return true;
	 }
	 
	 // validate String is character or not
	 public static boolean isCharacter(String str){
		if (str == null || str.trim().equals("")){
	  		  return false;
	  	} 
		boolean isChar = str.matches("[a-zA-z]{1}");
		return isChar;
	 }
	 
	 public static String replaceNullOrWhileSpaceWithBlank(Object str){
		 if (str == null) {
			return "";
		 }
		 if(!str.toString().trim().equalsIgnoreCase("")){
			 return str.toString();
		 }else{
			 return "";
		 }
	 }
	 
	 // check if provided string is digit or not
	 public static boolean isDigit(String str){
		if (str == null || str.trim().equals("")){
	  		  return false;
	  	}
		Double val = null;
		try {
		  val = Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		String tempStr = val.intValue()+"";
		boolean isDigit = tempStr.matches("\\d+"); 
		return isDigit;
	 }
	 
	 //validate time of 24 hours like HHMM from provided string.
	 public boolean validateTime24Hours(final String time){
		 final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3])[0-5][0-9]";
		 Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
		 Matcher matcher = pattern.matcher(time);
		 return matcher.matches();
	  }

	// check null object
	 public static boolean checkNull(Object obj){
		if (obj == null){
	  		  return true;
	  	}
		
		return false;
	 }
	 public static boolean isEmpty(String str){
		 if (str == null || str.trim().length() == 0){
	  		  return true;
	  	  } 
		return false;
	 }
	 public static boolean sendEmailWithAttachments(final String emailFrom, final String subject,
		      final InternetAddress[] addressesTo, final String body, final File attachment) {
		 	log.info("Entering sendEmailWithAttachments("+emailFrom+")");
		    try {
		      Session session = Session.getInstance(GlobalVariables.EMAIL_PROPS, new javax.mail.Authenticator() {
		        @Override
		        protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication("", "");
		        }
		      });

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
		      messageBodyPart.setText(body);
		      messageBodyPart.setContent(body, "text/html");

		      // Create a multi part message
		      Multipart multipart = new javax.mail.internet.MimeMultipart();

		      // Set text message part
		      multipart.addBodyPart(messageBodyPart);

		      // Part two is attachment
		      messageBodyPart = new javax.mail.internet.MimeBodyPart();

		      DataSource source = new FileDataSource(attachment);
		      messageBodyPart.setDataHandler(new DataHandler(source));
		      messageBodyPart.setFileName(attachment.getName());
		      multipart.addBodyPart(messageBodyPart);

		      // Send the complete message parts
		      message.setContent(multipart);
		      // Send message
		      Transport.send(message);

		      return true;
		    } catch (Exception ex) {
		      log.error(ex.getMessage());
		      return false;
		    }
		  }
	 public static String exceptionToString(Exception e){
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter );
			printWriter.flush();
			String stackTrace = writer.toString();
			return stackTrace;
	 }
	 
		public static void createDir(String rootDir,String dirName,String addedDir)
		{
			try 
			{
				log.info("Step-1: Create Directory");
				Runtime rtDir = Runtime.getRuntime();
				String cmdDir = "mkdir -p " + rootDir+dirName+addedDir;
				log.info("MKDIR :"+cmdDir);
				Process procDir = rtDir.exec(cmdDir);
				int exitValDir=procDir.waitFor();
				log.info("mkdir result="+exitValDir);
				if(exitValDir==1)
				{
					//errorNotfPermission(dirName+addedDir);					
				}
				
				log.info("Step-2: Set Permissions ");
				Runtime rtChown = Runtime.getRuntime();
				String cmdChown = "/bin/chown -R "+GlobalVariables.OWNER+":"+GlobalVariables.GROUP+" " + rootDir+dirName;
				log.info("CHOWN :"+cmdChown);			
				Process procChown = rtChown.exec(cmdChown);
				int exitValChown=procChown.waitFor();
				log.info("chown result="+exitValChown);
				
				if(exitValChown==1)
					{
						//errorNotfPermission(dirName+addedDir);					
					}
			}
			catch(Exception e)
			{
				log.info("Exception in creating directory :"+e);
				//errorNotfication(dirName,e);
			}
		}
		
		public static  File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
	        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + 
	                                multipart.getOriginalFilename());
	        multipart.transferTo(tmpFile);
	        return tmpFile;
	    }

		public static  String createErrorStringFromList(List<String> errorList) {
			String errorString ="";
			if(errorList !=null && errorList.size() > 0){
				if(errorList.size() ==1){
					//no need to append <br/> at the end of each string
					errorString = errorList.get(0).toString();
				}else{
					// Append <br/> at the end of each string 
					for(int i= 0; i < errorList.size();i++){
						String str = errorList.get(i).toString()+"<br/>";
						errorString += str;
					}
				}
			}
			return errorString;
		}
		
		
		public static  XSSFSheet getWorkSheet(File incoming_file) throws IOException {
			FileInputStream file = new FileInputStream(incoming_file);
			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			return sheet;
		}

		
}

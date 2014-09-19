package com.iana.boesc.utility;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class AbstractExcelFileManager {
	
	Logger log = Logger.getLogger(AbstractExcelFileManager.class);

	protected <T> boolean validateHeaderArr(T[] headerArray) throws WrongConfigurationException{
		if(headerArray ==null || headerArray.length == 0)
			throw new WrongConfigurationException("Please send valid header list , Header list should not be empty.");
		return true;
	}
	
	protected <T> boolean validateProperyArray(T[] propertyListToShow) throws WrongConfigurationException{
		if(propertyListToShow ==null || propertyListToShow.length == 0)
			throw new WrongConfigurationException("Please send valid property list , Property list should not be empty.");
		return true;
	}

	protected <T> boolean isSameHeaderPropertyMapping(T[] headerArray,T[] propertyListToShow) throws WrongConfigurationException{
		if(headerArray.length != propertyListToShow.length)
			throw new WrongConfigurationException("Supplied header column is not matched with property list.");
		return true;
	}
	
	protected <T> boolean validateInputData(String sheetName,String excelReportTitle,T[] headerArray,T[] propertyListToShow,List<?> listResultBean) throws WrongConfigurationException{
		try {
			
			if(sheetName ==null || sheetName.isEmpty())
				throw new WrongConfigurationException("Please supply proper sheet name.");
			
			if(excelReportTitle ==null || excelReportTitle.isEmpty())
					throw new WrongConfigurationException("Please supply proper report title.");	
			
			if(listResultBean ==null || listResultBean.size()==0){
				throw new WrongConfigurationException("Please provide valid result list details or It should not be empty.");
			}else {
				PropertyDescriptor [] pd = this.getPropertyDescriptor(listResultBean.get(0));
				if(pd ==null || pd.length ==0)
					throw new WrongConfigurationException("There is no getter /setter found in bean class. please create getter /setter method first.");
			}
			if(this.validateHeaderArr(headerArray) && this.validateProperyArray(propertyListToShow) && this.isSameHeaderPropertyMapping(headerArray,propertyListToShow)){
				return true;	
			}
			
		} catch (WrongConfigurationException e) {
			EmailUtility.prepareAndSendExceptionEmail(null,e,null);
			throw e;
		}
		return false;
	}
	
	 protected HSSFSheet createXLS(String sheetName) {
	   // logger.info("createXLS...");
	    // Create new workbook
	    HSSFWorkbook workbook = new HSSFWorkbook();
	    // Create new worksheet
	    HSSFSheet worksheet = workbook.createSheet(sheetName);
	    return worksheet;
	  }
	 
	 
	 /**
	   * Builds the report title and the date header
	   * 
	   * @param worksheet
	   * @param startRowIndex
	   *          starting row offset
	   * @param startColIndex
	   *          starting column offset
	   */
	 public <T> HSSFSheet  buildHeaders(HSSFSheet worksheet, int startRowIndex, int startColIndex, String title,T[] headerArray) {
		 int mergeTitle =headerArray.length-1;
		// Set column widths for report title.
		 for(int i =0; i<= mergeTitle ;i++){
			 worksheet.setColumnWidth(i, 5000);
		 }

		// Create font style for the report title
	    Font fontTitle = worksheet.getWorkbook().createFont();
	    fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    fontTitle.setFontHeight((short) 280);

	    // Create cell style for the report title
	    HSSFCellStyle cellStyleTitle = worksheet.getWorkbook().createCellStyle();
	    cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyleTitle.setWrapText(true);
	    cellStyleTitle.setFont(fontTitle);

	    // Create report title
	    HSSFRow rowTitle = worksheet.createRow((short) startRowIndex);
	    rowTitle.setHeight((short) 500);
	    HSSFCell cellTitle = rowTitle.createCell(startColIndex);
	    cellTitle.setCellValue(title);
	    cellTitle.setCellStyle(cellStyleTitle);

	    // Create merged region for the report title
	    worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, mergeTitle));

	    // Create date header
	    HSSFRow dateTitle = worksheet.createRow((short) startRowIndex + 1);
	    HSSFCell cellDate = dateTitle.createCell(startColIndex);
	    cellDate.setCellValue("This report was generated at " + new Date());
	    
	    //Title : Row Start.. 
	    
	    // Create font style for the headers
	    Font font = worksheet.getWorkbook().createFont();
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);

	    // Create cell style for the headers
	    HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
	    headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    headerCellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    headerCellStyle.setWrapText(true);
	    headerCellStyle.setFont(font);
	    headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

	    // Create the column headers
	    HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
	    rowHeader.setHeight((short) 500);
	    HSSFCell cell =null;
	    for(int i =0 ;i <headerArray.length;i++){
	    	cell = rowHeader.createCell(startColIndex + i);
	    	cell.setCellValue(headerArray[i].toString());
	    	cell.setCellStyle(headerCellStyle);
	    }
	    
	    return worksheet;
	  }
	 
	 protected PropertyDescriptor[] getPropertyDescriptor(Object javaBean) throws WrongConfigurationException{
	        BeanInfo bi =null;
			try {
				bi = Introspector.getBeanInfo(javaBean.getClass(),Object.class);
			} catch (IntrospectionException e) {
		    	 Map<String,Object> inputParamMap = new HashMap<String,Object>();
		    	 inputParamMap.put("javaBean", javaBean);
		    	 EmailUtility.prepareAndSendExceptionEmail(null,e,inputParamMap);
				throw new WrongConfigurationException("Unable to get bean information.");
			}  
	        PropertyDescriptor pds[] = bi.getPropertyDescriptors();
			return pds; 
	 }
	 protected <T> Object getPropertyValue(Object javaBean, T propertyName)throws WrongConfigurationException{   
	       try {  
	           PropertyDescriptor pds[] = this.getPropertyDescriptor(javaBean);
	           for (PropertyDescriptor pd : pds) {  
	        	   if(propertyName.toString().isEmpty()) continue;
	               if (pd.getName().equals(propertyName.toString())) {  
	            	    String name = pd.getName();  
				        Method getter = pd.getReadMethod();  
				        Class<?> type = pd.getPropertyType();
				        Object value = getter.invoke(javaBean);  
				        if(value == null){
				        	if(type.equals(String.class)){
				        		return "";
					        }
				        }
				        log.info(name + " = " + value + "; type = " + type); 
				        return value;
	               }  
	           }  
	       } catch (Exception e) { 
	    	     e.printStackTrace();
		    	 Map<String,Object> inputParamMap = new HashMap<String,Object>();
		    	 inputParamMap.put("JavaBean", javaBean);
		    	 inputParamMap.put("propertyName", propertyName);
		    	 EmailUtility.prepareAndSendExceptionEmail(null,e,inputParamMap);
	             throw new WrongConfigurationException("Unable to get property value.");
	       }  
	       return "";
	   }  
	 
	@SuppressWarnings("unchecked")
	protected <T> T[] getMatchingColumnName(Object javaBean,T[] propertyListToShow) throws WrongConfigurationException{
		 int counter = 0;
		 T[] matchedArr =(T[]) Array.newInstance(propertyListToShow.getClass().getComponentType(), propertyListToShow.length);
		 PropertyDescriptor[] pd = this.getPropertyDescriptor(javaBean);
		 for(int i = 0; i< propertyListToShow.length; i++){
		 for(int j = 0; j< pd.length; j++){
				 PropertyDescriptor pds = pd [j];
				 if(pds.getName().equalsIgnoreCase(propertyListToShow[i].toString())){
					 log.info("pds.getName().toString()"+pds.getName().toString());
					 matchedArr [counter] = (T) propertyListToShow[i];
					 counter++;
				 }
			 }
		 }
		 if(propertyListToShow.length != counter){
			 throw new WrongConfigurationException("Please supplied valid property name same as bean.");
		 }
		 
		return matchedArr; 
	}
	 
	 @SuppressWarnings("unchecked")
	public <T> HSSFSheet  fillWorkSheetData(HSSFSheet worksheet, int startRowIndex, int startColIndex,T[] propertyListToShow,List<?> listResultBean) throws WrongConfigurationException{
		 System.out.println("Filling data into Users Excel Report...");
		 T[] matchingColsArr = this.getMatchingColumnName(listResultBean.get(0), propertyListToShow);
		 if(matchingColsArr ==null || matchingColsArr.length ==0)
				throw new WrongConfigurationException("No properties are matched with bean fields.");
		 System.out.println("Some properties are matched ::"); 
		 // Row offset
		 startRowIndex += 2;

		 // Create cell style for the body
		 HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		 bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		 bodyCellStyle.setWrapText(true);
		 for(int i = 0 ; i <listResultBean.size() ;i++){
			 // Create a new row
			 HSSFRow row = worksheet.createRow((short) startRowIndex + 1);
			 startRowIndex++;
			 int innerCount = 0;  
			 for (T val : matchingColsArr){
//				 if(val ==null || val.toString().trim().equals("")) continue;
				 if(val ==null || val.toString().trim().equals("")) val = (T) GlobalVariables.N_A;
				 HSSFCell cell = row.createCell(innerCount);
				 log.info("listResultBean.get(i) ::"+listResultBean.get(i) +"::Val -- "+val);
				 cell.setCellValue(this.getPropertyValue(listResultBean.get(i), val).toString());
				 cell.setCellStyle(bodyCellStyle);
				 innerCount++;
			 }
		 }
			 
		return worksheet;
	 }
	 
	 
	
}

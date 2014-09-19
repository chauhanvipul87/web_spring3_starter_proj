package com.iana.boesc.utility;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class ExcelFillManager extends AbstractExcelFileManager {
	
	Logger logger = Logger.getLogger(ExcelFillManager.class);
	
	public <T> HSSFSheet generateExcelSheet(String sheetName,String excelReportTitle, T[] headerArray,T[] propertyListToShow,List<?> listResultBean ) throws WrongConfigurationException{
		//logger.info("in generateExcelSheet");
		boolean properValue = this.validateInputData(sheetName,excelReportTitle, headerArray,propertyListToShow,listResultBean);
		HSSFSheet worksheet  =null;
		if(properValue){
			
			//create a blank XLS file.
			worksheet = this.createXLS(sheetName);
			int startRowIndex = 0;
			int startColIndex = 0;
			// Build the title and date headers
			this.buildHeaders(worksheet, startRowIndex, startColIndex, excelReportTitle, headerArray);
			this.fillWorkSheetData(worksheet, startRowIndex, startColIndex,propertyListToShow,listResultBean);
		}
		return worksheet;
	}
}

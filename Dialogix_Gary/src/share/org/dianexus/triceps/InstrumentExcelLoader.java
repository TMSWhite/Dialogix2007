/* ******************************************************** 
** Copyright (c) 2000-2007, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dianexus.triceps;

import jxl.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
  This class loads instruments from Excel files:
  (1) Save as Unicode Text to needed directory
  (2) Create horizontal database table, if needed
*/

public class InstrumentExcelLoader implements java.io.Serializable {
  static Logger logger = Logger.getLogger(InstrumentExcelLoader.class);
  private static int UseCounter = 0;
  private String contents = null;
  private int numCols = 0;
  private int numRows = 0;
	private int nulLanguages = 0;
	private int majorVersion = 1;
	private int minorVersion = 0;
	private int numLanguages = 0;
	private boolean status = false;
	private String title = null; 
  
  /**
  	Upload instrument 
  	@param filename The absolute path of the filename
  	@return	true if succeeds
  */
  
  public InstrumentExcelLoader() {
  }
  
  public boolean loadInstrument(String filename) {
  	++InstrumentExcelLoader.UseCounter;
  	
  	if (filename == null || "".equals(filename.trim())) {
  		this.status = false;
  	}
  	
    String justFileName = filename.substring(filename.lastIndexOf(File.separatorChar) + 1 );
    
  	logger.debug("Importing '" + justFileName + "' from '" + filename + "'");
    
  	Workbook workbook = retrieveExcelWorkbook(filename);
  	if (workbook != null && processWorkbook(workbook)) {
  		this.status = writeFile("/bin/tomcat6/webapps/Demos/WEB-INF/schedules/InstrumentExcelLoader-test_" + InstrumentExcelLoader.UseCounter + ".txt");
  	}
  	else {
  		this.status = false;
  	}
  	return this.status;
  }
  
  Workbook retrieveExcelWorkbook(String filename) {
  	try {
      Workbook workbook = Workbook.getWorkbook(new File(filename));
      return workbook;
  	}
  	catch (Exception e) {
  		logger.error("", e);
  	}
  	return null;
  }
  
  /* XXX Should this return a class representing the information needed to create table?
  	title
  	majorVersion
  	minorVersion
  	numLanguages
  	varName[]
  	?hash?
  	
  	If so, should it be done as a InstrumentBean?
  */
  boolean processWorkbook(Workbook workbook) {
  	try {
    	StringBuffer schedule = new StringBuffer();

      Sheet sheet = workbook.getSheet(0);  		
  		
      this.numCols = sheet.getColumns();	
      this.numRows = sheet.getRows();
      
      // process rows one at a time
      for(int i = 0;i < numRows; i++){
          //process cols
          Cell cell = sheet.getCell(0,i);
          // check to see if it is a header row
          // if it is we need to get the languages title and sched versions from the appropriate lines
          if(cell.getContents().equals("RESERVED")){
              Cell cell2 = sheet.getCell(1, i);
              Cell cell3 = sheet.getCell(2, i);
              schedule.append(cell.getContents()+"\t"+cell2.getContents()+"\t"+cell3.getContents()+"\n");
              // check for number of languages
              
              if(cell2.getContents().equals("__LANGUAGES__")){
                  StringTokenizer st = new StringTokenizer((String)cell3.getContents(),"|");
                  numLanguages = st.countTokens();
              } else if(cell2.getContents().equals("__TITLE__")){
                  this.title = (String) cell3.getContents();
              }else if(cell2.getContents().equals("__SCHED_VERSION_MAJOR__")){
                  this.majorVersion = new Integer(cell3.getContents()).intValue();
              } else if(cell2.getContents().equals("__SCHED_VERSION_MINOR__")){
                  this.minorVersion = new Integer(cell3.getContents()).intValue();
              }
          } 
          // check to see if the row is a comment row. If it is add it to the text version of the instrument
          else if(cell.getContents().startsWith("COMMENT")){
              schedule.append(cell.getContents());
              for(int m = 1;m < numCols;m++){
                  Cell myCell = sheet.getCell(m,i);
                  schedule.append("\t").append(myCell.getContents());
              }
              schedule.append("\n");
          // otherwise it is a data row. Extract the data elements from the spreadsheet and build the text file
          } else {
              String concept = sheet.getCell(0,i).getContents();
              String varName = sheet.getCell(1, i).getContents();
              /*
              if(!myCell.getContents().equals("")){
                  // add column to sql create table
                  tableSql.append(",`"+myCell.getContents()+"` text collate latin1_general_ci\n");
              }
              */
              String externalName = sheet.getCell(2,i).getContents();
              String relevance = sheet.getCell(3,i).getContents();
              String actionType = sheet.getCell(4,i).getContents();
              
              // if the number of languages is more than one there will be 4 more columns per language to process
              // cycle through for the number of languages
              // There may be more lanauges listed than actual langauges entered - handle this gracefully
              ArrayList langCols = new ArrayList();
              for(int j=1; j <= numLanguages; j++){
                  // get readback
                  langCols.add( sheet.getCell((j*4)+1,i).getContents());
                  // get action
                  langCols.add( sheet.getCell((j*4)+2,i).getContents());
                  // get response options
                  langCols.add( sheet.getCell((j*4)+3, i).getContents());
                  // get helpURL
                  langCols.add( sheet.getCell((j*4)+4,i).getContents());
              }
              // TODO - Check whether this works for multiple languages
              schedule.
              	append(concept).append("\t").
              	append(varName).append("\t").
              	append(externalName).append("\t").
              	append(relevance).append("\t").
              	append(actionType);
              	
              for( int k = 0;k <langCols.size();k++){
                  schedule.append("\t").append(langCols.get(k));
              }
              schedule.append("\n");
          }
      }// end for i loop
      workbook.close();
      
      this.contents = schedule.toString();
      return true;
      
	  } catch(Exception e){
	  	logger.error("", e);
	  }
	  return false;
  }
  
  boolean writeFile(String filename) {
		if (filename == null || "".equals(filename.trim()))
			return false;
			
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename),"UTF-16"));
			out.write(this.contents);
			out.close();
			return true;
		}
		catch (Exception e) {
			logger.error(filename, e);
			return false;
		}
  }
  
  public String getContents() {
  	return this.contents;
  }
  
  public String getTitle() {
  	return this.title;
  }
  
  public String getFormattedContents() {
  	StringBuffer sf = new StringBuffer();
  	
  	sf.append("<TABLE BORDER='1'><TR>");
  	for (int i=0;i<this.numCols;++i) {
  		sf.append("<TH>Column ").append(i+1).append("</TH>");
  	}
  	sf.append("</TR>");
  	
  	// iterate over rows, then columns
		StringTokenizer rows = new StringTokenizer(this.contents,"\n");
		
		while (rows.hasMoreTokens()) {
			sf.append("<TR>");
			StringTokenizer cols = new StringTokenizer(rows.nextToken(),"\t");
			int colCount = 0;
			while (cols.hasMoreTokens()) {
				++colCount;
				sf.append("<TD>");
				String col = cols.nextToken();
				if ("".equals(col.trim())) {
					sf.append("&nbsp;");
				}
				else {
					sf.append(col);
				}
			}
			while (colCount < this.numCols) {
				++colCount;
				sf.append("<TD>&nbsp;</TD>");
			}
			sf.append("</TR>");
		}
		sf.append("</TABLE>");
		return sf.toString();
  }
  
  public boolean getStatus() {
  	return this.status;
  }
}
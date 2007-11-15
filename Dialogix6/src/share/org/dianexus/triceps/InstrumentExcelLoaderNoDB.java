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
public class InstrumentExcelLoaderNoDB implements java.io.Serializable {

    static Logger logger = Logger.getLogger(InstrumentExcelLoaderNoDB.class);
    private static int UseCounter = 0;
    private static final String DIALOGIX_SCHEDULES_DIR = "@@DIALOGIX.SCHEDULES.DIR@@";
    private StringBuffer instrumentAsText = null;
    private int numCols = 0;
    private int numRows = 0;
    private int nulLanguages = 0;
    private int majorVersion = 1;
    private int minorVersion = 0;
    private int numLanguages = 0;
    private boolean status = false;
    private String title = null;
    private String outputFilename = null;

    /**
    Upload instrument
    @param filename The absolute path of the filename
    @return	true if succeeds
     */
    public InstrumentExcelLoaderNoDB() {
    }

    /**
    This is the main method for loading an Excel file
    @param filename  the full name of the Excel file
    @return true if everything succeeds
     */
    public boolean loadInstrument(String filename) {
        if (filename == null || "".equals(filename.trim())) {
            this.status = false;
        }

        String justFileName = filename.substring(filename.lastIndexOf(File.separatorChar) + 1);

        logger.debug("Importing '" + justFileName + "' from '" + filename + "'");

        Workbook workbook = retrieveExcelWorkbook(filename);
        if (workbook != null && processWorkbook(workbook)) {
            outputFilename = InstrumentExcelLoaderNoDB.DIALOGIX_SCHEDULES_DIR + justFileName + "_" + ++InstrumentExcelLoaderNoDB.UseCounter + ".txt";
            this.status = writeFile(outputFilename);
        } else {
            this.status = false;
        }
        return this.status;
    }

    /**
    Load the Excel file into the Java Workbook structures as needed for further processing
    @param  filename - the full name of the Excel file
    @return the Workbook
     */
    Workbook retrieveExcelWorkbook(String filename) {
        try {
            Workbook workbook = Workbook.getWorkbook(new File(filename));
            return workbook;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /* Process and  Excel file, doing the following:
    (1) Save it as  Unicode .txt to the target directory
    @param workbook the loaded Excel file
    @return true if everything succeeds (FIXME - need partial solution - e.g. just load to directory without doing all database calls)
     */
    boolean processWorkbook(Workbook workbook) {
        try {
            instrumentAsText = new StringBuffer();

            Sheet sheet = workbook.getSheet(0);

            this.numCols = sheet.getColumns();
            this.numRows = sheet.getRows();

            // process rows one at a time
            for (int i = 0; i < numRows; i++) {
                //process cols
                Cell cell = sheet.getCell(0, i);
                // check to see if it is a header row
                // if it is we need to get the languages title and sched versions from the appropriate lines
                if (cell.getContents().equals("RESERVED")) {
                    Cell reservedName = sheet.getCell(1, i);
                    Cell reservedValue = sheet.getCell(2, i);
                    instrumentAsText.append(cell.getContents() + "\t" + reservedName.getContents() + "\t" + reservedValue.getContents() + "\n");
                    // check for number of languages
                    if (reservedName.getContents().equals("__LANGUAGES__")) {
                        StringTokenizer st = new StringTokenizer(reservedValue.getContents(), "|");
                        numLanguages = st.countTokens();
                    } else if (reservedName.getContents().equals("__TITLE__")) {
                        this.title = reservedValue.getContents();
                    } else if (reservedName.getContents().equals("__SCHED_VERSION_MAJOR__")) {
                        this.majorVersion = new Integer(reservedValue.getContents()).intValue();
                    } else if (reservedName.getContents().equals("__SCHED_VERSION_MINOR__")) {
                        this.minorVersion = new Integer(reservedValue.getContents()).intValue();
                    }
                } else if (cell.getContents().startsWith("COMMENT")) {
                    instrumentAsText.append(cell.getContents());
                    for (int m = 1; m < numCols; m++) {
                        Cell myCell = sheet.getCell(m, i);
                        instrumentAsText.append("\t").append(myCell.getContents());
                    }
                    instrumentAsText.append("\n");
                    // otherwise it is a data row. Extract the data elements from the spreadsheet and build the text file
                } else {
                    String conceptString = sheet.getCell(0, i).getContents();
                    String varNameString = sheet.getCell(1, i).getContents();
                    String displayNameString = sheet.getCell(2, i).getContents();
                    String relevanceString = sheet.getCell(3, i).getContents();
                    String actionTypeString = sheet.getCell(4, i).getContents();

                    // if the number of languages is more than one there will be 4 more columns per language to process
                    // cycle through for the number of languages
                    // There may be more lanauges listed than actual langauges entered - handle this gracefully
                    ArrayList<String> langCols = new ArrayList<String>();
                    for (int j = 1; j <= numLanguages; j++) {
                        String readbackString = "";
                        String questionString = "";
                        String responseOptions = "";
                        String helpString = "";

                        if (numCols > (j * 4) + 1) {
                            readbackString = sheet.getCell((j * 4) + 1, i).getContents(); // is this used in model?
                        }
                        if (numCols > (j * 4) + 2) {
                            questionString = sheet.getCell((j * 4) + 2, i).getContents(); // action - questionString or evaluation
                        }
                        if (numCols > (j * 4) + 3) {
                            responseOptions = sheet.getCell((j * 4) + 3, i).getContents(); // this gets parsed into dataType, displayType, and AnswerLis
                        }
                        if (numCols > (j * 4) + 4) {
                            helpString = sheet.getCell((j * 4) + 4, i).getContents();
                        }

                        // Save them to flat file
                        langCols.add(readbackString);
                        langCols.add(questionString);
                        langCols.add(responseOptions);
                        langCols.add(helpString);
                    }
                    // TODO - Check whether this works for multiple languages
                    instrumentAsText.append(conceptString).append("\t").append(varNameString).append("\t").append(displayNameString).append("\t").append(relevanceString).append("\t").append(actionTypeString);

                    for (int k = 0; k < langCols.size(); k++) {
                        instrumentAsText.append("\t").append(langCols.get(k));
                    }
                    instrumentAsText.append("\n");
                }
            } // end for i loop
            workbook.close();

            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    boolean writeFile(String filename) {
        if (filename == null || "".equals(filename.trim())) {
            return false;
        }

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-16"));
            out.write(instrumentAsText.toString());
            out.close();
            return true;
        } catch (Exception e) {
            logger.error(filename, e);
            return false;
        }
    }

    public String getContents() {
        return this.instrumentAsText.toString();
    }

    public String getTitle() {
        return this.title;
    }

    public String getLaunchCommand() {
        if (getStatus() == false) {
            return "";
        }

        return "servlet/Dialogix?schedule=" + outputFilename + "&DIRECTIVE=START";
    }

    public boolean getStatus() {
        return this.status;
    }
}
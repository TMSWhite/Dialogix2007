/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dianexus.triceps;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.*;
import org.dialogix.entities.*;

import org.apache.log4j.Logger;

/**
 *
 * @author George
 */
public class EntityFileWriter {

    private static Logger logger = Logger.getLogger(EntityFileWriter.class);
    InstrumentSession instrumentSession = null;
    DataElement dataElement = null;
    private ArrayList<InstrumentSession> instrumentSessions = null;
    private ArrayList<DataElement> dataElements = null;
    StringBuffer sb = new StringBuffer();
    private  String OBR  = "OBR|4|CE|";
    private  String OBX3 = "OBX|3|CE|";
    private  String OBX5 = "OBK|5|CE|";
    
    private static final String DIALOGIX_SCHEDULES_DIR = "/xampp/htdocs/Dialogix5/";

    public void processHL7Segments(InstrumentSession instrumentSession) {

        Iterator<DataElement> iterator = instrumentSession.getDataElementCollection().iterator();
        dataElements = new ArrayList<DataElement>();
        while (iterator.hasNext()) {
            dataElement = iterator.next();
            if (!dataElement.getAnswerString().equals("*UNASKED*")) {
              sb.append("OBX||CE|");
              sb.append(dataElement.getInstrumentContentID().getItemID().getItemID()).append("^").
                      append(dataElement.getQuestionAsAsked()).append("^L^");
              sb.append(dataElement.getInstrumentContentID().getVarNameID().getVarName()).append("^").
                      append(dataElement.getQuestionAsAsked()).append("^L");
              sb.append("||");
              
              String actualAnswer = null;
              
              if (dataElement.getAnswerID() == null) {
                  sb.append(dataElement.getAnswerString()).append("|*\n");
              }
              else {
                  Iterator<AnswerListContent> it = dataElement.getInstrumentContentID().getItemID().getAnswerListID().getAnswerListContentCollection().iterator();
                  Answer answer = null;
                  while (it.hasNext()) {
                      AnswerListContent answerListContent = it.next();
                      if (answerListContent.getValue().equals(dataElement.getAnswerString())) {
                          answer = answerListContent.getAnswerID();
                          break;
                      }
                  }
                  if (answer != null) {
                    Iterator<AnswerLocalized> it2 = answer.getAnswerLocalizedCollection().iterator();
                    while (it2.hasNext()) {
                        AnswerLocalized al = it2.next();
                        if (al.getLanguageCode().equals("en")) {
                            actualAnswer = al.getAnswerString();
                        }
                    }
                  }
                  sb.append(dataElement.getAnswerID()).append("^").append(actualAnswer).append("^L^");
                  sb.append(dataElement.getAnswerString()).append("^").append(actualAnswer).append("^L|*\n");
              }
            }
        }
    }

    public void processCCDElements(InstrumentSession instrumentSession) {

        Iterator<DataElement> iterator = instrumentSession.getDataElementCollection().iterator();
        dataElements = new ArrayList<DataElement>();
        while (iterator.hasNext()) {
            dataElement = iterator.next();
        //dataElement
        }
    }
    
    public Boolean writeHL7ToFile(Integer id) {
        String HL7File = DIALOGIX_SCHEDULES_DIR + "HL7_25_Output_Session_" + id + ".htm";
               
        try {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(HL7File), "UTF-8"));
            out.write("<html><head><meta content='text/html; charset=UTF-8'><body>");
            out.write(sb.toString());
            out.write("</body></html>");
            out.close();
        } catch (Exception e) {
            logger.error(HL7File, e);
            return false;
        }
        return true;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.hl7;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.*;
import org.dialogix.entities.*;

import java.util.logging.*;

/**
 *
 * @author George
 */
public class EntityFileWriter implements java.io.Serializable {
/* FIXME - needs to use new naming from Data_Element to Item_Usage
    private Logger logger = Logger.getLogger("org.dialogix.hl7.EntityFileWriter");
    InstrumentSession instrumentSession = null;
    DataElement dataElement = null;
    private ArrayList<InstrumentSession> instrumentSessions = null;
    private ArrayList<DataElement> dataElements = null;
    StringBuffer sb = new StringBuffer();
    private static final String DIALOGIX_SCHEDULES_DIR = "/xampp/htdocs/Dialogix5/";

    public void processHL7Segments(InstrumentSession instrumentSession) {

        Iterator<DataElement> iterator = instrumentSession.getDataElementCollection().iterator();
        dataElements = new ArrayList<DataElement>();
        while (iterator.hasNext()) {
            dataElement = iterator.next();
            if (!dataElement.getAnswerString().equals("*UNASKED*")) {
                sb.append("OBX||CE|");
                sb.append(dataElement.getInstrumentContentId().getItemId().getItemId()).append("^").
                    append(dataElement.getQuestionAsAsked()).append("^L^");
                sb.append(dataElement.getInstrumentContentId().getVarNameId().getVarName()).append("^").
                    append(dataElement.getQuestionAsAsked()).append("^L");
                sb.append("||");

                String actualAnswer = null;

                if (dataElement.getAnswerId() == null) {
                    sb.append(dataElement.getAnswerString()).append("|*\n");
                } else {
                    Iterator<AnswerListContent> it = dataElement.getInstrumentContentId().getItemId().getAnswerListId().getAnswerListContentCollection().iterator();
                    Answer answer = null;
                    while (it.hasNext()) {
                        AnswerListContent answerListContent = it.next();
                        if (answerListContent.getAnswerCode().equals(dataElement.getAnswerCode())) {
                            answer = answerListContent.getAnswerId();
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
                    sb.append(dataElement.getAnswerId()).append("^").append(actualAnswer).append("^L^");
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
            out.write("<html><META http-equiv='Content-Type' content='text/html; charset=utf-8'><head><title>HL7 2.5 Message for Session " + id);
            out.write("</title></head><body><pre>");
            out.write(sb.toString());
            out.write("</pre></body></html>");
            out.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, HL7File, e);
            return false;
        }
        return true;
    }
 */
}

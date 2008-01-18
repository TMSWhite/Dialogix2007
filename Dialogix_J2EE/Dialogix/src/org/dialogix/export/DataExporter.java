/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.export;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.*;
import org.dialogix.entities.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.dialogix.session.DialogixEntitiesFacadeLocal;

/**
 *
 * @author Coevtmw
 */
public class DataExporter implements java.io.Serializable {

    private static Logger logger = Logger.getLogger("org.dialogix.export.DataExporter");
    private DialogixEntitiesFacadeLocal dialogixEntitiesFacade = null;
    private boolean initialized = false;
    private InstrumentVersion instrumentVersion = null;
    private String languageCode = "en";

    private StringBuffer spssImportFile = new StringBuffer("No File Found");
    
    public DataExporter() {
        lookupDialogixEntitiesFacadeLocal();
    }
    
    public void setInstrumentVersionID(String instrumentVersionID) {
        try {
            lookupDialogixEntitiesFacadeLocal();
            Long id = Long.parseLong(instrumentVersionID);
            instrumentVersion = dialogixEntitiesFacade.getInstrumentVersion(id);
            if (instrumentVersion == null) {
                throw new Exception("Unable to find Instrument #" + instrumentVersionID);
            }
            init();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected Error " + e);            
        }
    }

    public DataExporter(String instrumentTitle,
                         String major_version,
                         String minor_version) {
        try {
            lookupDialogixEntitiesFacadeLocal();

            //	handle error if versions not found
            if (major_version == null || major_version.trim().length() == 0) {
                major_version = "0";
            }
            if (minor_version == null || minor_version.trim().length() == 0) {
                minor_version = "0";
            }

            instrumentVersion = dialogixEntitiesFacade.getInstrumentVersion(instrumentTitle, major_version, minor_version);
            if (instrumentVersion == null) {
                throw new Exception("Unable to find Instrument " + instrumentTitle + "(" + major_version + "." + minor_version + ")");
            }
            init();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected Error" + e);
        }
    }
    
    private void init() {
        generateSPSSimportFile();
        initialized = true;
    }
    
    private void lookupDialogixEntitiesFacadeLocal() {
        try {
            Context c = new InitialContext();
            dialogixEntitiesFacade =
                (DialogixEntitiesFacadeLocal) c.lookup("java:comp/env/DialogixEntitiesFacade_ejbref");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }
    }
    

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }    
    
    private void generateSPSSimportFile() {
        if (instrumentVersion == null) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        StringBuffer labels = new StringBuffer();
        
        // FIXME - add title indicating for which Instrument and Version this is, plus any additional filtering parameters
        String filename = "???.tsv";    // FIXME
        
        sb.append("GET DATA /TYPE = TXT\n");
        sb.append("/FILE = '").append(filename).append("'\n");
        sb.append("/DELCASE = LINE\n");
        sb.append("/DELIMITERS = \"\\t\"\n");
        sb.append("/ARRANGEMENT = DELIMITED\n");
        sb.append("/FIRSTCASE = 2\n");
        sb.append("/IMPORTCASE = ALL\n");
        sb.append("/VARIABLES =\n");
        
        /* List variables in desired sort order with this syntax:  
         * VarName SPSSformat
         */
        /* Will want to filter InstrumentContents - for now just use all */
        Iterator<InstrumentContent> instrumentContentIterator = instrumentVersion.getInstrumentContentCollection().iterator();
        
        while (instrumentContentIterator.hasNext()) {
            InstrumentContent instrumentContent = instrumentContentIterator.next();
            
            if (instrumentContent.getIsMessage() == 1) {
                continue;   // FIXME - should be boolean?
            }
            
            String varName = instrumentContent.getVarNameID().getVarName();
            Item item = instrumentContent.getItemID();
            
            String question = "";
            Iterator<QuestionLocalized> questionLocalizedIterator = item.getQuestionID().getQuestionLocalizedCollection().iterator();
            while (questionLocalizedIterator.hasNext()) {
                QuestionLocalized questionLocalized = questionLocalizedIterator.next();
                if (questionLocalized.getLanguageCode().equals(languageCode)) {
                    question = questionLocalized.getQuestionString();
                    question = question.replaceAll("\"", "'");
                }
            }
            
            sb.append("  ").append(varName).append(" ").append(instrumentContent.getSPSSFormat()).append("\n");
            
            /* Set Variable Levels
                VARIABLE LABELS ALC219
                    "[Alc219] In the past 12 months, have you often been under the effects of alcohol or suffering its after effects while at work or school or while taking acare of children?".
             */
            labels.append("VARIABLE LABELS ").append(varName).append("\n");
            labels.append("  \"[").append(varName).append("] ").append(question).append("\".\n");

            // Iterate over value set, if has one 
            /*
                VALUE LABELS ALC219
                    99999  "*NA*"
                    44444  "*UNASKED*"
                    1 "[1] Yes"
                    2 "[2] No"
                    .
             */
            /* FIXME - add missing value labels as needed */
            /* FIXME - fix the output strings to remove spaces and quotes */
            AnswerList answerList = item.getAnswerListID();
            if (answerList != null) {
                labels.append("VALUE LABELS ").append(varName).append("\n");
                Iterator<AnswerListContent> answerListContentIterator = answerList.getAnswerListContentCollection().iterator();
                while (answerListContentIterator.hasNext()) {
                    AnswerListContent answerListContent = answerListContentIterator.next();
                    String value = answerListContent.getAnswerCode();
                    Answer answer = answerListContent.getAnswerID();
                    String msg = "";
                    Iterator<AnswerLocalized> answerLocalizeds = answer.getAnswerLocalizedCollection().iterator();
                    while (answerLocalizeds.hasNext()) {
                        AnswerLocalized answerLocalized = answerLocalizeds.next();
                        if (answerLocalized.getLanguageCode().equals(languageCode)) {
                            msg = answerLocalized.getAnswerString();
                        }
                    }
                    msg = "[" + value + "] " + msg;
                    value = value.replaceAll("'", "\"");
                    msg = msg.replaceAll("\"", "'");

                    labels.append("  ").append(value).append(" \"").append(msg).append("\"\n");
                }
                labels.append(".\n");
            }
            /* Set SPSS Level Type:
               VARIABLE LEVEL ALC219 (NOMINAL).
             */
            labels.append("VARIABLE LEVEL ").append(varName).append(" (").append(instrumentContent.getSPSSLevel()).append(").\n");
            
            /* Set SPSS Format type:
                FORMATS ALC219 (F8.0).
             */
            labels.append("FORMATS ").append(varName).append(" (").append(instrumentContent.getSPSSFormat()).append(").\n\n");
            /* Set SPSS Missing Values - FIXME - need to know desired mapping of missing values to internal codes; and whether numeric or string *
                MISSING VALUES ALC219 (99999,44444).
             */            
        }
        sb.append(".\n\n");
        
        /* FIXME: Should there be  syntax to let users  customize the naming scheme? */
        /* FIXME: Users need to be able to set the internal coded value for missing values */
        
        spssImportFile = new StringBuffer();
        spssImportFile.append(sb);
        spssImportFile.append(labels);
    }

    public String getSpssImportFile() {
        return spssImportFile.toString();
    }
    
    public ArrayList<InstrumentVersionView> getInstrumentVersions() {
        ArrayList<InstrumentVersionView> instrumentVersionViewList = new ArrayList<InstrumentVersionView> ();
        Iterator<InstrumentVersion> instrumentVersionIterator = dialogixEntitiesFacade.getInstrumentVersionCollection().iterator();
        
        while (instrumentVersionIterator.hasNext()) {
            InstrumentVersion _instrumentVersion = instrumentVersionIterator.next();
            Instrument _instrument = _instrumentVersion.getInstrumentID();
            String instrumentName = _instrument.getInstrumentName() + " (" + _instrumentVersion.getVersionString() + ")[" + _instrumentVersion.getInstrumentVersionID() + "]";
            instrumentVersionViewList.add(new InstrumentVersionView(instrumentName, _instrumentVersion.getInstrumentVersionID()));
        }
        return instrumentVersionViewList;
    }
}

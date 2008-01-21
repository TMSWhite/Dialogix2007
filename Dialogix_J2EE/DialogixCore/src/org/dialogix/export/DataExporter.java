/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.export;

import org.dialogix.beans.InstrumentVersionView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.*;
import org.dialogix.entities.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.dialogix.beans.InstrumentSessionResultBean;
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
    private String instrumentTitle = "unknown";
    
    // Fields needed from input form
    private Boolean sas_script=true;
    private Boolean spss_script=true;
    private String spss_unasked="44444";
    private String sas_unasked=".";
    private String spss_na="99999";
    private String sas_na=".";
    private String spss_refused="55555";
    private String sas_refused=".";
    private String spss_unknown="33333";
    private String sas_unknown=".";
    private String spss_huh="22222";
    private String sas_huh=".";
    private String spss_invalid="11111";
    private String sas_invalid=".";
    private String sort_order="sort_order_asked";
    private String exclude_regex="";
    private Boolean value_labels=true;
    private Boolean variable_labels=true;
    private Boolean frequency_distributions=true;
    private Boolean extract_data=true;

    private static int UNASKED = 1;
    private static int NA = 2;
    private static int REFUSED = 3;
    private static int INVALID = 4;
    private static int UNKNOWN = 5;
    private static int HUH = 6;
    private String[] spssNullFlavors = new String[7];
    private String[] sasNullFlavors = new String[7];

    private StringBuffer spssImportFile = new StringBuffer("No File Found");
    private String spss_missing_value_labels="";
    private String spss_missing_values_list="";;
    private ArrayList<String> varNames = new ArrayList<String>();   // list of variables which pass filter criteria  - do this as first pass  before searching data
    private ArrayList<Long> varNameIDs = new ArrayList<Long>(); // list of VarNameIDs for sub-select query
    private HashMap<String,String> varNameFormat = new HashMap<String,String>();
    private List<InstrumentSessionResultBean> instrumentSessionResultBeans;
    private String transposedInstrumentSesionResults;
    
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

    private void filterVarNames() {
        varNames = new ArrayList<String>();
        varNameIDs = new ArrayList<Long>();
        // FIXME - seems that this is coming out in the wrong order - thought would be order-asked, but not appearing that way
        ArrayList<InstrumentContent> instrumentContentCollection = new ArrayList(instrumentVersion.getInstrumentContentCollection());
        Collections.sort(instrumentContentCollection, new InstrumentContentsComparator());
        Iterator<InstrumentContent> instrumentContentIterator = instrumentContentCollection.iterator();
        
        while (instrumentContentIterator.hasNext()) {
            InstrumentContent instrumentContent = instrumentContentIterator.next();
            
            if (instrumentContent.getIsMessage() == 1) {
                continue;   // FIXME - should be boolean?
            }
            VarName varName = instrumentContent.getVarNameID();
            String varNameString = varName.getVarName();
            
            if (exclude_regex.trim().length() > 0) {
                if (varNameString.matches(exclude_regex)) {
                    continue;
                }
            } 
            varNames.add(varNameString);
            varNameIDs.add(varName.getVarNameID());
        }
        if (sort_order.equals("sort_varname")) {
            Collections.sort(varNames);        // NOTE - this works fine, unlike default order
        }
        logger.log(Level.SEVERE,varNames.toString());
        logger.log(Level.SEVERE,"VarName.size()=" + varNames.size());
    }

    private void generateSASimportFile() {
        // FIXME
//        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private void init() {
        if (instrumentVersion == null) {
            return;
        }        
        instrumentTitle = instrumentVersion.getInstrumentID().getInstrumentName() + " (" + instrumentVersion.getVersionString() + ")[" + instrumentVersion.getInstrumentVersionID() + "]";
        configure();
        filterVarNames();
        if (spss_script == true) {
            generateSPSSimportFile();
        }
        if (sas_script == true) {
            generateSASimportFile();
        }
        if (extract_data == true) {
            findInstrumentSessionResults();            
            transposeInstrumentSessionResultsToTable();
        }
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
        StringBuffer sb = new StringBuffer();
        StringBuffer labels = new StringBuffer();
        
        // Output title showing which parameters were set
        sb.append("/*\n");
        sb.append("Import file for ").append(instrumentTitle).append("\n");
        sb.append("Options:\n");
        sb.append("  Missing Value Mappings:\n");
        sb.append("    Unasked=").append(spss_unasked).append("\n");
        sb.append("    N/A=").append(spss_na).append("\n");
        sb.append("    Refused=").append(spss_refused).append("\n");
        sb.append("    Unknown=").append(spss_unknown).append("\n");
        sb.append("    Not Understood=").append(spss_huh).append("\n");
        sb.append("    Invalid=").append(spss_invalid).append("\n");
        sb.append("  Output Options:\n");
        sb.append("    Sort Variables by=").append(sort_order).append("\n");
        sb.append("    Exclude Variables matching=").append(exclude_regex).append("\n");
        sb.append("    Value Labels=").append(value_labels).append("\n");
        sb.append("    Variable Labels=").append(variable_labels).append("\n");
        sb.append("    Frequency Distribution=").append(frequency_distributions).append("\n");
        sb.append("    Language Code=").append(languageCode).append("\n");
        sb.append("    Generate Data=").append(extract_data).append("\n");
        sb.append("*/\n\n");
        
        // This is the content SPSS needs
        
        sb.append("GET DATA /TYPE = TXT\n");
        sb.append("/FILE = '").append(instrumentTitle).append(".tsv'\n");
        sb.append("/DELCASE = LINE\n");
        sb.append("/DELIMITERS = \"\\t\"\n");
        sb.append("/ARRANGEMENT = DELIMITED\n");
        sb.append("/FIRSTCASE = 2\n");  // FIXME - does Perl output variable names in first row?
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
            
            if (exclude_regex.trim().length() > 0) {
                if (varName.matches(exclude_regex)) {
                    continue;
                }
            }
            
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
            
            // Store mapping of variable name to SPSS format statement in HashMap so can sort it
            if (sort_order.equals("sort_varname")) {
                varNameFormat.put(varName,instrumentContent.getSPSSFormat());
            }
            else {
                // they are already sorted by order asked, so do it here
                sb.append("  ").append(varName).append(" ").append(instrumentContent.getSPSSFormat()).append("\n");                
            }
            
            /* Set Variable Levels
                VARIABLE LABELS ALC219
                    "[Alc219] In the past 12 months, have you often been under the effects of alcohol or suffering its after effects while at work or school or while taking acare of children?".
             */
            if (variable_labels == true) {
                labels.append("VARIABLE LABELS ").append(varName).append("\n");
                labels.append("  \"[").append(varName).append("] ").append(question).append("\".\n");
            }

            // Iterate over value set, if has one 
            /*
                VALUE LABELS ALC219
                    99999  "*NA*"
                    44444  "*UNASKED*"
                    1 "[1] Yes"
                    2 "[2] No"
                    .
             */
            if (value_labels == true) {
                AnswerList answerList = item.getAnswerListID();
                if (answerList != null) {
                    labels.append("VALUE LABELS ").append(varName).append("\n");
                    labels.append(spss_missing_value_labels);
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
            }
            /* Set SPSS Level Type:
               VARIABLE LEVEL ALC219 (NOMINAL).
             */
            labels.append("VARIABLE LEVEL ").append(varName).append(" (").append(instrumentContent.getSPSSLevel()).append(").\n");
            
            /* Set SPSS Format type:
                FORMATS ALC219 (F8.0).
             */
            labels.append("FORMATS ").append(varName).append(" (").append(instrumentContent.getSPSSFormat()).append(").\n");
            /* Set SPSS Missing Values - FIXME - need to know desired mapping of missing values to internal codes; and whether numeric or string *
                MISSING VALUES ALC219 (99999,44444).
             */
            if (spss_missing_values_list.trim().length() > 0) {
                labels.append("MISSING VALUES ").append(varName).append(" ").append(spss_missing_values_list).append(".\n");
            }
            labels.append("\n");
        }
        // Now sort the list of variables
        if (sort_order.equals("sort_varname")) {
            for (int i=0;i<varNames.size();++i) {
                String varName = varNames.get(i);
                sb.append(" ").append(varName).append(" ").append(varNameFormat.get(varName)).append("\n");
            }
        }
        
        sb.append(".\n\n");
        
        /* FIXME: Should there be  syntax to let users  customize the naming scheme? */
        /* FIXME: Users need to be able to set the internal coded value for missing values */
        
        spssImportFile = new StringBuffer();
        spssImportFile.append(sb);
        spssImportFile.append(labels);
    }
    
    private void configure() {
        /* Set value labels for SPSS */
        StringBuffer sb = new StringBuffer("");
        if (spss_unasked.trim().length() > 0) {
                    sb.append("  ").append(spss_unasked).append(" \"*UNASKED*\"\n");
        }
        if (spss_na.trim().length() > 0) {
                    sb.append("  ").append(spss_na).append(" \"*NA*\"\n");
        }
        if (spss_refused.trim().length() > 0) {
                    sb.append("  ").append(spss_refused).append(" \"*REFUSED*\"\n");
        }
        if (spss_unknown.trim().length() > 0) {
                    sb.append("  ").append(spss_unknown).append(" \"*UNKNOWN*\"\n");
        }
        if (spss_huh.trim().length() > 0) {
                    sb.append("  ").append(spss_huh).append(" \"*HUH*\"\n");
        }
        if (spss_invalid.trim().length() > 0) {
                    sb.append("  ").append(spss_invalid).append(" \"*INVALID*\"\n");
        }
        spss_missing_value_labels = sb.toString();
        
        /* Set missing values list for SPSS */
        ArrayList<String> missingValues = new ArrayList<String>();
        if (spss_unasked.trim().length() > 0) {
            missingValues.add(spss_unasked);
        }
        if (spss_na.trim().length() > 0) {
            missingValues.add(spss_na);
        }
        if (spss_refused.trim().length() > 0) {
            missingValues.add(spss_refused);
        }
        if (spss_unknown.trim().length() > 0) {
            missingValues.add(spss_unknown);
        }
        if (spss_huh.trim().length() > 0) {
            missingValues.add(spss_huh);
        }
        if (spss_invalid.trim().length() > 0) {
            missingValues.add(spss_invalid);
        }
        
        sb = new StringBuffer("");
        for (int i=0;i<missingValues.size();++i) {
            if (i == 0) {
                sb.append("(");
            }
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(missingValues.get(i));
        }
        sb.append(")");
        
        spss_missing_values_list = sb.toString();
        
        spssNullFlavors[HUH] = this.getSpss_huh();
        spssNullFlavors[INVALID] = this.getSpss_invalid();
        spssNullFlavors[NA] = this.getSpss_na();
        spssNullFlavors[REFUSED] = this.getSpss_refused();
        spssNullFlavors[UNASKED] = this.getSpss_unasked();
        spssNullFlavors[UNKNOWN] = this.getSpss_unknown();
        
        sasNullFlavors[HUH] = this.getSas_huh();
        sasNullFlavors[INVALID] = this.getSas_invalid();
        sasNullFlavors[NA] = this.getSas_na();
        sasNullFlavors[REFUSED] = this.getSas_refused();
        sasNullFlavors[UNASKED] = this.getSas_unasked();
        sasNullFlavors[UNKNOWN] = this.getSas_unknown();        
    }

    public String getSpssImportFile() {
        if (!spss_script) {
            return "";
        }        
        return spssImportFile.toString();
    }
    
//    public ArrayList<InstrumentVersionView> getInstrumentVersions() {
//        ArrayList<InstrumentVersionView> instrumentVersionViewList = new ArrayList<InstrumentVersionView> ();
//        Iterator<InstrumentVersion> instrumentVersionIterator = dialogixEntitiesFacade.getInstrumentVersionCollection().iterator();
//        
//        while (instrumentVersionIterator.hasNext()) {
//            InstrumentVersion _instrumentVersion = instrumentVersionIterator.next();
//            Instrument _instrument = _instrumentVersion.getInstrumentID();
//            String instrumentName = _instrument.getInstrumentName() + " (" + _instrumentVersion.getVersionString() + ")[" + _instrumentVersion.getInstrumentVersionID() + "]";
//            instrumentVersionViewList.add(new InstrumentVersionView(instrumentName, _instrumentVersion.getInstrumentVersionID()));
//        }
//        return instrumentVersionViewList;
//    }
    
    public List<InstrumentVersionView> getInstrumentVersions() {
        return dialogixEntitiesFacade.getInstrumentVersions();
    }
    
    public List<InstrumentSessionResultBean> getRawResults()  {
        return instrumentSessionResultBeans;
    }
    
    private void findInstrumentSessionResults() {
        try {
            if (instrumentVersion == null) {
                return;
            }
            String inVarNameIDs = null;
//            if (exclude_regex.length() > 0) {
//                /* Then filtering set of variables */
                StringBuffer sb = new StringBuffer("(");
                for (int i=0;i<varNameIDs.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(varNameIDs.get(i));
                }
                sb.append(")");
                inVarNameIDs = sb.toString();
//            }
            instrumentSessionResultBeans = dialogixEntitiesFacade.getFinalInstrumentSessionResults(instrumentVersion.getInstrumentVersionID(), inVarNameIDs, (sort_order.equals("sort_varname")));
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(), e);
        }
    }
    
    private void transposeInstrumentSessionResultsToTable() {
        StringBuffer sb = new StringBuffer();
        
        sb.append("<table border='1'>\n<tr>");
        for (int i=0;i<varNames.size();++i) {
            sb.append("<td>").append(varNames.get(i)).append("</td>");
        }
        sb.append("</tr>\n");
        
        Iterator<InstrumentSessionResultBean> isrbs = instrumentSessionResultBeans.iterator();
        int counter = 0;
        while (isrbs.hasNext()) {
            if (counter++ == 0) {
                sb.append("<tr>");
            }
            InstrumentSessionResultBean isrb = isrbs.next();
//            if (!varNames.contains(isrb.getVarNameString())) {
//                continue;
//            }
            sb.append("<td>");            
            if (isrb.getNullFlavorID() > 0) {
                sb.append(spssNullFlavors[isrb.getNullFlavorID()]);
            }
            else {
                String answerCode = isrb.getAnswerCode();
                if (answerCode == null) {
                    sb.append(spssNullFlavors[INVALID]);    // FIXME - is this correct behavior?
                }
                else {
                    sb.append(answerCode);
                }
            }
            sb.append("</td>");
            if (counter == varNames.size()) {
                counter = 0;
                sb.append("</tr>\n");
            }
        }
        sb.append("</table>\n");
        logger.log(Level.SEVERE,sb.toString());
        transposedInstrumentSesionResults = sb.toString();
    }
    
    private void transposeInstrumentSessionResults() {
        StringBuffer sb = new StringBuffer();
        
        for (int i=0;i<varNames.size();++i) {
            if (i > 0) {
                sb.append("\t");
            }
            sb.append(varNames.get(i));            
        }
        sb.append("\n");
        logger.log(Level.SEVERE,sb.toString());
        
        Iterator<InstrumentSessionResultBean> isrbs = instrumentSessionResultBeans.iterator();
        int counter = 0;
        while (isrbs.hasNext()) {
            InstrumentSessionResultBean isrb = isrbs.next();
            if (exclude_regex.length() > 0) {
                if (isrb.getVarNameString().matches(exclude_regex)) {
                    continue;
                }
            }
            if (++counter > 1) {
                sb.append("\t");
            }
            
            if (isrb.getNullFlavorID() > 0) {
                sb.append(spssNullFlavors[isrb.getNullFlavorID()]);
            }
            else {
                String answerCode = isrb.getAnswerCode();
                if (answerCode == null) {
                    sb.append(spssNullFlavors[INVALID]);    // FIXME - is this correct behavior?
                }
                else {
                    sb.append(isrb.getAnswerCode());
                }
            }
            if (counter == varNames.size()) {
                counter = 0;
                sb.append("<br>\n");
            }
        }
        sb.append("\n");
        logger.log(Level.SEVERE,sb.toString());
        transposedInstrumentSesionResults = sb.toString();
    }
    
    public void setExclude_regex(String exclude_regex) {
        this.exclude_regex = exclude_regex;
    }

    public void setFrequency_distributions(String frequency_distributions) {
        this.frequency_distributions = ("1".equals(frequency_distributions));
    }

    public void setSas_huh(String sas_huh) {
        this.sas_huh = sas_huh;
    }

    public void setSas_invalid(String sas_invalid) {
        this.sas_invalid = sas_invalid;
    }

    public void setSas_na(String sas_na) {
        this.sas_na = sas_na;
    }

    public void setSas_refused(String sas_refused) {
        this.sas_refused = sas_refused;
    }

    public void setSas_script(String sas_script) {
        this.sas_script = ("1".equals(sas_script));
    }

    public void setSas_unasked(String sas_unasked) {
        this.sas_unasked = sas_unasked;
    }

    public void setSas_unknown(String sas_unknown) {
        this.sas_unknown = sas_unknown;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public void setSpss_huh(String spss_huh) {
        this.spss_huh = spss_huh;
    }

    public void setSpss_invalid(String spss_invalid) {
        this.spss_invalid = spss_invalid;
    }

    public void setSpss_na(String spss_na) {
        this.spss_na = spss_na;
    }

    public void setSpss_refused(String spss_refused) {
        this.spss_refused = spss_refused;
    }

    public void setSpss_script(String spss_script) {
        this.spss_script = ("1".equals(spss_script));
    }

    public void setSpss_unasked(String spss_unasked) {
        this.spss_unasked = spss_unasked;
    }

    public void setSpss_unknown(String spss_unknown) {
        this.spss_unknown = spss_unknown;
    }

    public void setValue_labels(String value_labels) {
        this.value_labels = ("1".equals(value_labels));
    }

    public void setVariable_labels(String variable_labels) {
        this.variable_labels = ("1".equals(variable_labels));
    }
    
    public String getExclude_regex() {
        return exclude_regex;
    }

    public String getFrequency_distributions() {
        return (frequency_distributions == true) ? "1": "";
    }

    public String getInstrumentTitle() {
        return instrumentTitle;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getSas_huh() {
        return sas_huh;
    }

    public String getSas_invalid() {
        return sas_invalid;
    }

    public String getSas_na() {
        return sas_na;
    }

    public String getSas_refused() {
        return sas_refused;
    }

    public String getSas_script() {
        return (sas_script == true) ? "1" : "";
    }

    public String getSas_unasked() {
        return sas_unasked;
    }

    public String getSas_unknown() {
        return sas_unknown;
    }

    public String getSort_order() {
        return sort_order;
    }

    public String getSpss_huh() {
        return spss_huh;
    }

    public String getSpss_invalid() {
        return spss_invalid;
    }

    public String getSpss_missing_value_labels() {
        return spss_missing_value_labels;
    }

    public String getSpss_missing_values_list() {
        return spss_missing_values_list;
    }

    public String getSpss_na() {
        return spss_na;
    }

    public String getSpss_refused() {
        return spss_refused;
    }

    public String getSpss_script() {
        return (spss_script == true) ? "1" : "";        
    }

    public String getSpss_unasked() {
        return spss_unasked;
    }

    public String getSpss_unknown() {
        return spss_unknown;
    }

    public String getValue_labels() {
        return (value_labels == true) ? "1" : "";
    }

    public String getVariable_labels() {
        return (variable_labels == true) ? "1" : "";
    }    
    

    public String getTransposedInstrumentSesionResults() {
        return transposedInstrumentSesionResults;
    }
    
    public String getExtract_data() {
        return (extract_data == true) ? "1" : "";
    }

    public void setExtract_data(String extract_data) {
        this.extract_data = ("1".equals(extract_data));
    }    
}

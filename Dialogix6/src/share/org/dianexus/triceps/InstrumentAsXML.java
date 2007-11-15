 /*
 * InstrumentAsXML.java
 * 
 * Created on Nov 6, 2007, 4:47:50 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dianexus.triceps;
import java.text.DecimalFormat;
import java.util.*;
import org.dialogix.entities.*;
import org.apache.log4j.Logger;
/**
    Parse an Instrument and create the encoding needed by Apelon.
 */
public class InstrumentAsXML {
    private static Logger logger = Logger.getLogger(InstrumentAsXML.class);
    private StringBuffer sb = null;
    private boolean status = false;
    private InstrumentVersion instrumentVersion = null;
    private String namespaceName = null;
    private String namespace = null;
    private String fromNamespace = null;
    private String toNamespace = null;
    private int proptypeCount = 0;
    private int assntypeCount = 0;
    private int qualtypeCount = 0;
    private int termCount = 0;
    private int conceptCount = 0;
    private int assnCount = 0;
    private int propCount = 0;
    private int qualCount = 0 ;
    private static DecimalFormat ThousandFormatter = new DecimalFormat("000");
    private static DecimalFormat HundredFormatter = new DecimalFormat("00");
    private String  namespaceCode = null;
    private static int UseCounter = 0;
    
    public InstrumentAsXML(String title, String versionString, String _namespace) {
        instrumentVersion = DialogixConstants.parseInstrumentVersion(title, versionString); // FIXME doesn't work - need a  find command
        if (instrumentVersion == null) {
            logger.error("Unable to find " + title + "(" + versionString + ")");
            return;
        }
        init(instrumentVersion, _namespace);
    }
    
    public InstrumentAsXML(InstrumentVersion instrumentVersion, String _namespace) {
        init(instrumentVersion, _namespace);
    }
    
    private void init(InstrumentVersion instrumentVersion, String _namespace) {
        // This assumes that full, nested contents of InstrumentVersion are now instantiated
        this.instrumentVersion = instrumentVersion;
        
        if (_namespace == null || _namespace.trim().equals("")) {
            this.namespaceName = "Instruments_" + this.UseCounter++;
        }
        else {
            this.namespaceName = _namespace;
        }
        
        this.namespace = "<namespace>" + namespaceName + "</namespace>";
        this.fromNamespace = "<from_namespace>" + namespaceName + "</from_namespace>";
        this.toNamespace = "<to_namespace>" + namespaceName + "</to_namespace>";
        this.namespaceCode = "Ndlx_" + UseCounter++;
        
        try {
            // iterate through contents
            // Note, must create leaf-level concepts before associating them with higher-level ones
            
            sb = new StringBuffer(this.getDTSheader());
            
            String instrumentName = "Inst[" + instrumentVersion.getInstrumentID().getInstrumentID() + "]";
            sb.append(term(instrumentName, "Root Property", "V-" + namespaceCode));
            
            String instrumentVersionName = instrumentName + "v[" + instrumentVersion.getVersionString() + "]";
            
            ArrayList<InstrumentContent> sortedInstrumentContents = new ArrayList<InstrumentContent>(instrumentVersion.getInstrumentContentCollection());
            Collections.sort(sortedInstrumentContents, new InstrumentContentSequenceComparator());
            Iterator<InstrumentContent> instrumentContents = sortedInstrumentContents.iterator();
            ArrayList<String> instrumentContentNames = new ArrayList<String>();
            while(instrumentContents.hasNext()) {
                InstrumentContent instrumentContent = instrumentContents.next();
                String instrumentContentName = instrumentVersionName + ".[" + ThousandFormatter.format(instrumentContent.getItemSequence())  + "]";   
                instrumentContentNames.add(instrumentContentName);
 
                Item item = instrumentContent.getItemID();
                String itemName = instrumentContentName + ".";
                
                Question question = item.getQuestionID();
                String questionName = itemName + "Q";      
                
                Iterator<QuestionLocalized> questions = question.getQuestionLocalizedCollection().iterator();
                ArrayList<String> questionLocalizedNames = new ArrayList<String>();
                while (questions.hasNext()) {
                    QuestionLocalized questionLocalized = questions.next();
                    String questionLocalizedName = questionName + "[" + questionLocalized.getLanguageCode() + "]";
                    questionLocalizedNames.add(questionLocalizedName);
                    
                    sb.append(concept(questionLocalizedName));
                    sb.append(property("QuestionLocalized_ID", questionLocalized.getQuestionLocalizedID()));
                    sb.append(property("LanguageCode", questionLocalized.getLanguageCode()));
                    sb.append(property("QuestionString",XMLAttrEncoder.encode(questionLocalized.getQuestionString())));
                    sb.append(conceptEnd());
                }
                
                // Now declare containing concepts
                sb.append(concept(questionName));
                sb.append(property("Question_ID", question.getQuestionID()));   
                for (int i=0;i<questionLocalizedNames.size();++i) {
                    sb.append(association("Parent Of", questionName, questionLocalizedNames.get(i)));
                }
                sb.append(conceptEnd());                   
                
                // give answerList or not, based upon Item type?
                
                AnswerList answerList = item.getAnswerListID();
                String answerListName = itemName + "A";
                if (answerList != null) {
	                ArrayList<AnswerListContent> sortedAnswerListContents = new ArrayList<AnswerListContent>(answerList.getAnswerListContentCollection());
	                Collections.sort(sortedAnswerListContents, new AnswerListContentSequenceComparator());
	                Iterator<AnswerListContent> answers = sortedAnswerListContents.iterator();
                    ArrayList<String> answerListContentNames = new ArrayList<String>();                    
	                while (answers.hasNext()) {
	                    AnswerListContent answerListContent = answers.next();
	                    String answerListContentName = answerListName + "[" + HundredFormatter.format(answerListContent.getAnswerOrder()) + "]";
	                    answerListContentNames.add(answerListContentName);
                        
	                    Answer answer = answerListContent.getAnswerID();
	                    String answerName = answerListContentName + ".";
	                    
	                    Iterator<AnswerLocalized> localizedAnswers = answer.getAnswerLocalizedCollection().iterator();
                        ArrayList<String> localizedAnswerNames = new ArrayList<String>();                    
                        while (localizedAnswers.hasNext()) {
	                        AnswerLocalized answerLocalized = localizedAnswers.next();
	                        String localizedAnswerName = answerName + "[" + answerLocalized.getLanguageCode() + "]";
	                        localizedAnswerNames.add(localizedAnswerName);
                            
	                        sb.append(concept(localizedAnswerName));
	                        sb.append(property("AnswerLocalized_ID", answerLocalized.getAnswerLocalizedID()));
	                        sb.append(property("LanguageCode", answerLocalized.getLanguageCode()));
	                        sb.append(property("AnswerString", XMLAttrEncoder.encode(answerLocalized.getAnswerString()))); 
	                        sb.append(conceptEnd());
	                    }
	                    
	                    sb.append(concept(answerName));
	                    sb.append(property("Answer_ID", answer.getAnswerID()));
	                    sb.append(property("hasLAcode", answer.getHasLAcode()));
	                    sb.append(property("LAcode", answer.getLAcode()));
                        for (int i=0;i<localizedAnswerNames.size();++i) {
                            sb.append(association("Parent Of", answerName, localizedAnswerNames.get(i)));
                        }                        
	                    sb.append(conceptEnd());        
	                    
	                    sb.append(concept(answerListContentName));
	                    sb.append(property("AnswerListContent_ID", answerListContent.getAnswerListContentID()));
	                    sb.append(property("AnswerOrder", answerListContent.getAnswerOrder()));
	                    sb.append(property("AnswerValue", answerListContent.getValue()));
	                    sb.append(association("Parent Of", answerListContentName, answerName));
	                    sb.append(conceptEnd());                                
	                }
	                
	                sb.append(concept(answerListName));
                    for (int i=0;i<answerListContentNames.size();++i) {
                        sb.append(association("Parent Of", answerListName, answerListContentNames.get(i)));
                    }                             
	                sb.append(conceptEnd());  
                }
               
                sb.append(concept(itemName));
                sb.append(property("Item_ID", item.getItemID()));
                sb.append(property("DataType", item.getDataTypeID().getDataType()));
                sb.append(property("hasLOINCcode", item.getHasLOINCcode()));    // CHECK - simply skip if absent?
                sb.append(property("LOINC_NUM", item.getLoincNum()));
                if (answerList != null) {
                    sb.append(association("Parent Of", itemName, answerListName));                
                }
                sb.append(conceptEnd());    

                
                sb.append(concept(instrumentContentName));
                sb.append(property("InstrumentContent_ID",instrumentContent.getInstrumentContentID()));
                sb.append(property("ItemVarName",instrumentContent.getVarNameID().getVarName()));
                sb.append(property("ItemSequence",instrumentContent.getItemSequence()));
                sb.append(property("ItemRelevance",XMLAttrEncoder.encode(instrumentContent.getRelevance()))); 
                sb.append(property("ItemType",instrumentContent.getItemActionType()));
                sb.append(association("Parent Of", instrumentContentName, itemName));                                   
                sb.append(conceptEnd());                  
            }
            // Now declare containing concepts
            
            sb.append(concept(instrumentVersionName));
            sb.append(property("InstrumentVersion_ID",instrumentVersion.getInstrumentVersionID()));
            sb.append(property("InstrumentVersion",XMLAttrEncoder.encode(instrumentVersion.getVersionString()))); 
            sb.append(property("hasLOINCcode",instrumentVersion.getHasLOINCcode()));    // CHECK - simply skip if absent?
            sb.append(property("LOINC_NUM",instrumentVersion.getLoincNum()));
            for (int i=0;i<instrumentContentNames.size();++i) {
                sb.append(association("Parent Of", instrumentVersionName,instrumentContentNames.get(i)));
            }
            sb.append(conceptEnd());
                        
            sb.append(concept(instrumentName));
            sb.append(property("Instrument_ID",instrumentVersion.getInstrumentID().getInstrumentID()));
            sb.append(property("InstrumentName",XMLAttrEncoder.encode(instrumentVersion.getInstrumentID().getInstrumentName())));  
            sb.append(property("InstrumentDescription",XMLAttrEncoder.encode(instrumentVersion.getInstrumentID().getInstrumentDescription()))); 
            sb.append(synonym("Synonym", instrumentName, true));
            sb.append(association("Parent Of", instrumentName, instrumentVersionName));
            sb.append(conceptEnd());
            
            sb.append(this.getDTSFooter());
            this.status = true;
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    private StringBuffer concept(String name) {
        StringBuffer sb0 = new StringBuffer("<concept>");
        sb0.append("<name>").append(name).append("</name>\n");
        return sb0;
    }
    
    private String conceptEnd() {
    	return "</concept>\n";
    }
    
    private  StringBuffer property(String name, Object value) {
        StringBuffer sb0 = new StringBuffer("  <");
        sb0.append(name).append(">").append(value).append("</").append(name).append(">\n");
        return sb0;
    }
    
    private StringBuffer association(String type, String fromString, String toString) {
        StringBuffer sb0 = new StringBuffer();
        sb0.append("<").append(type).append(">").append(toString).append("</").append(type).append(">\n");
        return sb0;
    }
    
    private StringBuffer term(String name, String property, String value) {
        return new StringBuffer();
    }
    
    private StringBuffer synonym(String name, String toName, boolean preferred) {
        return new StringBuffer();
    }
    
    public boolean getStatus() {
        return this.status;
    }
    
    public StringBuffer getNamespace() {
        return this.sb;
    }
    
    private StringBuffer getDTSheader() {
        StringBuffer sb0 = new StringBuffer();
        sb0.append("<?xml version='1.0' encoding='UTF-8' ?>\n");        
        return sb0;
    }
    
    private StringBuffer proptype(String name, String type, String size, boolean index) {
        StringBuffer sb0 = new StringBuffer();
        sb0.append("<proptype type='").append(type).append("' size='").append(size).append("' word='").append((index) ? "true" : "false").append("'>");
        sb0.append(namespace);
        sb0.append("<name>").append(name).append("</name>");
        sb0.append("<code>P").append(++proptypeCount).append("</code><id>").append(proptypeCount).append("</id></proptype>\n");
        return sb0;
    }
    
    private StringBuffer assntype(String name, String invName, String type, String purpose) {
        StringBuffer sb0 = new StringBuffer();
        sb0.append("<assntype type='").append(type).append("' purpose='").append(purpose).append("'>");
        sb0.append(namespace);
        sb0.append("<name>").append(name).append("</name>");
        sb0.append("<inverse_name>").append(invName).append("</inverse_name>");
        sb0.append("<code>A").append(++assntypeCount).append("</code><id>").append(assntypeCount).append("</id></assntype>\n");
        return sb0;
    }
    
    private StringBuffer qualtype(String name, String type) {
        StringBuffer sb0 = new StringBuffer();
        sb0.append("<qualtype type='").append(type).append("'>");
        sb0.append(namespace);
        sb0.append("<name>").append(name).append("</name>");
        sb0.append("<code>Q").append(++qualtypeCount).append("</code><id>").append(qualtypeCount).append("</id></qualtype>\n");
        return sb0;        
    }
    
    private String getDTSFooter() {
        return "</terminology>";
    }
}

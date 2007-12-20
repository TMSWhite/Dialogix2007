/*
 * ApelonDTSExporter.java
 * 
 * Created on Nov 6, 2007, 4:47:50 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dianexus.triceps;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;
import org.dialogix.entities.*;
import java.util.logging.*;

/**
Parse an Instrument and create the encoding needed by Apelon.
 */
public class ApelonDTSExporter {

    private static Logger logger = Logger.getLogger("org.dianexus.triceps.ApelonDTSExporter");
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
    private int qualCount = 0;
    private static DecimalFormat ThousandFormatter = new DecimalFormat("000");
    private static DecimalFormat HundredFormatter = new DecimalFormat("00");
    private String namespaceCode = null;
    private BigInteger namespaceID;

//    public ApelonDTSExporter(String title, String versionString, String _namespace) {
//        instrumentVersion = DialogixConstants.parseInstrumentVersion(title, versionString); // FIXME doesn't work - need a  find command
//        if (instrumentVersion == null) {
//            logger.log(Level.SEVERE,"Unable to find " + title + "(" + versionString + ")");
//            return;
//        }
//        init(instrumentVersion, _namespace);
//    }
    public ApelonDTSExporter(InstrumentVersion instrumentVersion, String _namespace) {
        init(instrumentVersion, _namespace);
    }

    private void init(InstrumentVersion instrumentVersion, String _namespace) {
        // This assumes that full, nested contents of InstrumentVersion are now instantiated
        this.instrumentVersion = instrumentVersion;

        try {
            // iterate through contents
            // Note, must create leaf-level concepts before associating them with higher-level ones
            this.namespaceID = instrumentVersion.getInstrumentVersionID();

            if (_namespace == null || _namespace.trim().equals("")) {
                this.namespaceName = "Instruments_" + namespaceID;
            } else {
                this.namespaceName = _namespace;
            }

            this.namespace = "<namespace>" + namespaceName + "</namespace>";
            this.fromNamespace = "<from_namespace>" + namespaceName + "</from_namespace>";
            this.toNamespace = "<to_namespace>" + namespaceName + "</to_namespace>";
            this.namespaceCode = "Ndlx_" + namespaceID;

            sb = new StringBuffer(this.getDTSheader());

            String instrumentVersionName = "I(" + instrumentVersion.getInstrumentVersionID() + ")";
            String instrumentVersionNameConcept = instrumentVersionName + ": " + instrumentVersion.getInstrumentID().getInstrumentName() + "(" + instrumentVersion.getVersionString() + ")";

            sb.append(term(instrumentVersionNameConcept, "Root Property", "V-" + namespaceCode));

            ArrayList<InstrumentContent> sortedInstrumentContents = new ArrayList<InstrumentContent>(instrumentVersion.getInstrumentContentCollection());
            Collections.sort(sortedInstrumentContents, new InstrumentContentSequenceComparator());
            Iterator<InstrumentContent> instrumentContents = sortedInstrumentContents.iterator();
            ArrayList<String> instrumentContentNameConcepts = new ArrayList<String>();
            while (instrumentContents.hasNext()) {
                InstrumentContent instrumentContent = instrumentContents.next();
                String instrumentContentName = instrumentVersionName + ".[" + ThousandFormatter.format(instrumentContent.getItemSequence()) + "]";

                Item item = instrumentContent.getItemID();
                String itemName = instrumentContentName + ".";

                AnswerList answerList = item.getAnswerListID();
                String answerListName = itemName + "A";
                ArrayList<String> answerListContentNameConcepts = new ArrayList<String>();

                if (answerList != null) {
                    ArrayList<AnswerListContent> sortedAnswerListContents = new ArrayList<AnswerListContent>(answerList.getAnswerListContentCollection());
                    Collections.sort(sortedAnswerListContents, new AnswerListContentSequenceComparator());
                    Iterator<AnswerListContent> answers = sortedAnswerListContents.iterator();
                    while (answers.hasNext()) {
                        AnswerListContent answerListContent = answers.next();
                        String answerListContentName = answerListName + "[" + HundredFormatter.format(answerListContent.getAnswerOrder()) + "]";

                        Answer answer = answerListContent.getAnswerID();
                        String answerName = answerListContentName + ".";

                        String EnglishAnswerString = null;
                        BigInteger EnglishAnswerLocalizedID = null;

                        Iterator<AnswerLocalized> localizedAnswers = answer.getAnswerLocalizedCollection().iterator();
                        ArrayList<String> localizedAnswerNames = new ArrayList<String>();
                        while (localizedAnswers.hasNext()) {
                            AnswerLocalized answerLocalized = localizedAnswers.next();
                            String localizedAnswerName = answerName + "[" + answerLocalized.getLanguageCode() + "]";
                            if (answerLocalized.getLanguageCode().equals("en")) {
                                localizedAnswerNames.add(localizedAnswerName);

                                EnglishAnswerString = XMLAttrEncoder.encode(answerLocalized.getAnswerString());
                                EnglishAnswerLocalizedID = answerLocalized.getAnswerLocalizedID();
                            }
                        }

                        String answerListContentNameConcept = answerListContentName + ": " + EnglishAnswerString;
                        answerListContentNameConcepts.add(answerListContentNameConcept);

                        sb.append(concept(answerListContentNameConcept));
                        sb.append(property("AnswerOrder", answerListContent.getAnswerOrder()));
                        sb.append(property("AnswerString", EnglishAnswerString));
                        sb.append(property("AnswerLocalized_ID", EnglishAnswerLocalizedID));
                        sb.append(property("AnswerValue", answerListContent.getAnswerCode()));
                        sb.append(property("AnswerListContent_ID", answerListContent.getAnswerListContentID()));
                        sb.append(property("Answer_ID", answer.getAnswerID()));
                        sb.append(property("hasLAcode", answer.getHasLAcode()));
                        sb.append(property("LAcode", (answer.getHasLAcode()) ? answer.getLAcode() : "-"));
                        sb.append(property("InstrumentVersion_ID", instrumentVersion.getInstrumentVersionID()));
                        sb.append(property("Item_ID", item.getItemID()));
                        sb.append(property("Question_ID", item.getQuestionID().getQuestionID()));
                        sb.append(property("AnswerList_ID", item.getAnswerListID().getAnswerListID()));
                        sb.append(property("InstrumentContent_ID", instrumentContent.getInstrumentContentID()));
                        sb.append(conceptEnd());    // END of answerListContentName
                    }
                }

                Question question = item.getQuestionID();
                String questionName = itemName + "Q";

                String EnglishQuestionString = null;
                BigInteger EnglishQuestionLocalizedID = null;

                Iterator<QuestionLocalized> questions = question.getQuestionLocalizedCollection().iterator();
                ArrayList<String> questionLocalizedNames = new ArrayList<String>();
                while (questions.hasNext()) {
                    QuestionLocalized questionLocalized = questions.next();
                    String questionLocalizedName = questionName + "[" + questionLocalized.getLanguageCode() + "]";

                    if (questionLocalized.getLanguageCode().equals("en")) {
                        questionLocalizedNames.add(questionLocalizedName);

                        EnglishQuestionString = XMLAttrEncoder.encode(questionLocalized.getQuestionString());
                        EnglishQuestionLocalizedID = questionLocalized.getQuestionLocalizedID();
                    }
                }

                String instrumentContentNameConcept = instrumentContentName + ": " + EnglishQuestionString;

                instrumentContentNameConcepts.add(instrumentContentNameConcept);

                sb.append(concept(instrumentContentNameConcept));  // name of question - sos need English Text 
                sb.append(property("InstrumentContent_ID", instrumentContent.getInstrumentContentID()));
                sb.append(property("ItemVarName", instrumentContent.getVarNameID().getVarName()));
                sb.append(property("ItemSequence", instrumentContent.getItemSequence()));
                sb.append(property("ItemRelevance", XMLAttrEncoder.encode(instrumentContent.getRelevance())));
                sb.append(property("ItemType", instrumentContent.getItemActionType()));
                sb.append(property("Item_ID", item.getItemID()));
                sb.append(property("DataType", item.getDataTypeID().getDataType()));
                sb.append(property("QuestionString", EnglishQuestionString));
                sb.append(property("QuestionLocalized_ID", EnglishQuestionLocalizedID));
                sb.append(property("Question_ID", item.getQuestionID().getQuestionID()));
                sb.append(property("InstrumentVersion_ID", instrumentVersion.getInstrumentVersionID()));
                sb.append(property("hasLOINCcode", item.getHasLOINCcode()));
                sb.append(property("LOINC_NUM", (item.getHasLOINCcode()) ? item.getLoincNum() : "-"));

                if (answerList != null) {
                    sb.append(property("AnswerList_ID", item.getAnswerListID().getAnswerListID()));
                    for (int i = 0; i < answerListContentNameConcepts.size(); ++i) {
                        sb.append(association("Parent Of", instrumentContentNameConcept, answerListContentNameConcepts.get(i)));
                    }
                }
                sb.append(conceptEnd());
            }
            // Now declare containing concepts

            sb.append(concept(instrumentVersionNameConcept));
            sb.append(property("InstrumentVersion_ID", instrumentVersion.getInstrumentVersionID()));
            sb.append(property("InstrumentVersion", XMLAttrEncoder.encode(instrumentVersion.getVersionString())));
            sb.append(property("hasLOINCcode", instrumentVersion.getHasLOINCcode()));
            sb.append(property("LOINC_NUM", (instrumentVersion.getHasLOINCcode()) ? instrumentVersion.getLoincNum() : "-"));
            sb.append(property("Instrument_ID", instrumentVersion.getInstrumentID().getInstrumentID()));
            sb.append(property("InstrumentName", XMLAttrEncoder.encode(instrumentVersion.getInstrumentID().getInstrumentName())));
            sb.append(property("InstrumentDescription", XMLAttrEncoder.encode(instrumentVersion.getInstrumentID().getInstrumentDescription())));
            sb.append(synonym("Synonym", instrumentVersionNameConcept, true));
            for (int i = 0; i < instrumentContentNameConcepts.size(); ++i) {
                sb.append(association("Parent Of", instrumentVersionNameConcept, instrumentContentNameConcepts.get(i)));
            }
            sb.append(conceptEnd());

            sb.append(this.getDTSFooter());
            this.status = true;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"", e);
        }
    }

    private StringBuffer concept(String name) {
        StringBuffer sb0 = new StringBuffer("<concept>");
        sb0.append(namespace).append("<name>").append(name).append("</name>\n");
        return sb0;
    }

    private String conceptEnd() {
        return "</concept>\n";
    }

    private StringBuffer property(String name, Object value) {
        StringBuffer sb0 = new StringBuffer("  <property>");
        sb0.append(namespace).append("<name>").append(name).append("</name><value>").append(value).append("</value></property>\n");
        return sb0;
    }

    private StringBuffer association(String type, String fromString, String toString) {
        StringBuffer sb0 = new StringBuffer("  <association>");
        sb0.append(namespace).append("<name>").append(type).append("</name>");
        sb0.append(fromNamespace).append("<from_name>").append(fromString).append("</from_name>");
        sb0.append(toNamespace).append("<to_name>").append(toString).append("</to_name></association>\n");
        return sb0;
    }

    private StringBuffer term(String name, String property, String value) {
        StringBuffer sb0 = new StringBuffer("<term>");
        sb0.append(namespace).append("<name>").append(name).append("</name>");
//        sb0.append("<code>T").append(++termCount).append("<code><id>").append(termCount).append("</id>");
        sb0.append("<property>").append(namespace).append("<name>").append(property).append("</name>");
        sb0.append("<value>").append(value).append("</value>");
        sb0.append("</property></term>\n");
        return sb0;
    }

    private StringBuffer synonym(String name, String toName, boolean preferred) {
        StringBuffer sb0 = new StringBuffer("  <synonym>");
        sb0.append(namespace).append("<name>").append(name).append("</name>");
        sb0.append(toNamespace).append("<to_name>").append(toName).append("</to_name>");
        sb0.append("<preferred>").append((preferred) ? "true" : "false").append("</preferred>");
        sb0.append("</synonym>\n");
        return sb0;
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
        sb0.append("<!-- DTS Export Terminology File Version 2 -->\n");
        sb0.append("<terminology type='namespace'>\n");
        sb0.append(" <namespace type='Thesaurus'>");
        sb0.append("  <name>").append(namespaceName).append("</name>");
        sb0.append("  <code>").append(namespaceCode).append("</code><id>").append(namespaceID).append("</id>");
        sb0.append(" </namespace>\n");

        sb0.append(proptype("Root Property", "T", "I", false));
        sb0.append(proptype("Instrument_ID", "C", "I", false));
        sb0.append(proptype("InstrumentVersion_ID", "C", "I", false));
        sb0.append(proptype("InstrumentContent_ID", "C", "I", false));
        sb0.append(proptype("Item_ID", "C", "I", false));
        sb0.append(proptype("Question_ID", "C", "I", false));
        sb0.append(proptype("QuestionLocalized_ID", "C", "I", false));
        sb0.append(proptype("AnswerList_ID", "C", "I", false));
        sb0.append(proptype("AnswerListContent_ID", "C", "I", false));
        sb0.append(proptype("Answer_ID", "C", "I", false));
        sb0.append(proptype("AnswerLocalized_ID", "C", "I", false));
        sb0.append(proptype("LanguageCode", "C", "I", true));
        sb0.append(proptype("AnswerString", "C", "I", true));
        sb0.append(proptype("hasLAcode", "C", "I", false));
        sb0.append(proptype("LAcode", "C", "I", false));
        sb0.append(proptype("AnswerOrder", "C", "I", false));
        sb0.append(proptype("AnswerValue", "C", "I", true));
        sb0.append(proptype("QuestionString", "C", "S", true));
        sb0.append(proptype("ItemType", "C", "I", false));
        sb0.append(proptype("hasLOINCcode", "C", "I", false));
        sb0.append(proptype("LOINC_NUM", "C", "I", false));
        sb0.append(proptype("DataType", "C", "I", false));
        sb0.append(proptype("ItemSequence", "C", "I", false));
        sb0.append(proptype("ItemRelevance", "C", "I", false));
        sb0.append(proptype("ItemVarName", "C", "I", false));
        sb0.append(proptype("InstrumentDescription", "C", "S", true));
        sb0.append(proptype("InstrumentName", "C", "I", true));
        sb0.append(proptype("InstrumentVersion", "C", "I", false));

        sb0.append(assntype("Synonym", "Synonym", "S", "M"));
        sb0.append(assntype("Parent Of", "Child Of", "C", "M"));
        sb0.append(assntype("Maps To (Answer)", "Maps From (Answer)", "C", "M"));
        sb0.append(assntype("Maps To (Question)", "Maps From (Question)", "C", "M"));
        sb0.append(assntype("Maps To (Question + Answer)", "Maps From (Question + Answer)", "C", "M"));
        sb0.append(assntype("Maps To (Instrument + Question + Answer)", "Maps From (Instrument + Question + Answer)", "C", "M"));

        sb0.append(qualtype("Mapping Quality", "CA"));
        sb0.append(qualtype("Mapped By", "CA"));

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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;
import org.dialogix.entities.*;
/**
 *
 * @author Coevtmw
 */
public interface InstrumentLoaderFacadeLocal {
     ActionType parseActionType(String token) throws InstrumentLoadException;
     AnswerListDenorm parseAnswerListDenorm(String token, String languageCode) throws InstrumentLoadException;
     AnswerLocalized parseAnswerLocalized(String token, String languageCode) throws InstrumentLoadException;
     DataType parseDataType(String token) throws InstrumentLoadException;
     DisplayType parseDisplayType(String token) throws InstrumentLoadException;
     HelpLocalized parseHelpLocalized(String token, String languageCode) throws InstrumentLoadException;
     Instrument parseInstrument(String token) throws InstrumentLoadException;
     InstrumentHash parseInstrumentHash(InstrumentHash instrumentHash) throws InstrumentLoadException;     
     InstrumentVersion parseInstrumentVersion(String title, String token) throws InstrumentLoadException;
     Integer parseNullFlavor(String token) throws InstrumentLoadException;
     Item findItem(Item newItem, String questionString, String answerListDenormalizedString, String dataType, boolean hasNewContents) throws InstrumentLoadException;
     LanguageList parseLanguageList(String token) throws InstrumentLoadException;
     QuestionLocalized parseQuestionLocalized(String token, String languageCode) throws InstrumentLoadException;
     ReadbackLocalized parseReadbackLocalized(String token, String languageCode) throws InstrumentLoadException;
     ReservedWord parseReservedWord(String token) throws InstrumentLoadException;
     String parseItemActionType(String token) throws InstrumentLoadException;
     Validation parseValidation(DataType dataType, String minVal, String maxVal, String inputMask, String otherVals) throws InstrumentLoadException;
     VarName parseVarName(String token) throws InstrumentLoadException;
     boolean lastItemComponentsHadNewContent();
     void init();
     void merge(Instrument instrument);
     void merge(InstrumentVersion instrumentVersion);     

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import javax.ejb.Local;
import org.dialogix.entities.*;
/**
 *
 * @author Coevtmw
 */
@Local
public interface InstrumentLoaderFacadeLocal {
    ActionType parseActionType(String token);
    AnswerListDenormalized parseAnswerListDenormalized(String token, String languageCode);
    AnswerLocalized parseAnswerLocalized(String token, String languageCode);
    DataType parseDataType(String token);
    DisplayType parseDisplayType(String token);
    HelpLocalized parseHelpLocalized(String token, String languageCode);
    Instrument parseInstrument(String token);
    InstrumentVersion parseInstrumentVersion(String title, String token);
    Integer parseNullFlavor(String token);
    Item findItem(Item newItem, String questionString, String answerListDenormalizedString, String dataType, boolean hasNewContents);
    LanguageList parseLanguageList(String token);
    QuestionLocalized parseQuestionLocalized(String token, String languageCode);
    ReadbackLocalized parseReadbackLocalized(String token, String languageCode);
    ReservedWord parseReservedWord(String token);
    String parseItemActionType(String token);
    Validation parseValidation(DataType dataType, String minVal, String maxVal, String inputMask, String otherVals);
    VarName parseVarName(String token);
    boolean lastItemComponentsHadNewContent();
    void init();
    void merge(Instrument instrument);
}

package org.dianexus.triceps.modules.data;
/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** InstrumentMetaDAO.java ,v 3.0.0 Created on March 9, 2006, 12:37 PM
 ** @author Gary Lyons
 ******************************************************** 
 ***/

import java.sql.*;
 
 public interface InstrumentMetaDAO {
     
     boolean getInstrumentMeta(int id);
     boolean setInstrumentMeta();
     boolean updateInstrumentMeta(String column, String value);
     boolean deleteInstrumentMeta(int id);
     int getInstrumentMetaLastInsertId();
     void setInstrumentVersionId(int id);
     public int getInstrumentVersionId();
     void setTitle(String title);
     public String getTitle();
     void setVersion(String version);
     public String getVersion();
     void setCreationDate(Timestamp date);
     public Timestamp getCreationDate();
     void setNumVars(int numVars);
     int getNumVars();
     void setVarListMD5(String varListMD5);
     public String getVarListMD5();
     void setInstrumentMD5(String instrumentMD5);
     String getInstrumentMD5();
     void setLanguageList(String languageList);
     String getLanguageList();
     String[] getLanguageListArray();
     void setNumLanguages(int numLanguages);
     int getNumLanguages();
     void setNumInstructions(int numInstructions);
     int getNumInstructions();
     void setNumEquations(int numEquations);
     int getNumEquations();
     void setNumQuestions(int numQuestions);
     int getNumQuestions();
     void setNumBranches(int numBranches);
     int getNumBranches();
     void setNumTailorings(int numTailorings);
     int getNumTailorings();
     
     
     
 }
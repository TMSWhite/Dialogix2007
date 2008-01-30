/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.loader;

/**
 * This class presumes it is reading an instrument from Ruby, so minimalistic contents
 * @author Coevtmw
 */
public class InstrumentLoader {
    private String title;
    private String version;
    private String contents;
    private String result;
    private Boolean hasLoadErrors;
    private InstrumentExcelLoader instrumentExcelLoader;
    private Boolean status = false;

    // FIXME - create List of errors?

    public InstrumentLoader() {
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResult() {
        return result;
    }    
    
    public Boolean getStatus() {
        return status;
    }
    
    public String getLaunchCommand() {
        return instrumentExcelLoader.getLaunchCommand();
    }
    
    public String getLoadErrors() {
        return instrumentExcelLoader.getLoadErrorsAsHtmlTable();
    }
    
    public Boolean getHasLoadErrors() {
        return hasLoadErrors;
    }    
    
    public void loadInstrumentRuby() {
        status = false;
        result = "ERROR";
        if (contents == null || contents.trim().length() == 0) {
            return;
        }
        if (title == null || title.trim().length() == 0 ||
            version == null || version.trim().length() == 0) {
            return;
        }
        StringBuffer out = new StringBuffer();  // this will be the format which InstrumentExcelLoader expects
        
        // set default values
        out.append("RESERVED\t__TRICEPS_FILE_TYPE__\tSCHEDULE\n");
        out.append("RESERVED\t__TITLE__\t").append(title).append("\n");
        out.append("RESERVED\t__HEADER_MSG__\t").append(title).append("\n");
        out.append("RESERVED\t__SCHED_VERSION_MAJOR__\t").append(version).append("\n");
        out.append("RESERVED\t__SCHED_VERSION_MINOR__\t0\n");
        out.append("RESERVED\t__LANGUAGES__\ten\n");
        out.append("RESERVED\t__ICON__\tdialogo.jpg\n");
        
        String[] lines = contents.split("\n");
        for (int i=0;i<lines.length;++i) {
            String line = lines[i];
            String[] tokens = line.split("\t");
            
            String varName="";
            String relevance="";
            String question="";
            String displayType="";
            String answerList="";
            if (tokens.length >= 0) {
                varName = tokens[0].trim();
            }
            if (tokens.length >= 1) {
                relevance = tokens[1].trim();
            }
            if (tokens.length >= 2) {
                question = tokens[2].trim();
            }
            if (tokens.length >= 3) {
                displayType = tokens[3].trim();
            }
            if (tokens.length >= 4) {
                displayType = tokens[4].trim();
            }
            
            out.append("\t");                   // concept
            out.append(varName).append("\t");   // varName
            out.append("\t");                   // externalName
            out.append(relevance).append("\t"); // relevance
            out.append("q\t");                  // questionOrEvalType
            out.append("\t");                   // readback
            out.append(question).append("\t");  // question
            out.append(displayType);            // displayType
            if (answerList.length() > 0) {
                out.append("|").append(answerList); // answerList
            }
            out.append("\t\n");                 // helpURL
        }
        
        instrumentExcelLoader = new InstrumentExcelLoader();
        status = instrumentExcelLoader.loadInstrument(title,out.toString());
        if (status == true) {
            result = "Successfully loaded contents for " + title;
        }
        hasLoadErrors = instrumentExcelLoader.hasInstrumentLoadErrors();
    }

    public void loadInstrument() {
        status = false;
        result = "ERROR";
        if (contents == null || contents.trim().length() == 0) {
            return;
        }
        if (title == null || title.trim().length() == 0) {
            return;
        }
        
        instrumentExcelLoader = new InstrumentExcelLoader();
        status = instrumentExcelLoader.loadInstrument(title,contents);
        if (status == true) {
            result = "Successfully loaded contents for " + title;
        }
        hasLoadErrors = instrumentExcelLoader.hasInstrumentLoadErrors();
    }    
}

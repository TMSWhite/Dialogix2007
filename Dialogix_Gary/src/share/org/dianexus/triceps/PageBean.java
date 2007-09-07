package org.dianexus.triceps;

public class PageBean {
	
 private String directive;
 private String varName;
 
 private int currentStep;
 private int lastRequest;
 private int previousRequest;

public void setDirective(String directive) {
		this.directive = directive;
	}
 
public String getDirective() {
	return directive;
}
 
public void setCurrentStep( int currentStep){
	this.currentStep = currentStep;
	
}
public int getCurrentStep(){
return this.currentStep;
}

public void setVarName (String varName){
	this.varName = varName;
}
public String getVarname(){
	return this.varName;
	
}
public void setLastReceivedRequest(int lastRequest){
	this.lastRequest = lastRequest;
}
public int getLastReceivedRequest(){
	return this.lastRequest;
}
public void setPreviousRequest( int previousRequest){
	this.previousRequest = previousRequest;
}
public int getPreviousRequest(){
	return this.previousRequest;
}
}

package org.dianexus.triceps;

public class EventAggregate {
	
	private String varName;
	private int itemVacillation;
	private int responseLatency;
	private int responseDuration;
	public int getItemVacillation() {
		return itemVacillation;
	}
	public void setItemVacillation(int itemVacillation) {
		this.itemVacillation = itemVacillation;
	}
	public int getResponseDuration() {
		return responseDuration;
	}
	public void setResponseDuration(int responseDuration) {
		this.responseDuration = responseDuration;
	}
	public int getResponseLatency() {
		return responseLatency;
	}
	public void setResponseLatency(int responseLatency) {
		this.responseLatency = responseLatency;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	

}

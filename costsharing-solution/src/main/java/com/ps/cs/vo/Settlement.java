package com.ps.cs.vo;


import java.math.BigDecimal;

public class Settlement {
	
	
	private int eventId;
	private BigDecimal amountSettled;	
	private String personPaid;
	private String personRecevied;
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public BigDecimal getAmountSettled() {
		return amountSettled;
	}
	public void setAmountSettled(BigDecimal amountSettled) {
		this.amountSettled = amountSettled;
	}
	public String getPersonPaid() {
		return personPaid;
	}
	public void setPersonPaid(String personPaid) {
		this.personPaid = personPaid;
	}
	public String getPersonRecevied() {
		return personRecevied;
	}
	public void setPersonRecevied(String personRecevied) {
		this.personRecevied = personRecevied;
	}
	
	
	

}

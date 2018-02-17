package com.ps.cs.vo;


import java.math.BigDecimal;

public class Settlement {
	
	
	private String eventId;
	private BigDecimal amountSettled;	
	private String payer;
	private String receiver;
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public BigDecimal getAmountSettled() {
		return amountSettled;
	}
	public void setAmountSettled(BigDecimal amountSettled) {
		this.amountSettled = amountSettled;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	
	
	

}

package com.ps.cs.vo;

import java.math.BigDecimal;

public class CostSplit {
	
	private String spender;
	private String[] participantsWithoutSpender;
	private BigDecimal amount;
	
	public String getSpender() {
		return spender;
	}
	public void setSpender(String spender) {
		this.spender = spender;
	}

	public String[] getParticipantsWithoutSpender() {
		return participantsWithoutSpender;
	}
	public void setParticipantsWithoutSpender(String[] participantsWithoutSpender) {
		this.participantsWithoutSpender = participantsWithoutSpender;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	

}

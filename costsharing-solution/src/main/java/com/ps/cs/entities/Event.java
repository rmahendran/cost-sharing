package com.ps.cs.entities;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import com.ps.cs.vo.CostSplit;

public class Event {
	
	private String eventId;
	private String eventName;
	private String[] participantsList;
	private BigDecimal eventExpense;
	private Person spender;
	private CostSplit splits;
	private BigDecimal settlementAmount;
	private boolean isSettled;
	
	public boolean isSettled() {
		return isSettled;
	}
	public void setSettled(boolean isSettled) {
		this.isSettled = isSettled;
	}
	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public CostSplit getSplits() {
		return splits;
	}
	public void setSplits(CostSplit splits) {
		this.splits = splits;
	}
	
	public Person getSpender() {
		return spender;
	}
	public void setSpender(Person spender) {
		this.spender = spender;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String[] getParticipantsList() {
		return participantsList;
	}
	public void setParticipantsList(String[] participantsList) {
		this.participantsList = participantsList;
	}
	public BigDecimal getEventExpense() {
		return eventExpense;
	}
	public void setEventExpense(BigDecimal eventExpense) {
		this.eventExpense = eventExpense;
	}	

	public void calculateSplit(HashMap<String,Person> personData)
	{
		int numberOfParticipants = participantsList.length;
		
		BigDecimal participants = new BigDecimal(numberOfParticipants);  
		
		BigDecimal amountSplit = eventExpense.divide(participants,2, RoundingMode.HALF_UP);
		splits = new CostSplit();
		splits.setAmount(amountSplit);
		splits.setSpender(spender.getPersonId());
		String[] participantsWithoutSpender = new String[numberOfParticipants-1];
		for (int index = 0, offset =0 ; index < numberOfParticipants; index++ )
		{
		
			if ( !participantsList[index].equalsIgnoreCase(spender.getPersonId()) )
			{
				
				participantsWithoutSpender[ offset ] = participantsList[ index ];
				Person p = (Person)personData.get(participantsWithoutSpender[ offset ]);
			//	System.out.println("Participant - spender: " + spender.getPersonId());
			//	System.out.println("Participant - Non spender: " + p.getPersonId());
				spender.isolatePersonsNeedToPayAlongWithAmount(p,amountSplit);
				offset++;
			}
		}
		splits.setParticipantsWithoutSpender(participantsWithoutSpender);
	//	System.out.println("Amount Split:" + amountSplit);
	//	System.out.println("Participant paid:" + spender);
	//	System.out.println("Participants not paid size:" + participantsWithoutSpender.length);
		
		
	}

}

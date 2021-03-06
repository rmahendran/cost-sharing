package com.ps.cs.entities;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import com.ps.cs.Constants;
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
				spender.isolatePersonsNeedToPayAlongWithAmount(p,amountSplit);
				offset++;
			}
		}
		splits.setParticipantsWithoutSpender(participantsWithoutSpender);	
		
	}
	
	
	
	/**On Demand Changes**/
	// Assumption - Event file exists with header
	public void addEvent(String dataFilePath, HashMap<String,Person> personData, String spenderValue) throws IOException
	{				
		BufferedWriter output = null;	
		StringBuffer event = new StringBuffer();
		StringBuffer participantValues = new StringBuffer("");

		
		
		try {
			
			output = new BufferedWriter(new FileWriter(dataFilePath+"/"+ Constants.EVENTTYPE +".db",true));
			event.append("\n");
			event.append(eventId);
			event.append(Constants.SPLITBY);
			event.append(eventName);
			event.append(Constants.SPLITBY);
			
			
			for ( int index=0;index<participantsList.length;index++ )
			{
				participantValues.append(participantsList[index]);
				if ( index < participantsList.length - 1)
					participantValues.append(Constants.PARTICIPATNT_SPLITBY);
			}			
			event.append(participantValues.toString());
			
			event.append(Constants.SPLITBY);
			event.append(eventExpense);			
			event.append(Constants.SPLITBY);
			event.append(spenderValue);
					
			
			output.write(event.toString());
			output.flush();		
			this.setSpender( personData.get(spenderValue) );
			this.calculateSplit(personData);
			associateEventWithPersonBasedonPayment(spenderValue,personData);

		}catch(IOException e)
		{			
			
			throw e;
		}
		finally {
			try {
				if (output != null)
					output.close();
			} catch (IOException ex) {
				ex.printStackTrace();

			}
		}
		
		
	
		
		
	}
	
	private void associateEventWithPersonBasedonPayment(String spender, HashMap<String,Person> personData) {
		Person p = personData.get(spender);
		if (p != null) {
			p.addToPaidEvents(this);
		} else {
			CostSplit splitByEvent = this.getSplits();
			String[] participantsWithoutSender = splitByEvent
					.getParticipantsWithoutSpender();
			for (int index = 0; index < participantsWithoutSender.length; index++) {
				p = personData.get(participantsWithoutSender[index]);
				p.addToUnPaidEvents(this);
			}

		}
	}
	
	/**On Demand Changes**/
	
	
	

}

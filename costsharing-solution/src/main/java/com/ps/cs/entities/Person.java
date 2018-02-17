package com.ps.cs.entities;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.math.BigDecimal;

import com.ps.cs.vo.Settlement;

public class Person {
	
	private String personId;
	private String personName;
	private Map<String, Event> paidEvents;//may not needed	
	private Map<String, Event> unPaidEvents; //may not needed
	private BigDecimal totalAmountPaid;
	private HashMap<String, BigDecimal> personsNeedtoPay;
	private HashMap<String, BigDecimal> personsSettled;
	 
	
	
	public HashMap<String, BigDecimal> getPersonsNeedtoPay() {
		return personsNeedtoPay;
	}
	public void setPersonsNeedtoPay(HashMap<String, BigDecimal> personsNeedtoPay) {
		this.personsNeedtoPay = personsNeedtoPay;
	}
	
	public BigDecimal getTotalAmountPaid() {
		return totalAmountPaid;
	}
	public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public HashMap<String, BigDecimal> getPersonsSettled() {
		return personsSettled;
	}
	public void setPersonsSettled(HashMap<String, BigDecimal> personsSettled) {
		this.personsSettled = personsSettled;
	}

	
	
	public void addToPaidEvents(Event paidEvent)
	{
		if ( paidEvents == null )
			paidEvents = new HashMap<String,Event>();
		
		paidEvents.put(paidEvent.getEventId(),paidEvent);
	}
	
	public void addToUnPaidEvents(Event unPaidEvent)
	{
		if ( unPaidEvents == null )
			unPaidEvents = new HashMap<String,Event>();
		
		unPaidEvents.put(unPaidEvent.getEventId(),unPaidEvent);
	}
	
	
	public void isolatePersonsNeedToPayAlongWithAmount(Person person, BigDecimal amount)
	{	  
	        if ( personsNeedtoPay == null )
	        	personsNeedtoPay = new  HashMap<String, BigDecimal>();
	        
        	BigDecimal existingAmount = personsNeedtoPay.get(person.getPersonId());
        	if ( existingAmount != null)
        		amount = existingAmount.add (amount);
        	
        	personsNeedtoPay.put(person.getPersonId(),amount);           
	}
	public void acceptSettlement(Settlement settlement) {
		BigDecimal amountToBePaid = personsNeedtoPay.get(settlement.getPayer());
		BigDecimal substractedAmount;
		
		
		if ( amountToBePaid.compareTo(settlement.getAmountSettled()) == 0 ) 
		{
			 if ( personsSettled == null )
				 personsSettled =  new  HashMap<String, BigDecimal>();			 
			 personsSettled.put( settlement.getPayer(), settlement.getAmountSettled());//trail of settlements
			 substractedAmount = amountToBePaid.subtract(settlement.getAmountSettled());
			 if ( substractedAmount.compareTo( new BigDecimal(0) ) == 0 ) 
				 	personsNeedtoPay.remove(settlement.getPayer());
			 else
				 	personsNeedtoPay.put(settlement.getPayer(), substractedAmount);//negative conditions not checked			 
			 //get the Event from paid event and substract the payment - Gives settlement left for the event
			 Event event = paidEvents.get(settlement.getEventId());			 
			 BigDecimal eventSettlement = event.getSettlementAmount();	
			 
			 if ( eventSettlement == null )
				 eventSettlement = settlement.getAmountSettled();
			 else
				 eventSettlement.add(settlement.getAmountSettled());		
			 
			 if ( personsNeedtoPay.isEmpty())
				 event.setSettled(true);
			 else
				 event.setSettlementAmount(eventSettlement);
		}		
			
	}
}

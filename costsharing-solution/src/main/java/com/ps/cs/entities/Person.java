package com.ps.cs.entities;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class Person {
	
	private String personId;
	private String personName;
	private List<Event> paidEvents;
	private List<Event> unPaidEvents;
	private BigDecimal totalAmountPaid;
	private HashMap<String, BigDecimal> personsNeedtoPay;
	
	
	public HashMap<String, BigDecimal> getPersonsNeedtoPay() {
		return personsNeedtoPay;
	}
	public void setPersonsNeedtoPay(HashMap<String, BigDecimal> personsNeedtoPay) {
		this.personsNeedtoPay = personsNeedtoPay;
	}
	public List<Event> getUnPaidEvents() {
		return unPaidEvents;
	}
	public void setUnPaidEvents(List<Event> unPaidEvents) {
		this.unPaidEvents = unPaidEvents;
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

	public List<Event> getPaidEvents() {
		return paidEvents;
	}
	public void setPaidEvents(List<Event> paidEvents) {
		this.paidEvents = paidEvents;
	}
	
	public void addToPaidEvents(Event paidEvent)
	{
		if ( paidEvents == null )
			paidEvents = new ArrayList<Event>();
		
		paidEvents.add(paidEvent);
	}
	
	public void addToUnPaidEvents(Event unPaidEvent)
	{
		if ( unPaidEvents == null )
			unPaidEvents = new ArrayList<Event>();
		
		unPaidEvents.add(unPaidEvent);
	}
	
	
	public void isolatePersonsNeedToPayAlongWithAmount(Person person, BigDecimal amount)
	{	        
	    
	        
	   
	        if ( personsNeedtoPay == null )
	        	personsNeedtoPay = new  HashMap<String, BigDecimal>();
	        
        	BigDecimal existingAmount = personsNeedtoPay.get(person.getPersonId());
        	if ( existingAmount != null)
        		amount = existingAmount.add (amount);
        	
        	personsNeedtoPay.put(person.getPersonId(),amount);
        	
        //	System.out.println("Spender:" + this.personId );
        //	System.out.println("Payer :" + person.getPersonId() );
        //	System.out.println("Payers map with amount:" + personsNeedtoPay );
	}
	
	public void determineAmountOwedbyPerson(String personToGive)
	{
		
	}
	
	

	

}

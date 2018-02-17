package com.ps.cs;

import java.util.HashMap;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.math.BigDecimal;

import com.ps.cs.data.DataManager;
import com.ps.cs.entities.Event;
import com.ps.cs.entities.Person;
import com.ps.cs.vo.Settlement;

public class CostSplitManager {
	DataManager dataManager = null;
	
	public void bootStrap()
	{
		Properties prop = new Properties();
		InputStream input = null;
		String dataDirectory = null;
		dataManager = new DataManager();

		try {
			
			input = getClass().getResourceAsStream("/config.properties");
			prop.load(input);			
			dataDirectory = prop.getProperty("data_directory");
			dataManager.setDataFilePath(dataDirectory);
			dataManager.loadData(Constants.PERSON);
			dataManager.loadData(Constants.EVENT);	
			dataManager.loadData(Constants.SETTLEMENT);
				
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public BigDecimal determineAmountPayablePersonbyPersion (String payer, String receiver){
		
		BigDecimal returnAmount = null;
		HashMap<String,Person> personData = dataManager.getPersonData();
		Person payingPerson = null;
		if ( personData != null )
			payingPerson = personData.get(receiver);
			if( payingPerson != null && payingPerson.getPersonsNeedtoPay() != null ) {				
				returnAmount = payingPerson.getPersonsNeedtoPay().get(payer); //if both persons are same then problem
			}
			if ( returnAmount != null )
				System.out.println( "Person " + payer + " owes "+ returnAmount +" to person " + receiver );
			else
				System.out.println( "Person " + payer + " owes no money to person " + receiver );
			
			return returnAmount;
	}
	
	public BigDecimal findEventExpense(String eventId){
	
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		BigDecimal returnAmount = null;
		if ( event != null) {
			returnAmount = event.getEventExpense();
			System.out.println( "Event " + event.getEventName() + " expense: "+ returnAmount );
		}else
			System.out.println( "Incorrect Event :" + eventId );
		return returnAmount;
	}
	
	public BigDecimal getSettlementLeftForTheEvent(String eventId){
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		BigDecimal eventExpsense =  null;
		BigDecimal settlementAmount = null;
		if ( event != null) {
			if ( event.isSettled() )
				System.out.println( "Event " +  event.getEventName() + " got settled");
			else{
				eventExpsense = event.getEventExpense();				
				settlementAmount = event.getSettlementAmount();		
				if ( settlementAmount == null ) {
					settlementAmount = eventExpsense;
					System.out.println( "Event " + event.getEventName() + " settlement amount: "+ settlementAmount + " pending");
				}
				else
					System.out.println( "Event " + event.getEventName() + " settlement amount: "+ eventExpsense.subtract(settlementAmount) + " pending");
			}
		}else
			System.out.println( "Incorrect Event :" + eventId );
		return settlementAmount;
	}
	
	public BigDecimal determineCostPerPersonForAnEvent(String eventId){
		BigDecimal eventCostPerson = null;
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		if ( event != null) {		
			eventCostPerson = event.getSplits().getAmount();
			System.out.println( "Event " + event.getEventName() + " costed "+ eventCostPerson + " per person");
		}else
			System.out.println( "Incorrect Event :" + eventId );
		return eventCostPerson;
	}
	
	
	
	public static void main (String args[] )
	{
		//load the person data, event data & transactional data
		//Open Http Server with path & handler attached for each query
		
		CostSplitManager manage = new CostSplitManager();
		manage.bootStrap();
		manage.determineAmountPayablePersonbyPersion(args[0],args[1]);
		manage.findEventExpense(args[2]);
		manage.getSettlementLeftForTheEvent(args[2]);
		manage.determineCostPerPersonForAnEvent(args[2]);
		manage.createSettlement("");
		manage.getSettlementLeftForTheEvent("3");
		manage.createEvent("");
		manage.determineCostPerPersonForAnEvent("7");
		manage.getSettlementLeftForTheEvent("7");
		
	}	
	
	private void createSettlement(String settlementValue) 
	{
		
		Settlement settlement = new Settlement();
		settlement.setEventId("3");
		settlement.setPayer("3");
		settlement.setReceiver("4");
		settlement.setAmountSettled( new BigDecimal("33.33"));		
		HashMap<String,Person> personData = dataManager.getPersonData();
		try{
			settlement.addSettlement(dataManager.getDataFilePath(),personData.get("4"));
			
		}catch(IOException ex)
		{ex.printStackTrace();}
	}
	
	private void createEvent(String eventValue){
		Event event = new Event();
		event.setEventId("7");
		event.setEventName("Event7");
		event.setEventExpense(new BigDecimal(250));
		String eventParticipants [] = {"5","6"};		
		event.setParticipantsList(eventParticipants);
		
		HashMap<String,Person> personData = dataManager.getPersonData();
		HashMap<String,Event> eventData = dataManager.getEventData();
		
		try{
			event.addEvent(dataManager.getDataFilePath(), personData,  "6");
			eventData.put(event.getEventId(), event);
		}catch(IOException ex)
		{ex.printStackTrace();}	
		
	}
	

}

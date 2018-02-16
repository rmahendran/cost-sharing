package com.ps.cs.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import com.ps.cs.Constants;
import com.ps.cs.entities.Event;
import com.ps.cs.entities.Person;
import com.ps.cs.vo.CostSplit;

public class DataManager {
	
	private String dataFilePath;	
	@SuppressWarnings("unused")
	private HashMap<String,Person> personData;
	@SuppressWarnings("unused")
	private HashMap<String,Event> eventData;
	
	
	
	
	public HashMap<String, Person> getPersonData() {
		return personData;
	}

	public void setPersonData(HashMap<String, Person> personData) {
		this.personData = personData;
	}

	public HashMap<String, Event> getEventData() {
		return eventData;
	}

	public void setEventData(HashMap<String, Event> eventData) {
		this.eventData = eventData;
	}

	public String getDataFilePath() {
		return dataFilePath;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public void loadData(int dataType){
		
	
	//	System.out.println("Path Formed=" + dataFilePath+ Constants.SEPERATOR+ dataType+".db");
		
		
	    try{	
	    	    
				switch(dataType){			
					
					case 1:						
						personData = loadPersonData();
					//	System.out.println("personData :" + personData);
						break;
					case 2:						
						eventData = loadEventData();
				//		System.out.println("eventData :" + eventData);
						break;					
					default:
						System.out.println("No data load");
						
				}
							
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private HashMap<String,Person> loadPersonData() throws IOException
	{
		HashMap<String,Person> persons = null;
		String sCurrentLine = null;
		Person person = null;
		BufferedReader input = null;
		try {			
			input = new BufferedReader(new FileReader(dataFilePath+"/"+ Constants.PERSONTYPE+".db"));
			String header = input.readLine();
			if ( header != null ) {	
			persons = new HashMap<String,Person>();
			while ((sCurrentLine = input.readLine()) != null) {
			//	System.out.println(sCurrentLine);
				String[] personData = sCurrentLine.split(Constants.SPLITBY);
				if ( personData != null && personData.length == 2){
					
						person = new Person();
						person.setPersonId(personData[0]);
						person.setPersonName(personData[1]);
						persons.put(personData[0],person);
						
				    }
				}
			}
		}
		catch(IOException e)
		{
		
			throw e;
		}
		finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException ex) {
				ex.printStackTrace();

			}
		}
		
		return persons;
	}
	
	private HashMap<String,Event> loadEventData() throws IOException
	{
		HashMap<String,Event> events = null;
		String sCurrentLine = null;
		Event event = null;
		BufferedReader input = null;
		String[] participantsList = null;
		try {
			input = new BufferedReader(new FileReader(dataFilePath+"/"+ Constants.EVENTTYPE +".db"));
			String header = input.readLine();
			if ( header != null ) {	
			events = new HashMap<String,Event>();
			while ((sCurrentLine = input.readLine()) != null) {
			//	System.out.println(sCurrentLine);
				String[] eventData = sCurrentLine.split(Constants.SPLITBY);
				if ( eventData != null && eventData.length == 5){
					
						event = new Event();
						event.setEventId(eventData[0]);
						event.setEventName(eventData[1]);						
						/***** Participants Split *******/
					//	System.out.println("participants String:" + eventData[2]);
						participantsList = eventData[2].split(Constants.PARTICIPATNT_SPLITBY);
				//		System.out.println("participantsList :" + participantsList.length);
						event.setParticipantsList(participantsList);
						/***** Participants Split *******/	
						event.setEventExpense(new BigDecimal(eventData[3]));	
						event.setSpender(personData.get(eventData[4]));
						event.calculateSplit(personData);						
						events.put(eventData[0],event);						
						associateEventPersonBasedonPayment(eventData[4],event);
						
				    }
				}
			}
		}catch(IOException e)
		{			
			
			throw e;
		}
		finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException ex) {
				ex.printStackTrace();

			}
		}
		return events;
	}
	
	private void associateEventPersonBasedonPayment(String spender, Event event) {		
		Person p = personData.get(spender);		
		if (  p != null ) {
			p.addToPaidEvents(event);			
		}
		else
		{
			CostSplit splitByEvent = event.getSplits();
			String[] participantsWithoutSender = splitByEvent.getParticipantsWithoutSpender();
			for ( int index = 0; index < participantsWithoutSender.length; index++ ) {
				p = personData.get(participantsWithoutSender[index]);				
				p.addToUnPaidEvents(event);
			}	
			
		}
	}		
}
	
	

		
		
		
	


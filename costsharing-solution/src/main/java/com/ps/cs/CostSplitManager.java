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

public class CostSplitManager {
	DataManager dataManager = null;
	
	private void bootStrap()
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
	
	public void determineAmountPayablePersonbyPersion (String payer, String receiver){
		
		BigDecimal returnAmount = null;
		HashMap<String,Person> personData = dataManager.getPersonData();
		Person payingPerson = null;
		if ( personData != null )
			payingPerson = personData.get(receiver);
			if( payingPerson != null ) {
				System.out.println("Person to pay" + payingPerson.getPersonsNeedtoPay());
				returnAmount = payingPerson.getPersonsNeedtoPay().get(payer); //if both persons are same then problem
			}
			
			System.out.println( "Person " + payer + " owes "+ returnAmount +" to person " + receiver );
		
		
	}
	
	public void findEventExpense(String eventId){
	
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		BigDecimal returnAmount = event.getEventExpense();
		System.out.println( "Event " + event.getEventName() + " costed "+ returnAmount );
	}
	
	
	public static void main (String args[] )
	{
		//load the person data, event data & transactional data
		//Open Http Server with path & handler attached for each query
		
		CostSplitManager manage = new CostSplitManager();
		manage.bootStrap();
		manage.determineAmountPayablePersonbyPersion(args[0],args[1]);
		manage.findEventExpense(args[2]);
	}	
	

}

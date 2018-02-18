package com.ps.cs;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;

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
	
/*	public BigDecimal determineAmountPayablePersonbyPersion (String payer, String receiver){
		
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
	} */
	
	
	public String determineAmountPayablePersonbyPersion (String payer, String receiver){
		
		BigDecimal returnAmount = null;
		String returnResponse = null;
		HashMap<String,Person> personData = dataManager.getPersonData();
		
		Person payingPerson = null;
		if ( personData != null )
			payingPerson = personData.get(receiver);
			System.out.println("payingPerson.getPersonsNeedtoPay" + payingPerson.getPersonsNeedtoPay());
			
			if( payingPerson != null && payingPerson.getPersonsNeedtoPay() != null ) {				
				returnAmount = payingPerson.getPersonsNeedtoPay().get(payer); //if both persons are same then problem
			}
			if ( returnAmount != null )
				returnResponse = "Person " + payer + " owes "+ returnAmount +" to person " + receiver;
			else
				returnResponse =  "Person " + payer + " owes no money to person " + receiver;
			
			return returnResponse;
	}
	
	/*public BigDecimal findEventExpense(String eventId){
	
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		BigDecimal returnAmount = null;
		if ( event != null) {
			returnAmount = event.getEventExpense();
			System.out.println( "Event " + event.getEventName() + " expense: "+ returnAmount );
		}else
			System.out.println( "Incorrect Event :" + eventId );
		return returnAmount;
	}*/
	
	public String findEventExpense(String eventId){
		String returnResponse = null;
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		BigDecimal returnAmount = null;
		if ( event != null) {
			returnAmount = event.getEventExpense();
			returnResponse = "Event " + event.getEventName() + " expense: "+ returnAmount ;
		}else
			returnResponse =  "Incorrect Event :" + eventId;
		return returnResponse;
	}
	
	
	
	
	/*public BigDecimal getSettlementLeftForTheEvent(String eventId){
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
	}*/
	
	
	public String getSettlementLeftForTheEvent(String eventId){
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		BigDecimal eventExpsense =  null;
		BigDecimal settlementAmount = null;
		String response = null;
		if ( event != null) {
			if ( event.isSettled() )
				response = "Event " +  event.getEventName() + " got settled";
			else{
				eventExpsense = event.getEventExpense();				
				settlementAmount = event.getSettlementAmount();		
				if ( settlementAmount == null ) {
					settlementAmount = eventExpsense;
					response = "Event " + event.getEventName() + " settlement amount: "+ settlementAmount + " pending";
				}
				else
					response = "Event " + event.getEventName() + " settlement amount: "+ eventExpsense.subtract(settlementAmount) + " pending";
			}
		}else
			response = "Incorrect Event :" + eventId;
		return response;
	}
	
	/*public BigDecimal determineCostPerPersonForAnEvent(String eventId){
		BigDecimal eventCostPerson = null;
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		if ( event != null) {		
			eventCostPerson = event.getSplits().getAmount();
			System.out.println( "Event " + event.getEventName() + " costed "+ eventCostPerson + " per person");
		}else
			System.out.println( "Incorrect Event :" + eventId );
		return eventCostPerson;
	}*/
	
	public String determineCostPerPersonForAnEvent(String eventId){
		BigDecimal eventCostPerson = null;
		HashMap<String,Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		String response = null;
		if ( event != null) {		
			eventCostPerson = event.getSplits().getAmount();
			response = "Event " + event.getEventName() + " costed "+ eventCostPerson + " per person";
		}else
			response =  "Incorrect Event :" + eventId;
		return response;
	}
	
	
	
	public static void main (String args[] ) throws Exception
	{
		//load the person data, event data & transactional data
		//Open Http Server with path & handler attached for each query
		
		CostSplitManager appManager = new CostSplitManager();
		appManager.bootStrap();
		appManager.startApplicationServer(Constants.SERVERPORT,appManager);
		
		
		
		/** These have to be made queryable */
		
		/*
		manage.determineAmountPayablePersonbyPersion(args[0],args[1]);
		manage.findEventExpense(args[2]);
		manage.getSettlementLeftForTheEvent(args[2]);
		manage.determineCostPerPersonForAnEvent(args[2]);
		manage.createSettlement("");
		manage.getSettlementLeftForTheEvent("3");
		manage.createEvent("");
		manage.determineCostPerPersonForAnEvent("7");
		manage.getSettlementLeftForTheEvent("7");
		*/
		/** These have to be made queryable */
		
	}	
	
	public String createSettlement(String eventId, String payerId, String receiverId, String settlementAmount) 
	{
		
		Settlement settlement = new Settlement();
		settlement.setEventId(eventId);
		settlement.setPayer(payerId);
		settlement.setReceiver(receiverId);
		settlement.setAmountSettled( new BigDecimal(settlementAmount));		
		HashMap<String,Person> personData = dataManager.getPersonData();
		String response = null;
		try{
			settlement.addSettlement(dataManager.getDataFilePath(),personData.get(receiverId));
			response = "Settlement created and processed succefully";
			
		}catch(IOException ex)
		{
			ex.printStackTrace();
			response = "Settlement failed";
		}
		return response;
	}
	
	public String createEvent(String eventId, String eventName, String eventExpense,String eventParticipants,String spender){
		Event event = new Event();
		event.setEventId(eventId);
		event.setEventName(eventName);
		event.setEventExpense(new BigDecimal(eventExpense));				
		event.setParticipantsList(eventParticipants.split(Constants.PARTICIPATNT_SPLITBY));
		
		HashMap<String,Person> personData = dataManager.getPersonData();
		HashMap<String,Event> eventData = dataManager.getEventData();
		String response = null;
		
		try{
			event.addEvent(dataManager.getDataFilePath(), personData,  spender);
			eventData.put(event.getEventId(), event);
			response = "Event created "+ eventId+"  and processed successfully";
		}catch(IOException ex)
		{
			ex.printStackTrace();
			response = "Event creation failed"; 
					
		}	
		return response;
	}
	
	
	private void startApplicationServer(int port,CostSplitManager appManager) throws IOException
	{
		ServerSocket serverSocket = null;
		
	    Socket s=null;	    
	    ExecutorService threadPool = null;
		try{
		serverSocket = new ServerSocket(port);
		threadPool =   Executors.newFixedThreadPool(10);
		boolean isStopped = false;
		while(!isStopped){
			
			
		    s = serverSocket.accept();
		    threadPool.execute(new QueryRunnable(s,appManager));
		    
		    /*is= new BufferedReader(new InputStreamReader(s.getInputStream()));
		    os=new PrintWriter(s.getOutputStream());
		    line=is.readLine();
		    os.println("Query recieved is: " + line);
            os.flush();*/
		    
		}
		}catch (Exception ex)
		{
			ex.printStackTrace();			
		}finally {
			if  ( serverSocket != null && !serverSocket.isClosed() )
				serverSocket.close();
			
		}
	}

}

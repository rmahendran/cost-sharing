package com.ps.cs;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Properties;

import com.ps.cs.data.DataManager;
import com.ps.cs.entities.Event;
import com.ps.cs.entities.Person;
import com.ps.cs.entities.Settlement;

/*
 * Manages the application by interacting the domain objects. Entry point for any client 
 * to interact with the system.
 * Prevents the need for the clients to communicate with domain objects
 * 
 */

public class CostSplitManager {

	DataManager dataManager = null;

	/*
	 * Loads the data from db files and keep it in memory to make it available
	 * for queries
	 */
	public void bootStrap() {
		Properties prop = new Properties();
		InputStream input = null;
		String dataDirectory = null;
		dataManager = new DataManager();

		try {

			input = getClass().getResourceAsStream("/config.properties");
			prop.load(input);
			dataDirectory = prop.getProperty("data_directory");
			dataManager.setDataFilePath(dataDirectory); // Path where data files
														// resides
			dataManager.loadData(Constants.PERSON); // Loads Persons data
			dataManager.loadData(Constants.EVENT); // Loads Events data
			dataManager.loadData(Constants.SETTLEMENT);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Determines how much payer owes to receiver. Return textual response
	 */
	public String determineAmountPayablePersonbyPersion(String payer,
			String receiver) {

		BigDecimal returnAmount = null;
		String returnResponse = null;
		HashMap<String, Person> personData = null;

		if (!payer.equalsIgnoreCase(receiver)) {
			personData = dataManager.getPersonData();
			Person payingPerson = null;
			Person receivingPerson = null;
			if (personData != null) {
				receivingPerson = personData.get(receiver);
				payingPerson = personData.get(payer);

				if (payingPerson == null && receivingPerson == null)
					returnResponse = "Invalid users. Both of them do exists in the system";
				else if (payingPerson == null)
					returnResponse = "Invalid Payer. Payer with Id: " + payer
							+ " does not exists in system";
				else if (receivingPerson == null)
					returnResponse = "Invalid Reciever. Receiver with Id: "
							+ receiver + " does not exists in system";

			}
			if (receivingPerson.getPersonsNeedtoPay() != null) {
				returnAmount = receivingPerson.getPersonsNeedtoPay().get(payer);
				if (returnAmount != null)
					returnResponse = "Person " + payer + " owes "
							+ returnAmount + " to person " + receiver;
				else
					returnResponse = "Person " + payer
							+ " owes no money to person " + receiver;
			}
		} else
			returnResponse = "Payer and Receiver cannot be same person";

		return returnResponse;
	}

	/*
	 * Determines the expense incurred for an Event
	 */
	public String findEventExpense(String eventId) {
		String returnResponse = null;
		HashMap<String, Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		BigDecimal returnAmount = null;
		if (event != null) {
			returnAmount = event.getEventExpense();
			returnResponse = "Event " + event.getEventName() + " expense: "
					+ returnAmount;
		} else
			returnResponse = "Incorrect Event :" + eventId;
		return returnResponse;
	}

	/*
	 * Determines the settlement pending to be settled for an event
	 */
	public String getSettlementLeftForTheEvent(String eventId) {	
		
		HashMap<String, Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		BigDecimal eventExpsense = null;
		BigDecimal settlementAmount = null;
		String response = null;
		if (event != null) {
			if (event.isSettled())
				response = "Event " + event.getEventName() + " got settled";
			else {
				eventExpsense = event.getEventExpense();
				System.out.println("Event expense " + eventExpsense);
				settlementAmount = event.getSettlementAmount();
				System.out.println("Event Settlement Amount " + settlementAmount);
				if (settlementAmount == null) {
					settlementAmount = eventExpsense;
					response = "Event " + event.getEventName()
							+ " settlement amount: " + settlementAmount
							+ " pending settlement";
				} else
					response = "Event " + event.getEventName()
							+ " settlement amount: "
							+ eventExpsense.subtract(settlementAmount)
							+ " pending settlement";
			}
		} else
			response = "Incorrect Event :" + eventId;

		return response;
	}

	/*
	 * Determines cost per person for an event
	 */
	public String determineCostPerPersonForAnEvent(String eventId) {
		BigDecimal eventCostPerson = null;
		HashMap<String, Event> eventData = dataManager.getEventData();
		Event event = eventData.get(eventId);
		String response = null;
		if (event != null) {
			eventCostPerson = event.getSplits().getAmount();
			response = "Event " + event.getEventName() + " costed "
					+ eventCostPerson + " per person";
		} else
			response = "Incorrect Event :" + eventId;
		return response;
	}

	/*
	 * Persists and process the settlement
	 */

	public String createSettlement(String eventId, String payerId,
			String receiverId, String settlementAmount) {

		Settlement settlement = new Settlement();
		settlement.setEventId(eventId);
		settlement.setPayer(payerId);
		settlement.setReceiver(receiverId);
		settlement.setAmountSettled(new BigDecimal(settlementAmount));
		HashMap<String, Person> personData = dataManager.getPersonData();
		HashMap<String, Event> eventData = dataManager.getEventData();
		String response = null;
		try {
			if (eventData.get(eventId) != null) {
				if (personData.get(payerId) != null
						&& personData.get(receiverId) != null) {

					if (personData.get(receiverId).getPersonsNeedtoPay()
							.get(payerId) != null) {
						settlement.addSettlement(dataManager.getDataFilePath(),
								personData.get(receiverId));
						response = "Settlement created and processed succefully";
					} else
						response = "Settlement failed - Invalid Settlement as payer "
								+ payerId + " owes no money to " + receiverId;
				} else
					response = "Settlement failed - One or both of persons associated with settlement"
							+ " doesn't exist in the system";
			} else
				response = "Settlement failed - Invalid event associated with the settlement";

		} catch (IOException ex) {
			ex.printStackTrace();
			response = "Settlement failed";
		}
		return response;
	}

	/*
	 * Persists and process the settlement
	 */
	public String createEvent(String eventId, String eventName,
			String eventExpense, String eventParticipants, String spender) {
		Event event = new Event();
		event.setEventId(eventId);
		event.setEventName(eventName);
		event.setEventExpense(new BigDecimal(eventExpense));
		String[] participants = eventParticipants
				.split(Constants.PARTICIPATNT_SPLITBY);
		event.setParticipantsList(participants);

		HashMap<String, Person> personData = dataManager.getPersonData();
		HashMap<String, Event> eventData = dataManager.getEventData();
		String response = null;

		try {
			if (areValidParticipants(participants, spender, personData)) {
				event.addEvent(dataManager.getDataFilePath(), personData,
						spender);
				eventData.put(event.getEventId(), event);
				response = "Event created " + eventId
						+ "  and processed successfully";
			} else
				response = "Invalid Event because participants or spender data not exists in system";

		} catch (IOException ex) {
			ex.printStackTrace();
			response = "Event creation failed";

		}
		return response;
	}

	private boolean areValidParticipants(String[] eventParticipants,
			String spender, HashMap<String, Person> personData) {
		boolean isValid = true;

		for (int index = 0; index < eventParticipants.length; index++)
			if (personData.get(eventParticipants[index]) == null)
				isValid = false;

		if (personData.get(spender) == null)
			isValid = false;

		return isValid;

	}
}

package com.ps.cs.entities;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import com.ps.cs.Constants;

public class Settlement {
	
	
	private String eventId;
	private BigDecimal amountSettled;	
	private String payer;
	private String receiver;
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public BigDecimal getAmountSettled() {
		return amountSettled;
	}
	public void setAmountSettled(BigDecimal amountSettled) {
		this.amountSettled = amountSettled;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**On Demand Changes**/
	// Assumption - Settlement file exists with header
	public void addSettlement(String dataFilePath, Person receiverObj) throws IOException
	{				
		BufferedWriter output = null;	
		StringBuffer settlement = new StringBuffer();
				
		
		
		try {
			
			output = new BufferedWriter(new FileWriter(dataFilePath+"/"+ Constants.SETTLEMENTTYPE +".db",true));
			settlement.append("\n");
			settlement.append(eventId);
			settlement.append(Constants.SPLITBY);
			settlement.append(payer);
			settlement.append(Constants.SPLITBY);
			settlement.append(receiver);
			settlement.append(Constants.SPLITBY);
			settlement.append(amountSettled);
			
			output.write(settlement.toString());
			output.flush();		
			
			receiverObj.acceptSettlement(this);

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
	
	
	
	/**On Demand Changes**/
	
	

}

package com.ps.cs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class QueryRunnable implements Runnable {
	
	protected Socket clientSocket = null;
	protected CostSplitManager appManager = null;
    

    public QueryRunnable(Socket clientSocket, CostSplitManager appManager) {
        this.clientSocket = clientSocket;       
        this.appManager = appManager;
    }

    public void run() {
    	BufferedReader  is = null;
	    PrintWriter os =null;
	    String line = null;
	    String response = null;
    	try {
    	
    	is= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    os=new PrintWriter(clientSocket.getOutputStream());
	    line=is.readLine();
	    System.out.println("Query received by the server :" + line);
	    response = performQuery(line);	   
	    os.println(response);
        os.flush();
    	}catch (IOException ex)
    	{
    		ex.printStackTrace();
    	} finally {    		 
    			try{
 			 	if ( is != null )
 			 		is.close();
 			 	if ( os != null )
 			 		os.close(); 			 	
 			 	if( clientSocket != null)
 			 		clientSocket.close();
 		     //   System.out.println("Connection Closed");
    			}catch (IOException ex){
    				ex.printStackTrace();
    			}
 		
    	}
    }
    
    private String performQuery (String line)
    {
    	String[] queryValues = line.split(Constants.SPLITBY);
    	String queryType = queryValues [queryValues.length - 1];    
    	String response = null;
    	
    	synchronized (appManager) {
    		switch(new Integer(queryType).intValue() ){
    			case 1:
    				response = appManager.determineAmountPayablePersonbyPersion(queryValues[0], queryValues[1]);
    				break;
    			case 2:
    				response = appManager.findEventExpense(queryValues[0]);//Event Id is input
    				break;
    			case 3:
    				response = appManager.getSettlementLeftForTheEvent(queryValues[0]);//Event Id is input
    				break;    				
    			case 4:
    				response = appManager.determineCostPerPersonForAnEvent(queryValues[0]);//Event Id is input
    				break;
    			case 5:
    				response = appManager.createSettlement(queryValues[0],queryValues[1],queryValues[2],queryValues[3]);//Event Id is input
    				break;    				
    			case 6:
    				response = appManager.createEvent(queryValues[0],queryValues[1],queryValues[2],queryValues[3],queryValues[4]);//Event Id is input
    				break;
    			default:
    				response = "Invalid Query";
    				
    		}
		}
    	
    	
    	
    	return response;
    	
    }

}

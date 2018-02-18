package com.ps.cs.queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

import com.ps.cs.Constants;
import com.ps.cs.entities.Event;
import com.ps.cs.entities.Person;
import com.ps.cs.vo.Settlement;

public class CreateAnEvent {
		
	public static void main ( String args[] ) throws Exception 
	{
		Socket s = null;
		BufferedReader br=null;
	    BufferedReader is=null;
	    PrintWriter os=null;
	    String response=null;
	    StringBuffer inputMessage = null;
	    StringBuffer participantBuffer = new StringBuffer();
		try {
			
				if ( args != null && args.length != 5 ) {
					System.out.println("Query Invalid.Please send valid event id, event name, event expense, event participants and spender in format like this"
							+ "1 Event1 100.00 3:4:5 3");
					return;
				}
				InetAddress host = InetAddress.getLocalHost();
				inputMessage = new StringBuffer();
		        s = new Socket(host.getHostName(), Constants.SERVERPORT);        
		        br= new BufferedReader(new InputStreamReader(System.in));
		        is=new BufferedReader(new InputStreamReader(s.getInputStream()));
		        os= new PrintWriter(s.getOutputStream());
		        inputMessage.append(args[0]);        //Event Id
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(args[1]);  // Event Name
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(args[2]);  // Event Expense
		        inputMessage.append(Constants.SPLITBY);
		        
		        /*String participants[] = args[3].split(Constants.PARTICIPATNT_SPLITBY);
		        for (int index = 0; index < participants.length;index++ ){
		        	
		        	participantBuffer.append (participants[index]);
		        	if ( index < participants.length - 1)
		        		participantBuffer.append (Constants.SPLITBY);
		        	
		        }	        
		        
		        inputMessage.append(participantBuffer.toString());*/
		        
		        inputMessage.append(args[3]);		        
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(args[4]);
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(Constants.CREATEEVENTQUERY);//Query Type
		        
		      
		        os.println(inputMessage);
		        os.flush();
		        response=is.readLine();
		        System.out.println("Query Response : "+response);       
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
		}
		 finally{

			 	if ( is != null )
			 		is.close();
			 	if ( os != null )
			 		os.close();
			 	if (br != null )
			 		br.close();
			 	if( s != null)
			 		s.close();
		        System.out.println("Connection Closed");
		}
        
	}

}

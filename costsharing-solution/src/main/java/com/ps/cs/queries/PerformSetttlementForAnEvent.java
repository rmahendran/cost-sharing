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
import com.ps.cs.entities.Person;
import com.ps.cs.vo.Settlement;

public class PerformSetttlementForAnEvent {
	
	
	public static void main ( String args[] ) throws Exception 
	{
		Socket s = null;
		BufferedReader br=null;
	    BufferedReader is=null;
	    PrintWriter os=null;
	    String response=null;
	    StringBuffer inputMessage = null;;
		try {
			
				if ( args != null && args.length != 4 ) {
					System.out.println("Query Invalid.Please send valid event id, Payer Id, Receiver Id and Settle Amount ");
					return;
				}
			//	InetAddress host = InetAddress.getLocalHost();
				inputMessage = new StringBuffer();
		        s = new Socket("localhost", Constants.SERVERPORT);        
		        br= new BufferedReader(new InputStreamReader(System.in));
		        is=new BufferedReader(new InputStreamReader(s.getInputStream()));
		        os= new PrintWriter(s.getOutputStream());
		        inputMessage.append(args[0]);        //Event Id
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(args[1]);  // Payer Person Id
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(args[2]);  // Receiver Person Id
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(args[3]);  // Settlement Amount
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(Constants.SETTLEMENTQUERY);//Query Type
		        
		      
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
		        
		}
        
	}

}

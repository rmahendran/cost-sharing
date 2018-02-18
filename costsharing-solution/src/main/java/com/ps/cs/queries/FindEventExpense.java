package com.ps.cs.queries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.ps.cs.Constants;

/*
 *  Generates query, communicates with server to determine the expense incurred for a given event id
 */

public class FindEventExpense {
	
	
	public static void main ( String args[] ) throws Exception 
	{
		Socket s = null;
		BufferedReader br=null;
	    BufferedReader is=null;
	    PrintWriter os=null;
	    String response=null;
	    StringBuffer inputMessage = null;;
		try {
			
				if ( args != null && args.length != 1 ) {
					System.out.println("Query Invalid.Please send valid event id ");
					return;
				}						
				inputMessage = new StringBuffer();			
				s = new Socket("localhost", Constants.SERVERPORT);
		        br= new BufferedReader(new InputStreamReader(System.in));
		        is=new BufferedReader(new InputStreamReader(s.getInputStream()));
		        os= new PrintWriter(s.getOutputStream());
		        inputMessage.append(args[0]);        
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(Constants.EVENTEXPENSEQUERY);//Query Type    
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

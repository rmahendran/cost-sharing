package com.ps.cs.queries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.ps.cs.Constants;

/*
 * Generates query, communicates with server to identify how much one person owes to another person
 */
public class FindAmountPersonByPerson {
	
	
	public static void main ( String args[] ) throws Exception 
	{
		Socket s = null;
		BufferedReader br=null;
	    BufferedReader is=null;
	    PrintWriter os=null;
	    String response=null;
	    StringBuffer inputMessage = null;;
		try {
			if ( args != null && args.length != 2 ) {
				System.out.println("Invalid arguements. First arguement should be th id of person who owes money and"
						+ "Second arguement is the id of person who is required to get paid ");
				return;
			}
			
			inputMessage = new StringBuffer();
	        s = new Socket("localhost", Constants.SERVERPORT);        
	        br= new BufferedReader(new InputStreamReader(System.in));
	        is=new BufferedReader(new InputStreamReader(s.getInputStream()));
	        os= new PrintWriter(s.getOutputStream());
	        inputMessage.append(args[0]);
	        inputMessage.append(Constants.SPLITBY);
	        inputMessage.append(args[1]);
	        inputMessage.append(Constants.SPLITBY);
	        inputMessage.append(Constants.PERSONBYPERSONQUERY);//Query Type   
	       // System.out.println("Query to server:" + inputMessage);        
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

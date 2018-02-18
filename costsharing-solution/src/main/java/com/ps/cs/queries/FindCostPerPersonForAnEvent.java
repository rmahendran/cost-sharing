package com.ps.cs.queries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.ps.cs.Constants;

public class FindCostPerPersonForAnEvent {
	
	
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
				InetAddress host = InetAddress.getLocalHost();
				inputMessage = new StringBuffer();
		        s = new Socket(host.getHostName(), Constants.SERVERPORT);        
		        br= new BufferedReader(new InputStreamReader(System.in));
		        is=new BufferedReader(new InputStreamReader(s.getInputStream()));
		        os= new PrintWriter(s.getOutputStream());
		        inputMessage.append(args[0]);        
		        inputMessage.append(Constants.SPLITBY);
		        inputMessage.append(Constants.COSTPERPERSONQUERY);//Query Type
		        
		      
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

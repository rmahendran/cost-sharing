package com.ps.cs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


 /*
  
  This class is a Server implementation which Bootstraps the application and listens on a particular port 
  to accept queries for the client
  
  Coordinates with Application manager, gets the required data and respond to the queries 
 
 */

public class CostSharingServer {
	
	
	private void startApplicationServer(int port,CostSplitManager appManager) throws IOException
	{
		ServerSocket serverSocket = null;		
	    Socket s=null;	    
	    ExecutorService threadPool = null;
	    
		try{
			System.out.println("Cost sharing server started and listening on.." + port);
			serverSocket = new ServerSocket(port);
			threadPool =   Executors.newFixedThreadPool(10);
			boolean isStopped = false;
			while(!isStopped){		
			    s = serverSocket.accept();
			    threadPool.execute(new QueryRunnable(s,appManager));		    
			}
		}catch (Exception ex)
		{
			ex.printStackTrace();			
		}finally {
			if  ( serverSocket != null && !serverSocket.isClosed() )
				serverSocket.close();			
		}
	}

	public static void main (String args[] ) throws Exception{
		CostSplitManager appManager = new CostSplitManager();
		CostSharingServer server = new CostSharingServer();
		appManager.bootStrap();
		server.startApplicationServer(Constants.SERVERPORT,appManager);
	}

}

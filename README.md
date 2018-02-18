# cost-sharing
Repository for Cost sharing application


Cost sharing application is implemented as 
  1. Maven based project. Maven should be available to build the project and execution. 
  2. Implemented as request-response style. Application bootstraps with data from the data files. Sample data files are available in the repository
  
Steps to set-up the project for execution  
  1. Build the project using Maven. Please make sure to move forward with the build even if JUnit tests fails. JUnit test created for  for few major scenrios and it may fail if data is not set as per the expected result. However it does not mean that the expected result is incorrect. Essentially expected result varies with data. Alternatively you can use skip the unit tests using skipTests option
  2. config.properties file is available under "src/main/resources". This file holds property that holds the location of data files. Change the property according to the environment where tests being performed. Have tested in Linux guest OS using VMWare Workstation
  3. Essentially there are 3 data files. Data files used for testing are uploaded
       person.db - Holds the list of persons. 
       event.db - Holds the list of events
       settlement.db - Holds the list of settlements 
       Make sure headers are retained and files are available during boot strap
       Addition of Person at runtime is not supported where as event can be added and settlement can be performed at runtime without re-starting the server
 4. Six Queries (Implementation) are available. Which are programs with main method. Right arguments have to be passed. When right arguments, passed it communicates with the server and gets the response. Response is implemented as Textual fashion to mimick querying. Following are the 6 queries. Run these queries without arguments to find out the arguements needed to pass
       1. com.ps.cs.queries.FindAmountPersonByPerson
       2. com.ps.cs.queries.FindCostPerPersonForAnEvent
       3. com.ps.cs.queries.FindEventExpense
       4. com.ps.cs.queries.FindSetttlementLeftForAnEvent
       5. com.ps.cs.queries.PerformSetttlementForAnEvent
       6. com.ps.cs.queries.CreateAnEvent
 5. Main file is configured in POM.xml, run the server using java -jar costsharing-solution-0.0.1-SNAPSHOT.jar. Main file com.ps.cs.CostSharingServer which boot straps the application by loading data from person.db,event.db & settlement.db and listens for query clients
       

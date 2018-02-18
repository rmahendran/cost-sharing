
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ps.cs.CostSplitManager;

public class CostSharingTest {
	
	 private CostSplitManager splitManager;
	 @Before
	 public void setUp() {
		 splitManager = new CostSplitManager(); 
		 splitManager.bootStrap();
	 }
	 
	 @Test
	 public void determineAmountPayablePersonbyPersionTest(){	    
		 	String actualValue = splitManager.determineAmountPayablePersonbyPersion("1","2");	
		 	System.out.println("actualValue:" + actualValue);
		 	Assert.assertTrue(actualValue.equalsIgnoreCase( "Person 1 owes no money to person 2"));	 	
	 }
	 
	 @Test
	 public void findEventExpense(){	    
		    String actualValue = splitManager.findEventExpense("2");
		    System.out.println("actualValue:" + actualValue);
		 	Assert.assertTrue(actualValue.equalsIgnoreCase("Event Event2 expense: 100.00"));
	 }
	 
	 @Test
	 public void getSettlementLeftForTheEvent(){	    
		 	String actualValue = splitManager.getSettlementLeftForTheEvent("1");
		 	System.out.println("actualValue:" + actualValue);
		 	Assert.assertTrue(actualValue.equalsIgnoreCase("Event Event1 got settled"));		 	
	 }
	 
	 @Test
	 public void determineCostPerPersonForAnEvent(){	    
		 	String actualValue = splitManager.determineCostPerPersonForAnEvent("1");
		 	System.out.println("actualValue:" + actualValue);
		 	Assert.assertTrue(actualValue.equalsIgnoreCase("Event Event1 costed 50.00 per person"));
	 }	  
	 
}

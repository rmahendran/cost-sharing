
import java.math.BigDecimal;

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
		// 	BigDecimal actualValue = splitManager.determineAmountPayablePersonbyPersion("1","2");
		 	BigDecimal expectedValue = new BigDecimal(50.00);
		// 	Assert.assertTrue(actualValue.compareTo(expectedValue) == 0);
	 }
	 
	 @Test
	 public void findEventExpense(){	    
		// 	BigDecimal actualValue = splitManager.findEventExpense("2");
		 	BigDecimal expectedValue = new BigDecimal(100.00);
		// 	Assert.assertTrue(actualValue.compareTo(expectedValue) == 0);
	 }
	 
	 @Test
	 public void getSettlementLeftForTheEvent(){	    
		// 	BigDecimal actualValue = splitManager.getSettlementLeftForTheEvent("1");
		 	BigDecimal expectedValue = new BigDecimal(100.00);
		// 	Assert.assertTrue(actualValue.compareTo(expectedValue) == 0);
	 }
	 
	 @Test
	 public void determineCostPerPersonForAnEvent(){	    
		// 	BigDecimal actualValue = splitManager.determineCostPerPersonForAnEvent("1");
		 	BigDecimal expectedValue = new BigDecimal(50.00);
		// 	Assert.assertTrue(actualValue.compareTo(expectedValue) == 0);
	 }

	  

}

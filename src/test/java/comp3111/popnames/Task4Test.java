package comp3111.popnames;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.Scene;

public class Task4Test {

	@Test
	public void testCheckSummary() {
		Task4 t = new Task4("John", "Mary", 1880, 1880, 1);
		String str = "For a baby boy, we recommend George.\n" + 
				"This name is very similar to dad's in popularity in year 1880. Out of 1058 male names, George is rank 5.\n" + 
				"And baby's dad name John is rank 1.\n" + 
				"With a name of similar popularity, we believe this baby boy will share a similar feeling as his dad towards their name, \n" + 
				"forming more special family bonds.\n" + 
				"\n" + 
				"For a baby girl, we recommend Minnie.\n" + 
				"This name is very similar to mom's in popularity in year 1880. Out of 942 female names, Minnie is rank 5.\n" + 
				"And baby's mom name Mary is rank 1.\n" + 
				"With a name of similar popularity, we believe this baby girl will share a similar feeling as her mom towards their name, \n" + 
				"forming more special family bonds.\n" + 
				"\n";
		assertEquals(t.getSummary(), str);
	}
	
	@Test
	public void testCheckSummary2() {
		Task4 t = new Task4("T", "M", 1880, 1880, 1);
		String str = "For a baby boy, we recommend Woodie.\n" + 
				"This name is very similar to dad's in popularity in year 1880. Out of 1058 male names, Woodie is rank 1054.\n" + 
				"This is because baby's dad name T is one we can't find it in our database, so it must be super rare.\n" + 
				"With a name of similar popularity, we believe this baby boy will share a similar feeling as his dad towards their name, \n" + 
				"forming more special family bonds.\n" + 
				"\n" + 
				"For a baby girl, we recommend Ula.\n" + 
				"This name is very similar to mom's in popularity in year 1880. Out of 942 female names, Ula is rank 938.\n" + 
				"This is because baby's mom name M is one we can't find it in our database, so it must be super rare.\n" + 
				"With a name of similar popularity, we believe this baby girl will share a similar feeling as her mom towards their name, \n" + 
				"forming more special family bonds.\n" + 
				"\n";
		assertEquals(t.getSummary(), str);
	}
	
//	@Test
//	public void testMTableChart() {
//		Task4 t = new Task4("Tony", "Mary", 1880, 1880, 1);
//		Scene s = t.getMTableCharts();
//		assertNotEquals(s, null);
//	}
	
//	@Test
//	public void testFTableChart() {
//		Task4 t = new Task4("Tony", "Mary", 1880, 1880, 1);
//		assertNotEquals(t.getFTableCharts(), null);
//	}
	

}

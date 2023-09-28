package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;
import javafx.scene.Scene;

public class Task3Test {
	
	@Test
	public void testSummaryNormal(){
		Task3 t3 = new Task3(1997,2010,"M","Jordan");
		String result = t3.getSummary();
		assertEquals(result,"The year when the name Jordan was most popular is 1997 at rank 26.\n"
				+ " In that year, the number of occurrences is 14759,\n"
				+ " which represents 9.742814 percent of total male births in 1997.");
	}
	
	@Test
	public void testSummaryWrongName() {
		Task3 t3 = new Task3(1997,2010,"M","aeigabie");
		String result = t3.getSummary();
		assertEquals(result,"The name is not found in any of the years.");
	}
	
	@Test
	public void testSummaryWrongYear() {
		Task3 t3 = new Task3(2050,2060,"M","Jordan");
		String result = t3.getSummary();
		assertEquals(result,"The name is not found in any of the years.");
	}
	
	@Test
	public void testGenerateTableCharts() {
		Task3 t3 = new Task3(1997,2010,"M","Jordan");
		Scene scene = t3.generateTableCharts();
		assertNotNull(scene);
	}
	

}

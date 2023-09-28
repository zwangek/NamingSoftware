package comp3111.popnames;

import static org.junit.Assert.*;

import org.junit.Test;

public class Task1Test {

	@Test
	public void testCheckInputCorrect() {
		Task1 t = new Task1(2000, 5);
		assertEquals(t.checkInput(), true);
	}
	
	@Test
	public void testCheckInputWrongYear() {
		Task1 t = new Task1(2020, 5);
		assertEquals(t.checkInput(), false);
	}
	
	@Test
	public void testCheckInputWrongNum() {
		Task1 t = new Task1(2000, 11);
		assertEquals(t.checkInput(), false);
	}
	
	@Test
	public void testCheckSummary() {
		Task1 t = new Task1(2000, 2);
		String str = "Summary (Year of 2000):\n\n" + 
				"Jacob is the most popular name with the number of occurrences of 34465, \n" + 
				"which represents 1.76% of total male births in 2000.\n\n" + 
				"Emily is the most popular name with the number of occurrences of 25952, \n" + 
				"which represents 1.43% of total female births in 2000.\n\n";
		assertEquals(t.getSummary(), str);
	}
	


}

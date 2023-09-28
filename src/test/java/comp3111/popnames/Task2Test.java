package comp3111.popnames;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task2Test {
	Task2 t2FwithHighRank;
	Task2 t2MwithLowRank;
	
	@Before
	public void setUp() {
		t2FwithHighRank = new Task2(2010, 2019, 1, 'F');
		t2MwithLowRank = new Task2(1880, 2019, 50, 'M');
	}
	
	@Test
	public void testTask2GetSummary() {
		String expected = "Summary:\n"
				+ "Emma has hold the 1-th rank most often for a total of 5 times among the names registered for baby girls born in the period from 2010 to 2019."
				+ " The total number of occurrences of Emma is 99366, which represents 48.3% of total female births at the 1-th rank in the period from 2010 to 2019¡£";
		assertEquals(expected, t2FwithHighRank.getSummary());
		
		expected = "Summary:\n"
				+ "Jesse has hold the 50-th rank most often for a total of 6 times among the names registered for baby boys born in the period from 1880 to 2019."
				+ " The total number of occurrences of Jesse is 32675, which represents 4.7% of total male births at the 50-th rank in the period from 1880 to 2019¡£";
		assertEquals(expected, t2MwithLowRank.getSummary());
	}
	
	@Test
	public void testT2Scene() {
		assertTrue(t2FwithHighRank.getTableCharts() != null);
	}
}

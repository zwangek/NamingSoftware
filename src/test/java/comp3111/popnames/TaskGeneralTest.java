package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;

public class TaskGeneralTest{
	
	@Test
	public void testGetRecordsSuccess() {
		assertTrue(TaskGeneral.getRecords(1999) != null);
	}
	
	@Test
	public void testGetRecordsFail() {
		assertTrue(TaskGeneral.getRecords(2020) == null);
	}

	@Test
	public void testGetTotalNamesFemale() {
		assertTrue(TaskGeneral.getTotalNames(2019, 'F') == 17905);
	}
	
	@Test
	public void testGetTotalNamesMale() {
		assertTrue(TaskGeneral.getTotalNames(2019, 'M') == 14049);
	}
	
	@Test
	public void testGetTotalBabiesFemale() {
		assertTrue(TaskGeneral.getTotalBabies(2018, 'F') == 1694640);
	}

	@Test
	public void testGetTotalBabiesMale() {
		assertTrue(TaskGeneral.getTotalBabies(2018, 'M') == 1809166);
	}
	
	@Test
	public void testGetTotalBabiesFail() {
		assertEquals(-1, TaskGeneral.getTotalBabies(2018, 2017, 'M'));
	}
	
	@Test
	public void testGetTotalBabiesPeriodMale() {	
		assertEquals(179306065, TaskGeneral.getTotalBabies(1880, 2019, 'M'));
	}

	@Test
	public void testGetTotalBabiesPeriodFemale() {
		assertEquals(175701667, TaskGeneral.getTotalBabies(1880, 2019, 'F'));
	}
	
	@Test
	public void testGetRecordSuccessFemale() {
		assertTrue(TaskGeneral.getRecord(2019, 1, 'F').get(0).equals( "Olivia"));
		assertTrue(TaskGeneral.getRecord(2019, 1, 'F').get(1).equals("F"));
		assertTrue(TaskGeneral.getRecord(2019, 1, 'F').get(2).equals("18451"));
	}
	
	public void testGetRecordSuccessMale() {
		assertTrue(TaskGeneral.getRecord(2019, 1, 'M').get(0).equals( "Liam"));
		assertTrue(TaskGeneral.getRecord(2019, 1, 'M').get(1).equals("M"));
		assertTrue(TaskGeneral.getRecord(2019, 1, 'M').get(2).equals("20502"));
	}
	
	
	@Test
	public void testGetRecordFail() {
		assertTrue(TaskGeneral.getRecord(2019, 99999999, 'M') == null);
	}

}


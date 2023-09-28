package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;

public class AnalyzeNamesTest {
	
    @Test 
    public void testGetRankNotFound() {
    	AnalyzeNames a = new AnalyzeNames();
    	int i = a.getRank(2019, "XXX", "M");
		assertEquals(i, -1);
    }
    
    @Test 
    public void testGetRankMale() {
    	AnalyzeNames a = new AnalyzeNames();
    	int i = a.getRank(2019, "David", "M");
    	assertTrue(i==27);
    }
    
    @Test 
    public void testGetRankFemale() {
    	AnalyzeNames a = new AnalyzeNames();
    	int i = a.getRank(2019, "Desire", "F");
    	assertTrue(i==2192);
    }
    	
    @Test 
    public void testGetNameMale() {
    	AnalyzeNames a = new AnalyzeNames();
    	String name = a.getName(2019, 27, "M");
    	assertTrue(name.equals("David"));
    }
    
    @Test 
    public void testGetNameFemale() {
    	AnalyzeNames a = new AnalyzeNames();
    	String name = a.getName(2019, 2192, "F");
    	assertTrue(name.equals("Desire"));
    }

    @Test 
    public void testGetNameNotFound() {
    	AnalyzeNames a = new AnalyzeNames();
    	String name = a.getName(1880, 1000000, "F");
    	assertTrue(name.equals("information on the name at the specified rank is not available"));
    }
    
    @Test 
    public void testGetSummary() {
    	String summary = AnalyzeNames.getSummary(1880);
    	assertTrue(summary.equals("Summary (Year of 1880):\n" + 
    			"Total Births = 201,484\n" + 
    			"***Baby Girls = 90,993\n" + 
    			"***Baby Boys = 110,491\n" + 
    			"Total Number of Unique Names = 2,000\n" + 
    			"***Unique Names (baby girls) = 942\n" + 
    			"***Unique Names (baby boys) = 1,058\n" + 
    			""));
    }
   
    @Test
    public void testGetFileParserSuccess() {
    	assertTrue(AnalyzeNames.getFileParser(2015) != null);
    }

    @Test
    public void testSummary() {
    	String expected = "Summary (Year of 2019):\n"
    			+ "Total Births = 3,445,321\n"
    			+ "***Baby Girls = 1,665,373\n"
    			+ "***Baby Boys = 1,779,948\n"
    			+ "Total Number of Unique Names = 31,954\n"
    			+ "***Unique Names (baby girls) = 17,905\n"
    			+ "***Unique Names (baby boys) = 14,049\n"
    			+ "";
    	assertTrue(AnalyzeNames.getSummary(2019).equals(expected));
    }
}

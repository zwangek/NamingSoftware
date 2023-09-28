package comp3111.popnames;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.Scene;

public class Task5Test {
	Task5 t5withDifferentGender = new Task5("Tom", 'M', 1945, 'F', 5, true);
	Task5 t5withSameGender= new Task5("Mary", 'F', 2010, 'F', 20, false);
	
	@Test
	public void testT5X1Summary() {
		String expected = "A simple recommendation: Mary\n"
				+ "Mary is the most popular female name in your birth year 1945.\n";
		assertTrue(t5withDifferentGender.algorithmT5X1().equals(expected));
		
		expected = "A simple recommendation: Isabella\n"
				+ "Isabella is the most popular female name in your birth year 2010.\n";
		assertTrue(t5withSameGender.algorithmT5X1().equals(expected));
	}
	
	@Test
	public void testT5X2Summary() {
		String expected = "\nA slightly more complicated recommendation: Brooke.\n"
				+ "Among all names for people born at most 20 years earlier than your birth year 2010, female name Brooke"
				+ " and your female name Mary have the most number of similar rank (5 times) in the period 1990~2010."
				+ " Your name is as 'RARE' as this soulmate name. Find a Brooke around you and you can definately become good friends.";
		assertTrue(t5withSameGender.summaryT5X2().equals(expected));
		
		expected =  "\nA slightly more complicated recommendation: Jeanne.\n"
				+ "Among all names for people born at most 5 years earlier than your birth year 1945, female name Jeanne and your male name Tom have the most number of similar rank (2 times) in the period 1945~1950. Your name is as 'RARE' as this soulmate name. Find a Jeanne around you and you can definately become good friends.";
		assertTrue(t5withDifferentGender.summaryT5X2().equals(expected));
	}
	
	@Test
	public void testT5Scene() {
		assertTrue(t5withDifferentGender.getSceneT5X2() != null);
		assertTrue(t5withSameGender.getSceneT5X2() != null);
	}
}

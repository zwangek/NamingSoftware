package comp3111.popnames;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputCheckerGeneralTest {	
	@Test
	public void testIsDigit() {
		String overflow = "9999999999999999";
		String notADigit = "123abc";
		String valid = "1930";
		String empty = "";
		
		assertTrue(InputCheckerGeneral.isDigit(overflow) == false);
		assertTrue(InputCheckerGeneral.isDigit(notADigit) == false);
		assertTrue(InputCheckerGeneral.isDigit(valid) == true);
		assertTrue(InputCheckerGeneral.isDigit(empty) == false);
	}
	
	@Test
	public void testIsInBound() {
		String empty = "";
		String belowBound = "1200";
		String aboveBound = "2020";
		String inBound = "1950";
		String notNumber = "abc";
		String symbols = "1&$(*&";
		
		assertTrue(InputCheckerGeneral.isInBound(empty) == false);
		assertTrue(InputCheckerGeneral.isInBound(belowBound) == false);
		assertTrue(InputCheckerGeneral.isInBound(aboveBound) == false);
		assertTrue(InputCheckerGeneral.isInBound(inBound) == true);
		assertTrue(InputCheckerGeneral.isInBound(notNumber) == false);
		assertTrue(InputCheckerGeneral.isInBound(symbols) == false);
	}
	
	@Test
	public void testIsPureChars() {
		String empty = "";
		String notChars = "abc221";
		String name = "Tom";
		String weird = "!^%${]/";
		
		assertTrue(InputCheckerGeneral.isPureChars(weird) == false);
		assertTrue(InputCheckerGeneral.isPureChars(empty) == false);
		assertTrue(InputCheckerGeneral.isPureChars(notChars) == false);
		assertTrue(InputCheckerGeneral.isPureChars(name) == true);
	}

}

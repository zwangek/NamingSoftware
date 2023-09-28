package comp3111.popnames;
/**
 * Base class for input checker. Provide utilities. 
 * @author WANG, Zeyu
 *
 */
public abstract class InputCheckerGeneral {    
	/**
	 * Check whether the str is a number and will not overflow
	 * It will not check (return true) empty condition.
	 * @param str string to be checked
	 * @return True if str is a number and will not overflow. Otherwise, false.
	 */
	public static boolean isDigit (String str) {
		// empty
		if (str.isEmpty()) return false;
		// check length
		if (str.length() > 9) return false;
		// check char
		for (int i = 0; i != str.length(); i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') return false;
		}
		return true;
	}
	
	/**
	 * Check whether str is a valid number and is between 1880-2019
	 * @param str string to be checked.
	 * @return True if str is a valid number and is between 1880*2019
	 */
	public static boolean isInBound (String str) {
		if (str.isEmpty()) return false;
		if (!isDigit(str)) return false;
		int val = Integer.parseInt(str);
		if (val < 1880 || val > 2019) return false;
		return true;
	}
	
	/**
	 * Check whether str is consists of english characters only.
	 * It will capitalized the first letter if it is in lower case.
	 * @param str string to be checked
	 * @return True if str is consists of 'A'-'Z' for the first letter and 'a'-'z' for the rest.
	 */
	public static boolean isPureChars (String str) {
		if (str.isEmpty()) return false;
		char firstChar = str.charAt(0);
		if (firstChar < 'A' || firstChar > 'z' || (firstChar >= 91 && firstChar <= 96)) return false;
		// capitalized firstChar
		if (firstChar >= 'a' && firstChar <= 'z') str = String.valueOf((char)(firstChar-32)) + str.substring(1).toLowerCase();
		for (int i = 1; i != str.length(); i++) {
			if (str.charAt(i) < 'a' || str.charAt(i) > 'z') return false;
		}
		return true;
	}
	
	/**
	 * A concrete subclass can use this to run its checking procedure.
	 */
	public abstract void check();
}

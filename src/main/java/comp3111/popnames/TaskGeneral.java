package comp3111.popnames;

import java.util.List;
import org.apache.commons.csv.*;
import edu.duke.*;
/**
 * Utilities for Task2 and Task5.
 * This is an abstract class. Task2 and Task5 extend it and are concrete classes.
 * @author WANG, Zeyu
 *
 */
public abstract class TaskGeneral {
	/**
	 * Get a CSV file of certain year from database.
	 * @param year The year of the csv file.
	 * @return A CSV file of that year.
	 */
	public static CSVParser getFileParser(int year) {
	     FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
	     return fr.getCSVParser(false);
	}
	
	/**
	 * Get all the CSVRecords of all names in a certain year.
	 * This method puts all the csv records (lines) in the csv file of the year into a list
	 * @param year The year of the csv file.
	 * @return A list of csv records.
	 */
	public static List<CSVRecord> getRecords (int year) {
		List<CSVRecord> res = null;
		try {
			res = getFileParser(year).getRecords();
		}
		catch (Exception e) {
			System.out.println("Exception when calling getRecords");
			e.printStackTrace();
		}
		return res; 
	}
	
	/**
	 * Get total number of distinct names of certain gender in a certain year
	 * @param year Year to be checked.
	 * @param gender Gender to be checked.
	 * @return Total number of distinct names
	 */
	public static int getTotalNames(int year, char gender) {
		int res = 0;
		for (CSVRecord rec : getRecords(year)) {
			if (rec.get(1).charAt(0) != gender) continue;
			res++;
		}
		return res;
	}

	/**
	 * Get the total number of babies of certain gender in a certain year
	 * @param year Year to be checked.
	 * @param gender Gender to be checked.
	 * @return Total number of babies of certain gender in a certain year.
	 */
	public static int getTotalBabies (int year, char gender) {
		int res = 0;
		for (CSVRecord rec : getRecords(year)) {
			if (rec.get(1).charAt(0) != gender) continue;
			res += Integer.parseInt(rec.get(2));
		}
		return res;
	}
	

	/**
	 * Get the total number of babies of certain gender in a period.
	 * @param startYear Start year of the period.
	 * @param endYear End year of the period.
	 * @param gender Gender to be checked.
	 * @return Total number of babies of certain gender in a period.
	 */
	public static int getTotalBabies (int startYear, int endYear, char gender) {
		if (endYear < startYear) return -1;
		int res = 0;
		for (int year = startYear; year <= endYear; year++) {
			for (CSVRecord rec : getRecords(year)) {
				if (rec.get(1).charAt(0) != gender) continue;
				res += Integer.parseInt(rec.get(2));
			}
		}
		return res;
	}

	/**
	 * Get the CSVRecord (one line of result)for certain years, rank and gender
	 * @param year Year of the csv file
	 * @param rank Rank of a name
	 * @param gender Gender of a name
	 * @return A CSVRecord in [NAME, GENDER, OCCURENCE].
	 */
	public static CSVRecord getRecord (int year, int rank, char gender) {
		if (rank > getTotalNames(year, gender)) return null;
		int index = 0;
		for (CSVRecord rec : getRecords(year)) {
			if (rec.get(1).charAt(0) != gender) index++;
			else
				return getRecords(year).get(index + rank - 1);
		}
		return null;
	}
	
	/**
	 * Get the rank of a certain name for certain gender in certain year.
	 * @param year Year to be checked.
	 * @param name Name to be checked.
	 * @param gender Gender to be checked.
	 * @return Rank of a certain name for certain gender in certain year.
	 */
	 public static int getRank(int year, String name, char gender) {
	     boolean found = false;
	     int oRank = 0;
	 	int rank = 1;
	     for (CSVRecord rec : getFileParser(year)) {
	         // Increment rank if gender matches param
	         if (rec.get(1).charAt(0) == gender) {
	             // Return rank if name matches param
	             if (rec.get(0).equals(name)) {
	             	found = true;
	             	oRank = rank;
	             	break;
	             }
	             rank++;
	         }
	     }
	     if (found)
	     	return oRank;
	     else
	     	return getTotalNames(year, gender);
	 }
}

package comp3111.popnames;

import java.lang.Math;

import javafx.scene.layout.HBox;
import javafx.scene.Scene;

/**
 * this class handles all inquiries of Task6, no need to create an object, use static methods only
 */
public class Task6 {

	/**
	 * static function that return a summary according to algorithm X1
	 * @param iName
	 * @param mName
	 * @return summary
	 */
	public static String compatibility_X1(String iName, String mName){
		float score = (iName.length()==mName.length()) ? 100 : 0;
		return String.format("Your compatibility score with your mate is %f." , score);
	}
	
	/**
	 * static method that return a summary according to algorithm X2
	 * @param iName User's name
	 * @param iGender
	 * @param iYOB
	 * @param mName Mate's name
	 * @param mGender
	 * @param mYOB
	 * @param iPreference true when user prefer older mate
	 * @return summary
	 */
	public static String compatibility_X2
	(String iName, String iGender, int iYOB, String mName, String mGender, int mYOB, boolean iPreference) {
		float score = 100;
		
		int year1 = (iYOB<mYOB) ? iYOB : mYOB;
		int year2 = (iYOB<mYOB) ? mYOB : iYOB;
		Task3 t3I = new Task3 (year1,year2,iGender,iName);
		Task3 t3M = new Task3 (year1,year2,mGender,mName);
		float iRankMean = t3I.getAverageRank();
		float mRankMean = t3M.getAverageRank();
		if(iRankMean<0||mRankMean<0) {
			return "One of your names cannot be found in the dataset.";
		}
		
		double iRankMeanSqrt = Math.sqrt(iRankMean);
		double mRankMeanSqrt = Math.sqrt(mRankMean);
		if (iRankMeanSqrt>100) {iRankMeanSqrt=100;}
		if (mRankMeanSqrt>100) {mRankMeanSqrt=100;}
		float rankDiff = (float)Math.abs( iRankMeanSqrt - mRankMeanSqrt );
		float ageDiff = (iPreference)? Math.abs(iYOB-5-mYOB) : Math.abs(iYOB+5-mYOB);
		score -= rankDiff;
		score -= ageDiff;
		
		String summary = "";
		
		summary += String.format("Between you and your mate's year of birth, that is, between %d and %d,\n", year1, year2);
		summary += String.format("your name has average rank of %f," , iRankMean);
		summary += String.format(" your mate's name has a average rank of %f.\n" , mRankMean);
		
		if(rankDiff < 10)
			summary += "You and your mate's names have similar level of popularity around that time,";
		else
			summary += "You and your mate's names are far apart in terms of popularity,";
		
		if(ageDiff <= 5)
			summary += " your mate's age is just right for you.\n";
		else
			summary += " your mate's age is not preferrable.\n";
		
		summary += String.format("Therefore, your compatibility score with your mate is %f." , score);
		
		return summary;
	}
	
	/**
	 * static method that return 2 bar chart, one for each of the names
	 * @param iName
	 * @param iGender
	 * @param iYOB
	 * @param mName
	 * @param mGender
	 * @param mYOB
	 * @return bar charts
	 */
	public static Scene X2Chart(String iName, String iGender, int iYOB, String mName, String mGender, int mYOB) {
		HBox root = new HBox();
		Scene T6scene = new Scene(root);
		
		int year1 = (iYOB<mYOB) ? iYOB : mYOB;
		int year2 = (iYOB<mYOB) ? mYOB : iYOB;
		Task3 t3I = new Task3 (year1,year2,iGender,iName);
		Task3 t3M = new Task3 (year1,year2,mGender,mName);
		
		root.getChildren().addAll(t3I.generateBar(),t3M.generateBar());
		return T6scene;
	}
	
}

package comp3111.popnames;

import java.util.ArrayList;
import java.util.Comparator;

import org.apache.commons.csv.CSVRecord;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

/**
 * Task 5 handler.
 * Will be initiated after T5InputChecker has checked all inputs.
 * @author WANG, Zeyu
 *
 */
public class Task5 extends TaskGeneral {
	// A helper class to store information of table
	private class Name implements Comparable<Name>{
		private SimpleStringProperty name;
		private SimpleIntegerProperty frequency;
		private SimpleIntegerProperty occurrences;
		private SimpleDoubleProperty percentage;
		
		public Name(String name, int frequency, int occurrences) {
			this.name = new SimpleStringProperty(name);
			this.frequency = new SimpleIntegerProperty(frequency);
			this.occurrences = new SimpleIntegerProperty(occurrences);
		}
		
		public void setPercentage(double perc) { this.percentage = new SimpleDoubleProperty(perc); }
		public String getName() { return this.name.get(); }
		public int getFreq() { return this.frequency.get(); }
		public int getOccur() { return this.occurrences.get(); }
		// public double getPercentage() { return this.percentage.get(); }
		public SimpleStringProperty nameProperty() { return this.name; }
		public SimpleIntegerProperty freqProperty() { return this.frequency; }
		public SimpleIntegerProperty occurProperty() { return this.occurrences; }
		public SimpleStringProperty percProperty() { return new SimpleStringProperty(this.percentage.get() + "%"); }
		@Override
		public int compareTo(Name name) {
			if (this.getFreq() > name.getFreq()) return 1;
			else if (this.getFreq() == name.getFreq()) {	// same freq, sort by alphabetical order
				int diff = this.getName().compareTo(name.getName());
				if (diff < 0) return 1;
				else if (diff == 0) return 0;
				else return -1;
			}
			else return -1;
		}
	}
	
	private String iName;
	private char iGender;
	private int iYOB;
	private char iGenderMate;
	private int[] myRecentRanks;
	private ArrayList<Name> nameInfo;
	private String mateName;
	private int[] mateRecentRanks;
	private int startYear;
	private int endYear;
	
	/**
	 * Construct a Task5 object with following parameters
	 * @param iName user's name
	 * @param iGender user's gender
	 * @param iYOB user's year of birth
	 * @param iGenderMate soul mate gender that the user desire
	 * @param maxAgeDifference the max age difference that the user wants between the user and the soul mate
	 * @param older user's preference on the age of the soul mate. False for younger true for older
	 */
	public Task5(String iName, char iGender, int iYOB, char iGenderMate, int maxAgeDifference, boolean older) {
		this.iName = iName.substring(0, 1).toUpperCase() + iName.substring(1).toLowerCase();
		this.iGender = iGender;
		this.iYOB = iYOB;
		this.iGenderMate = iGenderMate;

		if (older) {
			startYear = iYOB;
			endYear = iYOB + maxAgeDifference;
		} else {
			startYear = iYOB - maxAgeDifference;
			endYear = iYOB;
		}
		myRecentRanks = new int[endYear - startYear + 1];
		mateRecentRanks = new int[endYear - startYear + 1];
		nameInfo = new ArrayList<Name>();
		setT5X2Info();

	}
	
	private void setT5X2Info() {
		// int totalFreq = 0;

		// Find name with similar rank in every year in the period
		ArrayList<CSVRecord> popRecords = new ArrayList<>();
		for (int year = startYear; year <= endYear; year++) {
			int myRank = getRank(year, iName, iGender);
			myRecentRanks[year - startYear] = myRank;	// my ranks
			// get close ranks
			int[] consideredRanks;
			int range = myRank / 100 + 2;
			// same gender, close ranks are myRank - range till myRank + range except myRank
			if (iGender == iGenderMate) {
				consideredRanks = new int[2 * range];
				int start;
				if (myRank - range <= 0) start = 1;
				else if (myRank + range > getTotalNames(year, iGenderMate))
					start = getTotalNames(year, iGender) - range * 2;
				else start = myRank - range;
				
				for (int i = 0; i != consideredRanks.length + 1; i++) {
					if (start + i == myRank) continue;
					else if (start + i < myRank) consideredRanks[i] = start + i;
					else consideredRanks[i - 1] = start + i;
				}
			// different gender
			} else {
				consideredRanks = new int[2 * range + 1];
				int start;
				if (myRank - range <= 0) start = 1;
				else if (myRank + range > getTotalNames(year, iGenderMate))
					start = getTotalNames(year, iGenderMate) - range * 2;
				else start = myRank - range;
				
				for (int i = 0; i != consideredRanks.length; i++) {
					consideredRanks[i] = start + i;
				}
			}
			
			// add record with close ranks
			for (int i = 0; i != consideredRanks.length; i++) {
				popRecords.add(getRecord(year, consideredRanks[i], iGenderMate));
			}
		}
		for (int i = 0; i != popRecords.size(); i++) {
			int freq = 0;
			int occur = 0;
			boolean newName = true;
			for (int j = 0; j != popRecords.size(); j++) {
				if (popRecords.get(i).get(0).equals(popRecords.get(j).get(0))) {
					if (j < i) {
						newName = false;	// name already dealt
						break;
					}
					else {
						freq++;
						occur += Integer.parseInt(popRecords.get(j).get(2));
					}
				}
			}
			if (newName) {
				nameInfo.add(new Name(popRecords.get(i).get(0), freq, occur));
			}
		}
		nameInfo.sort(Comparator.reverseOrder());
		/*
		// fill total info

		Name total = new Name("TOTAL", totalFreq, totalOccur);
		total.setPercentage(100.0);
		nameInfo.add(total);
		*/
		int totalOccur = 0;

		for (Name name : nameInfo) {
			totalOccur += name.getOccur();
		}
		
		for (Name name : nameInfo) {
			name.setPercentage(Double.parseDouble(String.format("%.1f", (double)name.getOccur() / (double)totalOccur * 100.0)));
		}

		
		// Fill matched mate's information
		mateName = nameInfo.get(0).getName();
		for (int i = 0; i != mateRecentRanks.length; i++) {
			mateRecentRanks[i] = getRank(i+startYear, mateName, iGenderMate);
		}
	}
	
	/**
	 * Simple algorithm
	 * @return the story of the most pupular name for mate gender in my birth year
	 */
	public String algorithmT5X1() {
		String oGender = iGenderMate == 'M' ? "male" : "female";
		String oName = TaskGeneral.getRecord(iYOB, 1, iGenderMate).get(0);
		return String.format("A simple recommendation: %s\n%s is the most popular %s name in your birth year %d.\n", oName, oName, oGender, iYOB);
	}
	
	/**
	 * Complicated algorithm
	 * @return
	 */
	public String summaryT5X2() {
		if (mateName == null) return "\nFailure in finding a soul mate name.\n";
		String myGender = iGender == 'M' ? "male" : "female";
		String mateGender = iGenderMate == 'M' ? "male" : "female";
		String preference = endYear >= startYear ? "earlier" : "later";
		int maxAD = endYear >= startYear ? endYear - startYear : startYear - endYear;
		String story = String.format("\nA slightly more complicated recommendation: %s.\n", mateName);
		story += String.format("Among all names for people born at most %d years %s than your birth year %d,", maxAD, preference, iYOB);
		story += String.format(" %s name %s and your %s name %s have the most number of similar rank (%d times) in the period %d~%d.",
				mateGender, mateName, myGender, iName, nameInfo.get(0).getFreq(), startYear, endYear);
		story += String.format(" Your name is as 'RARE' as this soulmate name. Find a %s around you and you can definately become good friends.", mateName);
		return story;
	}
	
	/**
	 * Get a scene to be shown in new window after running T5X2
	 * @return A scene containing table and chart
	 */
	@SuppressWarnings("unchecked")
	public Scene getSceneT5X2() {
		HBox root = new HBox();
		Scene sc = new Scene(root);
		
		// Create a table
		ObservableList<Name> tableData = FXCollections.observableArrayList(nameInfo);
		TableView<Name> table = new TableView<Name>(tableData);
		TableColumn<Name, String> nameCol = new TableColumn<>("Name");
		TableColumn<Name, Number> freqCol = new TableColumn<>("Frequency");
		TableColumn<Name, Number> occurCol = new TableColumn<>("Occurences");
		TableColumn<Name, String> percCol = new TableColumn<>("Percentage");
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		freqCol.setCellValueFactory(cellData -> cellData.getValue().freqProperty());
		occurCol.setCellValueFactory(cellData -> cellData.getValue().occurProperty());
		percCol.setCellValueFactory(cellData -> cellData.getValue().percProperty());
		table.getColumns().setAll(nameCol, freqCol, occurCol, percCol);
		
		// Create a line chart
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Year");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Rank");
		XYChart.Series<String, Number> myData = new XYChart.Series<String, Number>();
		myData.setName("Rank of Your Name");
		XYChart.Series<String, Number> mateData = new XYChart.Series<String, Number>();
		mateData.setName("Rank of Soul Mate Name");
		for (int year = startYear; year <= endYear; year++) {
			myData.getData().add(new Data<String, Number>(String.valueOf(year), myRecentRanks[year-startYear]));
			mateData.getData().add(new Data<String, Number>(String.valueOf(year), mateRecentRanks[year-startYear]));
		}
		LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.getData().addAll(myData, mateData);
		
		root.getChildren().addAll(table, lineChart);
		return sc;
	}
}

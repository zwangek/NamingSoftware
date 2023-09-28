package comp3111.popnames;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.Comparator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class for handling Task 4 (Application 1, baby name suggestion) algorithms
 * @author tuyifan
 *
 */
public class Task4 {
	
	private int dadYear, momYear, algorithm, totalBoys, totalGirls, dadNameRank, momNameRank, boyNames, girlNames;
	private String dadName, momName;
	private ArrayList<Name> fNameInfo, mNameInfo;
	private int num = 5;
	
	/**
	 * Generate a Task4 object with user inputs
	 * @param dadName The name of the baby's dad
	 * @param momName The name of the baby's mom
	 * @param dadYear The year of birth of the baby's dad
	 * @param momYear The year of birth of the baby's mom
	 * @param algorithm Choose between Algorithm 1 (suggestion based on most popular name of parent's birth year) or Algorithm 2 (suggestion based on parent's name popularity in their birth year)
	 */
	public Task4(String dadName, String momName, int dadYear, int momYear, int algorithm) {
		this.dadYear = dadYear;
		this.momYear = momYear;
		this.algorithm = algorithm;
		this.dadName = dadName;
		this.momName = momName;
		
		this.fNameInfo = new ArrayList<Name>();
		this.mNameInfo = new ArrayList<Name>();
		
		if (algorithm == 0) {
			getNameInfo1();
		} else {
			getNameInfo2();
		}
	}
	
	private void getNameInfo1() {
		for (CSVRecord rec : getFileParser(dadYear)) {
			
			int numBorn = Integer.parseInt(rec.get(2));
			String nameBorn = rec.get(0);

			if (rec.get(1).equals("M")) {
				if (mNameInfo.size() < num) {
					mNameInfo.add(new Name(nameBorn, numBorn));
				}
				totalBoys += numBorn;
			}
		}
		
		for (CSVRecord rec : getFileParser(momYear)) {
			
			int numBorn = Integer.parseInt(rec.get(2));
			String nameBorn = rec.get(0);

			if (!rec.get(1).equals("M")) {
				if (fNameInfo.size() < num) {
					fNameInfo.add(new Name(nameBorn, numBorn));
				}
				totalGirls += numBorn;
			}
		}
		
		mNameInfo.sort(Comparator.reverseOrder());
		fNameInfo.sort(Comparator.reverseOrder());
		
		// Boys name
		for (Name name : mNameInfo) {
			name.setPercentage(Double.parseDouble(String.format("%.2f", (double)name.getOccur() / (double)totalBoys * 100.0)));
		}
		
		// Girls name
		for (Name name : fNameInfo) {
			name.setPercentage(Double.parseDouble(String.format("%.2f", (double)name.getOccur() / (double)totalGirls * 100.0)));
		}
	}
	
	private void getNameInfo2() {
		
		// find dad name rank
		dadNameRank = -1;
		momNameRank = -1;
		
		boyNames = 0; 
		for (CSVRecord rec : getFileParser(dadYear)) {
			
			int numBorn = Integer.parseInt(rec.get(2));
			String nameBorn = rec.get(0);

			if (rec.get(1).equals("M")) {
				
				if (mNameInfo.size() < num) {
					Name newName = new Name(nameBorn, numBorn);
					newName.setRank(boyNames + 1);
					mNameInfo.add(newName);
				} else {
					if (dadNameRank == -1) {
						mNameInfo.remove(0);
						Name newName = new Name(nameBorn, numBorn);
						newName.setRank(boyNames + 1);
						mNameInfo.add(newName);
					}
				}
				
				if (nameBorn.toLowerCase().equals(dadName.toLowerCase())) {
					dadNameRank = boyNames;
				}
				
				totalBoys += numBorn;
				boyNames++;
			}
		}
		
		
		girlNames = 0; 
		for (CSVRecord rec : getFileParser(momYear)) {
			
			int numBorn = Integer.parseInt(rec.get(2));
			String nameBorn = rec.get(0);

			if (!rec.get(1).equals("M")) {
				if (fNameInfo.size() < num) {
					Name newName = new Name(nameBorn, numBorn);
					newName.setRank(girlNames + 1);
					fNameInfo.add(newName);
				} else {
					if (momNameRank == -1) {
						fNameInfo.remove(0);
						Name newName = new Name(nameBorn, numBorn);
						newName.setRank(girlNames + 1);
						fNameInfo.add(newName);
					}
				}
				
				if (nameBorn.toLowerCase().equals(momName.toLowerCase())) {
					momNameRank = girlNames;
				}
				
				totalGirls += numBorn;
				girlNames++;
			}
		}
		
		mNameInfo.sort(Comparator.reverseOrder());
		fNameInfo.sort(Comparator.reverseOrder());
		
		// Boys name
		for (Name name : mNameInfo) {
			name.setPercentage(Double.parseDouble(String.format("%.2f", (double)name.getOccur() / (double)totalBoys * 100.0)));
		}
		
		// Girls name
		for (Name name : fNameInfo) {
			name.setPercentage(Double.parseDouble(String.format("%.2f", (double)name.getOccur() / (double)totalGirls * 100.0)));
		}
	}
	
	private CSVParser getFileParser(int year) {
	     FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
	     return fr.getCSVParser(false);
		}
	
	/**
	 * Generates a recommendations, including a boy name and a girl name and supporting reasons.
	 * @return A String containing the generated recommendation.
	 */
	public String getSummary() {
		
		String oReport = "";
		
		String boyName = mNameInfo.get(0).getName();
		String girlName = fNameInfo.get(0).getName();
		
		int boyOcc = mNameInfo.get(0).getOccur();
		int girlOcc = fNameInfo.get(0).getOccur();
		
		if (algorithm == 0) {
			oReport += String.format("For a baby boy, we recommend %s.\nThis name is the most popular male name in year %d, occuring %d times, \nwhich represents %.2f%% of total male births in %d.\nWith a popular name, we hope your baby boy will also be very popular!\n\n", 
					boyName, dadYear, boyOcc, (double)boyOcc / (double)totalBoys * 100.0, dadYear);
			
			oReport += String.format("For a baby girl, we recommend %s.\nThis name is the most popular female name in year %d, occuring %d times, \nwhich represents %.2f%% of total female births in %d.\nWith a popular name, we believe your baby girl will have many friends!\n\n", 
					girlName, momYear, girlOcc, (double)girlOcc / (double)totalGirls * 100.0, momYear);
		} else {
			
			int boyRank = mNameInfo.get(0).getRank(); // Stored rank starts from 1
			int girlRank = fNameInfo.get(0).getRank();
			
			if (boyName.toLowerCase().equals(dadName.toLowerCase())) {
				boyName = mNameInfo.get(4).getName();
				boyRank = mNameInfo.get(4).getRank();
			}
			
			if (girlName.toLowerCase().equals(momName.toLowerCase())) {
				girlName = fNameInfo.get(4).getName();
				girlRank= fNameInfo.get(4).getRank();
			}
			
			oReport += String.format("For a baby boy, we recommend %s.\nThis name is very similar to dad's in popularity in year %d. Out of %d male names, %s is rank %d.\n", 
					boyName, dadYear, boyNames, boyName, boyRank);
			if (dadNameRank == -1) {
				oReport += String.format("This is because baby's dad name %s is one we can't find it in our database, so it must be super rare.\n", dadName);
			} else {
				oReport += String.format("And baby's dad name %s is rank %d.\n", dadName, dadNameRank + 1);
			}
			oReport += "With a name of similar popularity, we believe this baby boy will share a similar feeling as his dad towards their name, \nforming more special family bonds.\n\n";
			
			oReport += String.format("For a baby girl, we recommend %s.\nThis name is very similar to mom's in popularity in year %d. Out of %d female names, %s is rank %d.\n", 
					girlName, momYear, girlNames, girlName, girlRank);
			if (momNameRank == -1) {
				oReport += String.format("This is because baby's mom name %s is one we can't find it in our database, so it must be super rare.\n", momName);
			} else {
				oReport += String.format("And baby's mom name %s is rank %d.\n", momName, momNameRank + 1);
			}
			oReport += "With a name of similar popularity, we believe this baby girl will share a similar feeling as her mom towards their name, \nforming more special family bonds.\n\n";
		}
		
		return oReport;
	}
	
	private class Name implements Comparable<Name>{
		private SimpleStringProperty name;
		private SimpleIntegerProperty frequency;
		private SimpleIntegerProperty occurrences;
		private SimpleDoubleProperty percentage;
		private SimpleIntegerProperty rank;
		
		public Name(String name, int occurrences) {
			this.name = new SimpleStringProperty(name);
			this.occurrences = new SimpleIntegerProperty(occurrences);
		}
		
		public void setPercentage(double perc) { this.percentage = new SimpleDoubleProperty(perc); }
		public void setRank(int rank) { this.rank = new SimpleIntegerProperty(rank); }
		public String getName() { return this.name.get(); }
		public int getFreq() { return this.frequency.get(); }
		public int getOccur() { return this.occurrences.get(); }
		public double getPercentage() { return this.percentage.get(); }
		public int getRank() {return this.rank.get(); }
		public SimpleStringProperty nameProperty() { return this.name; }
		public SimpleIntegerProperty occurProperty() { return this.occurrences; }
		public SimpleStringProperty percProperty() { return new SimpleStringProperty(this.percentage.get() + "%"); }
		public SimpleIntegerProperty rankProperty() { return this.rank; }
		@Override
		public int compareTo(Name name) {
			if (this.getOccur() > name.getOccur()) return 1;
			else if (this.getOccur() == name.getOccur()) {	// same occur, sort by alphabetical order
				int diff = this.getName().compareTo(name.getName());
				if (diff < 0) return 1;
				else if (diff == 0) return 0;
				else return -1;
			}
			else return -1;
		}
	}
	
	private TableView<Name> getTable(ArrayList<Name> nameInfo) {
		ObservableList<Name> tableData = FXCollections.observableArrayList(nameInfo);
		TableView<Name> table = new TableView<Name>(tableData);
		TableColumn<Name, String> nameCol = new TableColumn<>("Name");
		TableColumn<Name, Number> occurCol = new TableColumn<>("Occurences");
		TableColumn<Name, String> percCol = new TableColumn<>("Percentage");
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		occurCol.setCellValueFactory(cellData -> cellData.getValue().occurProperty());
		percCol.setCellValueFactory(cellData -> cellData.getValue().percProperty());
		table.getColumns().setAll(nameCol, occurCol, percCol);
		return table;
	}
	
	private TableView<Name> getTableRank(ArrayList<Name> nameInfo) {
		ObservableList<Name> tableData = FXCollections.observableArrayList(nameInfo);
		TableView<Name> table = new TableView<Name>(tableData);
		TableColumn<Name, String> nameCol = new TableColumn<>("Name");
		TableColumn<Name, Number> occurCol = new TableColumn<>("Occurences");
		TableColumn<Name, Number> rankCol = new TableColumn<>("Rank");
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		occurCol.setCellValueFactory(cellData -> cellData.getValue().occurProperty());
		rankCol.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		table.getColumns().setAll(nameCol, occurCol, rankCol);
		return table;
	}
	
	private BarChart<String, Number> getBarChart(ArrayList<Name> nameInfo) {
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Name");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Occurences");
		BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for (int i = 0; i != nameInfo.size() ; i++) {
			XYChart.Data<String, Number> barData = new XYChart.Data<>(nameInfo.get(i).getName(), nameInfo.get(i).getOccur());
			if (nameInfo.size() <= 10) {
		        StackPane node = new StackPane();
		        Text text = new Text(nameInfo.get(i).getOccur() + "");
		        text.setFill(Color.WHITE);
		        Group group = new Group(text);
		        StackPane.setAlignment(group, Pos.TOP_CENTER);
		        StackPane.setMargin(group, new Insets(0, 0, 5, 0));
		        node.getChildren().add(group);
		        barData.setNode(node);
			}
			series.getData().add(barData);
		}
		if (nameInfo.size() <= 10) barChart.setPrefWidth(75 * nameInfo.size());
		else barChart.setPrefWidth(800);
		barChart.getData().add(series);
		barChart.setLegendVisible(false);
		return barChart;
	}
	
	private PieChart getPieChart(ArrayList<Name> nameInfo) {
		PieChart pieChart = new PieChart();
		for (int i = 0; i != nameInfo.size() ; i++)
			pieChart.getData().add(new PieChart.Data(nameInfo.get(i).getName(), nameInfo.get(i).getOccur()));
		pieChart.setPrefHeight(nameInfo.size() / 5 * 25 + 400);
		return pieChart;
	}
	
	private Scene getTableCharts(ArrayList<Name> nameInfo) {
		
		// Create a table
		TableView<Name> table;
		
		if (algorithm == 0) {
			table = getTable(nameInfo);
		} else {
			table = getTableRank(nameInfo);
		}
		
		// Create a bar chart
		BarChart<String, Number> barChart = getBarChart(nameInfo);
		
		HBox root = new HBox();	// A layout for table and charts
		Scene scene = new Scene (root);
		root.getChildren().addAll(table, barChart);
		return scene;
	}
	
	/**
	 * Formulate a scene with a table and a bar chart for boy name supporting info.
	 * @return A scene to be added to the pop window stage after user clicks the Recommend button
	 */
	public Scene getMTableCharts() {
		return getTableCharts(mNameInfo);
	}
	
	/**
	 * Formulate a scene with a table and a bar chart for girl name supporting info.
	 * @return A scene to be added to the pop window stage after user clickes the Recommend button
	 */
	public Scene getFTableCharts() {
		return getTableCharts(fNameInfo);
	}
	

}

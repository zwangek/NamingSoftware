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
 * Class for handling Task 1 (Data reporting) algorithms
 * @author tuyifan
 *
 */
public class Task1 {
	
	/**
	 * Generate a Task1 object with user inputs
	 * @param year The year of interest (1880 to 2019)
	 * @param num The number of most popular names to report (1 to 10)
	 */
	public Task1(int year, int num) {
		this.year = year;
		this.num = num;
		this.fNameInfo = new ArrayList<Name>();
		this.mNameInfo = new ArrayList<Name>();
		this.totalBoys = 0;
		this.totalGirls = 0;
		
		if (checkInput()) {
			getNameInfo();
		}
	}
	
	private void getNameInfo() {
		for (CSVRecord rec : getFileParser(year)) {
			
			int numBorn = Integer.parseInt(rec.get(2));
			String nameBorn = rec.get(0);

			if (rec.get(1).equals("M")) {
				if (mNameInfo.size() < num) {
					mNameInfo.add(new Name(nameBorn, numBorn));
				}
				totalBoys += numBorn;
			}
			else {
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
	
	private int year, num, totalBoys, totalGirls;
	private ArrayList<Name> fNameInfo, mNameInfo;
	
	private CSVParser getFileParser(int year) {
	     FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
	     return fr.getCSVParser(false);
		}
	
	/**
	 * Check if the input of year (1880 to 2019) and number (1 to 10) are in range
	 * @return A boolean: true is input in range, false is input out of range 
	 */
	public boolean checkInput() {
		if ((year >= 1880 && year <= 2019) && (num >= 1 && num <= 10)){
			return true;
		}
		return false;
	}
	
	/**
	 * Provides a summary about the most popular name, its occurrence and percentage
	 * @return A String containing summary information based on year 
	 */
	public String getSummary() {
		
		String oReport = "";
		
		if (!checkInput()) {
			if (!(year >= 1880 && year <= 2019)) {
				oReport = "Please enter a year between 1880 and 2019 and then try again!";
			} 
			
			if (!(num >= 1 && num <= 10)) {
				oReport = "The number of names to report must be between 1 and 10, please try again!";
			}
			
			return oReport;
		}
		
		String mName = mNameInfo.get(0).getName();
		String fName = fNameInfo.get(0).getName();
		
		int mOcc = mNameInfo.get(0).getOccur();
		int fOcc = fNameInfo.get(0).getOccur();
		
		oReport = String.format("Summary (Year of %d):\n\n", year);
		
		oReport += String.format("%s is the most popular name with the number of occurrences of %d, \nwhich represents %.2f%% of total male births in %d.\n\n", 
				mName, mOcc, (double)mOcc / (double)totalBoys * 100.0, year);
		
		oReport += String.format("%s is the most popular name with the number of occurrences of %d, \nwhich represents %.2f%% of total female births in %d.\n\n", 
				fName, fOcc, (double)fOcc / (double)totalGirls * 100.0, year);
		
		return oReport;
	}
	
	private class Name implements Comparable<Name>{
		private SimpleStringProperty name;
		private SimpleIntegerProperty frequency;
		private SimpleIntegerProperty occurrences;
		private SimpleDoubleProperty percentage;
		
		public Name(String name, int occurrences) {
			this.name = new SimpleStringProperty(name);
			this.occurrences = new SimpleIntegerProperty(occurrences);
		}
		
		public void setPercentage(double perc) { this.percentage = new SimpleDoubleProperty(perc); }
		public String getName() { return this.name.get(); }
		public int getFreq() { return this.frequency.get(); }
		public int getOccur() { return this.occurrences.get(); }
		public double getPercentage() { return this.percentage.get(); }
		public SimpleStringProperty nameProperty() { return this.name; }
		public SimpleIntegerProperty occurProperty() { return this.occurrences; }
		public SimpleStringProperty percProperty() { return new SimpleStringProperty(this.percentage.get() + "%"); }
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
		TableView<Name> table = getTable(nameInfo);
		
		// Create a bar chart
		BarChart<String, Number> barChart = getBarChart(nameInfo);
		
		// Create a pie chart
		PieChart pieChart = getPieChart(nameInfo); 
		
		HBox root = new HBox();	// A layout for table and charts
		Scene scene = new Scene (root);
		root.getChildren().addAll(table, barChart, pieChart);
		return scene;
	}
	
	/**
	 * Formulate a scene with a table, a bar chart and a pie chart for male info.
	 * @return A scene to be added to the pop window stage after user clicks the Report button
	 */
	public Scene getMTableCharts() {
		return getTableCharts(mNameInfo);
	}
	
	/**
	 * Formulate a scene with a table, a bar chart and a pie chart for female info.
	 * @return A scene to be added to the pop window stage after user clicks the Report button
	 */
	public Scene getFTableCharts() {
		return getTableCharts(fNameInfo);
	}
	

}

package comp3111.popnames;

import java.util.ArrayList;
import java.util.Comparator;
import org.apache.commons.csv.*;
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
 * Class to process task2
 * @author WANG, Zeyu
 */
public class Task2 extends TaskGeneral {	
	// helper class to store information
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
		public double getPercentage() { return this.percentage.get(); }
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
	
	private int startYear;
	private int endYear;
	private int rank;
	private char gender;
	private ArrayList<Name> nameInfo;
	
	/**
	 * Construct a Task2 object with following parameters
	 * @param startYear Start year of user's interested time period
	 * @param endYear End year of user's interested time period
	 * @param rank User's interested rank
	 * @param gender User's interested gender
	 */
	public Task2(int startYear, int endYear, int rank, char gender) {
		this.startYear = startYear;
		this.endYear = endYear;
		this.rank = rank;
		this.gender = gender;
		this.nameInfo = new ArrayList<Name>();
		getNameInfo();
	}
	
	// Find all the required information for this task
	private void getNameInfo() {
		CSVRecord[] popRecords = new CSVRecord[endYear - startYear + 1]; // find all k'th popular name in the period
		for (int year = startYear; year <= endYear; year++) {
			popRecords[year - startYear] = getRecord(year, rank, gender);
		}
		for (int i = 0; i != popRecords.length; i++) {
			int freq = 0;
			int occur = 0;
			boolean newName = true;
			for (int j = 0; j != popRecords.length; j++) {
				if (popRecords[i].get(0).equals(popRecords[j].get(0))) {
					if (j < i) {
						newName = false;	// name already dealt
						break;
					}
					else {
						freq++;
						occur += Integer.parseInt(popRecords[j].get(2));
					}
				}
			}
			if (newName) {
				nameInfo.add(new Name(popRecords[i].get(0), freq, occur));
			}
		}
		nameInfo.sort(Comparator.reverseOrder());
		int totalOccur = 0;
		for (Name name : nameInfo) {
			totalOccur += name.getOccur();
		}
		for (Name name : nameInfo) {
			name.setPercentage(Double.parseDouble(String.format("%.1f", (double)name.getOccur() / (double)totalOccur * 100.0)));
		}
		Name total = new Name("TOTAL", endYear-startYear+1, totalOccur);
		total.setPercentage(100.0);
		nameInfo.add(total);
	}
	
	/**
	 * Formulate a string result.
	 * @return A string to be added to the text console in the GUI
	 */
	public String getSummary() {
		String oReport = "";
		String kid = (gender == 'M') ? "boys" : "girls";
		String oGender = (gender == 'M') ? "male" : "female";
		Name first = nameInfo.get(0);
		oReport += String.format("Summary:\n%s has hold the %d-th rank most often for a total of %d times among the names registered for baby %s born in the period from %d to %d.",
				first.getName(), rank, first.getFreq(), kid, startYear, endYear);
		oReport += String.format(" The total number of occurrences of %s is %d,", first.getName(), first.getOccur());
		oReport += String.format(" which represents %.1f%% of total %s births at the %d-th rank in the period from %d to %d¡£", first.getPercentage(), oGender, rank, startYear, endYear);
		return oReport;
	}
	
	/**
	 * Formulate a scene with a table, a bar chart and a pie chart.
	 * @return A scene to be added to the pop window stage after user clicking generate button
	 */
	@SuppressWarnings("unchecked")
	public Scene getTableCharts() {
		HBox root = new HBox();	// A layout for table and charts
		Scene t2Scene = new Scene (root);
		
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
		//table.setPrefHeight(200);
		
		// Create a bar chart
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Name");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Occurences");
		BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for (int i = 0; i != nameInfo.size() - 1; i++) {
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
		
		// Create a pie chart
		PieChart pieChart = new PieChart();
		for (int i = 0; i != nameInfo.size() - 1; i++)
			pieChart.getData().add(new PieChart.Data(nameInfo.get(i).getName(), nameInfo.get(i).getOccur()));
		pieChart.setPrefHeight(nameInfo.size() / 5 * 25 + 400);
		
		root.getChildren().addAll(table, barChart, pieChart);
		return t2Scene;
	}
}

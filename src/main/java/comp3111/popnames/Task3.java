package comp3111.popnames;

import javafx.scene.layout.HBox;
import javafx.scene.Scene;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleFloatProperty;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;


/**
 * The class that handle the task of generating summary, table and chart in Task3
 */
@SuppressWarnings("unchecked")
public class Task3 {
	//this is a data type to store the ranking information for a name in a single year
	private class Ranking {
		private IntegerProperty year;
		private IntegerProperty rank;
		private IntegerProperty occurrence;
		private FloatProperty percentage;
		public Ranking (int year, int rank, int occurrence) {
			this.year=new SimpleIntegerProperty(year);
			this.rank=new SimpleIntegerProperty(rank);
			this.occurrence=new SimpleIntegerProperty(occurrence);
			percentage=new SimpleFloatProperty(0);		//will be set later
		}
		public void setPercentage(int totalOccurrence) {
			percentage=new SimpleFloatProperty( (float)occurrence.get()*100 / totalOccurrence );
		}
		
		public IntegerProperty yearProperty() {return year;}
		public IntegerProperty rankProperty() {return rank;}
		public IntegerProperty occurrenceProperty() {return occurrence;}
		public FloatProperty percentageProperty() {return percentage;}
	}
	//storing the needed information for a specific query
	private int year1;
	private int year2;
	private String gender;
	private String name;
	private ArrayList<Ranking> rankInfo;	//this is an array of Ranking, that is, where the result is stored
	private int numDataMissing=0;
	private int highestRank=Integer.MAX_VALUE;
	private int highestI=-1;
	/**
	 * constructor of the class, takes in the inquired information
	 * @param year1
	 * @param year2
	 * @param gender
	 * @param name
	 */
	public Task3 (int year1, int year2, String gender, String name) {
		this.year1=year1;
		this.year2=year2;
		this.gender=gender;
		this.name=name;	
		rankInfo = new ArrayList<Ranking>();
		if(year1<=2019&&year1>=1880&&year2<=2019&&year2>=1880)
			generateRankInfo();		//produce the result data in the constructor right away
	}
	
	private void generateRankInfo() {
		int totalOccurrence=0;
		//generate ranking and occurrence for each year, namely, filling the array list with ranking object
		for (int y = year1; y <= year2; y++) {
			int rank = AnalyzeNames.getRank(y,name,gender);
			int occurrence = AnalyzeNames.getOccurrence(y,name,gender);
			if (rank==-1) {
				numDataMissing++;
				continue;
			}
			rankInfo.add(new Ranking(y,rank,occurrence));
			totalOccurrence+=occurrence;

			if (rank < highestRank) {	//find the highest ranking
				highestRank = rank;
				highestI = rankInfo.size()-1;
			}
		}
		
		//set percentage for each Ranking object
		for (int i=0; i < rankInfo.size(); i++) {
			rankInfo.get(i).setPercentage(totalOccurrence);
		}
	}
	/**
	 * returns a scene that present the result in table, barchart and linechart
	 * @return
	 */
	public Scene generateTableCharts() {
		HBox root = new HBox();
		Scene T3scene = new Scene(root);
		root.getChildren().addAll(generateTable(),generateBar(),generateLine());
		return T3scene;
	}
	
	public TableView<Ranking> generateTable() {
		
		//create a table
		ObservableList<Ranking> tableData = FXCollections.observableArrayList(rankInfo);
		TableView<Ranking> table = new TableView<Ranking>(tableData);
		TableColumn<Ranking,Number> yearCol = new TableColumn<Ranking,Number>("Year");
		yearCol.setCellValueFactory(cellData -> cellData.getValue().yearProperty());//new PropertyValueFactory<Ranking,Number>("year")
		TableColumn<Ranking,Number> rankCol = new TableColumn<Ranking,Number>("Rank");
		rankCol.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		TableColumn<Ranking,Number> occurrenceCol = new TableColumn<Ranking,Number>("Occurrence");
		occurrenceCol.setCellValueFactory(cellData -> cellData.getValue().occurrenceProperty());
		TableColumn<Ranking,Number> percentageCol = new TableColumn<Ranking,Number>("Percentage");
		percentageCol.setCellValueFactory(cellData -> cellData.getValue().percentageProperty());
		table.getColumns().addAll(yearCol,rankCol,occurrenceCol,percentageCol); //add all created columns
		return table;
	}
		
	public BarChart<String, Number> generateBar(){
		//create a bar chart
		CategoryAxis xAxis1 = new CategoryAxis();
		xAxis1.setLabel("Year");
		NumberAxis yAxis1 = new NumberAxis();
		yAxis1.setLabel("Occurrences");
		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		for(int i=0; i < rankInfo.size(); i++) {
			series1.getData().add(new XYChart.Data<String,Number>( 
					Integer.toString(rankInfo.get(i).yearProperty().get()), 
					rankInfo.get(i).occurrenceProperty().get()
					));
		}
		BarChart<String, Number> barChart = new BarChart<>(xAxis1, yAxis1);
		barChart.getData().addAll(series1);
		barChart.setTitle(String.format("Occurrence of %s",name));
		return barChart;
	}
	
	public LineChart<String, Number> generateLine(){
		//create a line chart
		CategoryAxis xAxis2 = new CategoryAxis();
		xAxis2.setLabel("Year");
		NumberAxis yAxis2 = new NumberAxis();
		yAxis2.setLabel("Occurrences");
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		for(int i=0; i < rankInfo.size(); i++) {
			series2.getData().add(new XYChart.Data<String,Number>( 
					Integer.toString(rankInfo.get(i).yearProperty().get()), 
					rankInfo.get(i).occurrenceProperty().get()
					));
		}
		LineChart<String,Number> lineChart = new LineChart<>(xAxis2, yAxis2);
		lineChart.getData().add(series2);
		lineChart.setTitle(String.format("Occurrence of %s",name));
		return lineChart;
	}
	/**
	 * get the summary of result
	 * @return summary
	 */
	public String getSummary() {
		if (highestI==-1)
			return "The name is not found in any of the years.";
		String summary = "";
		int hYear = rankInfo.get(highestI).yearProperty().get();
		int hOccur = rankInfo.get(highestI).occurrenceProperty().get();
		float hPercent = rankInfo.get(highestI).percentageProperty().get();
		summary += String.format
				("The year when the name %s was most popular is %d at rank %d.\n", name, hYear , highestRank);
		summary += String.format(" In that year, the number of occurrences is %d,\n", hOccur);
		summary += String.format(" which represents %f percent of total male births in %d.", hPercent, hYear);
		return summary;
	}
	
	/**
	 * this is called in task 6, and return the average rank in the period of time
	 * @return average rank
	 */
	public float getAverageRank() {
		if(rankInfo.size()==0)
			return -1;
		int sum = 0;
		for (int i=0; i<rankInfo.size(); i++) {
			sum+=rankInfo.get(i).rankProperty().get();
		}
		return (float)sum / rankInfo.size();
	}

}

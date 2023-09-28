package comp3111.popnames;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
//below is my import
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;

import javafx.stage.Stage;
import javafx.scene.control.Label;


public class Controller {

    @FXML
    private Tab tabTaskZero;

    @FXML
    private TextField textfieldNameF;

    @FXML
    private TextField textfieldYear;

    @FXML
    private Button buttonRankM;

    @FXML
    private TextField textfieldNameM;

    @FXML
    private Button buttonRankF;

    @FXML
    private Button buttonTopM;

    @FXML
    private Button buttonTopF;

    @FXML
    private Button buttonSummary;

    @FXML
    private Tab tabReport1;

    @FXML
    private Tab tabReport2;

    @FXML
    private Tab tabReport3;
    //below is the UIs in Report 3
    @FXML
    private TextField R3TextfieldName;

    @FXML
    private TextField R3TextfieldYear1;

    @FXML
    private TextField R3TextfieldYear2;

    @FXML
    private Button R3button;

    @FXML
    private TextField R3TextfieldGender;  
    //above is UIs in Report 3

    @FXML
    private Tab tabApp1;

    @FXML
    private Tab tabApp2;

    @FXML
    private Tab tabApp3;
    //below is UI components in A3
    @FXML
    private TextField A3TextfieldIName;

    @FXML
    private TextField A3TextfieldIGender;

    @FXML
    private TextField A3TextfieldIYOB;

    @FXML
    private TextField A3TextfieldMName;

    @FXML
    private TextField A3TextfieldMGender;

    @FXML
    private TextField A3TextfieldMYOB;
    
    @FXML
    private RadioButton A3RadioYoung;

    @FXML
    private ToggleGroup preferrence;

    @FXML
    private RadioButton A3RadioX1;

    @FXML
    private ToggleGroup algorithm;

    @FXML
    private Button A3Button;
    //above is UI components in A3

    @FXML
    private TextArea textAreaConsole;
    
    
    // Task 2 components below
    @FXML
    private TextField textfieldT2SYear;

    @FXML
    private TextField textfieldT2EYear;

    @FXML
    private TextField textfieldT2Rank;

    @FXML
    private RadioButton radioButtonT2M;

    @FXML
    private RadioButton radioButtonT2F;

    @FXML
    private Button buttonT2Generate;
    
    @FXML
    private Button buttonT2C;
    
    @FXML
    private Label labelT2SYWarning;

    @FXML
    private Label labelT2EYWarning;
    
    @FXML
    private Label labelT2RankWarning;
    
    private Stage popWindow;
    // Task 2 components above
    
    // Task 5 components below
    @FXML
    private TextField textfieldT5IName;

    @FXML
    private TextField textfieldT5IYOB;

    @FXML
    private RadioButton radiobuttonT5IGenderM;

    @FXML
    private RadioButton radiobuttonT5IGenderF;

    @FXML
    private RadioButton radiobuttonT5MGenderM;

    @FXML
    private RadioButton radiobuttonT5MGenderF;
    
    @FXML
    private RadioButton radiobuttonT5AgeY;

    @FXML
    private RadioButton radiobuttonT5AgeO;

    @FXML
    private TextField textfieldT5MAgeDiff;

    @FXML
    private Button buttonT5Generate;

    @FXML
    private Button buttonT5Clear;
    
    @FXML
    private Label labelT5INameWarning;
    
    @FXML
    private Label labelT5IYOBWarning;
    
    @FXML
    private Label labelT5MADWarning;
    // Task 5 components above
  
    // Task 1 properties below
    @FXML
    private TextField textfieldT1Year;
    
    @FXML
    private TextField textfieldT1Num;

    @FXML
    private Button buttonT1Report;
    
    @FXML
    private Stage popWindowT1F;
    
    @FXML
    private Stage popWindowT1M;
    // Task 1 properties above
    
    // Task 4 properties below
    @FXML
    private TextField textfieldT4DadName;
    
    @FXML
    private TextField textfieldT4MomName;
    
    @FXML
    private TextField textfieldT4DadYOB;
    
    @FXML
    private TextField textfieldT4MomYOB;
    
    @FXML
    private Button buttonT4Recommend;
    
    @FXML
    private RadioButton radioButtonT4A1;
    
    @FXML
    private RadioButton radioButtonT4A2;
    
    @FXML
    private ToggleGroup algorithmT4;
    
    @FXML
    private Stage popWindowT4F;
    
    @FXML
    private Stage popWindowT4M;
    // Task 4 properties above
  
    /**
     *  Task Zero
     *  To be triggered by the "Summary" button on the Task Zero Tab 
     *  
     */
    @FXML
    void doSummary() {
    	int year = Integer.parseInt(textfieldYear.getText());
    	String oReport = AnalyzeNames.getSummary(year);
    	textAreaConsole.setText(oReport);
    }

  
    /**
     *  Task Zero
     *  To be triggered by the "Rank (female)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doRankF() {
    	String oReport = "";
    	String iNameF = textfieldNameF.getText();
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	int oRank = AnalyzeNames.getRank(iYear, iNameF, "F");
    	if (oRank == -1)
    		oReport = String.format("The name %s (female) has not been ranked in the year %d.\n", iNameF, iYear);
    	else
    		oReport = String.format("Rank of %s (female) in year %d is #%d.\n", iNameF, iYear, oRank);
    	textAreaConsole.setText(oReport);
    }

  
    /**
     *  Task Zero
     *  To be triggered by the "Rank (male)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doRankM() {
    	String oReport = "";
    	String iNameM = textfieldNameM.getText();
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	int oRank = AnalyzeNames.getRank(iYear, iNameM, "M");
    	if (oRank == -1)
    		oReport = String.format("The name %s (male) has not been ranked in the year %d.\n", iNameM, iYear);
    	else
    		oReport = String.format("Rank of %s (male) in year %d is #%d.\n", iNameM, iYear, oRank);
    	textAreaConsole.setText(oReport);
    }


    /**
     *  Task Zero
     *  To be triggered by the "Top 5 (female)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doTopF() {
    	String oReport = "";
    	final int topN = 5;
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	oReport = String.format("Top %d most popular names (female) in the year %d:\n", topN, iYear);
    	for (int i=1; i<=topN; i++)
    		oReport += String.format("#%d: %s\n", i, AnalyzeNames.getName(iYear, i, "F"));
    	textAreaConsole.setText(oReport);
    }


    /**
     *  Task Zero
     *  To be triggered by the "Top 5 (male)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doTopM() {
    	String oReport = "";
    	final int topN = 5;
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	oReport = String.format("Top %d most popular names (male) in the year %d:\n", topN, iYear);
    	for (int i=1; i<=topN; i++)
    		oReport += String.format("#%d: %s\n", i, AnalyzeNames.getName(iYear, i, "M"));
    	textAreaConsole.setText(oReport);
    }
  
  /**
     *  Task 1
     *  To be triggered by the "Report" button on the Task 1 Tab
     * 
     */
    @FXML
    void doT1Report() {
    	String oReport = "";
    	int year = 0;
    	int num = 0;
    	
    	if (textfieldT1Year.getText().isEmpty()) {
    		oReport = "Please enter the year of interest\n";
    		textAreaConsole.setText(oReport);
    		return;
    	}
    	
    	try { 
    		year = Integer.parseInt(textfieldT1Year.getText());
        } catch(NumberFormatException e) { 
        	oReport = "Please enter a number for the year of interest.\n";
    		textAreaConsole.setText(oReport);
            return; 
        }
    	
    	if (textfieldT1Num.getText().isEmpty()) {
    		oReport = "Please enter the number of names to report\n";
    		textAreaConsole.setText(oReport);
    		return;
    	}
    	
    	try { 
    		num = Integer.parseInt(textfieldT1Num.getText());
        } catch(NumberFormatException e) { 
        	oReport = "Please enter a number for the amount of names to report.\n";
    		textAreaConsole.setText(oReport);
            return; 
        }
    	
    	Task1 t1 = new Task1(year, num);
    	
    	oReport = t1.getSummary();
    	textAreaConsole.setText(oReport);
    	
    	if (t1.checkInput()) {
        	
        	if (popWindowT1M == null) popWindowT1M = new Stage();
        	if (popWindowT1F == null) popWindowT1F = new Stage();
        	
        	popWindowT1M.setTitle(String.format("Top %d names (male) in year %d, table & charts", num, year));
        	popWindowT1M.setScene(t1.getMTableCharts());
        	
        	
        	popWindowT1F.setTitle(String.format("Top %d names (female) in year %d, table & charts", num, year));
        	popWindowT1F.setScene(t1.getFTableCharts());
        	
        	
        	popWindowT1M.setX(100);
        	popWindowT1M.setY(100);
        	
        	popWindowT1F.setX(200);
        	popWindowT1F.setY(300);
        	
        	popWindowT1M.show();
        	popWindowT1F.show();
    	}
    	
    }
    
  /**
     *  Task 4
     *  To be triggered by the "Recommend" button on the Task 4 Tab
     * 
     */
    @FXML
    void doT4Recommend() {
    	String oReport = "";
    	
    	if (textfieldT4DadName.getText().isEmpty()) {
    		oReport = "Please enter the name of baby's dad\n";
    		textAreaConsole.setText(oReport);
    		return;
    	}
    	
    	// Not all English characters
    	if (!textfieldT4DadName.getText().matches("[a-zA-Z]+")) {
    		oReport = "We can only work with dad names in English alphabets\n";
    		textAreaConsole.setText(oReport);
    		return;
    	}
    	
    	if (textfieldT4MomName.getText().isEmpty()) {
    		oReport = "Please enter the name of baby's mom\n";
    		textAreaConsole.setText(oReport);
    		return;
    	}
    	
    	// Not all English characters
    	if (!textfieldT4MomName.getText().matches("[a-zA-Z]+")) {
    		oReport = "We can only work with mom names in English alphabets\n";
    		textAreaConsole.setText(oReport);
    		return;
    	}
    	
    	String dadName = textfieldT4DadName.getText();
    	String momName = textfieldT4MomName.getText();
    	
    	int dadYOB = 0;
    	int momYOB = 0;
    	
    	if (textfieldT4DadYOB.getText().isEmpty()) {
    		oReport = "Please enter the year of birth of baby's dad\n";
    		textAreaConsole.setText(oReport);
    		return;
    	}
    	
    	try { 
    		dadYOB = Integer.parseInt(textfieldT4DadYOB.getText());
        } catch(NumberFormatException e) { 
        	oReport = "Please enter a number for dad year of birth.\n";
    		textAreaConsole.setText(oReport);
            return; 
        }
    	
    	
    	if (!(dadYOB >= 1880 && dadYOB <= 2019)) {
    		oReport = "Please enter a year between 1880 and 2019 for dad's year of birth.\n";
    		textAreaConsole.setText(oReport);
            return;
    	}
    	
    	if (textfieldT4MomYOB.getText().isEmpty()) {
    		oReport = "Please enter the year of birth of baby's mom\n";
    		textAreaConsole.setText(oReport);
    		return;
    	}
    	
    	try { 
    		momYOB = Integer.parseInt(textfieldT4MomYOB.getText());
        } catch(NumberFormatException e) { 
        	oReport = "Please enter a number for mom year of birth.\n";
    		textAreaConsole.setText(oReport);
            return; 
        }
    	
    	if (!(momYOB >= 1880 && momYOB <= 2019)) {
    		oReport = "Please enter a year between 1880 and 2019 for mom's year of birth.\n";
    		textAreaConsole.setText(oReport);
            return;
    	}
    	
    	RadioButton algorithm = (RadioButton) algorithmT4.getSelectedToggle();
    	
    	Task4 t4;
    	
    	if (algorithm.getText().equals("Algorithm 1")) {
    		
    		t4 = new Task4(dadName, momName, dadYOB, momYOB, 0);
    		
    		System.out.println("Algorithm 1 selected");
    	} else {
    		
    		t4 = new Task4(dadName, momName, dadYOB, momYOB, 1);
    		
    		System.out.println("Algorithm 2 selected");
    	}
    	
    	oReport = t4.getSummary();
    	textAreaConsole.setText(oReport);
    	
    	if (popWindowT4F == null) popWindowT4F = new Stage();
    	popWindowT4F.setTitle(String.format("Top 5 female names in year %d, table & bar chart", momYOB));
    	popWindowT4F.setScene(t4.getFTableCharts());
    	
    	if (popWindowT4M == null) popWindowT4M = new Stage();
    	popWindowT4M.setTitle(String.format("Top 5 male names in year %d, table & bar chart", dadYOB));
    	popWindowT4M.setScene(t4.getMTableCharts());
    	
    	popWindowT4M.setX(800);
    	popWindowT4M.setY(100);
    	
    	popWindowT4F.setX(800);
    	popWindowT4F.setY(400);
    	
    	popWindowT4M.show();
    	popWindowT4F.show();
    }
  
    /**
     * task three
     * triggered by the "Produce" button on Report 3 tab
     */
    @FXML
    void doR3Generate() {
    	String oReport = "";
    	int year1 = Integer.parseInt(R3TextfieldYear1.getText());
   		int year2 = Integer.parseInt(R3TextfieldYear2.getText());
   		String iGender = R3TextfieldGender.getText();
   		String iName = R3TextfieldName.getText();
   		Task3 t3 = new Task3(year1,year2,iGender,iName);    		
   		oReport = t3.getSummary();
    	textAreaConsole.setText(oReport);
    	Scene t3scene = t3.generateTableCharts();
    	Stage stage = new Stage();
    	stage.setScene(t3scene);
    	stage.show();
    }
    
    /**
     * task six
     * triggered by the "generate" button on Activity 3 tab
     */
    @FXML
    void doA3Generate() {
    	String iName = A3TextfieldIName.getText();
    	String iGender = A3TextfieldIGender.getText();
    	int iYOB = Integer.parseInt(A3TextfieldIYOB.getText());
    	String mName = A3TextfieldMName.getText();
    	String mGender = A3TextfieldMGender.getText();
    	int mYOB = Integer.parseInt(A3TextfieldMYOB.getText());
    	Boolean preferOld = !A3RadioYoung.isSelected();
    	String oReport = "";
    	
    	if (A3RadioX1.isSelected()) {
    		oReport = Task6.compatibility_X1(iName,mName);
    	}
    	else {
    		oReport  = Task6.compatibility_X2(iName,iGender,iYOB,mName,mGender,mYOB,preferOld);//true means prefer older mate
  
    		Scene t6scene = Task6.X2Chart(iName,iGender,iYOB,mName,mGender,mYOB);
    		Stage stage = new Stage();
    		stage.setScene(t6scene);
    		stage.show();
    	}
		textAreaConsole.setText(oReport);
    	
    }

    // Task 2 methods below
    @FXML
    void doT2Generate() {
    	int sYear = Integer.parseInt(textfieldT2SYear.getText());
    	int eYear = Integer.parseInt(textfieldT2EYear.getText());
    	int rank = Integer.parseInt(textfieldT2Rank.getText());
    	char gender = radioButtonT2M.isSelected() ? 'M' : 'F';

    	Task2 t2 = new Task2(sYear, eYear, rank, gender);
    	textAreaConsole.setText(t2.getSummary());
    	
    	if (popWindow == null) popWindow = new Stage();
    	popWindow.setTitle("Table & Charts");
    	popWindow.setScene(t2.getTableCharts());
    	popWindow.show();
    }
    
    @FXML
    void doT2Clear() {
    	textfieldT2SYear.setText("");
    	textfieldT2EYear.setText("");
    	textfieldT2Rank.setText("");
    	radioButtonT2M.setSelected(false);
    	radioButtonT2F.setSelected(false);
    	labelT2SYWarning.setVisible(false);
    	labelT2EYWarning.setVisible(false);
    	labelT2RankWarning.setVisible(false);
    	buttonT2Generate.setDisable(true);
    	textAreaConsole.setText("");
    	if (popWindow != null) popWindow.close();
    }
    
    @FXML
    void selectGenderT2M() {
    	radioButtonT2F.setSelected(false);
    	checkT2Input();
    }
    
    @FXML
    void selectGenderT2F() {
    	radioButtonT2M.setSelected(false);
    	checkT2Input();
    }
    
    @FXML
    private void checkT2Input() {
    	T2InputChecker checker = new T2InputChecker(textfieldT2SYear, textfieldT2EYear, textfieldT2Rank, radioButtonT2M, radioButtonT2F,
    			buttonT2Generate, labelT2SYWarning, labelT2EYWarning, labelT2RankWarning);
    	checker.check();
    }
    // Task2 methods above
    
    // Task 5 methods below
    @FXML
    void doT5Clear() {
    	textfieldT5IName.setText("");
    	textfieldT5IYOB.setText("");
    	radiobuttonT5IGenderM.setSelected(false);
    	radiobuttonT5IGenderF.setSelected(false);
    	radiobuttonT5MGenderM.setSelected(false);
    	radiobuttonT5MGenderF.setSelected(false);
    	radiobuttonT5AgeO.setSelected(false);
    	radiobuttonT5AgeY.setSelected(false);
    	textfieldT5MAgeDiff.setText("");
    	buttonT5Generate.setDisable(true);
    	labelT5INameWarning.setVisible(false);
    	labelT5IYOBWarning.setVisible(false);
    	labelT5MADWarning.setVisible(false);
    	if (popWindow != null) popWindow.close();
    }

    @FXML
    void doT5Generate() {
    	String myName = textfieldT5IName.getText();
    	int iYOB = Integer.parseInt(textfieldT5IYOB.getText());
    	char iGender = radiobuttonT5IGenderM.isSelected() ? 'M' : 'F';
    	char mateGender = radiobuttonT5MGenderM.isSelected() ? 'M' : 'F';
    	boolean older = radiobuttonT5AgeO.isSelected();
    	int maxAD = Integer.parseInt(textfieldT5MAgeDiff.getText());
    	
    	Task5 t5 = new Task5(myName, iGender, iYOB, mateGender, maxAD, older);
    	textAreaConsole.setText(t5.algorithmT5X1() + "\n" + t5.summaryT5X2());
    	
    	if (popWindow == null) popWindow = new Stage();
    	popWindow.setTitle("Table & Charts");
    	popWindow.setScene(t5.getSceneT5X2());
    	popWindow.show();
    }
    
    @FXML
    void selectIGenderT5M() {
    	radiobuttonT5IGenderF.setSelected(false);
    	checkT5Input();
    }
    
    @FXML
    void selectIGenderT5F() {
    	radiobuttonT5IGenderM.setSelected(false);
    	checkT5Input();
    }
    
    @FXML
    void selectMGenderT5M() {
    	radiobuttonT5MGenderF.setSelected(false);
    	checkT5Input();
    }
    
    @FXML
    void selectMGenderT5F() {
    	radiobuttonT5MGenderM.setSelected(false);
    	checkT5Input();
    }
    
    @FXML
    void selectPrefT5Y() {
    	radiobuttonT5AgeO.setSelected(false);
    	checkT5Input();
    }
    
    @FXML
    void selectPrefT5O() {
    	radiobuttonT5AgeY.setSelected(false);
    	checkT5Input();
    }
    
    @FXML
    void checkT5Input() {
    	T5InputChecker checker = new T5InputChecker(textfieldT5IName, textfieldT5IYOB, radiobuttonT5IGenderM, radiobuttonT5IGenderF,
        		radiobuttonT5MGenderM, radiobuttonT5MGenderF, radiobuttonT5AgeY, radiobuttonT5AgeO, textfieldT5MAgeDiff,
        		buttonT5Generate, labelT5INameWarning, labelT5IYOBWarning, labelT5MADWarning);
    	checker.check();
    }
    
    // Task 5 methods above
}

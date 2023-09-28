package comp3111.popnames;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
/**
 * Class used to check Task2 input. 
 * Meanwhile it can manipulate some GUI components to show corresponding error messages.
 * @author WANG, Zeyu
 *
 */
public class T2InputChecker extends InputCheckerGeneral {
    private TextField textfieldT2SYear;

    private TextField textfieldT2EYear;

    private TextField textfieldT2Rank;

    private RadioButton radioButtonT2M;

    private RadioButton radioButtonT2F;

    private Button buttonT2Generate;
    
    private Label labelT2SYWarning;

    private Label labelT2EYWarning;

    private Label labelT2RankWarning;
	
    /**
     * Construct a T2InputChecker object.
     * Parameters are GUI components
     * @param textfieldT2SYear Input area for start year.
     * @param textfieldT2EYear Input area for end year.
     * @param textfieldT2Rank Input area for rank.
     * @param radioButtonT2M One of the gender input buttons
     * @param radioButtonT2F Another one of the gender input buttons.
     * @param buttonT2Generate Generate button at bottom-right corner.
     * @param labelT2SYWarning Error message for invalid start year.
     * @param labelT2EYWarning Error message for invalid end year.
     * @param labelT2RankWarning Error message for invalid rank.
     */
	public T2InputChecker(TextField textfieldT2SYear, TextField textfieldT2EYear, TextField textfieldT2Rank, RadioButton radioButtonT2M, RadioButton radioButtonT2F,
			Button buttonT2Generate, Label labelT2SYWarning, Label labelT2EYWarning, Label labelT2RankWarning) {
		this.textfieldT2SYear = textfieldT2SYear;
		this.textfieldT2EYear = textfieldT2EYear;
		this.textfieldT2Rank = textfieldT2Rank;
		this.radioButtonT2M = radioButtonT2M;
		this.radioButtonT2F = radioButtonT2F;
		this.buttonT2Generate = buttonT2Generate;
		this.labelT2SYWarning = labelT2SYWarning;
		this.labelT2EYWarning = labelT2EYWarning;
		this.labelT2RankWarning = labelT2RankWarning;
	}
	
    private boolean checkT2SY() {
    	String sYear = textfieldT2SYear.getText();
    	
    	if (sYear.isEmpty()) {
			labelT2SYWarning.setVisible(false);
			buttonT2Generate.setDisable(true);
			return false;
    	}
    	
    	if (!isDigit(sYear) || sYear.length() != 4) {
    		labelT2SYWarning.setText("Start year should be an integer with 4 digits");
    		labelT2SYWarning.setVisible(true);
    		buttonT2Generate.setDisable(true);
    		return false;
    	}
    	
    	int sYearInt = Integer.parseInt(sYear);
    	if (isInBound(textfieldT2EYear.getText())) {	// if there is already a valid end year input, start year should be within 1880 to that endyear
    		if (sYearInt > Integer.parseInt(textfieldT2EYear.getText()) || sYearInt < 1880) {
        		labelT2SYWarning.setText("Start year should be an integer within 1880 to end year");
        		labelT2SYWarning.setVisible(true);
        		buttonT2Generate.setDisable(true);
        		return false;
    		}
    	}
    	else {	// if no, check whether start year is within 1880-2019
    		if (sYearInt < 1880 || sYearInt > 2019) {
        		labelT2SYWarning.setText("Start year should be an integer within 1880 to 2019");
        		labelT2SYWarning.setVisible(true);
        		buttonT2Generate.setDisable(true);
        		return false;
    		}
    	}
    		
    	labelT2SYWarning.setVisible(false);
    	return true;
    }

    private boolean checkT2EY() {
    	String eYear = textfieldT2EYear.getText();
    	
    	if (eYear.isEmpty()) {
			labelT2EYWarning.setVisible(false);
			buttonT2Generate.setDisable(true);
			return false;
    	}
    	
    	if (!isDigit(eYear) || eYear.length() != 4) {
    		labelT2EYWarning.setText("End year should be an integer with 4 digits");
    		labelT2EYWarning.setVisible(true);
    		buttonT2Generate.setDisable(true);
    		return false;
    	}
    	
    	int eYearInt = Integer.parseInt(eYear);
    	if (isInBound(textfieldT2SYear.getText())) {	// if there is already a valid start year input, end year should be within start year to 2019
    		if (eYearInt < Integer.parseInt(textfieldT2SYear.getText()) || eYearInt > 2019) {
        		labelT2EYWarning.setText("End year should be an integer within start year to 2019");
        		labelT2EYWarning.setVisible(true);
        		buttonT2Generate.setDisable(true);
        		return false;
    		}
    	}
    	else {	// if no, check whether start year is within 1880-2019
    		if (eYearInt < 1880 || eYearInt > 2019) {
        		labelT2EYWarning.setText("End year should be an integer within 1880 to 2019");
        		labelT2EYWarning.setVisible(true);
        		buttonT2Generate.setDisable(true);
        		return false;
    		}
    	}
    		
    	labelT2EYWarning.setVisible(false);
    	return true;
    }

    private boolean checkT2Rank() {
    	String rank = textfieldT2Rank.getText();
    	
    	if (rank.isEmpty()) {
    		labelT2RankWarning.setVisible(false);
    		buttonT2Generate.setDisable(true);
    		return false;
    	}
    	
    	if (rank.charAt(0) == '0') {
    		labelT2RankWarning.setVisible(true);
    		labelT2RankWarning.setText("Rank should not start with 0");
    		buttonT2Generate.setDisable(true);
    		return false;
    	}
    	
    	if (!isDigit(rank)) {
    		labelT2RankWarning.setVisible(true);
    		labelT2RankWarning.setText("Rank should be an integer with fewer than 9 digits");
    		buttonT2Generate.setDisable(true);
    		return false;
    	}
	
    	if (checkT2SY() && checkT2EY() && (radioButtonT2M.isSelected() || radioButtonT2F.isSelected())) { // start year, end year, gender are correctly inputed      	
    		int sYear = Integer.parseInt(textfieldT2SYear.getText());
    		int eYear = Integer.parseInt(textfieldT2EYear.getText());
    		int rankInt = Integer.parseInt(textfieldT2Rank.getText());
    		char gender = radioButtonT2M.isSelected() ? 'M' : 'F';
    		
    		for (int year = sYear; year <= eYear; year++) {
    			if (rankInt > TaskGeneral.getTotalNames(year, gender)) {
    				labelT2RankWarning.setVisible(true);
    				labelT2RankWarning.setText("Rank too large. Some year does not have that many names");
    				return false;
    			}
    		}
    		labelT2RankWarning.setVisible(false);
    		return true;
    	}

    	labelT2RankWarning.setVisible(false);  
    	buttonT2Generate.setDisable(true);
    	return false;
    }

    /**
     * Check all the inputs.
     * If they are all valid, enable the generate button.
     * Otherwise, disable it.
     */
    @Override
    public void check() {
    	boolean sy = checkT2SY();
    	boolean ey = checkT2EY();
    	boolean rank = checkT2Rank();
    	if (sy && ey && rank) {
    		buttonT2Generate.setDisable(false);
    	}
    	else buttonT2Generate.setDisable(true);
    }
}

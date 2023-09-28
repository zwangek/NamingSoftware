package comp3111.popnames;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Handle Task 5 input checking and error messages.
 * @author WANG, Zeyu
 *
 */
public class T5InputChecker extends InputCheckerGeneral {
    private TextField textfieldT5IName;
    private TextField textfieldT5IYOB;
    private RadioButton radiobuttonT5IGenderM;
    private RadioButton radiobuttonT5IGenderF;
    private RadioButton radiobuttonT5MGenderM;
    private RadioButton radiobuttonT5MGenderF;
    private RadioButton radiobuttonT5AgeY;
    private RadioButton radiobuttonT5AgeO;
    private TextField textfieldT5MAgeDiff;
    private Button buttonT5Generate;
    private Label labelT5INameWarning;
    private Label labelT5IYOBWarning;
    private Label labelT5MADWarning;
    
    /**
     * Construct an checker object.
     * Parameters are related UI components in the Controller.
     */
    public T5InputChecker(TextField textfieldT5IName, TextField textfieldT5IYOB, RadioButton radiobuttonT5IGenderM, RadioButton radiobuttonT5IGenderF,
    		RadioButton radiobuttonT5MGenderM, RadioButton radiobuttonT5MGenderF, RadioButton radiobuttonT5AgeY, RadioButton radiobuttonT5AgeO,
    		TextField textfieldT5MAgeDiff, Button buttonT5Generate, Label labelT5INameWarning, Label labelT5IYOBWarning, Label labelT5MADWarning) {
    	this.textfieldT5IName = textfieldT5IName;
    	this.textfieldT5IYOB = textfieldT5IYOB;
    	this.radiobuttonT5IGenderM = radiobuttonT5IGenderM;
    	this.radiobuttonT5IGenderF = radiobuttonT5IGenderF;
    	this.radiobuttonT5MGenderM = radiobuttonT5MGenderM;
    	this.radiobuttonT5MGenderF = radiobuttonT5MGenderF;
    	this.radiobuttonT5AgeO = radiobuttonT5AgeO;
    	this.radiobuttonT5AgeY = radiobuttonT5AgeY;
    	this.textfieldT5MAgeDiff = textfieldT5MAgeDiff;
    	this.buttonT5Generate = buttonT5Generate;
    	this.labelT5INameWarning = labelT5INameWarning;
    	this.labelT5IYOBWarning = labelT5IYOBWarning;
    	this.labelT5MADWarning = labelT5MADWarning;
    }
    
    private boolean checkT5IName() {
    	String iNameStr = textfieldT5IName.getText();
    	
    	// if empty, show nothing
    	if (iNameStr.isEmpty()) {
    		labelT5INameWarning.setVisible(false);
    		buttonT5Generate.setDisable(true);
    		return false;
    	}
    	
    	// if not chars, show error msg
    	if (!isPureChars(iNameStr)) {
    		labelT5INameWarning.setText("Please input a name with English characters only");
    		labelT5INameWarning.setVisible(true);
    		buttonT5Generate.setDisable(true);
    		return false;
    	}
    	iNameStr = iNameStr.substring(0,1).toUpperCase() + iNameStr.substring(1).toLowerCase();
    	// if can not find iName in iYOB for iGender, return false
    	if (checkT5IYOB() && (radiobuttonT5IGenderM.isSelected() || radiobuttonT5IGenderF.isSelected())) {
    		int iYOB = Integer.parseInt(textfieldT5IYOB.getText());
    		char iGender = radiobuttonT5IGenderM.isSelected() ? 'M' : 'F';
    		String iGenderStr = radiobuttonT5IGenderM.isSelected() ? "male" : "female";
    		if (TaskGeneral.getRank(iYOB, iNameStr, iGender) == TaskGeneral.getTotalNames(iYOB, iGender)) {
    			labelT5INameWarning.setText(String.format("Can not find this %s name in year %d", iGenderStr, iYOB));
    			labelT5INameWarning.setVisible(true);
    			buttonT5Generate.setDisable(true);
    			return false;
    		} else {
    	    	labelT5INameWarning.setVisible(false);
    	    	return true;
    		}
    	}
    	// info not sufficient
		labelT5INameWarning.setVisible(false);
		buttonT5Generate.setDisable(true);
    	return false;
    }
    
    private boolean checkT5IYOB() {
    	String iYOBStr = textfieldT5IYOB.getText();
    	// empty
    	if (iYOBStr.isEmpty()) {
    		labelT5IYOBWarning.setVisible(false);
    		buttonT5Generate.setDisable(true);
    		return false;
    	}
    	
    	// not pure number
    	if (!isDigit(iYOBStr) || (iYOBStr.length() != 4)) {
    		labelT5IYOBWarning.setText("Please input an 4 digits integer");
    		labelT5IYOBWarning.setVisible(true);
    		buttonT5Generate.setDisable(true);
    		return false;
    	}

    	int iYOBInt = Integer.parseInt(iYOBStr);
    	// preference and age diff are OK, check iYOB +- MAX between 1880 - 2019
    	if ((radiobuttonT5AgeY.isSelected() || radiobuttonT5AgeO.isSelected()) && isDigit(textfieldT5MAgeDiff.getText())) {
    		boolean older = radiobuttonT5AgeO.isSelected();
    		int bound = iYOBInt;
    		if (older) bound += Integer.parseInt(textfieldT5MAgeDiff.getText());
    		else bound -= Integer.parseInt(textfieldT5MAgeDiff.getText());
    		if (bound < 1880) {
    			labelT5IYOBWarning.setText("Please keep your birth year - max age difference >= 1880");
    			labelT5IYOBWarning.setVisible(true);
    			buttonT5Generate.setDisable(true);
        		return false;
    		}
    			
    		if (bound > 2019) {
    			labelT5IYOBWarning.setText("Please keep your birth year + max age difference <= 2019");
    			labelT5IYOBWarning.setVisible(true);
    			buttonT5Generate.setDisable(true);
        		return false;
    		}
    			
    	} else {
    		// not OK, only check iYOB between 1880 - 2019
    		if (iYOBInt < 1880 || iYOBInt > 2019) {
    			labelT5IYOBWarning.setText("Please input a year between 1880 - 2019");
    			labelT5IYOBWarning.setVisible(true);
    			buttonT5Generate.setDisable(true);
    			return false;
    		}
    	}
    	labelT5IYOBWarning.setVisible(false);
    	return true;
    }
    
    /**
     * check max age difference
     * @return
     */
    private boolean checkT5MAD() {
    	String maxAD = textfieldT5MAgeDiff.getText();
    	// empty
    	if (maxAD.isEmpty()) {
    		labelT5MADWarning.setVisible(false);
    		buttonT5Generate.setDisable(true);
    		return false;
    	}
    	
    	// not number
    	if (!isDigit(maxAD)) {
    		labelT5MADWarning.setText("Please input an integer with less than 9 digits");
    		labelT5MADWarning.setVisible(true);
    		buttonT5Generate.setDisable(true);
    		return false;
    	}
    	int maxADInt = Integer.parseInt(maxAD);
    	// preference and iYOB OK, check iYOB +- MAX between 1880 - 2019
    	if ((radiobuttonT5AgeY.isSelected() || radiobuttonT5AgeO.isSelected()) && isInBound(textfieldT5IYOB.getText())) {
    		boolean older = radiobuttonT5AgeO.isSelected();
    		int bound = Integer.parseInt(textfieldT5IYOB.getText());
    		if (older) bound += maxADInt;
    		else bound -= maxADInt;
    		if (bound < 1880) {
    			labelT5MADWarning.setText("Please keep your birth year - max age difference >= 1880");
        		labelT5MADWarning.setVisible(true);
        		buttonT5Generate.setDisable(true);
        		return false;
    		}
    			
    		if (bound > 2019) {
    			labelT5MADWarning.setText("Please keep your birth year + max age difference <= 2019");
    			labelT5MADWarning.setVisible(true);
        		buttonT5Generate.setDisable(true);
        		return false;
    		}

    	} else {
    		// info not sufficient, only check MAD <= 2019 - 1880
    		if (maxADInt > 2019 - 1880) {
    			labelT5MADWarning.setText("Please keep max age difference <= 139");
    			labelT5MADWarning.setVisible(true);
        		buttonT5Generate.setDisable(true);
        		return false;
    		}
    	}
    	
    	labelT5MADWarning.setVisible(false);
    	buttonT5Generate.setDisable(false);
    	return true;
    }
    
    /**
     * Start checking after an checker object is created.
     */
    @Override
    public void check() {
    	boolean iName = checkT5IName();
    	boolean iYOB = checkT5IYOB();
    	boolean maxAD = checkT5MAD();
    	
    	if (iName && iYOB && maxAD && (radiobuttonT5MGenderM.isSelected() || radiobuttonT5MGenderF.isSelected()))
    		buttonT5Generate.setDisable(false);
    	else buttonT5Generate.setDisable(true);
    }
}

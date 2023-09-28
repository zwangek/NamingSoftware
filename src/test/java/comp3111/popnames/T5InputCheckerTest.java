package comp3111.popnames;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class T5InputCheckerTest {
    private TextField textfieldT5IName = new TextField();
    private TextField textfieldT5IYOB = new TextField();
    private RadioButton radiobuttonT5IGenderM = new RadioButton();
    private RadioButton radiobuttonT5IGenderF = new RadioButton();
    private RadioButton radiobuttonT5MGenderM = new RadioButton();
    private RadioButton radiobuttonT5MGenderF = new RadioButton();
    private RadioButton radiobuttonT5AgeY = new RadioButton();
    private RadioButton radiobuttonT5AgeO = new RadioButton();
    private TextField textfieldT5MAgeDiff = new TextField();
    private Button buttonT5Generate = new Button();
    private Label labelT5INameWarning = new Label();
    private Label labelT5IYOBWarning = new Label();
    private Label labelT5MADWarning = new Label();
    
    public void reset() {
    	textfieldT5IName.setText("");
    	textfieldT5IYOB.setText("");
    	radiobuttonT5IGenderM.setSelected(false);
    	radiobuttonT5IGenderF.setSelected(false);
    	radiobuttonT5MGenderM.setSelected(false);
    	radiobuttonT5MGenderF.setSelected(false);
    	radiobuttonT5AgeY.setSelected(false);
    	radiobuttonT5AgeO.setSelected(false);
    	textfieldT5MAgeDiff.setText("");
    	labelT5INameWarning.setText("");
    	labelT5INameWarning.setVisible(false);
    	labelT5IYOBWarning.setText("");
    	labelT5IYOBWarning.setVisible(false);
    	labelT5MADWarning.setText("");
    	labelT5MADWarning.setVisible(false);
    	buttonT5Generate.setDisable(true);
    }
    
	public void test() {
    	T5InputChecker checker = new T5InputChecker(textfieldT5IName, textfieldT5IYOB, radiobuttonT5IGenderM, radiobuttonT5IGenderF,
        		radiobuttonT5MGenderM, radiobuttonT5MGenderF, radiobuttonT5AgeY, radiobuttonT5AgeO, textfieldT5MAgeDiff,
        		buttonT5Generate, labelT5INameWarning, labelT5IYOBWarning, labelT5MADWarning);
    	checker.check();
	}

	@Test
	public void testEmpty() {
		reset();
		test();
		assertTrue(buttonT5Generate.isDisable() == true);
	}
	
	@Test
	public void testNotName() {
		reset();
		textfieldT5IName.setText("1131bff");
		textfieldT5IYOB.setText("aabv");
		textfieldT5MAgeDiff.setText("-1");
		test();
		assertTrue(buttonT5Generate.isDisable() == true);
	}
	
	@Test
	public void testNameNotFound() {
		reset();
		textfieldT5IName.setText("aabbcejal");
		textfieldT5IYOB.setText("1950");
		radiobuttonT5IGenderM.setSelected(true);
		test();
		assertTrue(labelT5INameWarning.isVisible() == true);
	}
	
	@Test
	public void testYearOutBound() {
		textfieldT5IYOB.setText("1950");
		textfieldT5MAgeDiff.setText("2000");
		radiobuttonT5AgeY.setSelected(true);
		test();
		assertTrue(labelT5IYOBWarning.isVisible() == true);
		assertTrue(labelT5MADWarning.isVisible() == true);
		
		radiobuttonT5AgeY.setSelected(false);
		radiobuttonT5AgeO.setSelected(true);
		test();
		assertTrue(labelT5IYOBWarning.isVisible() == true);
		assertTrue(labelT5MADWarning.isVisible() == true);
		
		reset();
		textfieldT5IYOB.setText("2020");
		test();
		assertTrue(labelT5IYOBWarning.isVisible() == true);
		
		reset();
		textfieldT5MAgeDiff.setText("150");
		test();
		assertTrue(labelT5MADWarning.isVisible() == true);
	}
	
	@Test
	public void testValidInput() {
		reset();
		textfieldT5IName.setText("Tom");
		textfieldT5IYOB.setText("1960");
		radiobuttonT5IGenderM.setSelected(true);
		radiobuttonT5MGenderM.setSelected(true);
		radiobuttonT5AgeY.setSelected(true);
		textfieldT5MAgeDiff.setText("2");
		test();
		assertTrue(buttonT5Generate.isDisable() == false);
	}
}

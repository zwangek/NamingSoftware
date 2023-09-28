package comp3111.popnames;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class T2InputCheckerTest {

    private TextField textfieldT2SYear = new TextField();
    private TextField textfieldT2EYear = new TextField();
    private TextField textfieldT2Rank = new TextField();
    private RadioButton radioButtonT2M = new RadioButton();
    private RadioButton radioButtonT2F = new RadioButton();
    private Button buttonT2Generate = new Button();
    private Label labelT2SYWarning = new Label();
    private Label labelT2EYWarning = new Label();
    private Label labelT2RankWarning = new Label();
    
    public void reset() {
		textfieldT2SYear.setText("");
		textfieldT2EYear.setText("");
		textfieldT2Rank.setText("");
		radioButtonT2M.setSelected(false);
		radioButtonT2F.setSelected(false);
		labelT2SYWarning.setVisible(false);
		labelT2EYWarning.setVisible(false);
		labelT2RankWarning.setVisible(false);
		buttonT2Generate.setDisable(true);
    }
    
    public void test() {
		T2InputChecker checker = new T2InputChecker(textfieldT2SYear, textfieldT2EYear, textfieldT2Rank, radioButtonT2M, radioButtonT2F,
    			buttonT2Generate, labelT2SYWarning, labelT2EYWarning, labelT2RankWarning);
		checker.check();
    }
	@Test
	public void testCaseAllEmpty() {
		reset();
		test();
		assertTrue(buttonT2Generate.isDisable() == true);
	}
	@Test
	public void testCaseNotNumber() {
		reset();
		textfieldT2SYear.setText("nnn1!");
		textfieldT2SYear.setText("12va!");
		textfieldT2Rank.setText("abc");
		test();
		assertTrue(buttonT2Generate.isDisable() == true);
	}
	
	@Test
	public void testCaseOverflow() {
		reset();
		textfieldT2SYear.setText("111111111111111111111");
		textfieldT2EYear.setText("9999999999999999999");
		textfieldT2Rank.setText("1234966479876964");
		test();
		assertTrue(buttonT2Generate.isDisable() == true);
	}
	
	@Test
	public void testCaseValidInputM() {
		reset();
		textfieldT2SYear.setText("1950");
		textfieldT2EYear.setText("1960");
		textfieldT2Rank.setText("1");
		radioButtonT2M.setSelected(true);
		test();
		assertTrue(buttonT2Generate.isDisable() == false);
	}
	
	@Test
	public void testCaseValidInputF() {
		reset();
		textfieldT2SYear.setText("2000");
		textfieldT2EYear.setText("2016");
		textfieldT2Rank.setText("5");
		radioButtonT2F.setSelected(true);
		test();
		assertTrue(buttonT2Generate.isDisable() == false);
	}
	
	@Test
	public void testCaseInvalidRank() {
		reset();
		textfieldT2SYear.setText("1950");
		textfieldT2EYear.setText("1960");
		radioButtonT2F.setSelected(false);
		textfieldT2Rank.setText("999999");
		test();
		assertTrue(buttonT2Generate.isDisable() == true);
	}
	
	@Test
	public void testCaseOutOfBound() {
		reset();
		textfieldT2SYear.setText("1850");
		test();
		assertTrue(buttonT2Generate.isDisable() == true);
		
		textfieldT2EYear.setText("2020");
		test();
		assertTrue(buttonT2Generate.isDisable() == true);
		
		reset();
		textfieldT2SYear.setText("2000");
		textfieldT2EYear.setText("1950");
		test();
		assertTrue(buttonT2Generate.isDisable() == true);
	}
	
	@Test
	public void testRankStartWith0() {
		reset();
		textfieldT2Rank.setText("0010");
		test();
		assertTrue(buttonT2Generate.isDisable() == true);
	}
}

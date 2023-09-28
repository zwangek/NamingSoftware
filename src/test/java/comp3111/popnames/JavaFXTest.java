package comp3111.popnames;

import static org.junit.Assert.*;
import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class JavaFXTest extends ApplicationTest {

	private Scene s;
	private TextArea t;
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Popular Names");
   		stage.show();
   		s = scene;
		t = (TextArea)s.lookup("#textAreaConsole");
	}

    
	@Test
	public void testButtonRankTrue() {	
		clickOn("#tabTaskZero");
		clickOn("#buttonRankM");
		//sleep(1000);
		String s1 = t.getText();
		clickOn("#buttonRankM");
		//sleep(1000);
		String s2 = t.getText();
		assertTrue(s1.equals(s2));
	}
	
	
	@Test
	public void testButtonRankFalse() {	
		clickOn("#tabTaskZero");
		clickOn("#buttonRankM");
		//sleep(1000);
		String s1 = t.getText();
		clickOn("#buttonRankF");
		//sleep(1000);
		String s2 = t.getText();
		assertFalse(s1.equals(s2));
	}
	
	
	@Test
	public void testTextAreaConsole() {	
		t.setText("David");
		String s = t.getText();
		assertTrue(s.equals("David"));
	}
	
	@Test
	public void testT1EmptyInput() {	
		clickOn("#tabReport1");
		clickOn("#buttonT1Report");
		String s1 = t.getText();
		assertTrue(s1.equals("Please enter the year of interest\n"));
	}
	
	@Test
	public void testT1Input() {	
		clickOn("#tabReport1");
		TextField year = (TextField)s.lookup("#textfieldT1Year");
		year.setText("2000");
		
		TextField num = (TextField)s.lookup("#textfieldT1Num");
		num.setText("2");
		
		clickOn("#buttonT1Report");
		
		String s1 = t.getText();
		String s2 = "Summary (Year of 2000):\n\n" + 
				"Jacob is the most popular name with the number of occurrences of 34465, \n" + 
				"which represents 1.76% of total male births in 2000.\n\n" + 
				"Emily is the most popular name with the number of occurrences of 25952, \n" + 
				"which represents 1.43% of total female births in 2000.\n\n";
		assertTrue(s1.equals(s2));
	}
	
	@Test
	public void testT4EmptyInput() {	
		clickOn("#tabApp1");
		
		clickOn("#buttonT4Recommend");
		
		String s1 = t.getText();
		String s2 = "Please enter the name of baby's dad\n";
		assertTrue(s1.equals(s2));
	}
	
	@Test
	public void testT4InputAlgorithm1() {	
		clickOn("#tabApp1");
		TextField dadName = (TextField)s.lookup("#textfieldT4DadName");
		dadName.setText("Tony");
		
		TextField momName = (TextField)s.lookup("#textfieldT4MomName");
		momName.setText("Mary");
		
		TextField dadYear = (TextField)s.lookup("#textfieldT4DadYOB");
		dadYear.setText("1880");
		
		TextField momYear = (TextField)s.lookup("#textfieldT4MomYOB");
		momYear.setText("1880");
		
		clickOn("#buttonT4Recommend");
		
		String s1 = t.getText();
		String s2 = "For a baby boy, we recommend John.\n" + 
				"This name is the most popular male name in year 1880, occuring 9655 times, \n" + 
				"which represents 8.74% of total male births in 1880.\n" + 
				"With a popular name, we hope your baby boy will also be very popular!\n\n" + 
				"For a baby girl, we recommend Mary.\n" + 
				"This name is the most popular female name in year 1880, occuring 7065 times, \n" + 
				"which represents 7.76% of total female births in 1880.\n" + 
				"With a popular name, we believe your baby girl will have many friends!\n\n";
		assertTrue(s1.equals(s2));
	}
	
	@Test
	public void testT4InputAlgorithm2() {	
		clickOn("#tabApp1");
		TextField dadName = (TextField)s.lookup("#textfieldT4DadName");
		dadName.setText("Tony");
		
		TextField momName = (TextField)s.lookup("#textfieldT4MomName");
		momName.setText("Mary");
		
		TextField dadYear = (TextField)s.lookup("#textfieldT4DadYOB");
		dadYear.setText("1880");
		
		TextField momYear = (TextField)s.lookup("#textfieldT4MomYOB");
		momYear.setText("1880");
		
		clickOn("#radioButtonT4A2");
		clickOn("#buttonT4Recommend");
		
		String s1 = t.getText();
		String s2 = "For a baby boy, we recommend Neal.\n" + 
				"This name is very similar to dad's in popularity in year 1880. Out of 1058 male names, Neal is rank 257.\n" + 
				"And baby's dad name Tony is rank 261.\n" + 
				"With a name of similar popularity, we believe this baby boy will share a similar feeling as his dad towards their name, \n" + 
				"forming more special family bonds.\n" + 
				"\n" + 
				"For a baby girl, we recommend Minnie.\n" + 
				"This name is very similar to mom's in popularity in year 1880. Out of 942 female names, Minnie is rank 5.\n" + 
				"And baby's mom name Mary is rank 1.\n" + 
				"With a name of similar popularity, we believe this baby girl will share a similar feeling as her mom towards their name, \n" + 
				"forming more special family bonds.\n" + 
				"\n";
		assertTrue(s1.equals(s2));
	}
	
	@Test
	public void testT4EmptyYear() {	
		clickOn("#tabApp1");
		TextField dadName = (TextField)s.lookup("#textfieldT4DadName");
		dadName.setText("Tony");
		
		TextField momName = (TextField)s.lookup("#textfieldT4MomName");
		momName.setText("Mary");
	
		clickOn("#buttonT4Recommend");
		
		String s1 = t.getText();
		String s2 = "Please enter the year of birth of baby's dad\n";
		assertTrue(s1.equals(s2));
	}
	
	@Test
	public void testT4EmptyYearDouble() {	
		clickOn("#tabApp1");
		TextField dadName = (TextField)s.lookup("#textfieldT4DadName");
		dadName.setText("Tony");
		
		TextField momName = (TextField)s.lookup("#textfieldT4MomName");
		momName.setText("Mary");

		clickOn("#buttonT4Recommend");
		clickOn("#buttonT4Recommend");
		
		String s1 = t.getText();
		String s2 = "Please enter the year of birth of baby's dad\n";
		assertTrue(s1.equals(s2));
	}
  
  @Test
	public void testTask3button() {
		clickOn("#tabReport3");
		clickOn("#R3button");
		String s1 = t.getText();
		clickOn("#R3button");
		String s2 = t.getText();
		assertEquals(s1,s2);
	}
	
	@Test
	public void testTask6button() {
		clickOn("#tabApp3");
		clickOn("#A3Button");
		String s1 = t.getText();
		clickOn("#A3Button");
		String s2 = t.getText();
		assertEquals(s1,s2);
	}
		
}

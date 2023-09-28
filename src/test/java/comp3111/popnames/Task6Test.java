package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;
import javafx.scene.Scene;

public class Task6Test {
	
	@Test
	public void TestAlgorithmX1() {
		String result = Task6.compatibility_X1("Oliver","Olivia");
		assertEquals(result,"Your compatibility score with your mate is 100.000000.");
	}
	
	@Test
	public void TestAlgorithmX2() {
		String result = Task6.compatibility_X2("Oliver","M",1994,"Olivia","F",1996,false);
		assertEquals(result,"Between you and your mate's year of birth, that is, between 1994 and 1996,\n"
				+ "your name has average rank of 383.333344, your mate's name has a average rank of 41.000000.\n"
				+ "You and your mate's names are far apart in terms of popularity, your mate's age is just right for you.\n"
				+ "Therefore, your compatibility score with your mate is 83.824226.");
	}
	
	@Test
	public void TestX2Chart() {
		Scene scene = Task6.X2Chart("Oliver","M",1994,"Olivia","F",1996);
		assertNotNull(scene);
	}

}

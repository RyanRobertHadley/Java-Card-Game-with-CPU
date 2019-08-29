import java.io.FileNotFoundException;

public class run {

	//Purpose: Launches a new game of TUNK by creating a new game state
	//Pre-Conditions: The other classes are prepared
	//Inputs: NA
	//Post-Conditions: The game will be run
	public static void main(String[] args) throws FileNotFoundException {

		tunk_controller tunkGame = new tunk_controller();
		System.out.println("GET READY TO TUNK!!!!");
		tunkGame.startUp();
	}
	
}


//current hours spent on project : 48 hrs


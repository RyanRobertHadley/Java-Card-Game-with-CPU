
public class card {
	private int rank;
	private String suite;
	private String name;
	
	//the card is the basic item that will make up a deck 
	//Purpose: Organized information of every card in a game
	public card(int rank, String suite, String name){
		
		this.rank = rank;
		this.suite = suite;
		this.name = name;
	}
	
	//Purpose: Generic getter for the name value of a card
	//Pre-Conditions: There exists a card with a name that the getter will be used for
	//Inputs: NA
	//Post-Conditions: The name of a particular card will be returned
	public String getName(){
		
		return name;
	}
	//Purpose: Generic getter for the rank value of a card
	//Pre-Conditions: There exists a card with a rank that the getter will be used for
	//Inputs: NA
	//Post-Conditions: The rank of a particular card will be returned
	public int getRank(){
		return rank;
	}
	
	//Purpose: Generic getter for the suite value of a card
	//Pre-Conditions: There exists a card with a suite that the getter will be used for
	//Inputs: NA
	//Post-Conditions: The suite of a particular card will be returned	
	public String getSuite(){
		return suite;
	}
}

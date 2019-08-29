import java.io.FileNotFoundException;
import java.util.ArrayList;

public class tunk_controller {

	private player playerOne;				//player deck and card are the model for the tunk game
	private cpu thisCpu;
	private deck gameDeck;
	private tunk_view gameView;
	private XML thisXml;
	
	//Generic constructor for a new gameState
	public tunk_controller(){
		ArrayList<card>startingHand  = new ArrayList<card>(0) ;
		this.playerOne  =  new player(startingHand, 0);
		this.thisCpu = new cpu(startingHand, 0);
		this.gameDeck = gameDeck;
		this.gameView = new tunk_view();
		try {
			this.thisXml = new XML();
		} catch (FileNotFoundException e) {
		}
		thisXml.initial("Human", "Computer");
	}
	//Purpose: Change the deck of the current game. Used to add the default 52 cards 
	//Pre-Conditions: There is a deck to be changed
	//Inputs: the games deck
	//Post-Conditions: deck will be changed
	public void setGameDeck(deck newGameDeck) {
		this.gameDeck = newGameDeck;
	}
	//Purpose: Return the player one of the current game
	//Pre-Conditions: there is a player playing the game
	//Inputs: NA
	//Post-Conditions: The current player will be returned
	public player getPlayerOne(){
		return playerOne;
	}
	//Purpose: Return the cpu one of the current game
	//Pre-Conditions: there is a cpu playing the game
	//Inputs: NA
	//Post-Conditions: The current cpu will be returned
	public cpu getCpu(){
		return thisCpu;
	}
	//Purpose: Return the deck that is being used in this game
	//Pre-Conditions: there is a game with a deck to be retrieved 
	//Inputs: NA
	//Post-Conditions: The current deck will be returned
	public deck getGameDeck(){
		return gameDeck;
	}
	//Purpose: Begins the game, this will be called by the main method
	//Pre-Conditions: the main method has a call to this method
	//Inputs: NA
	//Post-Conditions: the game will be started and the player will be presented with respective options
	public void startUp(){
		gameView.printToUser("Welcome to the game of Tunk");
		this.gameDeck = new deck();					//this is where a new deck is created for this game using the deck class
		ArrayList<card> startingHand = new ArrayList<card>(0);
		this.playerOne = new player(startingHand, 0);
		beginTheGame();
		
	}
	
	//Purpose: For increment one, get the first player and only player to begin the game and insert his name
	//Pre-Conditions: the player has chosen to begin the game
	//Inputs: a game of tunk that the player has begun
	//Post-Conditions: the deck class will be called to give the player the option to deal the cards or call tunk
	public void beginTheGame(){
		this.gameDeck.initialCardDeal(this);
		gameView.printToUser("Here's your hand : ");
		sendViewCardNames(this.playerOne);
		gameView.printToUser(thisCpu.cpuDraw(this));
		gameView.printToUser(thisCpu.cpuDiscard(this));
		gameView.playerTurn(this);
		
		
	}
	
	//Purpose: Tunk has been called and the players total score will be added to
	//Pre-Conditions: there is a player in the game who has chosen to call tunk
	//Inputs: the current game of tunk and its information
	//Post-Conditions: the total score will be added to and if the maximum score has been reached the player will be removed from the game
	public void tunkCalled(){
		this.playerOne.addToScore(this.playerOne, this);
		this.thisCpu.addToScore(this.thisCpu, this);
		this.gameView.displayTotalScore(this.playerOne.getTotalScore());
		this.gameView.displayCpuTotalScore(this.thisCpu.getTotalScore());
		if (this.playerOne.getTotalScore() > 99){
			this.gameView.printToUser("You have reached a score of 100 or greater, and have lost the game.");

			thisXml.endOfRound("" + thisCpu.getTotalScore(), "" + playerOne.getTotalScore());
			thisXml.saveToXML();

		}
		else if (this.thisCpu.getTotalScore() > 99){
			this.gameView.printToUser("The computer player has reached a score of 100 or greater. You have won the game.");

			thisXml.endOfRound("" + thisCpu.getTotalScore(), "" + playerOne.getTotalScore());
			thisXml.saveToXML();
		}
		else{
			this.gameView.playerDecision(this);
		}

		}
	
	//Purpose: The controller will send the view a list of cardnames to be printed
	//Pre-Conditions: the player has cards in his/her hand
	//Inputs: a player 
	//Post-Conditions: the view class will print the names
	public void sendViewCardNames(player thisPlayer){
		String printNames = "";
		for(int x = 0; x < 7; x++){
			printNames = printNames + ("" + (x+1) + ":" + thisPlayer.getHand().get(x).getName() + " of " + thisPlayer.getHand().get(x).getSuite() + " ");
		}
		gameView.printToUser(""+ printNames);
	}
	
	//Purpose: Takes a chosen input from the view and then accesses the model to complete the task of discarding
	//Pre-Conditions: There is a player who is currently playing
	//Inputs: int x representing the index position of the card to be removed
	//Post-Conditions: discardACard in the player class will be utilized
	public void recievePlayerChoice(int x){
		playerOne.discardACard(this,x,1);
		
	}
	//Purpose: Input validation for the players choice of card to draw
	//Pre-Conditions: There is a player who is currently playing
	//Inputs: an int representing which pile the player would like to draw from
	//Post-Conditions: The game will continue if the player succesfully chooses a non empty pile
	public void recieveDrawInput(int x){
		if(x == 1){
			if(!gameDeck.isStockPileEmpty()){
			card e = gameDeck.drawFromStockPile(playerOne.getHand());
			gameView.printToUser("You drew a " + e.getName() + " of " + e.getSuite());
			sendViewUpdatedHand(playerOne);
			
			gameView.playerDiscardChoice(this);
			}
			
			else{
				gameView.printToUser("That pile is empty!");
				gameView.playerTurn(this);
			}
		}
		if(x == 2){
			if(!gameDeck.isDiscardPileEmpty()){
			card e = gameDeck.drawFromDiscardPile(playerOne.getHand());
			gameView.printToUser("You drew a " + e.getName() + " of " + e.getSuite());
			sendViewUpdatedHand(playerOne);

			gameView.playerDiscardChoice(this);
			}
			
			else{
				gameView.printToUser("That pile is empty!");
				gameView.playerTurn(this);
			}
		}
	}
	//Purpose: Will command the view class to display to the user the most up to date version of their hand after changes were made
	//Pre-Conditions: There is a player who is currently playing
	//Inputs: player whos hand is to be updated
	//Post-Conditions: The player will be shown all card they currently posses 
	public void sendViewUpdatedHand(player thisPlayer){
		ArrayList<String> newHand = thisPlayer.updateHand();
		String printNames = "";
		gameView.printToUser("Your new hand is: ");
			for(int x = 0; x< newHand.size(); x++){
				printNames = printNames +("" + (x+1) +": " +newHand.get(x) + " ");
			}
		gameView.printToUser(printNames);
	}

	
	public void tunk(card e, int x){
		gameDeck.takeCardFromComputer(e, this, x);
	}
}
	
	
	

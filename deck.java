import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

//Purpose :the deck class will serve as a record of all of the cards that are in play in any given game, as well as where they are 

public class deck {
	//discard pile will be implemented at a later time
	private ArrayList<card> stockPile;
	private ArrayList<card> discardPile;
	
	//constructor for the deck class, this is where I chose to create the original deck of 52 cards, so that each deck created will come with them all
	public deck(){
		stockPile = new ArrayList<card>(52);
		discardPile = new ArrayList<card>();
		addCards(this, "Spades");
		addCards(this, "Diamonds");
		addCards(this, "Clubs");
		addCards(this, "Hearts");
	}
	//Purpose: returns boolean representing whether or not the stockpile is empty
	//Pre-Conditions: There is a gamedeck with a stockpile with or without cards
	//Inputs: na
	//Post-Conditions:true or false returned respectively 
	public boolean isStockPileEmpty(){
		
		return this.stockPile.isEmpty();
	}
	//Purpose: returns boolean representing whether or not the discardpile is empty
	//Pre-Conditions: There is a gamedeck with a discardpile with or without cards
	//Inputs: na
	//Post-Conditions:true or false returned respectively
	public boolean isDiscardPileEmpty(){
		
		return this.discardPile.isEmpty();
	}	
	
	//Purpose: Generic getter for the stockpile of a deck
	//Pre-Conditions: There exists a stockpile to be retrieved 
	//Inputs: NA
	//Post-Conditions: The arraylist representing the stockpile will be returned
	public ArrayList<card> getStockPile(){
		return this.stockPile;
	}
	
	//Purpose: Player will decide whether or not to deal the cards and whether or not to call out tunk(which they must do for increment one)
	//Pre-Conditions: The tunk_controller has guided the player to the point where they can deal the cards
	//Inputs: the tunk_controller of the current game of TUNK
	//Post-Conditions: Cards will be dealt by calling the following deal method and the tunk_controller class may be told to call tunk by the player
	public void initialCardDeal(tunk_controller thisGame){
			//makes a call to the deck class to dish it out!!!
		    returnHandtoDeck(thisGame);
			deck.deal(thisGame);
			deck.dealCpu(thisGame);
	}

	 
	//Purpose: Seven random cards are taken from the stock pile and added to the players hand
	//Pre-Conditions: A deck must have at least seven cards in it as the situation has not been accounted for
	//Inputs: A player to recieve the cards and a deck where they are to be taken from
	//Post-Conditions: The cards will no longer be in the stockpile of the games stockpile and will be in possesion of the player in their deck
	private static void deal(tunk_controller thisGame){
		System.out.println("Dealing the cards...");
		thisGame.getGameDeck().shuffleDeck(thisGame.getGameDeck());
		ArrayList<card> dealtCards = new ArrayList<card>(7);
		for (int x = 0; x<7; x++){
			dealtCards.add(thisGame.getGameDeck().stockPile.get(x));			//this insures that the players are not dealt duplicate cards
		}
		for (int x = 0; x<7; x++){
			thisGame.getGameDeck().stockPile.remove(x);	//removes the dealt cards from stockpile it took me a long time to figure out I had to separate this part in another loop		
		}
		thisGame.getPlayerOne().setHand(dealtCards);
	}
	//Purpose: Seven random cards are taken from the stock pile and added to the cpu hand
	//Pre-Conditions: A deck must have at least seven cards in it as the situation has not been accounted for
	//Inputs: A cpu to recieve the cards and a deck where they are to be taken from
	//Post-Conditions: The cards will no longer be in the stockpile of the games stockpile and will be in possesion of the cpu in their deck
	private static void dealCpu(tunk_controller thisGame){
		ArrayList<card> dealtCardsCpu = new ArrayList<card>(7);
		int len = thisGame.getGameDeck().stockPile.size()-1;
		for (int x = 0; x<7; x++){
			dealtCardsCpu.add(thisGame.getGameDeck().stockPile.get(len-x));			//this insures that the players are not dealt duplicate cards 
		}
		for (int x = 0; x<7; x++){
			thisGame.getGameDeck().stockPile.remove(len-x);	//removes the dealt cards from stockpile it took me a long time to figure out I had to separate this part in another loop		
		}
		thisGame.getCpu().setHand(dealtCardsCpu);

	}
	
	//Purpose: Player will return their cards to the stockpile
	//Pre-Conditions: There is a game with a player and a deck 
	//Inputs: The current game of tunk where the desired action is to take place
	//Post-Conditions: Cards in a players hand will be placed back into the stockpile	
	private void returnHandtoDeck(tunk_controller thisGame){
		for (int x = 0; x < thisGame.getPlayerOne().getHand().size(); x++){
			thisGame.getGameDeck().stockPile.add(thisGame.getPlayerOne().getHand().get(x));	
		}
		for(int x = 0; x < thisGame.getPlayerOne().getHand().size(); x++){
			thisGame.getPlayerOne().getHand().remove(x);
		}
		
		for (int x = 0; x < thisGame.getCpu().getHand().size(); x++){
			thisGame.getGameDeck().stockPile.add(thisGame.getCpu().getHand().get(x));	
		}
		for(int x = 0; x < thisGame.getCpu().getHand().size(); x++){
			thisGame.getCpu().getHand().remove(x);
		}
	}
	
	//Purpose: A deck will have the 13 cards from a suite added to it
	//Pre-Conditions: There is a deck
	//Inputs: a deck to have cards added to and a name of the suite 
	//Post-Conditions: 13 cards will be added to the deck
	private void addCards(deck emptyDeck, String suiteName){
		int value = 1;
		String[] cardNames = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
		for (int x = 0; x< 13; x++){
			card newCard = new card(value, suiteName, "" + cardNames[x]);
			emptyDeck.stockPile.add(newCard);
			if (value < 10){
				value++;
			}
		}
	}
	
	//Purpose: Shuffle the cards in the game deck before they are dealt again
	//Pre-Conditions: There is a deck to be shuffled
	//Inputs: A deck to be shuffled
	//Post-Conditions: The input deck will be in a randomized order
	private void shuffleDeck(deck gameDeck){
		Collections.shuffle(gameDeck.stockPile);
	}
	//Purpose: removes a card from the hand and places it in the discard pile
	//Pre-Conditions: There is a player and a discard pile 
	//Inputs: the card to be removed, the hand of the player, and the given index position of that card
	//Post-Conditions: the card will be moved to the discard pile
	public void discard(card e, ArrayList<card> hand, int x){
		discardPile.add(e);
		hand.remove(x);
	}
	//Purpose: draws a card from the stockpile and gives it to the player
	//Pre-Conditions: There is a player and a stock pile 
	//Inputs: the hand
	//Post-Conditions: the card will be moved to the players hand
	public card drawFromStockPile(ArrayList<card> hand){
		int x = stockPile.size()-1;
		card e = stockPile.get(stockPile.size()-1);
		hand.add(e); ///will add the top card of the stockpile
		stockPile.remove(x);
		return e;
	}
	//Purpose: draws a card from the discard pile and gives it to the player
	//Pre-Conditions: There is a player and a discard pile 
	//Inputs: the hand
	//Post-Conditions: the card will be moved to the players hand
	public card drawFromDiscardPile(ArrayList<card> hand){
		card e = discardPile.get(discardPile.size()-1);
		hand.add(e); ///will add the top card of the discardPile
		discardPile.remove(e);
		return e;
	}
	//Purpose: draws a card from the computer and gives it to the player
	//Pre-Conditions: There is a player and a computer player with cards
	//Inputs: na
	//Post-Conditions: the card will be moved to the players hand
	public void takeCardFromComputer(card e, tunk_controller thisGame, int x){
		thisGame.getPlayerOne().getHand().add(e);
		thisGame.getCpu().getHand().remove(x);
		System.out.println();
		System.out.println("You took the " + e.getName() + " of " + e.getSuite() + " from the computer!!!");
		System.out.println();
	}
}


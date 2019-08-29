import java.util.ArrayList;
import java.util.Scanner;

public class player {
	
	private ArrayList<card> hand;
	private int totalScore;
	private String name;
	
	public player(ArrayList<card> hand, int totalScore){
		this.setHand(hand);
		this.totalScore = totalScore;
	}
	
	//Purpose: Generic getter for the hand of a player
	//Pre-Conditions: A player with a hand is available
	//Inputs: NA
	//Post-Conditions: The hand will be returned	
	public ArrayList<card> getHand(){
		return hand;
	}
	
	//Purpose: Change the hand of a player. This is used to update the players hand after new cards have been dealt 
	//Pre-Conditions: There is a hand to be changed
	//Inputs: The new hand for the player
	//Post-Conditions: The cards will no longer be in the stockpile of the games stockpile and will be in possesion of the player in their deck	
	public void setHand(ArrayList<card> hand) {
		this.hand = hand;
	}
	
	//Purpose: Return the current score of a player 
	//Pre-Conditions: there is a player with a score to be retrieved
	//Inputs: NA
	//Post-Conditions: The current total score for the player will be returned
	public int getTotalScore(){
		return totalScore;
	}
	
	//Purpose: Return the name of a player 
	//Pre-Conditions: there is a player with a name to be retrieved
	//Inputs: NA
	//Post-Conditions: The name for the player will be returned
	public String getName(){
		return name;
	}
	
	//Purpose: Add the score from a players hand to their total score
	//Pre-Conditions: there is a player with a score to be added to
	//Inputs: a player who's score must be added to
	//Post-Conditions: the players score will be added together
	public int addToScore(player thisPlayer, tunk_controller thisGame){
		checkForSetOfRank(thisPlayer, thisGame);
		checkForSequenceOfSuite(thisPlayer, thisGame);
		ArrayList<card> handToAdd = this.getHand();
		for (int x = 0; x < handToAdd.size(); x++){
			card cardToAdd = handToAdd.get(x);
			thisPlayer.totalScore += cardToAdd.getRank();
		}
		return thisPlayer.totalScore;	
	}
	
	//Purpose: Check the players hand for sets
	//Pre-Conditions: There is a player with a hand of cards
	//Inputs: the player with the hand to be evaluated and the current game of tunk
	//Post-Conditions: Paired cards will be identified and removeMatched will be called
	private void checkForSetOfRank(player thisPlayer, tunk_controller thisGame){
		ArrayList<card> matchedCards = new ArrayList<card>();//will contain all cards found to be matches
		for(int x = 0; x < thisPlayer.getHand().size(); x++){			
			card firstCheck = thisPlayer.getHand().get(x);			
			for(int y = x+1; y < thisPlayer.getHand().size(); y++){				
				card secondCheck = thisPlayer.getHand().get(y);				
				if(firstCheck.getName().equals(secondCheck.getName())){
					checkForSetOfRankThree(matchedCards, firstCheck, secondCheck, x, y, thisPlayer, thisGame);
				}
			}
		}
		
		removeMatchedCards(matchedCards, thisPlayer, thisGame);		//remove a match of three cards if no fourth card is found
	}
	//Purpose: Check the players hand for set of three
	//Pre-Conditions: There is a player with a hand of cards
	//Inputs: the player with the hand to be evaluated and the current game of tunk
	//Post-Conditions: Paired cards will be identified and removeMatched will be called	
	private void checkForSetOfRankThree(ArrayList<card> matchedCards, card firstCheck, card secondCheck, int x, int y, player thisPlayer, tunk_controller thisGame){
		for(int z = y+1; z < thisPlayer.getHand().size(); z++){						
			card thirdCheck = thisPlayer.getHand().get(z);						
			if(secondCheck.getName().equals(thirdCheck.getName())){	//third in a set of pairs has been found and will be removed from the scoring	
				matchedCards.add(firstCheck);
				matchedCards.add(secondCheck);
				matchedCards.add(thirdCheck);	
				System.out.println("There was a match of three!");
				checkForSetOfRankFour(matchedCards, firstCheck, secondCheck, thirdCheck, x, y, z, thisPlayer, thisGame);
		}
		}
	}
	
	
	//Purpose: Check the players hand for set of four
	//Pre-Conditions: There is a player with a hand of cards
	//Inputs: the player with the hand to be evaluated and the current game of tunk
	//Post-Conditions: Paired cards will be identified and removeMatched will be called	
	private void checkForSetOfRankFour(ArrayList<card> matchedCards, card firstCheck, card secondCheck, card thirdCheck, int x, int y, int z, player thisPlayer, tunk_controller thisGame){
		for(int q = z+1; q < thisPlayer.getHand().size(); q ++){								
			card fourthCheck = thisPlayer.getHand().get(q);								
			if(thirdCheck.getName().equals(fourthCheck.getName())){
				matchedCards.add(fourthCheck);			//possible fourth card to be added to the matched set
				System.out.println("There was a match of four!!!!");
			}
		}
	}
	//Purpose: Check the players hand for a sequence using two helper methods
	//Pre-Conditions: There is a player with a hand of cards
	//Inputs: the player with the hand to be evaluated and the current game of tunk
	//Post-Conditions: Paired cards will be identified and removeMatched will be called			
	private void checkForSequenceOfSuite(player thisPlayer, tunk_controller thisGame){
		ArrayList<card> matchedCards = new ArrayList<card>();//will contain all cards found to be matches
		for(int x = 0; x < thisPlayer.getHand().size(); x++){	
			card firstCheck = thisPlayer.getHand().get(x);
			for(int y = 0; y < thisPlayer.getHand().size(); y++){
				card secondCheck = thisPlayer.getHand().get(y);
				if(firstCheck.getSuite() == secondCheck.getSuite() && firstCheck.getRank() == (secondCheck.getRank() - 1)){
					checkForSequenceOfSuiteThree(matchedCards, firstCheck, secondCheck, thisPlayer, thisGame);
				}
			}
		}
		removeMatchedCards(matchedCards, thisPlayer, thisGame);
	}
	//Purpose: Check the players hand for a base sequence of three
	//Pre-Conditions: There is a player with a hand of cards
	//Inputs: the current matched cards, two previously confirmed cards in a sequence the player with the hand to be evaluated and the current game of tunk
	//Post-Conditions: Paired cards will be identified and removeMatched will be called	
	private void checkForSequenceOfSuiteThree(ArrayList<card> matchedCards, card firstCheck, card secondCheck, player thisPlayer, tunk_controller thisGame){
		for(int a = 0; a < thisPlayer.getHand().size(); a++){
			card thirdCheck = thisPlayer.getHand().get(a);
			if(thirdCheck.getSuite() == secondCheck.getSuite() && thirdCheck.getRank() == (secondCheck.getRank() + 1)){
				matchedCards.add(firstCheck);
				matchedCards.add(secondCheck);
				matchedCards.add(thirdCheck);
				System.out.println("There was a suite sequence of three!!!");
				checkForSequenceOfSuiteFour(matchedCards, firstCheck, secondCheck, thirdCheck, thisPlayer, thisGame);
			}
		}
	}
	//Purpose: Check the players hand for a possibly sequence of four
	//Pre-Conditions: There is a player with a hand of cards
	//Inputs: the current matched cards, three previously confirmed cards in a sequence the player with the hand to be evaluated and the current game of tunk
	//Post-Conditions: Paired cards will be identified and removeMatched will be called		
	private void checkForSequenceOfSuiteFour(ArrayList<card> matchedCards, card firstCheck, card secondCheck, card thirdCheck, player thisPlayer, tunk_controller thisGame){
		for(int b = 0; b < thisPlayer.getHand().size(); b++){
			card fourthCheck = thisPlayer.getHand().get(b);
			if(fourthCheck.getSuite() == thirdCheck.getSuite() && fourthCheck.getRank() == (thirdCheck.getRank()+1)){
				matchedCards.add(fourthCheck);
			}
		}
	}
	//Purpose: Remove selected cards from the players hand
	//Pre-Conditions: There is a player with cards in a hand to be removed
	//Inputs:The list of cards to be removed, the player they are to be removed from, and the current game of tunk
	//Post-Conditions: The hand will be printed to the user		
	private void removeMatchedCards(ArrayList<card> matchedCards, player thisPlayer, tunk_controller thisGame){
		for(int x = 0; x < matchedCards.size(); x++){
			for(int y = 0; y < thisPlayer.getHand().size(); y++){
				if(matchedCards.get(x) == thisPlayer.getHand().get(y)){
					thisGame.getGameDeck().getStockPile().add(thisPlayer.getHand().get(y));	//loop to add the cards to the stockpile
				}
			}
		}
		for(int x = 0; x < matchedCards.size(); x++){
			for(int y = 0; y < thisPlayer.getHand().size(); y++){
				if(matchedCards.get(x) == thisPlayer.getHand().get(y)){		//loop to remove the cards from the players hand
					thisPlayer.getHand().remove(y);
				}
			}
		}
		
	}
	//Purpose: Calls the discard method with a specific index to remove the card from
	//Pre-Conditions: There is a gamedeck with cards
	//Inputs: the current game of tunk, the index of desired card to remove
	//Post-Conditions:discard card will be used on the gamedeck
	public void discardACard(tunk_controller thisGame, int x, int y){
		card e = thisGame.getPlayerOne().getHand().get(x);
		if (y == 1){
			System.out.println("You discarded the " + e.getName() + " of " + e.getSuite());
		}
		thisGame.getGameDeck().discard(hand.get(x), hand, x);
	}
	//Purpose: Create an easily printable list of the current cards in a players hand
	//Pre-Conditions: There is a player with a hand
	//Inputs: na
	//Post-Conditions: the arraylist with the new names of the cards will be returned
	public ArrayList<String> updateHand(){
		ArrayList<String> handTitles = new ArrayList<String>();
		for(int x = 0; x < hand.size(); x++){
			String cardTitle = hand.get(x).getName() +" of " + hand.get(x).getSuite();
			handTitles.add(cardTitle);
		}
		return handTitles;
	}
	
}

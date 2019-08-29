import java.util.ArrayList;
import java.util.Random;
public class cpu extends player{

	public cpu(ArrayList<card> hand, int totalScore){
		super(hand, totalScore);
		this.setHand(hand);
	}
	//Purpose: Allows the cpu to discard a card
	//Pre-Conditions: A computer player with a hand of cards
	//Inputs: current game of tunk
	//Post-Conditions: the name of the discarded card will be returned
	public String cpuDiscard(tunk_controller thisGame){
		Random rand = new Random();
		int x = rand.nextInt(thisGame.getCpu().getHand().size()-2);
		card e = thisGame.getCpu().getHand().get(x);
		String discardName = "The computer discarded : " + e.getName() + " of " + e.getSuite() + "";
		thisGame.getCpu().discardACard(thisGame, x+1, 2);
		return discardName;
	}
	//Purpose: Allows the cpu to draw a card
	//Pre-Conditions: A computer player with a hand of cards
	//Inputs: current game of tunk
	//Post-Conditions: the name of the drawn card will be returned
	public String cpuDraw(tunk_controller thisGame){
		if (thisGame.getGameDeck().isDiscardPileEmpty() == true){
			card e = thisGame.getGameDeck().drawFromStockPile(thisGame.getCpu().getHand());

			String drawName = "The computer drew a : " + e.getName() + " of " + e.getSuite() + " from the Stock Pile";
			return drawName;
		}
		else{
			card e = thisGame.getGameDeck().drawFromDiscardPile(thisGame.getCpu().getHand());

			String drawName = "The computer drew a : " + e.getName() + " of " + e.getSuite() + " from the Discard Pile";
			return drawName;
		}
	}
}





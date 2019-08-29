import java.util.Scanner;

public class tunk_view {
	
	public tunk_view(){
	}
	//Purpose: display anything to the user, the contoller will use this
	//Pre-Conditions: there is a string to be displayed
	//Inputs: a string to be displayed
	//Post-Conditions: the object will be displayed
	public void printToUser(String s){
		System.out.println();
		System.out.println("" + s);		
	}
	//Purpose: display the current total score of a player
	//Pre-Conditions: there is a player with a score to be displayed
	//Inputs: a player who's score must be displayed
	//Post-Conditions: the players score will be displayed
	public void displayTotalScore(int totalScore){
		
		System.out.println("Your new total score is : " + totalScore);
	}
	
	//Purpose: display the current total score of a cpu
	//Pre-Conditions: there is a cpu with a score to be displayed
	//Inputs: a cpu who's score must be displayed
	//Post-Conditions: the cpu score will be displayed
	public void displayCpuTotalScore(int totalScore){
		
		System.out.println("The computer's total score is : " + totalScore);
	}
	
	//Purpose: Allow the player to continue or to end the game
	//Pre-Conditions: There is a player who is currently playing
	//Inputs: The current game of tunk
	//Post-Conditions: The game will either continue or end	
	public void playerDecision(tunk_controller thisGame){
		
		System.out.println();
		System.out.println("Press 1 to continue, Press 2 to stop, or to start over");
		Scanner reader = new Scanner(System.in);
		String input = reader.next();
		if(input.equals("1")){
			thisGame.beginTheGame();
			reader.close();
		}
		if(input.equals("2")){
			playerRestart(thisGame);
		}
		if(!input.equals("1") && !input.equals("2")){
			System.out.println("You did not choose correctly, try again");
			this.playerDecision(thisGame);
			reader.close();
		}			
	}
	//Purpose: Allow the player to restart the game or to quit
	//Pre-Conditions: There is a player who is currently playing
	//Inputs: The current game of tunk
	//Post-Conditions: The game will either restart or end		
	public void playerRestart(tunk_controller thisGame){
		System.out.println();
		System.out.println("Press 1 to restart the game, Press 2 to quit the game");
		Scanner reader2 = new Scanner(System.in);
		String input = reader2.next();
		if(input.equals("1")){
			thisGame.startUp();
			reader2.close();
		}
		if(input.equals("2")){
			System.out.println("You have chosen to end the game");
		}
		if(!input.equals("1") && !input.equals("2")){
			System.out.println("You did not choose correctly, try again");
			this.playerRestart(thisGame);
			reader2.close();
		}	
	}
	//Purpose: Allow the player to make their turn by selecting where to draw a card from
	//Pre-Conditions: There is a player who is currently playing
	//Inputs: The current game of tunk
	//Post-Conditions: The player will draw a card and the game will continue
	public void playerTurn(tunk_controller thisGame){
		System.out.println();
		System.out.println("Press 1 to draw a card from the stockpile, Press 2 to draw a card from the discard pile");
		Scanner reader3 = new Scanner(System.in);
		String input = reader3.next();
		if(input.equals("1")){
			thisGame.recieveDrawInput(1);
			reader3.close();
		}
		if(input.equals("2")){
			thisGame.recieveDrawInput(2);
			reader3.close();
		}
		if(!input.equals("1") && !input.equals("2")){
			System.out.println("You did not choose correctly, try again");
			this.playerTurn(thisGame);
			reader3.close();
		}	
	}
	
	//Purpose: Allow the player to choose which card to send to the discard pile
	//Pre-Conditions: There is a player who is currently playing
	//Inputs: The current game of tunk
	//Post-Conditions: Either a card will be send to the discard pile or the player will simply call out tunk
	public void playerDiscardChoice(tunk_controller thisGame){
		System.out.println();		
		System.out.println("Which card would you like to discard? (Enter the number on the left of the card), or Press 0 to call out TUNK!!");
		Scanner reader = new Scanner(System.in);
		int input = reader.nextInt();
		if(input == 0){
			playerTunkChoice(thisGame);
			reader.close();
		}
		else if(input> thisGame.getPlayerOne().getHand().size()||input <1 ){
			System.out.println("You did not choose correctly, try again");
			playerDiscardChoice(thisGame); 
			reader.close();
		}	
		
		else{
			thisGame.recievePlayerChoice(input-1);
			thisGame.tunkCalled();
			reader.close();
		}
	}
	//Purpose: Allows the player to choose which card to take from the
	//Pre-Conditions: a card will be taken from the computer and given to player one
	//Inputs: current game of tunk
	//Post-Conditions: the card will be transfered
	public void playerTunkChoice(tunk_controller thisGame){
		printToUser("TUNK!!!");
		printToUser("");
		printToUser("Here's the computer's hand : ");
		thisGame.sendViewCardNames(thisGame.getCpu());
		System.out.println();
		System.out.println("Which card will you take from the computer player's hand? (Enter the number on the left of the card)");
		Scanner reader = new Scanner(System.in);
		int input = reader.nextInt();
		
		if(input> thisGame.getCpu().getHand().size()||input <1 ){
			System.out.println("You did not choose correctly, try again");
			playerTunkChoice(thisGame); 
			reader.close();
		}	
		else{
		thisGame.tunk(thisGame.getCpu().getHand().get(input-1),input-1);

		thisGame.tunkCalled();
		}
		reader.close();
	}
	
}

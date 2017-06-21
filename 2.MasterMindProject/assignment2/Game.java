package assignment2;

import java.util.Scanner;

public class Game{
  /* Defining variables to be used. */

  private static boolean testMode;
  private static Guess userGuess;
  private static Placement userPlacement;
  private static boolean continueGame;
  public Scanner kb;

  public static boolean getTestMode(){ return testMode; }

  public Game(){
    this(false);
  }

  /* Default constructor, passes false value when no parameter is passed */
  public Game(boolean test) {
    testMode = test;
    userGuess = new Guess();
    userPlacement = new Placement();
    continueGame = false;
    kb = new Scanner(System.in);
  }

  public boolean newGame(){
    System.out.println("Do you want to play a new game? (Y/N):");

    if(kb.nextLine().equals("Y")){
      userGuess.resetGuess();
      userPlacement.resetPlacement();
      if (testMode)
        System.out.println(userGuess.getSecretCode());
      return true;
    }
    else
      return false;
  }

  public void runGame() {
    System.out.println("Welcome to Mastermind.");
    System.out.println("Do you want to play a new game? (Y/N):");

    if(kb.nextLine().equals("Y")){
      continueGame = true;
      if (testMode)
      System.out.println(userGuess.getSecretCode());
    }

    while(continueGame) {
      System.out.println();

      System.out.println("You have " + userGuess.getGuessesLeft() + " guess(es) left.");
      System.out.println("Enter guess:");
      String guess = kb.nextLine();

      userGuess.processGuess(guess, userPlacement);

      if(userPlacement.getDeciphered()){
        System.out.println("You win!");
        System.out.println();
        continueGame = false;
      }
      if(userGuess.getGuessesLeft() == 0){
        System.out.println("You lose! The pattern was " + userGuess.getSecretCode());
        System.out.println();
        continueGame = false;
      }
      if (!continueGame && newGame()){
        continueGame = true;
      }
    }
  }
}

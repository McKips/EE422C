package assignment2;

import java.io.*;
import java.util.*;

public class Driver {
  public static boolean testMode = false;
  public static Scanner kb = new Scanner(System.in);
  public static void main (String[] args) {
    boolean run = true;
    if(args.length != 0){
      if(args[0].equals("1"))
        testMode = true;
    }

    System.out.println("Welcome to Mastermind.");

    while(run){
      System.out.println("Do you want to play a new game? (Y/N) :");
      String play = kb.nextLine();
      System.out.println();
      if(play.equals("Y"))
        runGame();
      else
        run = false;
    }
  }

  public static void runGame() {
    Game mastermind = new Game(testMode);
    boolean isOver = false;

    if(mastermind.debug()){
      System.out.println("Secret code: " + mastermind.getSecretCode());
      System.out.println();
    }

    while(!isOver) {
      System.out.println("You have " + mastermind.getNumGuess() + " guess(es) left.");
      System.out.println("Enter guess: ");
      String guess = kb.nextLine();
      if(guess.equals("HISTORY")){
        mastermind.printHistory();
      }
      else if (mastermind.checkValidGuess(guess)){
        mastermind.update(guess);
        System.out.println(guess + "-> " + mastermind.placementHint(guess));
      }
      else
        System.out.println("INVALID_GUESS");
      if(mastermind.decrypted()){
        System.out.println("You win!");
        isOver = true;
      }
      if(mastermind.getNumGuess() == 0){
        System.out.println("You lose! The pattern was " + mastermind.getSecretCode());
        isOver = true;
      }
      System.out.println();
    }
  }
}

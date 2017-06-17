package assignment2;

import java.io.*;
import java.util.*;

public class Driver {
  public static void main (String[] args) {
    boolean run = true;
    boolean testMode = false;
    if(args.length != 0){
      if(args.equals("1"))
        testMode = true;
    }

    System.out.println("Welcome to Mastermind.");

    while(run){
      System.out.println("Do you want to play a new game? (Y/N) :");

      Scanner kb = new Scanner(System.in);
      String play = kb.nextLine();

      if(play.equals("Y"))
        runGame();
      else
        run = false;
    }
  }

  public static void runGame() {
    Game mastermind = new Game(testMode);
  }
}

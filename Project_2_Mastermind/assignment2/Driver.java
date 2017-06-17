package assignment2;

import java.io.*;
import java.util.*;

public class Driver {
  public static void main (String[] args) {
    boolean run = true;

    System.out.println("Welcome to Mastermind.");

    while(run){
      System.out.println("Do you want to play a new game? (Y/N) :");

      Scanner kb = new Scanner(System.in);
      String play = kb.next();

      if(play == "Y")
        runGame();
      else if (play == "N")
        run = false;
    }
  }

  public static void runGame() {

  }
}

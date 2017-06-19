package assignment2;

import java.io.*;
import java.util.*;

public class Driver {
  public static void main (String[] args) {
    boolean run = true;
    Scanner kb = new Scanner(System.in);

    System.out.println("Welcome to Mastermind.");
    System.out.println("Do you want to play a new game? (Y/N) :");
    String play = kb.nextLine();
    System.out.println();

    if(play.equals("Y")){
      Game mastermind = new Game(args[0], kb);
      mastermind.runGame();
    }
  }
}

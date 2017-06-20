package assignment2;

import java.io.*;
import java.util.*;

public class Game{
  /* Defining variables to be used. */

  private static boolean testMode;
  private static boolean deciphered;
  private static int numOfGuesses;
  private static int numOfPegs;
  private static int track;
  private static String[] history;
  private static String pegColors;
  private static String secretCode;
  public Scanner kb;

  public static boolean getTestMode(){ return testMode; }
  public static boolean deciphered(){ return deciphered; }
  public static int getGuessesLeft(){ return numOfGuesses; }
  public static String getSecretCode(){ return secretCode; }

  public Game(){
    this(false);
  }

  /* Default constructor, passes false value when no parameter is passed */
  public Game(boolean test) {
    testMode = test;
    kb = new Scanner(System.in);
    numOfGuesses = GameConfiguration.guessNumber;
    numOfPegs = GameConfiguration.pegNumber;
    pegColors = convertToString(GameConfiguration.colors);
    secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
    history = new String[numOfGuesses];
    track = 0;
    deciphered = false;
  }


  public static String convertToString(String[] str){
    String convert = "";
    for (int i = 0; i < str.length; i++){
      convert += str[i];
    }
    return convert;
  }

  /* Prints history of guesses made by the user. */ 

  public static void printHistory(){
    for(int i = 0; i < track ; i++)
      System.out.println(history[i]);
  }

  /* Checks if user made a valid guess and returns true if valid, and false if not. */
  public static boolean checkValidGuess(String code){
    if (code.length() != numOfPegs)
      return false;
    if(!code.equals(code.toUpperCase()))
      return false;
    char[] codeColors = code.toCharArray();
    for(int i = 0; i < numOfPegs; i++){
      int val = pegColors.indexOf(codeColors[i]);
      if (val == -1)
        return false;
    }
    return true;
  }

  /* Updates guess history and decrements number of guesses left. */
  public static void update(String store){
    history[track] = store;
    track += 1;
    numOfGuesses -= 1;
  }

  /* Tells user how many black (correct color and position) and white (correct color, wrong position) were guessed. */
  public static String placementHint(String code){
    int posRight = 0;
    int posWrong = 0;
    deciphered = secretCode.equals(code);
    char[] pegGuess = code.toCharArray();
    char[] secCode = secretCode.toCharArray();
    char[] arr = new char[numOfPegs];
    if (deciphered)
      posRight = 4;

    else {
      for(int i = 0; i < numOfPegs; i++){
        if(pegGuess[i] == secCode[i]){
          arr[i] = 'b';
          pegGuess[i] = 0;
        }
      }
      for(int i = 0; i < numOfPegs; i++){
        for(int j = 0; j < numOfPegs; j++){
          if (pegGuess[i] == 0)
            break;
          if(pegGuess[i] == secCode[j] && arr[j] == 0){
            arr[j] = 'w';
            pegGuess[i] = 0;
            break;
          }
        }
      }
    }
    for(int i = 0; i < numOfPegs; i++){
      if(arr[i] == 'b')
        posRight++;
      else if(arr[i] == 'w')
        posWrong++;
    }
    return (code + " -> " + posRight + "b_" + posWrong + "w");
  }

  public static void debugMode() {
    if (testMode)
      System.out.println(getSecretCode());
  }

  public boolean newGame(){
    System.out.println("Do you want to play a new game? (Y/N):");

    if(kb.nextLine().equals("Y")){
      return true;
    }
    else
      return false;
  }

  public static void runGuess(String pegGuess){
    if(pegGuess.equals("HISTORY")){
      printHistory();
    }
    else if (checkValidGuess(pegGuess)){
      String hint = placementHint(pegGuess);
      update(hint);
      System.out.println(hint);
    }
    else
      System.out.print("INVALID_GUESS");
  }

  public static void gameReset(){
    numOfGuesses = GameConfiguration.guessNumber;
    numOfPegs = GameConfiguration.pegNumber;
    pegColors = convertToString(GameConfiguration.colors);
    secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
    history = new String[numOfGuesses];
    track = 0;
    deciphered = false;

  }

  public void runGame() {
    System.out.println("Welcome to Mastermind.");

    boolean continueGame = newGame();
    debugMode();

    while(continueGame) {
      System.out.println();

      System.out.println("You have " + getGuessesLeft() + " guess(es) left.");
      System.out.println("Enter guess:");
      String guess = kb.nextLine();

      runGuess(guess);

      if(deciphered){
        System.out.println("You win!");
        System.out.println();
        continueGame = false;
      }
      if(numOfGuesses == 0){
        System.out.println("You lose! The pattern was " + getSecretCode());
        System.out.println();
        continueGame = false;
      }
      if (!continueGame && newGame()){
        continueGame = true;
        gameReset();
        debugMode();
      }
    }
  }
}

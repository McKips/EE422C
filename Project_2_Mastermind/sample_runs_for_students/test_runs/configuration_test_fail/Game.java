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
  public String getKeyboardInput() { 
    String word = kb.nextLine();
    return word;
  }

  /* Default constructor, passes false value when no parameter is passed */
  public Game(String test, Scanner userInput) {
    setTestMode(test);
    kb = userInput;
    numOfGuesses = GameConfiguration.guessNumber;
    numOfPegs = GameConfiguration.pegNumber;
    pegColors = convertToString(GameConfiguration.colors);
    secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
    history = new String[numOfGuesses];
    track = 0;
    deciphered = false;
  }

  public static void setTestMode(String test){
    if(test.equals("1"))
      testMode = true;
    else 
      testMode = false;
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
  public static void update(String guess, String hint){
    history[track] = guess + hint;
    track += 1;
    numOfGuesses -= 1;
  }

  /* Tells user how many black (correct color and position) and white (correct color, wrong position) were guessed. */
  public static String placementHint(String code){
    int posRight = 0;
    int posWrong = 0;
    if (secretCode.equals(code)){
      deciphered = true;
      return "4b_0w";
    }
    char[] pegGuess = code.toCharArray();
    char[] secCode = secretCode.toCharArray();
    for(int i = 0; i < numOfPegs; i++){
      if(pegGuess[i] == secCode[i]){
        posRight++;
        pegGuess[i] = 0;
      }
    }
    for(int i = 0; i < numOfPegs; i++){
      for(int j = 0; j < numOfPegs; j++){
        if (pegGuess[i] == 0)
          break;
        if(pegGuess[i] == secCode[j]){
          posWrong++;
          break;
        }
      }
    }
    return (posRight + "b_" + posWrong + "w");
  }

  public void runGame() {
    boolean isOver = false;

    if(testMode){
      System.out.println("Secret code: " + getSecretCode());
      System.out.println();
    }

    while(!isOver) {
      System.out.println("You have " + getGuessesLeft() + " guess(es) left.");
      System.out.println("Enter guess: ");
      String guess = getKeyboardInput();
      if(guess.equals("HISTORY")){
        printHistory();
      }
      else if (checkValidGuess(guess)){
        String hint = placementHint(guess);
        update(guess, hint);
        System.out.println(hint);
      }
      else
        System.out.println("INVALID_GUESS");
      if(deciphered){
        System.out.println("You win!");
        isOver = true;
      }
      if(numOfGuesses == 0){
        System.out.println("You lose! The pattern was " + getSecretCode());
        isOver = true;
      }
      System.out.println();
    }
  }
}

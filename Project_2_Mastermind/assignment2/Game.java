package assignment2;

import java.util.ArrayList;

public class Game{
  /* Defining variables to be used. */

  private static boolean testingMode;
  private static boolean deciphered;
  private static int numOfGuesses;
  private static int numOfPegs;
  private static int track;
  private static String[] history;
  private static String pegColors;
  private static String secretCode;

  /* Methods to return variables by invoking the method. */
  public static boolean debug(){ return testingMode; }
  public static String getSecretCode(){ return secretCode; }
  public static boolean decrypted(){ return deciphered; }
  public static int getNumGuess(){ return numOfGuesses; }

  /* Default constructor, passes false value when no parameter is passed */
  public Game(){ this(false); }

  public Game(boolean test) {
    testingMode = test;
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
  public static void update(String guess){
    history[track] = guess;
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
    StringBuilder secCod = new StringBuilder(code);
    for(int i = 0; i < numOfPegs; i++){
      int val = secCod.toString().indexOf((secretCode.toCharArray()[i]));
      if(val == i){
        posRight += 1;
        secCod.deleteCharAt(i);
      }
      else if (val != -1)
        posWrong += 1;
    }
    history[track-1] += "-> " + posRight +"b_"+posWrong+"w";
    return posRight + "b_" + posWrong + "w";
  }
}

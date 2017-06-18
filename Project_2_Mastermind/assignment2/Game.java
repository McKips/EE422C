package assignment2;

import java.util.ArrayList;

public class Game{
  private static boolean testingMode;
  private static int numOfGuesses;
  private static int numOfPegs;
  private static int track;
  private static String pegColors[];
  private static String secretCode;
  private static boolean deciphered;
  private static String[] history;

  public static boolean debug(){ return testingMode; }
  public static String getSecretCode(){ return secretCode; }
  public static boolean decrypted(){ return deciphered; }
  public static int getNumGuess(){ return numOfGuesses; }

  public Game(){ this(false); }

  public Game(boolean test) {
    testingMode = test;
    numOfGuesses = GameConfiguration.guessNumber;
    numOfPegs = GameConfiguration.pegNumber;
    pegColors = GameConfiguration.colors;
    secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
    history = new String[numOfGuesses];
    track = 0;
    deciphered = false;
  }

  public static void printHistory(){
    for(int i = 0; i < track ; i++)
      System.out.println(history[i]);
  }

  public static boolean checkValidGuess(String code){
    if (code.length() != numOfPegs)
      return false;
    if(!code.equals(code.toUpperCase()))
      return false;
    return true;
  }
  public static void update(String guess){
    history[track] = guess;
    track += 1;
    numOfGuesses -= 1;
  }

  public static String placementHint(String code){
    int posRight = 0;
    int posWrong = 0;
    if (secretCode.equals(code)){
      deciphered = true;
      return "4b_0w";
    }
    char[] secCod = secretCode.toCharArray();
    for(int i = 0; i < code.length(); i++){
      int val = code.indexOf(secCod[i]);
      if(val == i)
        posRight += 1;
      else if (val != -1)
        posWrong += 1;
    }
    history[track-1] += "-> " + posRight +"b_"+posWrong+"w";
    return posRight + "b_" + posWrong + "w";
  }
}

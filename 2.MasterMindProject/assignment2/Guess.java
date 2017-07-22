package assignment2;

public class Guess{
  private static int numOfGuesses;
  private static int numOfPegs;
  private static String pegColors;
  private static String secretCode;

  public Guess(){
    numOfGuesses = GameConfiguration.guessNumber;
    numOfPegs = GameConfiguration.pegNumber;
    pegColors = convertToString(GameConfiguration.colors);
    secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
  }

  public void resetGuess(){
    numOfGuesses = GameConfiguration.guessNumber;
    numOfPegs = GameConfiguration.pegNumber;
    pegColors = convertToString(GameConfiguration.colors);
    secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
  }

  public int getGuessesLeft(){ return numOfGuesses; }
  public String getSecretCode(){ return secretCode; }

  public static String convertToString(String[] str){
    String convert = "";
    for (int i = 0; i < str.length; i++){
      convert += str[i];
    }
    return convert;
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
    numOfGuesses -= 1;
    return true;
  }
  public void processGuess(String pegGuess, Placement userPlacement){
    if(pegGuess.equals("HISTORY")){
      userPlacement.printPlacementHistory();
    }
    else if (checkValidGuess(pegGuess)){
      String hint = userPlacement.placementHint(pegGuess, secretCode, numOfPegs);
      userPlacement.addToPlacementHistory(hint);
      System.out.println(hint);
    }
    else
      System.out.print("INVALID_GUESS");
  }
}

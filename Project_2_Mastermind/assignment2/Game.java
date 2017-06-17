package assignment2;

public class Game{
  private static boolean testingMode;
  private static int numOfGuesses;
  private static int numOfPegs;
  private static String[] pegColors; 

  public static void Game(boolean test) {
    testingMode = test;
    numOfGuesses = 12;
    numOfPegs = 4;
    //pegColors = SecretCodeGenerator.getInstance.getNewSecretCode(); 
  } 
}

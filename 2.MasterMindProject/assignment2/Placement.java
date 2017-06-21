package assignment2;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Placement{

  private static List<String> placementHistory;
  private static boolean deciphered;

  public boolean getDeciphered() { return deciphered; }

  public Placement(){
    placementHistory = new ArrayList<>();
    deciphered = false;
  }

  public void resetPlacement(){
    placementHistory = new ArrayList<>();
    deciphered = false;
  }

  public void addToPlacementHistory(String store){
    placementHistory.add(store);
  }

  public void printPlacementHistory(){
    for(String place : placementHistory)
      System.out.println(place);
  }

  /* Tells user how many black (correct color and position) and white (correct color, wrong position) were guessed. */
  public String placementHint(String guessInput, String pegSecret, int pegNum){
    int posRight = 0;
    int posWrong = 0;
    deciphered = pegSecret.equals(guessInput);
    if (deciphered)
      return (guessInput + " -> " + "4b_0w");

    char[] pegGuess = guessInput.toCharArray();
    char[] secCode = pegSecret.toCharArray();
    for (int i = 0; i < guessInput.length(); i++){
      if(pegGuess[i] == secCode[i]){
        posRight++;
        secCode[i] = 0; pegGuess[i] = 0;
      }
    }

    for (int i = 0; i < guessInput.length(); i++){
      for (int j = 0; j < pegSecret.length(); j++){
        if(pegGuess[i] == 0)
          break;
        if (pegGuess[i] == secCode[j]){
          posWrong++;
          pegGuess[i] = 0;
          secCode[j] = 0;
        }
      }
    }
    return (guessInput + " -> " + posRight + "b_" + posWrong + "w");
  }
}

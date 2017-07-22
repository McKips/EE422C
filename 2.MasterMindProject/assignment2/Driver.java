package assignment2;

public class Driver {

  public static void main (String[] args) {
    boolean testMode = false;
    Game mastermind;

    if (args.length != 0 && args[0] == "1")
      testMode = true;

    mastermind = new Game(testMode);
    mastermind.runGame();
  }
}

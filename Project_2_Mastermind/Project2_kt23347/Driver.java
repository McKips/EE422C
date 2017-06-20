package assignment2;

public class Driver {
  public static void main (String[] args) {
    boolean testMode = false;
    Game mastermind;

    if (args.length == 0)
      testMode = false;
    else if (args[0].equals("1"))
      testMode = true;
    mastermind = new Game(testMode);
    mastermind.runGame();
  }
}

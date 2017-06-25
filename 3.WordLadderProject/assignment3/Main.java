/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Summer 2017
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {

  static ArrayList<String> wordLadder;
  static ArrayList<Node> visited;
  static Set<String> dictionary;
  static Graph graph;

  public static void main(String[] args) throws Exception {

    Scanner kb;	// input Scanner for commands
    PrintStream ps;	// output file, for student testing and grading only
    // If arguments are specified, read/write from/to files instead of Std IO.
    if (args.length != 0) {
      kb = new Scanner(new File(args[0]));
      ps = new PrintStream(new File(args[1]));
      System.setOut(ps);			// redirect output to ps
    } else {
      kb = new Scanner(System.in);// default input from Stdin
      ps = System.out;			// default output to Stdout
    }
    initialize();

    while(true)
    {
      wordLadder = parse(kb);
      if(wordLadder == null)
        return;
      printLadder(wordLadder);
    }

    // TODO methods to read in words, output ladder
  }

  public static void initialize() {

    dictionary = makeDictionary();
    wordLadder = new ArrayList<String>();
    graph = new Graph();
    visited = new ArrayList<Node>();

  }

  /**
   * @param keyboard Scanner connected to System.in
   * @return ArrayList of Strings containing start word, rungs, and end word. 
   * If command is /quit, return empty ArrayList. 
   */
  public static ArrayList<String> parse(Scanner keyboard) {

    ArrayList<String> words = new ArrayList<String>();
    String start = new String();
    String end = new String();
    boolean invalid = false;

    while(!invalid)
    {
      start = keyboard.next();
      if(start.equals("/quit"))
        return null;

      end = keyboard.next();
      if(end.length() != 5)
        System.out.println("INVALID");
      else
        invalid = true;

      words = getWordLadderBFS(start.toUpperCase(),end.toUpperCase());
      words.add(start);
      words.add(end);
    }
    words.toString().toLowerCase();
    return words;
  }

  public static ArrayList<String> getWordLadderDFS(String start, String end) {

    // Returned list should be ordered start to end.  Include start and end.
    // If ladder is empty, return list with just start and end.
    // TODO some code
    // TODO more code

    return null; // replace this line later with real return
  }

  public static ArrayList<String> getWordLadderBFS(String start, String end) {

    // TODO some code
    // TODO more code

    graph.addNode(start);

    Node n = graph.find(start);
    Queue<Node> q = new LinkedList<Node>();
    q.add(n);
    visited.add(n);

    while ( !q.isEmpty()){
      Node u = q.poll();
      nodeNeighbors(u.word);
      if(u.word.equals(end)){
        break;
      }
      for (Node v : u.neighbors){
        if( !visited.contains(v) ){
          visited.add(v);
          v.parent = u;
          q.add(v);
        }
      }
    }

    Node fin = graph.find(end);

    while ( fin != null ){
      wordLadder.add(fin.word);
      fin = fin.parent;
    }

    Collections.reverse(wordLadder);

    return wordLadder; 
  }


  public static void printLadder(ArrayList<String> ladder) {

    if(ladder.size() == 2)
    {
      System.out.println("no word ladder can be found between " + ladder.get(0).toLowerCase() + " and " + ladder.get(1).toLowerCase() + ".");
      return;
    }
    else if(ladder.size() > 2)
    {
      int length = ladder.size() - 2;
      System.out.println("a " + length + "-rung word ladder exists between " + ladder.get(length-2) + " and " + ladder.get(length-1) + ".");
      for(int i = 0; i < ladder.size()-2; i++)
        System.out.println(ladder.get(i).toLowerCase());
      return;
    }

  }
  // TODO
  // Other private static methods here
  private static boolean differByOne(String s1, String s2) {
    if (s1.length() != s2.length())
      return false;

    int diff = 0;
    for (int i = 0; i < s1.length(); i++) {
      if (s1.charAt(i) != s2.charAt(i) && diff++ >= 1) {
        return false;
      }
    }

    return true;
  }

  public static void nodeNeighbors(String start){
    for(String dic : dictionary){
      if(differByOne(start,dic) && !start.equals(dic)){
        graph.addNode(dic);
        graph.addNeighbors(start,dic);
      }
    }
  }


  /* Do not modify makeDictionary */
  public static Set<String>  makeDictionary () {
    Set<String> words = new HashSet<String>();
    Scanner infile = null;
    try {
      infile = new Scanner (new File("five_letter_words.txt"));
    } catch (FileNotFoundException e) {
      System.out.println("Dictionary File not Found!");
      e.printStackTrace();
      System.exit(1);
    }
    while (infile.hasNext()) {
      words.add(infile.next().toUpperCase());
    }
    return words;
  }
}

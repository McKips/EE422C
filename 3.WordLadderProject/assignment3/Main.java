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
    static Set<String> dictionary;
    static HashMap<String,Node> graph;
    static HashSet<Node> visited;

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
            reset();
        }
    }

    public static void reset(){
        wordLadder = new ArrayList<String>();
        visited = new HashSet<Node>();
        graph = new HashMap<String,Node>();
        createGraph();
    }

    public static void initialize() {

        dictionary = makeDictionary();
        wordLadder = new ArrayList<String>();
        graph = new HashMap<String,Node>();
        visited = new HashSet<Node>();
        createGraph();
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
            start = keyboard.next().toLowerCase();
            if(start.equals("/quit"))
                return null;

            end = keyboard.next().toLowerCase();
            if(end.length() != 5)
                System.out.println("INVALID");
            else
                invalid = true;
        }
        return getWordLadderBFS(start,end);
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

        Node n = graph.get(start);
        Queue<Node> brit = new LinkedList<Node>();
        brit.add(n);
        visited.add(n);
        while ( !brit.isEmpty() ){
            Node u = brit.poll();
            if(u == null)
                break;
            if( u.word.equals(end) ){
                Node fin = graph.get(end);
                while ( fin != null ){
                    wordLadder.add(fin.word);
                    fin = fin.parent;
                }
                Collections.reverse(wordLadder);
                return wordLadder;
            }

            for (String str : u.neighbors){
                Node v = graph.get(str);
                if( !visited.contains(v) ){
                    visited.add(v);
                    v.parent = u;
                    brit.add(v);
                }
            }
        }
        wordLadder.add(start);
        wordLadder.add(end);
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
            int length = ladder.size();
            System.out.println("a " + length + "-rung word ladder exists between " + ladder.get(0).toLowerCase() + " and " + ladder.get(length-1).toLowerCase() + ".");
            for(int i = 0; i < ladder.size(); i++)
                System.out.println(ladder.get(i).toLowerCase());
            return;
        }

    }
    // TODO
    // Other private static methods here
    public static void createGraph(){
        for(String dic : dictionary){
            Node newNode = new Node(dic);
            graph.put(dic.toLowerCase(),newNode);
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

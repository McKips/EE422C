/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Kiptoo Tonui>
 * <kt23347>
 * <76175>
 * Slip days used: <0>
 * Git URL:
 * Summer 2017
 */


package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Main {

    static Set<String> dictionary;
    private static HashMap<String,Node> graph;
    private static HashSet<Node> visited;

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
        ArrayList<String> BFS = new ArrayList<>();
        ArrayList<String> DFS = new ArrayList<>();
        ArrayList<String> input = parse(kb);

        if(input == null)
            return;

        if(dictionary.contains(input.get(0).toUpperCase()) && dictionary.contains(input.get(1).toUpperCase())){

            BFS = getWordLadderBFS(input.get(0),input.get(1));
            printLadder(BFS);
            BFS.clear();

            /*DFS = getWordLadderDFS(input.get(0), input.get(1));
            printLadder(DFS);
            DFS.clear();*/
        }
        else{
            BFS.add(input.get(0));
            BFS.add(input.get(1));
            printLadder(BFS);
        }
        visited.clear();
        input.clear();
    }

    static void initialize() {

        dictionary = makeDictionary();
        graph = new HashMap<>();
        visited = new HashSet<>();
        createGraph();
    }


    /**
     * @param keyboard Scanner connected to System.in
     * @return ArrayList of Strings containing start word, rungs, and end word.
     * If command is /quit, return empty ArrayList.
     */
    private static ArrayList<String> parse(Scanner keyboard) {

        ArrayList<String> input = new ArrayList<>();

        String kb = keyboard.next().toLowerCase();
        if (kb.equals("/quit"))
            return null;
        input.add(kb);
        kb = keyboard.next().toLowerCase();
        input.add(kb);

        return input;
    }

    private static boolean DFSWordLadder(Node start, String end){

        if(start == null)
            return false;

        visited.add(start);

        if (start.word.equals(end)){
            return true;
        }
        else if ( start.neighbors.contains(end) ){
            Node over = graph.get(end);
            over.parent = start;
            return true;
        }
        else{
            for (String okay : start.neighbors){
                Node deep = graph.get(okay);
                boolean find = false;
                if( !visited.contains(deep) )
                    find = DFSWordLadder(deep,end);
                if(find){
                    deep.parent = start;
                    return true;
                }
            }
            return false;
        }
    }

    static ArrayList<String> getWordLadderDFS(String start, String end) {

        // Returned list should be ordered start to end.  Include start and end.
        // If ladder is empty, return list with just start and end.

        ArrayList<String> wordLadder = new ArrayList<>();

        Node rung = graph.get(start);

        if(diffByOne(start,end)){
            wordLadder.add(start);
            wordLadder.add(end);
            reset();
            return wordLadder;
        }

        if(DFSWordLadder(rung,end)){
            storeLadder(end,wordLadder);
            reset();
            return wordLadder;
        }
        else{
            wordLadder.add(start);
            wordLadder.add(end);
            reset();
            return wordLadder;
        }
    }

    static ArrayList<String> getWordLadderBFS(String start, String end) {

        ArrayList<String> wordLadder = new ArrayList<>();

        Node n = graph.get(start);
        Queue<Node> brit = new LinkedList<>();
        brit.add(n);

        visited.add(n);

        while ( !brit.isEmpty() ){
            Node u = brit.poll();

            if(u == null)
                break;

            if( u.word.equals(end) ){
                storeLadder(end,wordLadder);
                reset();
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

        reset();
        return wordLadder;
    }


    static void printLadder(ArrayList<String> ladder) {

        if(ladder.size() == 2)
        {
            System.out.println("no word ladder can be found between " + ladder.get(0).toLowerCase() + " and " + ladder.get(1).toLowerCase());
        }
        else if(ladder.size() > 2)
        {
            int length = ladder.size();
            System.out.println("a " + length + "-rung word ladder exists between " + ladder.get(0).toLowerCase() + " and " + ladder.get(length-1).toLowerCase() + ".");
            for (String rung : ladder)
                System.out.println(rung.toLowerCase());
        }

    }

    // Other private static methods here

    private static void storeLadder(String end, ArrayList<String> wordLadder){
        Node fin = graph.get(end);
        while(fin != null){
            wordLadder.add(fin.word);
            fin = fin.parent;
        }
        Collections.reverse(wordLadder);
    }

    private static void reset(){
        visited.clear();
        for(HashMap.Entry<String,Node> clear : graph.entrySet()){
            Node each = clear.getValue();
            each.parent = null;
        }
    }

    private static void createGraph(){
        for(String dic : dictionary){
            Node newNode = new Node(dic);
            graph.put(dic.toLowerCase(),newNode);
        }
    }

    private static boolean diffByOne(String s1, String s2){
        if( s1.length() != s2.length() )
            return false;
        int diff = 0;
        for(int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i) && diff++ >= 1)
                return false;
        }
        return true;
    }


    /* Do not modify makeDictionary */
    public static Set<String>  makeDictionary () {
        Set<String> words = new HashSet<String>();
        Scanner infile = null;
        try {
            infile = new Scanner (new File("3.WordLadderProject/five_letter_words.txt"));
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

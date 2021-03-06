package assignment3;

import java.util.HashSet;

class Node {
    HashSet<String> neighbors;
    String word;
    Node parent;

    Node(String word){
        this.word = word.toLowerCase();
        neighbors = new HashSet<>();
        parent = null;

        for(int i = 0; i < word.length(); i++){
            StringBuilder tmp = new StringBuilder(word.toUpperCase());
            for(char letter = 'A'; letter <= 'Z'; letter++){
                tmp.setCharAt(i,letter);
                if( !tmp.toString().equals( word.toUpperCase() ) ){
                    if(Main.dictionary.contains( tmp.toString().toUpperCase() )){
                        neighbors.add(tmp.toString().toLowerCase() );
                    }
                }
            }
        }
    }
}

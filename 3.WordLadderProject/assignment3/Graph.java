package assignment3;

import java.util.*;

public class Graph {
  public HashSet<Node> nodes;
  public HashSet<String> storeWords;

  public Graph() {
    nodes = new HashSet<Node>();
    storeWords = new HashSet<String>();
  }

  public void addNode(String word){
    Node newNode = find(word);
    if (newNode != null)
      return;
    newNode = new Node(word);
    nodes.add(newNode);
    //System.out.println("New node with: " + word);
  }

  public void addNeighbors(String spongebob, String patrick){
    Node sponge = find(spongebob);
    Node pat = find(patrick);

    if(sponge == null || pat == null)
      throw new RuntimeException("Attempting to add neighbors when nodes don't exist.");

    sponge.neighbors.add(pat);
    pat.neighbors.add(sponge);

    //System.out.println("Two neighbors are: " + spongebob + " " + patrick);
  }

  public Node find(String word){
    for(Node n: nodes)
      if(n.word.equals(word))
        return n;

    return null;
  }

}

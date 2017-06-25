package assignment3;

import java.util.*;

public class Node {
  ArrayList<Node> neighbors;
  String word;
  Node parent;

  public Node(String word){
    this.word = word;
    neighbors = new ArrayList<Node>();
    parent = null;
  }
}

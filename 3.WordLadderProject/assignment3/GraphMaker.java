package assignment3;

import java.util.*;

public class GraphMaker{
  ArrayList<Root> roots;
  HashSet<Root> visited;

  public GraphMaker() {
    roots = new ArrayList<>();
    visited = new HashSet<Root>();
  }


  public void buildGraph(String word, Set<String> dictionary, int iteration){
    for(int i = iteration; i < word.length(); i++){
      char[] beg = word.toCharArray();
      for(char letter = 'A'; letter <= 'Z'; letter++){ 
        beg[i] = letter; 
        String newWord = new String(beg); 
        if(dictionary.contains(newWord)){ 
          addRoots(newWord);
        }
      }
    }
  }

  public void connectNodes(){
    for(Root r : roots){
      for (Root b : roots){
        if (!r.word.equals(b.word)){
          if(differByOne(r.word,b.word)){
            addBiBranch(r,b);
          }
        }
      }
    }
  }

  public void printTree(String word){
    Root r = findRoot(word);
    while(r != null){
      System.out.println(r);
      r = r.parent;
    }
  }

  public Root findRoot(String defWord) {
    for(Root r : roots)
      if(r.word.equals(defWord))
        return r;
    return null;
  }

  public void addRoots (String word) {
    if (findRoot(word) == null){
      System.out.println(word);
      roots.add(new Root(word));
    }
  }

  public void addBiBranch(Root n1, Root n2){
    n1.branches.add(n2);
    n2.branches.add(n1); 
  }

  /*public void addBiBranch(String parent, String child) {
    Root p,c;
    p = findRoot(parent);
    c = findRoot(child);

    if(p == null || c == null)
      throw new RuntimeException("Adding edge for non-existing nodes.");
    p.branches.add(c);
    c.branches.add(p);
  }*/

  public void dfs(Root s){
    visited.add(s);
    for(Root r : s.branches)
      if (!visited.contains(r)){
        r.parent = s;
        dfs(r);
      }
  }

  public void bfs(String word){
    Root s = findRoot(word);
    System.out.println("Queue");
    Queue<Root> brit = new LinkedList<Root>();
    brit.add(s);
    visited.add(s);

    while(!brit.isEmpty()){
      Root r = brit.poll();

      for(Root stem : r.branches){
        if(!visited.contains(stem)){
          stem.parent = r;
          brit.add(stem);
        }
      }
    }
  }
	private static boolean differByOne(String s1, String s2) {
		if (s1.length() != s2.length())
			return false;

		int diff = 0;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i) && diff++ > 1) {
				return false;
			}
		}

		return true;
	}
}

class Root {
  public ArrayList<Root> branches;
  public String word;
  public Root parent;

  public Root(String word){
    this.word = word;
    parent = null;
    branches = new ArrayList<Root>();
  }
  @Override
  public String toString() { return word; }
}

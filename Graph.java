package assignment7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Represents a directed graph (a set of vertices and a set of edges).
 * 
 */
public class Graph 
{
  HashMap<String, Vertex> vertices; // the graph -- a set of vertices (String name mapped to Vertex instance)
  boolean directed;

  /**
   * Constructs an empty graph.
   */
  public Graph() 
  {
    vertices = new HashMap<String, Vertex>();
  }

  public static void main(String[] args) 
  {

  }

  public  void addEdge(String a, String b)
  {
	    Vertex vertex1;
	    if (vertices.containsKey(a))
	      vertex1 = vertices.get(a);
	    else 
		{
	      vertex1 = new Vertex(a);
	      vertices.put(a, vertex1);
	    }

	    Vertex vertex2;
	    if (vertices.containsKey(b))
	      vertex2 = vertices.get(b);
	    else 
		{
	      vertex2 = new Vertex(b);
	      vertices.put(b, vertex2);
	    }
	    vertex1.addVertex(vertex2);
	    this.directed = true;
	  }
public void setDirected(boolean b)
{
	this.directed = b;	
}
}

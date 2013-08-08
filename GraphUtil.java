package assignment7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * Utility class containing methods for operating on graphs. 
 *
 * @author Paymon Saebi & ??
 */
public class GraphUtil 
{
	

  /**
   * Performs a topological sort of the vertices in a directed acyclic graph.
   * (See Lecture 16 for the algorithm.)
   * 
   * Throws an UnsupportedOperationException if the graph is undirected or
   * cyclic.
   * 
   * @param filename
   *          -- Name of a file in DOT format, which specifies the graph to be
   *          sorted.
   * @return a list of the vertex names in sorted order
   */
  public static List<String> topologicalSort(String filename) 
  {
	// Build the graph
	Graph g = buildGraphFromDot(filename);  
	
	//If the directed variable was never set to true then it means the graph has no direction, and throws an error.
	if (g.directed == false)
		  throw new UnsupportedOperationException();
	//Boolean that will eventually be used to test of the graph is Acyclic or not
	boolean isAcyclic = true;

	// Create a list of the vertices
	List<String> sortedVertices = new ArrayList<String>();
	
	//Queue for sorting
	Queue<Vertex> q = new LinkedList<Vertex>();
	
	//Goes through the list of vertices and checks for any with indegree of 0 and adds them to the queue
	for(Vertex v : g.vertices.values())
	{
		if (v.degreeN == 0)
			q.add(v);
	}
	// While the queue isnt empty, it will take the front entry and assign it to temp, take the name from temp and add it to the list
	// then visits the neighbors and decrements their indegree value by 1. and if any of them have an indegree of 0 adds them to the queue.
	while (!q.isEmpty())
	{
		Vertex temp = q.poll();
		sortedVertices.add(temp.getName());
		for (Vertex x: temp.getAdj())
		{
			x.degreeN--;
			if (x.degreeN == 0)
				q.add(x);
		}
		
	}
	//Adds everything from the set .keySet()(String values of the vertices) of the vertices of the graph to outtest for cycle testing.
	//Will then compare outtest to the sortedVertices list, if the two aren't the same, some entries were left out from the sort
	//which means the original graph was acyclic.
	List<String> outtest = new ArrayList<String>();
	 outtest.addAll(g.vertices.keySet());

	if (!sortedVertices.containsAll(outtest))
		isAcyclic = false;
	
	if (isAcyclic == false)
		  throw new UnsupportedOperationException();
	
    return sortedVertices;
  }

  /**
   * Performs a breadth-first search of a graph to determine the shortest path
   * from a starting vertex to an ending vertex.
   * (See Lecture 16 for the algorithm.)
   * 
   * Throws an UnsupportedOperationException if the graph is undirected or if
   * the starting or ending vertex does not exist in the graph.
   * 
   * @param filename
   *          -- Name of a file in DOT format, which specifies the graph to be
   *          sorted.
   * @param start
   *          -- Name of the starting vertex in the path.
   * @param end
   *          -- Name of the ending vertex in the path.
   * @return a list of the vertices that make up the shortest path from the
   *         starting vertex (inclusive) to the ending vertex (inclusive).
   */
  public static List<String> breadthFirstSearch(String filename, String start, String end) 
  {
	  //Builds the graph and makes a localized version of vertices.
	  Graph g = buildGraphFromDot(filename);
	  HashMap<String, Vertex> h = g.vertices;
	  // If it is not directed, will throw the exception.
	  if (g.directed == false)
		  throw new UnsupportedOperationException();
	  
	  //Adds everything from the set .keySet()(String values of the vertices) of the vertices of the graph to outtest for testing.
	  //Compares everything in this list to the 'start' and 'end' strings sent in, if either doesn't exist, throws an error.
	  List<String> outtest = new ArrayList<String>();
	  outtest.addAll(g.vertices.keySet());
	  if (!outtest.contains(start) || !outtest.contains(end))
		  throw new UnsupportedOperationException();
	  
	  // Grab the starting vertex to begin the search
	  Vertex startVertex = h.get(start);
	  
	  // Create a LinkedList to use as a queue
	  LinkedList<Vertex> q = new LinkedList<Vertex>();
	  
	  //The list that will be sent back
	  LinkedList<String> returnList = new LinkedList<String>();
	  

	  // Begin the queue, sets the start vertex isvisited as true, and adds it to the list that is returned.
	  q.add(startVertex);
	  startVertex.isVisited = true;
	  returnList.add(start);
	  //While the queue is not empty, it will assign the vertex at the top of the queue to temp,first checking if its the end
	  //then going through its neighbors, for each unvisited neighbor, it will assign it as visisted, set its comeFrom variable as
	  //the current temp, then add that neighbor to the queue.
	  while (!q.isEmpty())
	  {		  
		  Vertex temp = q.poll();
		if (temp.getName().equals(end))
		{
			break;
		}
		for (Vertex v: temp.getAdj())
		{
			if (!v.isVisited)
			{
				v.isVisited = true;
				v.setCameFrom(temp);
				q.add(v);
			}
		}
		  
	  }	  
	  //If the vertex with the string key that matches the string end sent in hasnt been visited, will return null, indicating
	  //that the path wasn't possible.
	  if(g.vertices.get(end).isVisited == false)
		  return null;
	
	return returnList;
  }

  /**
   * Builds a graph according to the edges specified in the given DOT file
   * (e.g., "a -- b" or "a -> b"). Accepts directed ("digraph") or undirected
   * ("graph") graphs.
   * 
   * Accepts many valid DOT files (see examples posted with assignment).
   * --accepts \\-style comments 
   * --accepts one edge per line or edges terminated with ; 
   * --does not accept attributes in [] (e.g., [label = "a label"])
   * 
   * @param filename
   *          -- name of the DOT file
   */
  private static Graph buildGraphFromDot(String filename) {
	    // creates a new, empty graph (CHANGE AS NEEDED)
	    Graph g = new Graph();

	    Scanner s = null;
	    try {
	      s = new Scanner(new File(filename)).useDelimiter(";|\n");
	    } catch (FileNotFoundException e) {
	      System.out.println(e.getMessage());
	    }

	    // Determine if graph is directed or not (i.e., look for "digraph id {" or "graph id {")
	    String line = "", edgeOp = "";
	    while (s.hasNext()) {
	      line = s.next();

	      // Skip //-style comments.
	      line = line.replaceFirst("//.*", "");

	      if (line.indexOf("digraph") >= 0) {
	        g.setDirected(true); // Denotes that graph is directed (CHANGE AS NEEDED)
	        edgeOp = "->";
	        line = line.replaceFirst(".*\\{", "");
	        break;
	      }
	      if (line.indexOf("graph") >= 0) {
	        g.setDirected(false); // Denotes that graph is undirected (CHANGE AS NEEDED)
	        edgeOp = "--";
	        line = line.replaceFirst(".*\\{", "");
	        break;
	      }
	    }

	    // Look for edge operators -- (or ->) and determine the left and right vertices for each edge.
	    while (s.hasNext()) 
		{
	      String[] substring = line.split(edgeOp);

	      for (int i = 0; i < substring.length - 1; i += 2) {
	        // remove " and trim whitespace from node string on the left
	        String vertex1 = substring[0].replace("\"", "").trim();
	        // if string is empty, try again
	        if (vertex1.equals(""))
	          continue;

	        // do the same for the node string on the right
	        String vertex2 = substring[1].replace("\"", "").trim();
	        if (vertex2.equals(""))
	          continue;

	        // add edge between vertex1 and vertex2 (CHANGE AS NEEDED)
        g.addEdge(vertex1, vertex2);
	      }

	      // do until the "}" has been read
	      if (substring[substring.length - 1].indexOf("}") >= 0)
	        break;

	      line = s.next();

	      // Skip //-style comments.
	      line = line.replaceFirst("//.*", "");
	    }

	    return g;
	  }
}
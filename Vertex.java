package assignment7;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

public class Vertex 
{
  private String name; // used to id the Vertex

  private LinkedList<Vertex> adj; // adjacency list
  private Vertex cameFrom;
  public boolean isVisited = false;
  public int degreeN = 0;

  public Vertex(String _name) 
  {
    this.name = _name;
    this.adj = new LinkedList<Vertex>();
  }

  public String getName() 
  {
    return name;
  }

  public void addVertex(Vertex otherVertex) 
  {
    adj.add(otherVertex);
    otherVertex.degreeN++;
    otherVertex.cameFrom = this;
  }

  public Iterator<Vertex> Vertexs() 
  {
    return adj.iterator();
  }

  public String toString() 
  {
    String s = "Vertex " + name + " adjacent to ";
    Iterator<Vertex> itr = adj.iterator();
    while (itr.hasNext())
      s += itr.next() + "  ";
    return s;
  }
  public Vertex getCameFrom()
  {
	  return cameFrom;
  }
  public void setCameFrom(Vertex previous)
  {
	  this.cameFrom = previous;
  }
  public LinkedList<Vertex> getAdj()
  {
	  return this.adj;
  }
  public void addAdj(Vertex a)
  {
	  adj.add(a);
  }



}

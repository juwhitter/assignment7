package assignment7;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TestGraphUtil extends TestCase
{
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testTopologicalSort()
	{
		//Testing topological sort with a standard run.
		List<String> output = new ArrayList<String>();
		output.add("E");
		output.add("A");
		output.add("B");
		output.add("C");
		output.add("D");
		output.add("F");
		output.add("G");
		List<String> topo = new ArrayList<String>();
		topo = GraphUtil.topologicalSort("test1.dot");
		assertEquals(output.toString(),topo.toString());
		
		//Testing topological sort with a standard run again.
		output.clear();
		List<String> topo2 = new ArrayList<String>();
		output.add("v1");
		output.add("v5");
		output.add("v2");
		output.add("v3");
		output.add("v4");
		output.add("v6");
		output.add("v7");
		output.add("v8");
		output.add("v9");
		output.add("v10");
		topo2 = GraphUtil.topologicalSort("test2.dot");
		assertEquals(output.toString(),topo2.toString());
		
	}
	public void testBST()
	{
		//Testing BST from a-c
		List<String> bst1 = new ArrayList<String>();
		bst1.add("A");
		bst1.add("B");
		bst1.add("C");
		List<String> bstout = new ArrayList<String>();
		bstout = GraphUtil.breadthFirstSearch("test1.dot", "A", "C");
		assertEquals(bst1.toString(),bstout.toString());
		bst1.clear();
		
	
		//Testing BST from a-e this is where the code gets hairy and we need to figure out naming implementation.
		assertEquals(null,GraphUtil.breadthFirstSearch("test1.dot", "A", "E"));
		//These two test for errors, making sure it errors if we send in values that don't exist
		try
		{
			bstout = GraphUtil.breadthFirstSearch("test1.dot", "E", "Z");

			fail("Didn't throw the expected exception.");
		}
		catch (Exception e){}
		try
		{
			bstout = GraphUtil.breadthFirstSearch("test1.dot", "K", "B");

			fail("Didn't throw the expected exception.");
		}
		catch (Exception e){}
		
		//Continuation of things getting hairy.
		bst1.clear();
		bstout = GraphUtil.breadthFirstSearch("test2.dot", "v1", "v10");
		bst1.add("v1");
		bst1.add("v2");
		bst1.add("v4");
		bst1.add("v7");
		bst1.add("v8");
		bst1.add("v10");
		
		assertEquals(bst1.toString(),bstout.toString());
		
	}
}

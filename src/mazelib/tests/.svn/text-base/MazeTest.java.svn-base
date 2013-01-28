package mazelib.tests;

import java.util.Random;
import java.util.Stack;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import mazelib.data.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Monday, September 3, 2012, 18:00 PM
 */
public class MazeTest {
	
	private static Maze maze;
	private static Random randomGenerator = new Random();
	private static final int MAZE_SIZE_LIMIT = 100;
	private static int MAZE_WIDTH;
	private static int MAZE_HEIGHT;
	private static Position start, end;
	
	private static final String stringMaze = 
			"##############\n" +
			"#S      X   X#\n" +
			"#XXXXXX XXX X#\n" +
			"#           X#\n" +
			"#XXXXXXXXXX E#\n" +
			"##############\n";

	/**
	 * This test is run before any other tests.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		MAZE_WIDTH = randomGenerator.nextInt(MAZE_SIZE_LIMIT) + 2;
		MAZE_HEIGHT = randomGenerator.nextInt(MAZE_SIZE_LIMIT) + 2;
		maze = new Maze(MAZE_WIDTH, MAZE_HEIGHT);
		start = new Position(0, 0);
		end = new Position(MAZE_WIDTH - 1, MAZE_HEIGHT - 1);
		System.out.println("Generating a new " + Integer.toString(MAZE_WIDTH) + 
						   " by " + Integer.toString(MAZE_HEIGHT) +" maze.\n");
	}
	
	/**
	 * This test checks if a Maze has been properly initialized.
	 */
	@Test
	public void testInitialization() 
	{
		assertEquals(maze.getWidth(), MAZE_WIDTH);
		assertEquals(maze.getHeight(), MAZE_HEIGHT);
	}
	
	/**
	 * Test illegal Maze creation.
	 */
	@Test
	public void testIllegalMaze()
	{
		catchIllegal(1, 1);
		catchIllegal(-100, 100);
		catchIllegal(100, -100);
		catchIllegal(-1, -1);
		
		try
		{
			Maze noStart  = new Maze(
							"##############\n" +
							"#      X   X#\n" +
							"#XXXXXX XXX X#\n" +
							"#           X#\n" +
							"#XXXXXXXXXX E#\n" +
							"##############\n", false);
			fail("Maze with start was created!");
		}
		catch (Exception e) {}
		try
		{
			Maze noEnd  = new Maze(
							"##############\n" +
							"#S     X   X#\n" +
							"#XXXXXX XXX X#\n" +
							"#           X#\n" +
							"#XXXXXXXXXX  #\n" +
							"##############\n", false);
			fail("Maze with end was created!");
		}
		catch (Exception e) {}
	}
	
	/**
	 * Helper function for testIllegalMaze().
	 */
	private void catchIllegal(int x, int y)
	{
		boolean caught = false;
		try 
		{
			Maze bad = new Maze(x, y);
		} 
		catch (IllegalArgumentException e) 
		{
			caught = true;
		}
		assertTrue(caught);
	}
	/**
	 * Test to check if getNode returns the correct Node and handles 
	 * Positions out of bounds properly. Indirectly tests inBounds.
	 */
	@Test
	public void testGetNode()
	{
		assertEquals(maze.getStartNode(), maze.getNode(start));
		assertEquals(maze.getEndNode(), maze.getNode(end));
		
		Position center = new Position(MAZE_WIDTH/2, MAZE_HEIGHT/2);
		assertTrue(maze.getNode(center) != null);
		
		Position outOfBounds = new Position(MAZE_WIDTH, MAZE_HEIGHT);
		assertNull(maze.getNode(outOfBounds));
		
		outOfBounds = new Position(-1, -1);
		assertNull(maze.getNode(outOfBounds));
		
		outOfBounds = new Position(MAZE_WIDTH + 100, MAZE_HEIGHT/2);
		assertNull(maze.getNode(outOfBounds));
	}
	
	
	/**
	 * This test is used to check if the random Maze paths were generated 
	 * by checking if its a perfect Maze: specifically all passable Nodes are
	 * reachable from start, i.e. traversable.
	 * Basically requires full functionality of both Node and Maze classes.
	 */
	@Test
	public void testPerfectMaze() 
	{
		setParentNodesDFS();
		
		/* Check if all Maze Nodes are either impassable or 
		 * passable with non-null parent Node		 */
		for (int x = 0; x < MAZE_WIDTH; x++) 
		{
			for (int y = 0; y < MAZE_HEIGHT; y++)
			{
				Position position = new Position(x,y);
				Node node = maze.getNode(position);
				if (node.getIsPassable())
				{
					assertTrue(node.getParentNode() != null);
				}
			}
		}
		
		// Check if end Node can be reached from start Node
		assertTrue(maze.getEndNode().getParentNode() != null);
	}
	
	/** 
	 * Helper function to set all passable Nodes' parent Nodes
	 * to some non-null Node.
	 */
	private void setParentNodesDFS()
	{
		Map<Node,Boolean> visited = new HashMap<Node,Boolean>();
		Stack<Node> nodeStack = new Stack<Node>();
		nodeStack.addElement(maze.getStartNode());
		Node currentNode;
		
		// Perform a DFS traversal
		while(nodeStack.size() > 0) {
			currentNode = nodeStack.pop();
			if(!visited.containsKey(currentNode) || !visited.get(currentNode)) {
				visited.put(currentNode, true);
				// NOTE: put method overwrites the value if key exists and increments size
			}
			
			// If current Node is impassable, continue
			if(!currentNode.getIsPassable()) {
				continue;
			}
			
			// Get all non-diagonal neighbors of current Node
			List<Node> adjacentNodes = currentNode.getAdjacentNodes(false);
			for(Node node : adjacentNodes) 
			{
				if(!visited.containsKey(node) || !visited.get(node)) 
				{
					nodeStack.add(node);
				}
				node.setParentNode(currentNode);
			}
		}
	}
	
	
	/**
	 * This test checks the functionality of isSolvable funciton
	 */
	@Test
	public void testIsSolvable()
	{
		Maze solvable  = new Maze(
				"##############\n" +
				"#S         X #\n" +
				"#XXXXXX XXX X#\n" +
				"#           X#\n" +
				"#XXXXXXXXXX E#\n" +
				"##############\n", false);
		
		assertTrue(solvable.isSolvable());
		
		Maze unsolvable  = new Maze(
				"##############\n" +
				"#S     X  XX #\n" +
				"#XXXXXXXXXX X#\n" +
				"#      X    X#\n" +
				"#XXXXXXXXXX E#\n" +
				"##############\n", false);
		
		assertFalse(unsolvable.isSolvable());
	}
	/**
	 * This test to see if drawMaze correctly represents a maze as a String,
	 * and if the same maze is drawn the same way twice.
	 */
	@Test
	public void testDrawMaze() 
	{
		String out = maze.drawMaze();
		assertTrue(out != null);
		assertEquals(out, maze.drawMaze());
	}
	
	/**
	 * This test checks if a Maze can be generated using the
	 * string constructor.
	 */
	@Test
	public void testStringConstructor()
	{
		try
		{
			maze = new Maze(stringMaze, false);
		}
		catch (Exception e)
		{
			fail("String Constructor failed!");
		}
	}
	
}

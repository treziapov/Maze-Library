package mazelib.tests;

import static org.junit.Assert.*;

import mazelib.data.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Tuesday, September 11, 2012, 18:00 PM
 */
public class HeuristicsTest {
	
	private static Heuristic manhattanH;
	private static Heuristic euclideanH;
	private static Heuristic diagonalH;
	
	private static Maze maze = new Maze(
					"############################\n" +
					"#S      X                 X#\n" +
					"#XXXXXX X XXXXXXXXXXX XXX X#\n" +
					"#     X X       X   X X   X#\n" +
					"# XXX X XXXXXXXXX X X X XXX#\n" +
					"# X   X           X   X   X#\n" +
					"# X XXXXXXXXXXXXXXXXXXXXX X#\n" +
					"# X X       X X       X X X#\n" +
					"# XXX XXX X X X X XXX X X X#\n" +
					"#   X X   X X X X   X   X X#\n" +
					"# X X X XXX X X XXX XXXXX X#\n" +
					"# X   X X     X X X       X#\n" +
					"# XXXXX XXXXXXX X XXXXXXXXX#\n" +
					"#   X X   X     X         X#\n" +
					"#XX X XXX X XXXXXXXXX XXX X#\n" +
					"#   X   X X X   X     X X X#\n" +
					"# XXXXX X X X X X XXXXX X X#\n" +
					"#     X X X X X X X X   X X#\n" +
					"#XXXX X X X X X X X X X X X#\n" +
					"#       X     X   X   X   E#\n" +
					"############################\n", false);
	
	private static final int MAZE_WIDTH = maze.getWidth();
	private static final int MAZE_HEIGHT = maze.getHeight();
	
	private static final long COST_STRAIGHT = 100l;
	private static final long COST_DIAGONAL = 141l;
	
	private static Node endNode = maze.getEndNode();
	private static Position end = endNode.getPosition();

	/**
	 * This test is run before any other tests.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		manhattanH = new ManhattanDistance();
		euclideanH = new EuclideanDistance();
		diagonalH = new DiagonalDistance();
	}

	/**
	 * This test checks the Manhattan Distance Heuristic.
	 */
	@Test
	public void testManhattanDistance() {

		Position end = endNode.getPosition();
		
		// Loop through all Nodes in the Maze
		for (int y = 0; y < MAZE_HEIGHT; y++)
		{
			for (int x = 0; x < MAZE_WIDTH; x++)
			{
				Position currentPosition = new Position(x, y);
				Node currentNode = maze.getNode(currentPosition);
				
				long distance =  (end.getX() - currentPosition.getX()
						 				+ end.getY() - currentPosition.getY())
						 				* COST_STRAIGHT;
				
				assertEquals(manhattanH.calculateDistance(currentNode, endNode), 
							 distance);	
			}
		}
		
		// Test illegal arguments
		Maze anotherMaze = new Maze(5,5);
		try 
		{
			manhattanH.calculateDistance(anotherMaze.getStartNode(), endNode);
			fail("Calculated distance from different Mazes!");
		}
		catch (Exception e) {}

		try
		{
			Node node = null;
			manhattanH.calculateDistance(node, endNode);
			manhattanH.calculateDistance(null, null);
			fail("Accepted null Node arguments!");
		}
		catch (Exception e) {}
		
	}
	
	/**
	 * This test checks the Euclidean Distance Heuristic.
	 */
	@Test
	public void testEuclideanDistance()
	{
		// Loop through all Nodes in the Maze
		for (int y = 0; y < MAZE_HEIGHT; y++)
		{
			for (int x = 0; x < MAZE_WIDTH; x++)
			{
				Position currentPosition = new Position(x, y);
				Node currentNode = maze.getNode(currentPosition);
				
				// Math.sqrt() requires argument to be of type double
				double horizontal = (double) (Math.abs(end.getX() - currentPosition.getX()) * COST_STRAIGHT);
				double vertical = (double) (Math.abs(end.getY() - currentPosition.getY()) * COST_STRAIGHT);
				
				long distance = (long) Math.sqrt(Math.pow(horizontal, 2d)
											 + Math.pow(vertical, 2d));
				
				assertEquals(euclideanH.calculateDistance(currentNode, endNode), 
							 distance);	
				
				// Test illegal arguments
				Maze anotherMaze = new Maze(5,5);
				try 
				{
					euclideanH.calculateDistance(anotherMaze.getStartNode(), endNode);
					fail("Calculated distance from different Mazes!");
				}
				catch (Exception e) {}

				try
				{
					Node node = null;
					euclideanH.calculateDistance(node, endNode);
					euclideanH.calculateDistance(null, null);
					fail("Accepted null Node arguments!");
				}
				catch (Exception e) {}
			}
		}
	}

	/**
	 * This test checks the Diagonal Distance Heuristic.
	 */
	@Test
	public void testDiagonalDistance()
	{
		// Loop through all Nodes in the Maze
		for (int y = 0; y < MAZE_HEIGHT; y++)
		{
			for (int x = 0; x < MAZE_WIDTH; x++)
			{
				Position currentPosition = new Position(x, y);
				Node currentNode = maze.getNode(currentPosition);
				
				int horizontal = Math.abs(end.getX() - currentPosition.getX());
				int vertical = Math.abs(end.getY() - currentPosition.getY());
				
				int diagonal = Math.min(horizontal, vertical);
				int straight = horizontal + vertical;
				
				long distance = COST_DIAGONAL * diagonal +
							  COST_STRAIGHT * (straight - 2 * diagonal);
				
				assertEquals(diagonalH.calculateDistance(currentNode, endNode), 
							 distance);	
				
				// Test illegal arguments
				Maze anotherMaze = new Maze(5,5);
				try 
				{
					diagonalH.calculateDistance(anotherMaze.getStartNode(), endNode);
					fail("Calculated distance from different Mazes!");
				}
				catch (Exception e) {}	

				try
				{
					Node node = null;
					diagonalH.calculateDistance(node, endNode);
					diagonalH.calculateDistance(null, null);
					fail("Accepted null Node arguments!");
				}
				catch (Exception e) {}
			}
		}
	}
}

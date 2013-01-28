package mazelib.tests;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;

import mazelib.data.*;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Sunday, September 2, 2012 17:00 PM
 */
public class NodeTest {
	
	private static Maze maze;
	private static Random randomGenerator = new Random();
	private static final int MAZE_SIZE_LIMIT = 11;
	private static final int MAZE_WIDTH = randomGenerator.nextInt(MAZE_SIZE_LIMIT) + 4;
	private static final int MAZE_HEIGHT = randomGenerator.nextInt(MAZE_SIZE_LIMIT) + 4;
	private static Position  corner, edge, center;
	private static Node cornerNode, edgeNode, centerNode;
	private static final int CORNER_NEIGHBOR_COUNT = 2;
	private static final int EDGE_NEIGHBOR_COUNT = 3;
	private static final int CENTER_NEIGHBOR_COUNT = 4;
	
	/** This is run before any tests.
	 * Initializes a Test maze, draws it and finds the desired test Nodes.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		maze = new Maze(MAZE_WIDTH, MAZE_HEIGHT);
		System.out.println(maze.drawMaze());
		
		// Start Node is a corner so we'll use that for testing
		cornerNode = maze.getStartNode();
		corner = cornerNode.getPosition();
		
		/* Get the next passable Node adjacent to Start,
		 * which is guaranteed to be on an edge.	 */
		List<Node> adjacent = cornerNode.getAdjacentNodes(false);
		edgeNode = adjacent.get(0);
		edge = edgeNode.getPosition();
		
		/* Get a center node which must have 4 neighbors by starting
		 * a search from end Node.		*/
		boolean foundCenter = false;
		adjacent = maze.getEndNode().getAdjacentNodes(false);
		Node currentNode = maze.getEndNode();
		
		while (!foundCenter)
		{
			// If currentNode has 4 neighbors
			if (adjacent.size() == 4)
			{
				foundCenter = true;
				centerNode = currentNode;
				center = currentNode.getPosition();
				continue;
			}
			// Pick a random index from Adjacent list
			int randomIndex = randomGenerator.nextInt(adjacent.size());
			currentNode = adjacent.get(randomIndex);
			adjacent = currentNode.getAdjacentNodes(false);
		}
		
		System.out.println("Corner at " + Integer.toString(corner.getX()) + "," + Integer.toString(corner.getY())); 
		System.out.println("Edge at " + Integer.toString(edge.getX()) + "," + Integer.toString(edge.getY()));
		System.out.println("Center at " + Integer.toString(center.getX()) + "," + Integer.toString(center.getY()));		
	}
	
	/**
	 * Run after every test. Resets test Node fields.
	 * @throws Exception 
	 */
	@After
	public void tearDown() throws Exception
	{
		reset(cornerNode);
		reset(edgeNode);
		reset(centerNode);
	}
	/**
	 * Helper function to reset modifiable fields of a node.
	 * @param node the Node to be reset
	 */
	private void reset(Node node)
	{
		node.setAccumulatedCost(0l);
		node.setEstimatedCost(0l);
		node.setParentNode(null);
	}
	
	/**
	 * Checks if Nodes are correctly initialized by checking
	 * its fields' values.
	 */
	@Test
	public void testInitialization() 
	{
		Position test = cornerNode.getPosition();
		assertEquals(test, corner);
		assertTrue(cornerNode.getIsPassable());
		checkInitialization(cornerNode);
		
		test = edgeNode.getPosition();
		assertEquals(test, edge);
		assertTrue(cornerNode.getIsPassable());
		checkInitialization(edgeNode);
		
		test = centerNode.getPosition();
		assertEquals(test, center);
		checkInitialization(centerNode);
	}
	
	/**
	 * Helper function for testInitialization.
	 */
	private void checkInitialization(Node node)
	{
		assertEquals(cornerNode.getAccumulatedCost(), 0l);
		assertEquals(cornerNode.getEstimatedCost(), 0l);
		assertEquals(cornerNode.getParentMaze(), maze);
		assertNull(cornerNode.getParentNode());
	}
	
	/**
	 * Tests that a Node is equal to itself,
	 * and not equal to another Node.
	 */
	@Test
	public void equals()
	{
		assertTrue(cornerNode.equals(cornerNode));
		assertFalse(cornerNode.equals(edgeNode));
		assertTrue(centerNode.equals(centerNode));
		assertFalse(centerNode.equals(cornerNode));
		assertFalse(centerNode.equals(maze));
	}
	
	/**
	 * Tests if estimated, accumulated costs can be set and read correctly,
	 * and if total costs can be calculated properly.
	 */
	@Test
	public void testCosts()
	{
		assertEquals(cornerNode.getTotalCost(), 0l);
		assertEquals(centerNode.getTotalCost(), 0l);
		
		long testEstimated = 100l;
		long testAccumulated = 140l;
		
		cornerNode.setEstimatedCost(testEstimated);
		cornerNode.setAccumulatedCost(testAccumulated);
		assertEquals(cornerNode.getEstimatedCost(), testEstimated);
		assertEquals(cornerNode.getAccumulatedCost(), testAccumulated);
		assertEquals(cornerNode.getTotalCost(), testEstimated + testAccumulated);		
	}
	
	/**
	 * This test checks to see if a Node can be ordered between another
	 * based on their total costs 
	 */
	@ Test
	public void testCompareTo()
	{
		int sameResult = cornerNode.compareTo(edgeNode);
		
		long testEstimated = 100l;
		long testAccumulated = 140l;
		
		cornerNode.setEstimatedCost(testEstimated);
		cornerNode.setAccumulatedCost(testAccumulated);
		
		int largerResult = cornerNode.compareTo(centerNode);
		int smallerResult = edgeNode.compareTo(cornerNode);
		
		assertEquals(sameResult, 0);
		assertEquals(largerResult, 1);
		assertEquals(smallerResult, -1);
	}
	
	/**
	 * Test to check if Node's parent can be set properly.
	 */
	@Test
	public void testSetParent()
	{
		centerNode.setParentNode(cornerNode);
		assertEquals(centerNode.getParentNode(), cornerNode);
	}
	
	/**
	 * Test to check if getAdjacentNodes works properly.
	 * For both diagonal and non-diagonal calls.
	 */
	@Test
	public void testAdjacents()
	{
		// Check non-diagonal
		checkAdjacents(cornerNode, CORNER_NEIGHBOR_COUNT, false);
		checkAdjacents(edgeNode, EDGE_NEIGHBOR_COUNT, false);
		checkAdjacents(centerNode, CENTER_NEIGHBOR_COUNT, false);
		checkAdjacents(cornerNode, CORNER_NEIGHBOR_COUNT + 1, true);
		checkAdjacents(edgeNode, EDGE_NEIGHBOR_COUNT + 2, true);
		checkAdjacents(centerNode, CENTER_NEIGHBOR_COUNT + 4, true);
	}
	
	/**
	 * Helper function for testAdjacents.
	 * Checks by counting the size of the list of neighbors
	 * and checking distance difference is exactly 1.
	 * @param node the Node which neighbors will be checked
	 * @param count the number of neighbors the Node should have
	 */
	private void checkAdjacents(Node node, int count, boolean diagonal)
	{
		List<Node> adjacent = node.getAdjacentNodes(diagonal);
		assertEquals(adjacent.size(), count);

		for (Node neighbor : adjacent)
		{
			int differenceX = Math.abs(node.getPosition().getX() 
							  - neighbor.getPosition().getX());
			int differenceY = Math.abs(node.getPosition().getY()
							  - neighbor.getPosition().getY());
			if (diagonal)
			{
				assertTrue(differenceX + differenceY <= 2);
			}
			else
			{
				assertTrue(differenceX + differenceY == 1);
			}
		}
	}
	
	/**
	 * This test checks the functionality of canReach function.
	 */
	@Test
	public void testCanReach()
	{
		Maze testMaze = new Maze(
						"#######\n" +
						"#S    #\n" +
						"#    X#\n" +
						"#   XE#\n" +
						"#######\n", false);
		
		Position position = new Position (1,1);
		Node node = testMaze.getNode(position);
		
		for (Node neighbor : testMaze.getNode(position).getAdjacentNodes(true))
		{

				assertTrue(node.canReach(neighbor));
		}
		
		position = new Position (3,1);
		node = testMaze.getNode(position);
		
		assertFalse(node.canReach(maze.getEndNode()));
		
		for (Node neighbor : testMaze.getNode(position).getAdjacentNodes(true))
		{
			if (!neighbor.getIsPassable())
			{
				assertFalse(node.canReach(neighbor));
			}
		}
		
		Node endNode = maze.getEndNode();
		assertFalse(edgeNode.canReach(endNode));
		assertFalse(endNode.canReach(edgeNode));
	}
	
	/**
	 * This test checks the functionality of hashCode function.
	 */
	@Test
	public void testHashCode()
	{
		assertTrue(centerNode.hashCode() != edgeNode.hashCode());
		assertEquals(edgeNode.hashCode(), edgeNode.hashCode());
		assertTrue(maze.getEndNode().hashCode() != maze.getStartNode().hashCode());	
	}

	
}

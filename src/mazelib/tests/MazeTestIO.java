package mazelib.tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mazelib.data.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Tuesday, September 18, 2012, 18:00 PM
 */
public class MazeTestIO {

	private static final String IMAGE_PATH = "/Users/TiRez/Documents/UIUC/CS 242/Assignment1.2/images/";
	
	private final static File mazefile0 = new File (IMAGE_PATH + "maze0.bmp"); 
	private final static File mazefile1 = new File (IMAGE_PATH + "maze1.bmp");
	private final static File mazefile2 = new File (IMAGE_PATH + "maze2.bmp"); 
	private static BufferedImage image0, image1, image2; 

	private static Maze maze0, maze1, maze2;
	private static Position start0, start1, start2, end0, end1, end2;

	/**
	 * This code is run before any tests.
	 * Sets up the images, maze, and positions.
	 */
	@BeforeClass
	public static void setup() throws IOException
	{
		image0 = ImageIO.read(mazefile0);
		image1 = ImageIO.read(mazefile1);
		image2 = ImageIO.read(mazefile2);
		
		// Top left to bottom right
		start0 = new Position(0,0);
		end0 = new Position(image0.getWidth()-1, image1.getHeight()-1);
		
		// Bottom left to top right
		start1 = new Position(0, image0.getHeight()-1);
		end1 = new Position(image0.getWidth()-1, 0);
		
		// Hard coded start and end for maze2
		start2 = new Position(0, 28);
		end2 = new Position(199, 0);
	}
	
	/**
	 * This test checks if test image Mazes can be parsed to Maze objects.
	 * @throws IOException 
	 */
	@Test
	public void testParsingImages()
	{
		try 
		{
			maze0 = new Maze(image0, start0, end0);
			maze1 = new Maze(image1, start1, end1);
			maze2 = new Maze(image2, start2, end2);
		}
		catch (Exception e)
		{
			fail("Maze constructor failed!");
		}
		
		// Check that start and end nodes have been set
		assertTrue(maze0.getStartNode() != null);
		assertTrue(maze0.getEndNode() != null);
		assertTrue(maze1.getStartNode() != null);
		assertTrue(maze1.getEndNode() != null);
		assertTrue(maze2.getStartNode() != null);
		assertTrue(maze2.getEndNode() != null);
	}
	
	/**
	 * Test parsed mazes are solvable when they should be and unsolvable if
	 * start or end have been chosen inside walls.
	 */
	@Test
	public void testSolvability()
	{
		assertTrue(maze0.isSolvable());
		assertTrue(maze1.isSolvable());
		assertTrue(maze2.isSolvable());
		
		// Maze that starts inside a wall
		Position blocked = new Position(image0.getWidth()/2, image0.getHeight()/2);
		Maze unsolvable = new Maze(image0, blocked, end0);
		assertFalse(unsolvable.isSolvable());
		
		// Maze that ends inside a wall
		unsolvable = new Maze(image2, start2, end0);
		assertFalse(unsolvable.isSolvable());
		
		// Maze that both starts and ends in a wall
		unsolvable = new Maze(image2, start0, end0);
		assertFalse(unsolvable.isSolvable());
	}

	/**
	 * This test checks if MazeSolvers can solve input mazes.
	 */
	@Test
	public void testSolving()
	{
		runAllAlgorithms(maze0);
		runAllAlgorithms(maze1);
		runAllAlgorithms(maze2);
	}
	
	/**
	 * Helper function. Runs all possible algorithm combinations.
	 * @param maze the Maze to run algorithms on
	 */
	private void runAllAlgorithms(Maze maze)
	{
		Heuristic manhattan = new ManhattanDistance();
		Heuristic diagonal = new DiagonalDistance();
		Heuristic euclidean = new EuclideanDistance();
		
		MazeSolver solver = new AStarAlgorithm(maze, manhattan);
		assertTrue(solver.solveMaze() != null);
		
		solver = new AStarAlgorithm(maze, diagonal);
		assertTrue(solver.solveMaze() != null);
		
		solver = new AStarAlgorithm(maze, euclidean);
		assertTrue(solver.solveMaze() != null);
		
		solver = new DijkstrasAlgorithm(maze, manhattan);
		assertTrue(solver.solveMaze() != null);
	}
	
	/**
	 * This test checks if outputSolution returns an output image with solution.
	 */
	@Test
	public void checkOutput()
	{
		checkOutputSolution(maze0, image0);
		checkOutputSolution(maze1, image1);
		checkOutputSolution(maze2, image2);
	}
	
	/**
	 * Helper function for checking output images.
	 */
	private void checkOutputSolution(Maze maze, BufferedImage image)
	{
		Heuristic manhattan = new ManhattanDistance();
		MazeSolver solver = new AStarAlgorithm(maze, manhattan);
		
		assertTrue(solver.solveMaze() != null);
		
		BufferedImage output = solver.outputSolution();
		
		// Check that images have the same dimensions but different pixels
		assertFalse(output.equals(image));
		assertTrue(output.getHeight() == image.getHeight());
		assertTrue(output.getWidth() == image.getWidth());
		
		Node startNode = maze.getStartNode();
		int solutionRGB = Color.red.getRGB();
		
		// Start from end Node and check if solution Nodes have been marked red.
		for (Node currentNode = maze.getEndNode(); currentNode != startNode; currentNode = currentNode.getParentNode())
		{
			Position position = currentNode.getPosition();
			int currentRGB = output.getRGB(position.getX(), position.getY());
			assertTrue(currentRGB == solutionRGB);
		}
	}
}

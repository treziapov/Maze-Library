package mazelib.tests;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mazelib.data.*;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Tuesday, September 4, 2012, 18:00 PM
 */
public class AStarAlgorithmTest {

	private static Random randomGenerator = new Random();
	private static final int MAZE_SIZE_LIMIT = 21;
	private static final int MAZE_WIDTH = randomGenerator.nextInt(MAZE_SIZE_LIMIT) + 4;
	private static final int MAZE_HEIGHT = randomGenerator.nextInt(MAZE_SIZE_LIMIT) + 4;
	
	private static Maze maze = new Maze(MAZE_WIDTH, MAZE_HEIGHT);
	private static AStarAlgorithm solver;
	private static Heuristic heuristic = new DiagonalDistance();
	
	private static final String MAZE_STRING = 
			"##############\n" +
			"#S      X   X#\n" +
			"#XXXXXX XXX X#\n" +
			"#           X#\n" +
			"#XXXXXXXXXX E#\n" +
			"##############\n";
	
	/**
	 * Test to check AStartAlgorithm object initialization.
	 * Checks both Maze and String constructors.
	 */
	@Test
	public void testInitialization() 
	{
		boolean caught = false;
		try
		{
			solver = new AStarAlgorithm(maze, heuristic);
		}
		catch (Exception e)
		{
			caught = true;
		}
		assertFalse(caught);
		
		try
		{
			solver = new AStarAlgorithm(MAZE_STRING, heuristic);
		}
		catch (Exception e)
		{
			caught = true;
		}
		assertFalse(caught);
	}
	
	/**
	 * Test to check if main function causes any exceptions.
	 */
	@Test
	public void testMainExceptions() 
	{
		try
		{
			solver = new AStarAlgorithm(maze, heuristic);
			solver.solveMaze();
		}
		catch (Exception e)
		{
			fail("Solving exception!");
		}
		
		try
		{
			solver = new AStarAlgorithm(MAZE_STRING, heuristic);
			solver.solveMaze();
		}
		catch (Exception e)
		{
			fail("Solving exception!");
		}
	}
	
	/**
	 * This test checks if the algorithm can find a way in an area with no walls.
	 */
	@Test
	public void testOpenArea()
	{
		Maze wallMaze = new Maze(
						"#################\n" +
						"#               #\n" +
						"#S              #\n" +
						"#               #\n" +
						"#               #\n" +
						"#               #\n" +
						"#               #\n" +
						"#              E#\n" +
						"#################\n", false);
		
		solver = new AStarAlgorithm(wallMaze, heuristic);
		
		assertTrue(solver.solveMaze() != null);
		solver.drawSolution();
	}
	
	/**
	 * This test checks if the algorithm can find a way blocked by a wall.
	 */
	@Test
	public void testWall()
	{
		Maze wallMaze = new Maze(
						"#################\n" +
						"#               #\n" +
						"#       X       #\n" +
						"#       X       #\n" +
						"#S      X      E#\n" +
						"#       X       #\n" +
						"#       X       #\n" +
						"#               #\n" +
						"#################\n", false);
		
		solver = new AStarAlgorithm(wallMaze, heuristic);
		
		assertTrue(solver.solveMaze() != null);
		solver.drawSolution();
	}
	
	/**
	 * This test checks if the algorithm can find a way 
	 * blocked by a wall at the bottom
	 */
	@Test
	public void testWallBottom()
	{
		Maze wallMaze = new Maze(
						"#################\n" +
						"#               #\n" +
						"#S              #\n" +
						"#               #\n" +
						"#               #\n" +
						"#               #\n" +
						"# XXXXXXXXXXXXXX#\n" +
						"#              E#\n" +
						"#################\n", false);
		
		solver = new AStarAlgorithm(wallMaze, heuristic);
		
		assertTrue(solver.solveMaze() != null);
		solver.drawSolution();
	}
	
	/**
	 * This test checks if solver chooses the shortest path
	 * when there are multiple paths leading to the end Node.
	 * Shortest path is the one with most diagonal path.
	 */
	@Test
	public void testChooseShortestPath()
	{
		Maze multiplePaths = new Maze(
							"###################\n" +
							"#S                #\n" +
							"#    XXXXXXXXXXX  #\n" +
							"#     XXXXXX   X  #\n" +
							"#  X   XXX        #\n" +
							"#  XX   XX  XX    #\n" +
							"#  XXX   X  X XX  #\n" +
							"#  XXXX     X XX  #\n" +
							"#                E#\n" +
							"###################\n", false);
		
		solver = new AStarAlgorithm(multiplePaths, heuristic);
	
		assertTrue(solver.solveMaze() != null);
		solver.drawSolution();
	}

	/**
	 * This test checks if the algorithm can solve relatively big Mazes.
	 */
	@Test
	public void testBigMaze()
	{
		Maze big = new Maze(
					"###########################\n" +
					"#SX          XXX       X X#\n" +
					"#   XXX    XXX   XXXXX X X#\n" +
					"#      XXX   XXX   X      #\n" +
					"# XX   XX X XX     XX  X X#\n" +
					"# X   X       XXXX     X  #\n" +
					"# XXX XX    X X XX   XX   #\n" +
					"#   X   X X  XX  X       X#\n" +
					"# X XXX XXXXX       XX   X#\n" +
					"# X      X      XXX    X X#\n" +
					"#       XX XXX      XX X X#\n" +
					"# X         X     XXX    X#\n" +
					"#     XX        XXXX   X X#\n" +
					"#     X    XXX    XX  XX X#\n" +
					"#X   XX  XXXXX   X  X X  X#\n" +
					"#   X     X   XX     XX   #\n" +
					"#XX   XXX    X  X    X XXX#\n" +
					"#   X X     X            X#\n" +
					"# XXX X X X            X X#\n" +
					"#   X   X   XXXXXX       E#\n" +
					"###########################\n", false);
		
		solver = new AStarAlgorithm(big, heuristic);
		List<Node> solution = solver.solveMaze();
		
		assertTrue(solution != null);
		solver.drawSolution();
	}
	
	/**
	 * This test checks if the algorithm handles mazes where 
	 * target is blocked by walls.
	 */
	@Test
	public void testUnsolvable()
	{
		Maze wallMaze = new Maze(
						"#################\n" +
						"#               #\n" +
						"#S              #\n" +
						"#               #\n" +
						"#               #\n" +
						"#        XXXXXXX#\n" +
						"#        X      #\n" +
						"#        X     E#\n" +
						"#################\n", false);
		
		solver = new AStarAlgorithm(wallMaze, heuristic);
		
		assertNull(solver.solveMaze());
		assertNull(solver.drawSolution());
	}
	
	/**
	 * This test checks if the algorithm handles mazes where 
	 * target isn't surrounded by walls but can't be reached 
	 * diagonally.
	 */
	@Test
	public void testUnsolvableDiagonal()
	{
		Maze wallMaze = new Maze(
						"#################\n" +
						"#               #\n" +
						"#S              #\n" +
						"#               #\n" +
						"#           X X #\n" +
						"#          X X X#\n" +
						"#         X X X #\n" +
						"#          X X E#\n" +
						"#################\n", false);
		
		solver = new AStarAlgorithm(wallMaze, heuristic);
		
		assertNull(solver.solveMaze());
		assertNull(solver.drawSolution());
	}
	
	/**
	 * This test checks if Mazes are solveable regardless of the 
	 * Heuristic used.
	 */
	@Test
	public void testHeuristicIndependence()
	{
		heuristic = new EuclideanDistance();
		Maze maze = new Maze(
					"###########################\n" +
					"#SX          XXX       X X#\n" +
					"#   XXX    XXX   XXXXX X X#\n" +
					"#      XXX   XXX   X      #\n" +
					"# XX   XX X XX     XX  X X#\n" +
					"# X   X       XXXX     X  #\n" +
					"# XXX XX    X X XX   XX   #\n" +
					"#   X   X X  XX  X       X#\n" +
					"# X XXX XXXXX       XX   X#\n" +
					"# X      X      XXX    X X#\n" +
					"#       XX XXX      XX X X#\n" +
					"# X         X     XXX    X#\n" +
					"#     XX        XXXX   X X#\n" +
					"#     X    XXX    XX  XX X#\n" +
					"#X   XX  XXXXX   X  X X  X#\n" +
					"#   X     X   XX     XX   #\n" +
					"#XX   XXX    X  X    X XXX#\n" +
					"#   X X     X            X#\n" +
					"# XXX X X X            X X#\n" +
					"#   X   X   XXXXXX       E#\n" +
					"###########################\n", false);
		
		solver = new AStarAlgorithm(maze, heuristic);
		List<Node> solution = solver.solveMaze();
		
		assertTrue(solution != null);
		solver.drawSolution();
		
		heuristic = new DiagonalDistance();
		solver = new AStarAlgorithm(maze, heuristic);
		solution = solver.solveMaze();
		
		assertTrue(solution != null);
		solver.drawSolution();
		
		heuristic = new ManhattanDistance();
		solver = new AStarAlgorithm(maze, heuristic);
		solution = solver.solveMaze();
		
		assertTrue(solution != null);
		solver.drawSolution();
		
	}
	
	/**
	 * This test checks if drawSolutiion returns the same output if
	 * called twice and returns a String representation of a
	 * successfully solved maze.
	 */
	@Test
	public void testDrawSolution()
	{
		Maze maze = new Maze(MAZE_HEIGHT, MAZE_WIDTH);
		solver = new AStarAlgorithm(maze, heuristic);
		solver.solveMaze();
		String out = solver.drawSolution();
		
		assertTrue(out != null);
		assertEquals(out, solver.drawSolution());
	}
}

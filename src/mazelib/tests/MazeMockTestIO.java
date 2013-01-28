package mazelib.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import mazelib.data.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Tuesday, September 18, 2012, 18:00 PM
 */
public class MazeMockTestIO {

	private static BufferedImage validImage, invalidImage;
	private static Maze solvable, unsolvable;
	private static Position start, end;
	
	/**
	 * Set up mock objects.
	 */
	@BeforeClass
	public static void setup()
	{
		 validImage = mock(BufferedImage.class);
		 when(validImage.getHeight()).thenReturn(100);
		 when(validImage.getWidth()).thenReturn(100);
		 when(validImage.getRGB(anyInt(), anyInt())).thenReturn(0);
		 
		 invalidImage = mock(BufferedImage.class);
		 when(invalidImage.getHeight()).thenReturn(100);
		 when(invalidImage.getWidth()).thenReturn(100);
		 when(invalidImage.getRGB(anyInt(), anyInt())).thenReturn(Color.WHITE.getRGB());
		 
		 start = new Position(0,0);
		 end = new Position(99,99);
	}
	
	/** 
	 * Test initialization and image parsing.
	 */
	@Test
	public void testInitialization() 
	{
		// Valid Image Check
		solvable = new Maze(validImage, start, end);
		verify(validImage).getWidth();
		verify(validImage).getHeight();
		
		// Check if image.getRGB() was called for every pixel
		verify(validImage, times(100*100)).getRGB(anyInt(), anyInt());
		
		// Invalid Image Check
		unsolvable = new Maze(invalidImage, start, end);
		verify(validImage).getWidth();
		verify(validImage).getHeight();
		
		// Check if image.getRGB() was called for every pixel
		verify(validImage, times(100*100)).getRGB(anyInt(), anyInt());
	}
	
	/**
	 * Test for solvability.
	 */
	@Test
	public void testSolvability()
	{
		assertTrue(solvable.isSolvable());
		assertFalse(unsolvable.isSolvable());
	}

	/**
	 * This test checks if MazeSolvers can solve input mazes.
	 */
	@Test
	public void testSolving()
	{
		Heuristic manhattan = new ManhattanDistance();
		Heuristic diagonal = new DiagonalDistance();
		Heuristic euclidean = new EuclideanDistance();
		
		// Check solvable
		MazeSolver solver = new AStarAlgorithm(solvable, manhattan);
		assertTrue(solver.solveMaze() != null);
		
		solver = new AStarAlgorithm(solvable, diagonal);
		assertTrue(solver.solveMaze() != null);
		
		solver = new AStarAlgorithm(solvable, euclidean);
		assertTrue(solver.solveMaze() != null);
		
		solver = new DijkstrasAlgorithm(solvable, manhattan);
		assertTrue(solver.solveMaze() != null);
		
		// Check unsolvable
		solver = new AStarAlgorithm(unsolvable, manhattan);
		assertFalse(solver.solveMaze() != null);
		
		solver = new AStarAlgorithm(unsolvable, diagonal);
		assertFalse(solver.solveMaze() != null);
		
		solver = new AStarAlgorithm(unsolvable, euclidean);
		assertFalse(solver.solveMaze() != null);
		
		solver = new DijkstrasAlgorithm(unsolvable, manhattan);
		assertFalse(solver.solveMaze() != null);
	}

}

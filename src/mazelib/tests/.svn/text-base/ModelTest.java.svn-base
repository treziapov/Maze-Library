package mazelib.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;

import mazelib.data.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ModelTest {

	private static MazeInterfaceModel model;
	private File goodFile = new File ("/Users/TiRez/Documents/UIUC/CS 242/Assignment1.2/images/maze1.bmp");
	private File badFile = new File ("/Users/TiRez/Documents/UIUC/CS 242/Assignment1.2/images/mazeX.bmp");
	private static MazeInterfaceModel spy;
	
	/**
	 * This method is run before any other tests.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new MazeInterfaceModel();
		spy = spy(model);
	}

	/**
	 * Test file initialization. Particularly the new Maze File constructor.
	 */
	@Test
	public void testInitialization() {
		assertNull( model.initialize(badFile));
		assertTrue( model.initialize(goodFile) != null );
		assertTrue( spy.initialize(goodFile) != null );
	}
	
	/**
	 * Test solvability calls.
	 * Specifically checking the case when solvabiliy test has been run
	 * so model shouldn't call maze.isSolvable() twice
	 */
	@Test
	public void testSolvability() {
		assertTrue( model.testSolvability() );
		
		// Use the spy object to check the number of calls
		assertTrue( spy.testSolvability() );
		assertTrue( spy.testSolvability() );
		
		// Should only call isSolvable once because testSolvability ran twice
		verify( spy, times(1) ).isSolvable();
	}
	
	/**
	 * Should be a trivial test. String input to the function are always legal.
	 * And the rest of the testing is the same as in Maze classes.
	 */
	@Test
	public void testSolving() {
		assertTrue( model.solve("Euclidean", "A Star") != null );
		assertTrue( model.getSolutionImage() != null );
	}
	
	/**
	 * This test checks correct updating of Maze's start and end coordinates,
	 * also checks if solvableHasRan flag is updated appropriately.
	 */
	@Test
	public void testCoordinateUpdates() {
		// Default start: (0, 0)
		// Default end: (319,239)
		
		// These calls shouldn't change anything
		model.updateStartX(0);
		assertTrue( model.solvableHasRan() );
		model.updateEndX(319);
		assertTrue( model.solvableHasRan() );
		
		// These calls should change start or end and set solvalbeHasRan to false
		model.updateEndY(50);
		assertFalse( model.solvableHasRan() );
		
		model.testSolvability();
		
		assertTrue( model.solvableHasRan() );
		model.updateStartX(100);
		assertFalse( model.solvableHasRan() );

	}
}

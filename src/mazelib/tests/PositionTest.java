package mazelib.tests;

import java.util.Random;

import mazelib.data.*;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Sunday, September 2, 2012 18:00 PM
 */
public class PositionTest {

	private static Random randomGenerator;
	private static final int originX = 0;
	private static final int originY = 0;
	private static final int edgeX = 0;
	private static final int edgeY = 50;
	private static int randomX;
	private static int randomY;
	private static Position origin, edge, random;
	
	/**
	 * This test is run once before any tests are run.
	 * It initializes the test Position objects.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		randomGenerator = new Random();
		randomX = randomGenerator.nextInt(100) + 1;
		randomY = randomGenerator.nextInt(100) + 1;
		origin = new Position(originX, originY);
		edge = new Position(edgeX, edgeY); 
		random = new Position(randomX, randomY);
	}

	/**
	 * This trivial test checks that the newly constructed Positions'
	 * attributes have been set correctly and getters are working correctly.
	 */
	@Test
	public void testInitialization() 
	{
		assertEquals(origin.getX(), originX);
		assertEquals(origin.getY(), originY);
		assertEquals(edge.getX(), edgeX);
		assertEquals(edge.getY(), edgeY);
		assertEquals(random.getX(), randomX);
		assertEquals(random.getY(),randomY);
	}
	
	/**
	 * This test checks that same Position is equal to itself
	 * and different Positions are not equal to each other.
	 */
	@Test
	public void testEquals() 
	{
		assertTrue(origin.equals(origin));
		assertTrue(random.equals(random));
		assertFalse(origin.equals(edge));
		assertFalse(edge.equals(origin));
		assertFalse(edge.equals(random));
		assertFalse(random.equals(edge));
		assertFalse(edge.equals(null));
	}
	
	/**
	 * This test checks the functionality of the add method.
	 */
	@Test
	public void testAdding() 
	{
		random.addCoordinates(0, 0);
		assertEquals(random.getX(), randomX);
		assertEquals(random.getY(), randomY);
		random.addCoordinates(1, edgeY - randomY);
		assertTrue(random.getX() != randomX);
		assertTrue(random.getY() == edgeY);
		random.addCoordinates(-1, randomY - edgeY);
		assertEquals(random.getX(), randomX);
		assertEquals(random.getY(), randomY);
	}
	
	/**
	 * This test checks the hashCode function.
	 */
	@Test
	public void testHashCode()
	{
		assertEquals(edge.hashCode(), edge.hashCode());
		assertFalse(edge.hashCode() == random.hashCode());
		assertFalse(origin.hashCode() == edge.hashCode());
	}
	
	/**
	 * This test checks isDiagonal function.
	 */
	@Test 
	public void testIsDiagonal()
	{
		assertFalse(origin.isDiagonal(edge));
		Position diagonal = new Position (1,1);
		assertTrue(origin.isDiagonal(diagonal));
		
	}
}

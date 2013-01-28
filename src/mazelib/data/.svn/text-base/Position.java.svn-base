package mazelib.data;

/**
 * Helper class that will hold each Node's x and y coordinates in parent Maze.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Sunday, September 2, 2012 18:00 PM
 */
public class Position {	

	private int x;
	private int y;

	/**
	 * Constructs a Position. This constructor is public because it is a 
	 * helper class that we will also use for testing purposes.
	 * @param x the Node's row index in Maze
	 * @param y the Node's column index in Maze
	 */
	public Position(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}	
	
	/**
	 * Helper function, adds parameters to respective coordinates of this Position.
	 * Parameters can and will be negative.
	 * @param x the number to be added to this Position's x member
	 * @param y the number to be added to this Position's y member
	 */
	public void addCoordinates(int x, int y) 
	{
		this.x += x;
		this.y += y;
	}

	/**
	 * @return Position's x coordinate
	 */
	public int getX() 
	{
		return this.x;
	}

	/**
	 * @return Position's y coordinate
	 */
	public int getY() 
	{
		return this.y;
	}
	
	/**
	 * Returns a hash code for this Position, which is unique
	 * to other Positions with different coordinates.
	 * @return hash
	 */
	@Override
	public int hashCode() 
	{
		String hash = Integer.toString(this.x) + Integer.toString(this.y);
		return Integer.parseInt(hash);
	}
	
	/**
	 * Checks if other Object is another Position with same fields.
	 * @return true if other is a Position and has same fields.
	 * 		   false otherwise
	 */
	@Override
	public boolean equals(Object other) 
	{
		if (other instanceof Position) 
		{
			return (this.x == ((Position)other).getX()) &&
				   (this.y == ((Position)other).getY());
		}
		return false;
	}
	
	/**
	 * @return true if adjacent is diagonal to this Position,
	 * 		   false otherwise
	 */
	public boolean isDiagonal(Position adjacent) 
	{
		return (Math.abs(this.x - adjacent.getX()) + 
				Math.abs(this.y - adjacent.getY()) ) == 2;
	}
	
}

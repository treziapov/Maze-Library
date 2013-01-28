package mazelib.data;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Template method for Maze solving algorithms
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Wednesday, September 5, 2012 15:00 PM
 */
public abstract class MazeSolver {

	protected Maze maze;
	protected final Heuristic heuristic;
	protected Map<Node, Boolean> solutionNodes = null;
	protected List<Node> solution = null;
	
	public BufferedImage solutionImage = null;
	
	protected final long COST_STRAIGHT = 100l;
	protected final long COST_DIAGONAL = 141l;
	protected final int SOLUTION_COLOR = Color.red.getRGB();
	
	/**
	 * Constructor to be used by derived classes.
	 * @param maze the Maze to work on
	 */
	public MazeSolver(Maze maze, Heuristic heuristic)
	{
		// Check for null Maze argument 
		if (maze == null || heuristic == null)
		{
			throw new IllegalArgumentException("Null argument(s).");
		}
		else
		{
			this.maze = maze;
			this.heuristic = heuristic;
		}
	}
	
	/**
	 * Constructor to be used by derived classes.
	 * @param stringMaze the string representation of the Maze to solve
	 */
	public MazeSolver(String stringMaze, Heuristic heuristic)
	{
		// Check for null Maze argument 
		if (stringMaze == null)
		{
			throw new IllegalArgumentException("Null Maze argument.");
		}
		else
		{
			this.maze = new Maze(stringMaze, false);
			this.heuristic = heuristic;
		}
	}
	
	/**
	 * Main algorithm method that each non-abstract derived class must define.
	 * @return the List of solution Nodes
	 */
	abstract public List<Node> solveMaze();
	
	/**
	 * This function draws the maze with solution path.
	 * @return the string representation of Maze with solution path,
	 * 		   null if Maze hasn't been solved yet
	 */
	public String drawSolution()
	{
		if (solutionNodes.isEmpty())
		{
			return null;
		}
		
		String result = "";
		String border = "#";
		
		// Make a line of X's with length equal to the (width+2) of this Maze
		String horizontalBorder = "";
		for (int x = 0; x < this.maze.getWidth() + 2; x++) {
			horizontalBorder = horizontalBorder + border;
		}
		horizontalBorder = horizontalBorder + "\n";
		
		// Draw Walls
		for (int y = 0; y < this.maze.getHeight(); y++) {
			result = result + border;
			for (int x = 0; x < this.maze.getWidth(); x++) {
				
				Position position = new Position(x,y);
				
				if (!this.maze.getNode(position).getIsPassable()) {
					result = result + "X";
				}
				else if (this.maze.getStartNode() == this.maze.getNode(position))
				{
					result = result + "S";
				}
				else if (this.maze.getEndNode() == this.maze.getNode(position))
				{
					result = result + "E";
				}
				else if (solutionNodes.containsKey(this.maze.getNode(position)))
				{
					result = result + ".";
				}
				else	
					result = result + " ";
			}
			result = result + border + "\n";
		}
		
		// Add horizontal borders
		result = horizontalBorder + result + horizontalBorder;
		
		// Print result to screen
		System.out.println(result);
		
		return result;
	}
	
	/**
	 * This function uses the solution list to make a new BMP Image with the solution path
	 * outlines in red on the original maze.
	 * Assumed to be called on solved mazes
	 * @return the BufferedImage of maze with solution path
	 */
	public BufferedImage outputSolution()
	{
		BufferedImage original = maze.getImage();
		BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
		result.getGraphics().drawImage(original, 0, 0, null);
		
		if (solutionNodes == null)
		{
			return null;
		}
		
		for (Node node : solution)
		{
			Position position = node.getPosition();

			result.setRGB(position.getX(), position.getY(), SOLUTION_COLOR);
		}
		
		solutionImage = result;
		return result;
	}
	
	public BufferedImage getImage() {
		return solutionImage;
	}
}

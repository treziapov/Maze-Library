package mazelib.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Sunday, September 9, 2012 18:00 PM
 */
public class DijkstrasAlgorithm extends MazeSolver {

	private PriorityQueue<Node> queue;
	
	private static final long INFINITY = 7777777l;

	/**
	 * Constructor for DijkastrasAlgorithm Object.
	 * @param maze the Maze to solve
	 */
	public DijkstrasAlgorithm(Maze maze, Heuristic heuristic) 
	{
		super(maze, heuristic);
	}
	
	/**
	 * Constructor for DijkstrasAlgorithm Object.
	 * @param stringMaze the string representation of the Maze to solve
	 */
	public DijkstrasAlgorithm(String stringMaze, Heuristic heuristic) 
	{
		super(stringMaze, heuristic);
	}

	/**
	 * The main function of the algorithm.
	 */
	@Override
	public List<Node> solveMaze() 
	{
		// Initialization
		queue = new PriorityQueue<Node>();
		solutionNodes = new HashMap<Node, Boolean>();
		
		for (int y = 0; y < this.maze.getHeight(); y++) 
		{
			for (int x = 0; x < this.maze.getWidth(); x++)
			{
				Position position = new Position(x, y);
				Node currentNode = this.maze.getNode(position);

				if (!currentNode.getIsPassable())
				{
					continue;
				}
				currentNode.setParentNode(null);
				currentNode.setAccumulatedCost(INFINITY);
				queue.add(currentNode);
			}
		}
		
		// Set distance from start Node as 0 
		Node currentNode = this.maze.getStartNode();
		Node targetNode = this.maze.getEndNode();
		
		queue.remove(currentNode);
		currentNode.setAccumulatedCost(0l);
		queue.add(currentNode);
		
		while (!queue.isEmpty())
		{
			// Get the vertex with smallest total distance
			currentNode = queue.poll();
			
			if (currentNode == targetNode)
			{
				break;
			}
			
			// If distance is infinity, all other vertices are inaccessible
			long currentDistance = currentNode.getAccumulatedCost();
			if (currentDistance == INFINITY)
			{
				// Failed
				return null;
			}
			
			for (Node neighborNode : currentNode.getAdjacentNodes(true))
			{
				if(!currentNode.canReach(neighborNode))
				{
					continue;
				}
				
				// Calculate the cost to move to neighbor
				long cost = COST_STRAIGHT;
				if (neighborNode.getPosition().isDiagonal(currentNode.getPosition()))
				{
					cost = COST_DIAGONAL;
				}
				
				long neighborDistance = cost;
				long alternative = currentDistance + neighborDistance;
				
				if (alternative < neighborNode.getAccumulatedCost())
				{
					// Update distance to alternative and set parent to current Node
					neighborNode.setAccumulatedCost(alternative);
					neighborNode.setParentNode(currentNode);
					
					// Re-order the queue 
					queue.remove(neighborNode);
					queue.add(neighborNode);
				}	
			}
		}
		
		// Make a list of solution Nodes
		currentNode = this.maze.getEndNode();
		Node startNode = this.maze.getStartNode();
		
		solution = new ArrayList<Node>();
		
		while (!currentNode.equals(startNode))
		{
			solution.add(currentNode);
			solutionNodes.put(currentNode, true);
			currentNode = currentNode.getParentNode();
			if (currentNode == null)
			{
				return null;
			}
				
		}
		solution.add(startNode);
		
		return solution;
	}

}

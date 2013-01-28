package mazelib.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Wednesday, September 5, 2012 15:00 PM
 */
public class AStarAlgorithm extends MazeSolver {
	
	private PriorityQueue<Node> openNodes;
	private Map<Node, Boolean> closedNodes;
	
	private static final long COST_STRAIGHT = 100l;
	private static final long COST_DIAGONAL = 141l;

	/**
	 * Constructor for AStarAgorithm Object.
	 * @param maze the Maze to solve
	 */
	public AStarAlgorithm(Maze maze, Heuristic heuristic)
	{
		super(maze, heuristic);
	}
	
	/**
	 * Constructor for AStarAgorithm Object.
	 * @param stringMaze the string representation of the Maze to solve
	 */
	public AStarAlgorithm(String stringMaze, Heuristic heuristic)
	{
		super(stringMaze, heuristic);
	}
	
	/**
	 * The main function of the algorithm.
	 * @return List of Nodes that represent the solution to this maze,
	 * 		   null if there is no path from start Node to end Node
	 */
	@Override
	public List<Node> solveMaze() 
	{
		// Initialize Algorithm Data Structures
		if (solutionNodes != null)
		{
			maze.resetNodes();
		}
		
		openNodes = new PriorityQueue<Node>();
		closedNodes  = new HashMap<Node, Boolean>();
		solutionNodes = new HashMap<Node, Boolean>();
		
		Node startNode = this.maze.getStartNode();
		Node endNode = this.maze.getEndNode();
		Node currentNode = startNode;
		
		// Add starting Node to Open Nodes
		addToOpenNodes(currentNode);
		boolean failed = false;
	
		while (!isInClosedNodes(endNode) || openNodesIsEmpty())
		{
			// Get the smallest Total Cost Node in open Nodes, i.e. head of PQ
			currentNode = openNodes.poll();
			addToClosedNodes(currentNode);
			
			if (currentNode == null)
			{
				failed = true;
				break;
			}
			
			// Get all neighbors
			List<Node> neighborNodes = currentNode.getAdjacentNodes(true);
			for (Node neighborNode : neighborNodes)
			{
				// Check if neighbor is reachable 
				if (!currentNode.canReach(neighborNode))
				{
					continue;
				}
				// Check if neighbor is in closed Nodes
				if (isInClosedNodes(neighborNode))
				{
					continue;
				}
				
				// Calculate G cost to neighbor
				long cost = COST_STRAIGHT;
				if (neighborNode.getPosition().isDiagonal(currentNode.getPosition()))
				{
					cost = COST_DIAGONAL;
				}
				
				// If already in the open Nodes, remove returns true
				if (removeFromOpenNodes(neighborNode))
				{
					// Check if this Node leads to a better path

					long newAccumulated = currentNode.getAccumulatedCost() + cost;
					if (newAccumulated >= neighborNode.getAccumulatedCost())
					{
						addToOpenNodes(neighborNode);
						continue;
					}
				}

				neighborNode.setParentNode(currentNode);
				update(currentNode, neighborNode, cost);
				addToOpenNodes(neighborNode);				
			}
		}
		
		if (failed)
		{
			return null;
		}
		// Make a list of solution Nodes
		currentNode = endNode;
		solution = new ArrayList<Node>();
		
		while (!currentNode.equals(startNode))
		{
			solution.add(currentNode);
			solutionNodes.put(currentNode, true);
			currentNode = currentNode.getParentNode();
		}
		solution.add(startNode);
		
		return solution;
	}
	
	/**
	 * Helper function to update Node fields.
	 * @param currentNode the Node starting from
	 * @param neighborNode the Node getting to
	 * @param cost the cost to move to neighbor from current Node
	 */
	private void update(Node currentNode, Node neighborNode, long cost)
	{
		// Update costs
		Node endNode = this.maze.getEndNode();
		neighborNode.setAccumulatedCost(currentNode.getAccumulatedCost() + cost);
		neighborNode.setEstimatedCost(heuristic.calculateDistance(neighborNode, endNode));
		
		// Set parent node
		neighborNode.setParentNode(currentNode);
	}
	
	/**
	 * @param node the Node to add to Open Nodes
	 */
	private void addToOpenNodes(Node node)
	{
		openNodes.add(node);
	}
	
	/**
	 * @param node the Mode to add to Closed Nodes
	 */
	private void addToClosedNodes(Node node)
	{
		closedNodes.put(node, true);
	}
	
	/**
	 * @param node the Node to remove from Open Nodes
	 * @return true if node was removed,
	 * 		   false if node is not in Open Nodes
	 */
	private boolean removeFromOpenNodes(Node node)
	{
		return openNodes.remove(node);
	}
	
	/**
	 * @return is Open Nodes data structure is empty
	 */
	private boolean openNodesIsEmpty()
	{
		return openNodes.isEmpty();
	}
	
	/**
	 * @param node the Node to check if it belongs to Closed Nodes
	 * @return true if node is in Closed Nodes
	 * 		   false if node is not in Closed Nodes
	 */
	private boolean isInClosedNodes(Node node)
	{
		return this.closedNodes.containsKey(node);
	}
	
}

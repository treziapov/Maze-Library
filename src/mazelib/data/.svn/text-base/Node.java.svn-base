package mazelib.data;

import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

/**
 * This class represents a block in a Maze.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Sunday, September 2, 2012 17:00 PM
 */
public class Node implements Comparable<Node> {	
	
	private long accumulatedCost, estimatedCost;
	private Node parentNode;
	private final Position position;
	private boolean passable;
	private final Maze parentMaze;
	
	/**
	 * Constructs a Node. Protected because we only want to allow creation of
	 * Nodes from within a Maze object.
	 * @param position the Position of Node in a Maze
	 * @param passable determines if Node is a wall or not
	 */
	protected Node(Position position, boolean passable, Maze parentMaze) 
	{	
		if (position.getX() < 0 || position.getY() < 0 || position == null)		
		{
			throw new IllegalArgumentException("Illegal position.");
		}
		
		if (parentMaze == null)
		{
			throw new IllegalArgumentException("Null maze.");
		}
		
		this.setAccumulatedCost(0l);
		this.setEstimatedCost(0l);
		this.setParentNode(null);
		this.position = position;
		this.setPassable(passable);
		this.parentMaze = parentMaze;
	}
	
	/**
	 * @assume other Node is from the same parent Maze
	 * @return 1 if this Node has a higher total cost than parameter Node,
	 * 		   0 if this Node and parameter Node have the same total cost, and
	 * 		  -1 if this Node has a smaller total cost than parameter Node.
	 */
	public int compareTo(Node other) 
	{
		long thisTotal = this.getTotalCost();
		long otherTotal = other.getTotalCost();
		
		if (thisTotal > otherTotal)
		{
			return 1;
		}
		else if (thisTotal < otherTotal)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * This function is used to get this Node's adjacent Nodes.
	 * @param diagonal whether to include or not diagonal neighbors in return
	 * @return the list of non-diagonal adjacent Nodes
	 */
	public List<Node> getAdjacentNodes(boolean diagonal) 
	{
		List<Node> result = new LinkedList<Node>();
		Position thisPosition = this.getPosition();
		int thisX = thisPosition.getX();
		int thisY = thisPosition.getY();
		
		// Check a 3x3 Node area around this Node;
		for (int x = thisX - 1; x < thisX + 2; x++) 
		{
			for (int y = thisY - 1; y < thisY + 2; y++) 
			{
				Position adjacent = new Position(x, y);
				
				if (x == thisX && y == thisY) 
				{
					continue;
				}
				if (!this.parentMaze.inBounds(adjacent)) 
				{
					continue;
				}				
				if (thisPosition.isDiagonal(adjacent) && !diagonal) 
				{
					continue;
				}
				
				Node neighbor = this.parentMaze.getNode(adjacent);
				result.add(neighbor);
			}
		}
		return result;
	}
	
	/**
	 * Function to check if targetNode is reachable from this Node.
	 * @param targetNode the Node to check reaching to
	 * @return true if target Node is reachable from this Node,
	 * 		   false otherwise
	 */
	public boolean canReach(Node neighborNode)
	{
		// If neighbor is a wall
		if (!neighborNode.getIsPassable())
		{ 
			return false;
		}
		
		Position thisPosition = this.getPosition();
		Position neighborPosition = neighborNode.getPosition();
		
		// Neighbor is diagonal, so check if there are any walls around the way
		int thisX = thisPosition.getX();
		int thisY = thisPosition.getY();
		int directionX = neighborPosition.getX() - thisX;
		int directionY = neighborPosition.getY() - thisY;
		
		// If more than 1 cell away in any straight direction
		if (Math.abs(directionX) > 1 || Math.abs(directionY) > 1)
		{
			return false;
		}
		
		// If already passable neighbor is not diagonal
		if (!neighborPosition.isDiagonal(thisPosition))
		{	
			return true;
		}
		
		
		
		Position check1 = new Position(thisX + directionX, thisY);
		Position check2 = new Position(thisX, thisY + directionY);
		
		Maze maze = this.getParentMaze();
		
		if (!maze.getNode(check1).getIsPassable() || !maze.getNode(check2).getIsPassable())
		{
			return false;
		}
		else
			return true;
	}
	
	/**
	 * @return the accumulated cost for this Node
	 */
	public long getAccumulatedCost() 
	{
		return this.accumulatedCost;
	}

	/**
	 * Sets the accumulated cost for this Node.
	 * @param accumulatedCost
	 */
	public void setAccumulatedCost(long accumulatedCost) 
	{
		this.accumulatedCost = accumulatedCost;
	}

	/**
	 * @return the estimated cost for this Node
	 */
	public long getEstimatedCost() 
	{
		return this.estimatedCost;
	}

	/**
	 * Sets the estimated cost for this Node.
	 * @param estimatedCost
	 */
	public void setEstimatedCost(long estimatedCost) 
	{
		this.estimatedCost = estimatedCost;
	}
	
	/**
	 * @return total cost of this Node
	 */
	public long getTotalCost() 
	{
		return this.accumulatedCost + this.estimatedCost;
	}
	
	/**
	 * @return the parent Node of this Node
	 */
	public Node getParentNode() 
	{
		return this.parentNode;
	}

	/**
	 * Sets the parent Node for this Node.
	 * @param parent
	 */
	public void setParentNode(Node parentNode) 
	{
		this.parentNode = parentNode;
	}

	/**
	 * @return the Position of this Node
	 */
	public Position getPosition() 
	{
		return this.position;
	}

	/**
	 * @return if this Node is passable or impassable
	 */
	public boolean getIsPassable() 
	{
		return this.passable;
	}

	/**
	 * Sets this Node as passable or impassable
	 * @param passable
	 */
	protected void setPassable(boolean passable) 
	{
		this.passable = passable;
	}

	/** 
	 * @return the parent Maze of this Node
	 */
	public Maze getParentMaze() 
	{
		return parentMaze;
	}
	
	/**
	 * Returns an integer that corresponds to this Node's Position,
	 * which is unique to any other Node with a difference Position.
	 * @return a hash for this Node
	 */
	@Override
	public int hashCode() 
	{
		return this.getPosition().hashCode();
	}
	
	/**
	 * @return true if other is a Node with equal fields
	 * 		   false otherwise
	 */
	@Override
	public boolean equals(Object other) 
	{
		if (other instanceof Node) 
		{
			return this.position.equals(((Node)other).getPosition());
		}
		return false;
	}
	
}

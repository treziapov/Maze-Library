package mazelib.data;

/**
 * Maze Algorithm Heuristic to calculate Manhattan Distance.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Sunday, September 9, 2012 18:00 PM
 */
public class ManhattanDistance implements Heuristic {
	
	private static final long COST_STRAIGHT = 100l;

	/**
	 * Calculate distance in straight directions.
	 * @param originNode the Node to calculate distance from
	 * @param targetNode the Node to calculate distance to
	 * @return the Manhattan distance from origin to target
	 */
	@Override
	public long calculateDistance(Node originNode, Node targetNode) {
		
		// Check if arguments aren't null
		if (originNode == null || targetNode == null)
		{
			throw new IllegalArgumentException("Node(s) are null!");
		}
		
		// Check if parent Mazes are the same
		Maze maze = originNode.getParentMaze();
		if (maze != targetNode.getParentMaze())
		{
			throw new IllegalArgumentException("Nodes from different Mazes!");
		}
		
		Position origin = originNode.getPosition();
		Position target = targetNode.getPosition();
		
		long result = Math.abs(target.getX() - origin.getX())
					  + Math.abs(target.getY() - origin.getY());
		result = result * COST_STRAIGHT;
		
		return result;
	}

}

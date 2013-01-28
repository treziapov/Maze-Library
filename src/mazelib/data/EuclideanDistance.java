package mazelib.data;

/**
 * Maze Algorithm Heuristic to calculate Euclidean Distance.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Sunday, September 9, 2012 18:00 PM
 */
public class EuclideanDistance implements Heuristic{

	private final static long COST_STRAIGHT = 100l;
	
	/**
	 * Calculate distance in straight direction to target
	 * @param originNode the Node to calculate distance from
	 * @param targetNode the Node to calculate distance to
	 * @return the Distance from origin to end in a straight line
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
		
		// Math.sqrt() requires argument to be of type double
		double horizontal = (double) (Math.abs(target.getX() - origin.getX()) * COST_STRAIGHT);
		double vertical = (double) (Math.abs(target.getY() - origin.getY()) * COST_STRAIGHT);
		
		long result = (long) Math.sqrt(Math.pow(horizontal, 2d)
									 + Math.pow(vertical, 2d));

		return result;
	}

}

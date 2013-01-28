package mazelib.data;

/**
 * Maze Algorithm Heuristic to calculate Diagonal Distance.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Sunday, September 9, 2012 18:00 PM
 */
public class DiagonalDistance implements Heuristic {

	private final static long COST_STRAIGHT = 100l;
	private final static long COST_DIAGONAL = 141l;
	
	@Override
	public long calculateDistance(Node originNode, Node targetNode) {
		
		Maze maze = originNode.getParentMaze();
		
		if (maze != targetNode.getParentMaze())
		{
			throw new IllegalArgumentException("Nodes from different Mazes!");
		}
		
		Position origin = originNode.getPosition();
		Position target = targetNode.getPosition();
		
		int horizontal = Math.abs(target.getX() - origin.getX());
		int vertical = Math.abs(target.getY() - origin.getY());
		
		int diagonal = Math.min(horizontal, vertical);
		int straight = horizontal + vertical;
		
		long result = COST_DIAGONAL * diagonal +
					  COST_STRAIGHT * (straight - 2 * diagonal);
		
		return result;
	}

}

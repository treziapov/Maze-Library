package mazelib.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Monday, September 3, 2012, 18:00 PM
 */
public class Maze {
	
	private final int width, height;
	private Node[][] nodes;
	public Node startNode, endNode;
	public BufferedImage image;
	
	/**
	 * Constructs a Maze with random squares.
	 * @note all Nodes are impassable until Maze generation function is called
	 * @assume width and height will at least be 2
	 */
	public Maze(int width, int height) 
	{	
		// Handle width and height less than 2
		if(width < 2 || height < 2)
		{
			throw new IllegalArgumentException("Dimensions are too small.");
		}
		
		this.width = width;
		this.height = height;
		this.nodes = new Node[width][height];
		
		for(int x = 0; x < width; x++) 
		{
			for(int y = 0; y < height; y++) 
			{
				Position currentPosition = new Position(x, y);
				nodes[x][y] = new Node(currentPosition, false, this);
			}
		}
		
		this.startNode = nodes[0][0];
		this.startNode.setPassable(true);
		this.endNode = nodes[width - 1][height - 1];
		createMazeRB();
		
		// If Maze width is odd
		if (this.width % 2 == 0 && this.height % 2 == 0) 
		{
			Position end = endNode.getPosition();
			Position beforeEnd = new Position(end.getX() - 1, end.getY());
			this.getNode(beforeEnd).setPassable(true);
		}
		endNode.setPassable(true);
	}
	
	/**
	 * Constructs a Maze given a string.
	 * @param maze the String representation of a Maze object,
	 * 		  assumed to be in drawMaze's format
	 */
	public Maze(String maze, boolean test)
	{
		int width = 0;
		int height = 0;
		
		for (int i = 0; i < maze.length(); i ++)
		{
			char letter = maze.charAt(i);
			width++;
			
			if (letter == '\n')
			{
				break;
			}
		}
		
		height = maze.length() / width;
		this.width = width - 3;
		this.height = height - 2;
		nodes = new Node[this.width][this.height];
		Position startPosition = null;
		Position endPosition = null;
		
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				boolean passable = true;
				Position position = new Position(x - 1, y - 1);
				char letter = maze.charAt(y * width + x);
				
				switch (letter) 
				{
					case '#': 	continue;
					case '\n': 	continue;
					case 'X': 	passable = false;
								break;
					case ' ': 	passable = true;
								break;
					case 'S': 	startPosition = position;
								break;
					case 'E':	endPosition = position;
								break;
				}
				
				nodes[x - 1][y - 1] = new Node(position, passable, this);
			}
		}
		
		if (startPosition == null || endPosition == null)
		{
			throw new IllegalArgumentException("No start or end.");
		}
		else
		{	
			this.startNode = this.getNode(startPosition);
			this.endNode = this.getNode(endPosition);
		}
	}
	
	/**
	 * This helper function is used to randomize the nodes in a maze.
	 * It uses the Recursive Backtracker algorithm using pseudocode from Wikipedia:
	 * @reference http://en.wikipedia.org/wiki/Maze_generation_algorithm
	 */
	private void createMazeRB() 
	{		
		// Set up bookkeeping data structures
		Map<Position,Boolean> visited = new HashMap<Position,Boolean>();
		Stack<Node> nodeStack = new Stack<Node>();
		Random randomGenerator = new Random();
		int numShouldCheck = ((width+1)/2) * ((height+1)/2);
		
		// Put the start Node on the stack
		Node currentNode = this.startNode;
		visited.put(currentNode.getPosition(), true);
		
		// While all Nodes haven't been processed
		while(visited.size() != numShouldCheck) 
		{	
			Position currentPosition = currentNode.getPosition();
			int currentX = currentPosition.getX();
			int currentY = currentPosition.getY();
			
			// Get unvisited neighbors
			List<Node> unvisitedNeighbors = new LinkedList<Node>();
			for (Node neighbor : getAllNeighborsRB(currentNode)) 
			{
				Position neighborPosition = neighbor.getPosition();
				if (!visited.containsKey(neighborPosition)) 
				{
					unvisitedNeighbors.add(neighbor);
				}	
			}
			
			// If there are any unvisited neighbors
			int numUnvisitedNeighbors = unvisitedNeighbors.size();
			if (numUnvisitedNeighbors > 0) 
			{		
				// Push current Node to stack and mark it visited
				nodeStack.add(currentNode); 
				
				// Pick a random unvisited neighbor
				int randomNeighborIndex = randomGenerator.nextInt(numUnvisitedNeighbors);
				Node nextNode = unvisitedNeighbors.get(randomNeighborIndex);
				
				// Mark as visited and passable the next Node
				Position nextPosition = nextNode.getPosition();
				int differenceX = nextPosition.getX() - currentX;
				int differenceY = nextPosition.getY() - currentY;
				int distance = Math.abs(differenceX + differenceY);
				this.getNode(nextPosition).setPassable(true);
				visited.put(nextPosition, true);
				
				// Make the wall between current and next Nodes passable
				if (distance > 1) 
				{
					nextPosition = new Position(currentX + differenceX/2, 
												currentY + differenceY/2);
					this.getNode(nextPosition).setPassable(true);
				}
				
				currentNode = nextNode;
			}
			// No unvisited neighbors - a dead end
			else 
			{
				currentNode = nodeStack.pop();
			}
		}
	}
	
	/**
	 * Helper function for Recursive Backtracker maze generation
	 * @param currentNode the Node whose over 1 Node neighbors to return
	 * @return all over 1 Node neighbors that are in bounds of this Maze
	 */
	private List<Node> getAllNeighborsRB(Node currentNode) 
	{
		List<Node> neighbors = new LinkedList<Node>();
		Node neighbor = getNeighborRB(currentNode, 2, 0);
		if (neighbor != null) 
		{
			neighbors.add(neighbor);
		}
		neighbor = getNeighborRB(currentNode, -2, 0);
		if (neighbor != null) 
		{
			neighbors.add(neighbor);
		}
		neighbor = getNeighborRB(currentNode, 0, 2);
		if (neighbor != null) 
		{
			neighbors.add(neighbor);
		}
		neighbor = getNeighborRB(currentNode, 0, -2);
		if (neighbor != null) 
		{
			neighbors.add(neighbor);
		}
		return neighbors;
	}
	
	/**
	 * Helper function to get proper neighbors for createMazeRB function
	 * @param node the Node whose neighbors we want
	 * @return the list of all over 1 Node neighbors within the bounds
	 */
	private Node getNeighborRB(Node node, int addX, int addY) 
	{
		Position nodePosition = node.getPosition();
		int nodeX = nodePosition.getX();
		int nodeY = nodePosition.getY();
		
		Position overOne = new Position(nodeX + addX, nodeY + addY);
		
		if (inBounds(overOne)) 
		{
			return this.getNode(overOne);
		}
		else
		{
			return null;	
		}
	}
	
	/**
	 * This constructor parses a BMP image and creates a Maze object of it.
	 */
	public Maze(BufferedImage image, Position start, Position end)
	{		
		double grey_threshold = 255/2;

		this.height = image.getHeight();
		this.width = image.getWidth();

		this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		this.image.getGraphics().drawImage(image, 0, 0, null);
		
		this.nodes = new Node[width][height];
		
		for (int y = 0; y < this.height; y++)
		{
			for (int x = 0; x < this.width; x++)
			{
				boolean walkable = true;
				Position position = new Position(x, y);
				
				Color color = new Color(image.getRGB(x, y));
				double grey = color.getRed() * 0.299 + 
							  color.getBlue() * 0.144 +
							  color.getGreen() * 0.587;
				
				if (grey >= grey_threshold)
				{
					walkable = false;
				}
				
				this.nodes[x][y] = new Node(position, walkable, this);
			}
		}
		
		this.startNode = this.getNode(start);
		this.endNode = this.getNode(end);
	}
	
	/**
	 * Maze constructor that parses a File. Calls the image constructor.
	 * @param mazeFile
	 * @param start
	 * @param end
	 * @throws IOException
	 */
	public Maze(File mazeFile, Position start, Position end) throws IOException {
		this(ImageIO.read(mazeFile), start, end);
	}
	
	/**
	 * This function creates a String representation of this maze.
	 * Key: "X" - impassable/wall Node
	 * 		"#" - Maze border
	 * 		"S" - start Node
	 * 		"E" - end Node
	 * 		" " (space) - passable Node
	 * @return String representation of this Maze
	 */
	public String drawMaze() {
		
		String result = "";
		String border = "#";
		
		// Make a line of X's with length equal to the (width+2) of this Maze
		String horizontalBorder = "";
		for (int x = 0; x < this.width + 2; x++) 
		{
			horizontalBorder = horizontalBorder + border;
		}
		horizontalBorder = horizontalBorder + "\n";
		
		// Draw Walls
		for (int y = 0; y < this.height; y++) 
		{
			result = result + border;
			for (int x = 0; x < this.width; x++) 
			{
				if (!this.nodes[x][y].getIsPassable()) 
				{
					result = result + "X";
				}
				else 
				{
					result = result + " ";
				}
			}
			result = result + border + "\n";
		}
		
		// Add Start and End labels
		result = border + "S" + result.substring(2, result.length()-3) + 
						  "E" + border + "\n";
		
		// Add horizontal borders
		result = horizontalBorder + result + horizontalBorder;
		
		// Print result to screen
		System.out.println(result);
		
		return result;
	}

	/**
	 * Checks if parameter is in the bounds of this Maze.
	 * @param x the x coordinate to be checked
	 * @param y the y coordinate to be checked
	 * @return whether x and y are in the bounds of this Node's Maze
	 */
	public boolean inBounds(Position position) 
	{
		int x = position.getX();
		int y = position.getY();
		return (x >= 0 && x < this.width &&
				y >= 0 && y < this.height);
	}
	
	/**
	 * @param position the Position of the desired Node
	 * @return Node at parameter position if in maze bounds,
	 * 		   null if out of maze bounds
	 */
	public Node getNode(Position position) 
	{
		if (inBounds(position))
		{
			return this.nodes[position.getX()][position.getY()];
		}
		else
		{
			return null;
		}
	}
	
	/** 
	 * This function checks if this maze is solvable.
	 * @return true if end is reachable from start
	 * 		   false otherwise
	 */
	public boolean isSolvable()
	{
		Map<Node,Boolean> visited = new HashMap<Node,Boolean>();
		Stack<Node> nodeStack = new Stack<Node>();
		nodeStack.addElement(this.getStartNode());
		
		Node currentNode;
		Node endNode = this.getEndNode();
		
		// Perform a DFS traversal
		while(nodeStack.size() > 0) {
			currentNode = nodeStack.pop();
			
			if (currentNode.equals(endNode))
			{
				return true;
			}
			
			if(!visited.containsKey(currentNode) || !visited.get(currentNode)) {
				visited.put(currentNode, true);
				// NOTE: put method overwrites the value if key exists and increments size
			}
			
			// If current Node is impassable, continue
			if(!currentNode.getIsPassable()) {
				continue;
			}
			
			// Get all non-diagonal neighbors of current Node
			List<Node> adjacentNodes = currentNode.getAdjacentNodes(false);
			for(Node node : adjacentNodes) 
			{
				if(!visited.containsKey(node) || !visited.get(node)) 
				{
					nodeStack.add(node);
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Resets all the values of this Maze's Nodes to default.
	 */
	protected void resetNodes()
	{
		for(int x = 0; x < width; x++) 
		{
			for(int y = 0; y < height; y++) 
			{
				Node currentNode = nodes[x][y];
				currentNode.setParentNode(null);
				currentNode.setAccumulatedCost(0l);
				currentNode.setEstimatedCost(0l);
			}
		}
	}
	
	/**
	 * @return width of this Maze
	 */
	public int getWidth() 
	{
		return this.width;
	}

	/**
	 * @return height of this Maze
	 */
	public int getHeight() 
	{
		return this.height;
	}

	/**
	 * @return the starting Node for this Maze
	 */
	public Node getStartNode() 
	{
		return startNode;
	}

	/**
	 * @return the end Node for this Maze
	 */
	public Node getEndNode() 
	{
		return endNode;
	}

	/**
	 * @return image the BufferedImage of this maze
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * @param startNode
	 */
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	
	/**
	 * @param endNode
	 */
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	
}
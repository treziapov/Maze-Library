package mazelib.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;

/**
 * Maze GUI Interface. The Model component of the MVC model.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Saturday, September 22, 2012, 21:00 PM
 */
public class MazeInterfaceModel {
	
	private Maze maze;
	private MazeSolver solver;
	private Heuristic heuristic;
	
	private boolean solvableRan;
	private boolean solvable;
	
	private final static Position defaultStart = new Position(0, 0);
	private final static Position defaultEnd = new Position(319, 239);

	
	/**
	 * Initialize a Maze object by parsing a file
	 * @param file the File to parse
	 * @return the BufferedImage of the read File
	 */
	public BufferedImage initialize(File file) {
		try {
			maze = new Maze(file, defaultStart, defaultEnd);
		}
		catch (Exception e) {;
			return null;
		}
		solvableRan = false;
		return maze.getImage();
	}
	
	/**
	 * Tests if maze is solvable
	 * @return true if there is a path from start to end
	 * 		   false otherwise
	 */
	public boolean testSolvability() {
		
		if (!solvableRan) {
			solvable =  maze.isSolvable();
			solvableRan = true;
			return isSolvable();
		}
		return solvable;
	}
	
	/**
	 * Should reset solvableRan whenever start or end coordinates are changed.
	 */
	public void resetSolvableRan() {
		solvableRan = false;
	}
	
	public boolean solvableHasRan() {
		return solvableRan;
	}
	
	public boolean isSolvable() {
		return solvable;
	}
	/**
	 * Solves the maze given input from the View
	 * @param heuristic the desired heuristic in String format
	 * @param algorithm the desired algorithm in String format
	 * @return the image of the solution
	 */
	public BufferedImage solve(String heuristic, String algorithm) {
		
		switch(heuristic) {
			case "Manhattan":
				this.heuristic = new ManhattanDistance();
				break;
			case "Diagonal":
				this.heuristic = new DiagonalDistance();
				break;
			case "Euclidean":
				this.heuristic = new EuclideanDistance();
				break;
		}
		switch(algorithm) {
			case "A Star":
				this.solver = new AStarAlgorithm(maze, this.heuristic);
				break;
			case "Dijkstras":
				this.solver = new DijkstrasAlgorithm(maze, this.heuristic);
				break;
		}
		
		if (solver.solveMaze() == null)
		{
			return null;
		}
		
		return solver.outputSolution();
	}
	
	public BufferedImage getSolutionImage() {
		return solver.getImage();
	}
	
	/*	
	 * Setters for Start and End Nodes	
	 * Each one checks if the new coordinate is different
	 * and only then resets solvableRan flag
	 */
	public void updateStartX(int x) {
		Position start = maze.getStartNode().getPosition();
		if ( x == start.getX() ) {
			return;
		}
		solvableRan = false;
		start = new Position( x, start.getY() );
		maze.setStartNode( maze.getNode(start) );
	}
	
	public void updateStartY(int y) {
		Position start = maze.getStartNode().getPosition();
		if ( y == start.getY() ) {
			return;
		}
		solvableRan = false;
		start = new Position( start.getX(), y );
		maze.setStartNode( maze.getNode(start) );
	}
	
	public void updateEndX(int x) {
		Position end = maze.getEndNode().getPosition();
		if ( x == end.getX() ) {
			return;
		}
		solvableRan = false;
		end = new Position( x, end.getY() );
		maze.setEndNode( maze.getNode(end) );
	}
	
	public void updateEndY(int y) {
		Position end = maze.getEndNode().getPosition();
		if ( y == end.getY() ) {
			return;
		}
		solvableRan = false;
		end = new Position( end.getX(), y );
		maze.setEndNode( maze.getNode(end) );
	}
	
	public Maze getMaze() {
		return this.maze;
	}
	
}

package mazelib.data;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * User text-based interface that can be run from terminal.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Tuesday, September 18, 2012, 18:00 PM
 */
public class MazeConsole {
	
	// Constant Variables
	private static final String IMAGE_PATH = "/Users/TiRez/Documents/UIUC/CS 242/Assignment1.2/images/";
	
	// Normal message constants
	private static final String START_UP = "Welcome to Maze Library Interface.\n";
	private static final String INPUT_FILE = "Choose a Maze input file.\n";
	
	// Error message constants
	private static final String CONSOLE_ERROR = "Error: Console unavailable.\n";
	private static final String ILLEGAl_FILENAME = "Couldn't find the file!\n";
	private static final String INVALID_IMAGE = "Invalid image file!\n";
	private static final String INVALID_STARTEND = "Invalid START and/or END positions!\n";

	private static Console console = null;
	private static BufferedImage image = null;
	private static String filename = null;
	private static Maze maze = null;
	private static MazeSolver solver = null;
	
	/**
	 * 
	 * @param args
	 */
	public static void main (String[] args)
	{
		// Try starting the Console
		console = System.console();
		
		if (console != null)
		{
			// Let the user know the program started
			console.printf(START_UP);
			
			// Command Loop
			while (true)
			{
				console.printf("Enter a command.\n");
				String command = console.readLine().toUpperCase();
				
				switch(command)
				{
					case "START":
						parseFile();
						break;
					case "TEST":
						checkIfSolvable(maze);
						break;
						
					case "SOLVE":
						setupAndSolve(maze);
						break;
					case "OUTPUT":
						outputSolution();
						break;
						
					case "QUIT":
						return;
				}
			}		
		}
		else
		{
			throw new RuntimeException(CONSOLE_ERROR);
		}
	}
	
	/**
	 * TEST command.
	 * Checks if a Maze is solvable.
	 * @param maze the Maze to check solvability of
	 */
	private static void checkIfSolvable(Maze maze)
	{	
		if (maze.isSolvable() != false)
		{
			console.printf("Maze is solvable.\n");
		}
		else
		{
			console.printf("Maze is unsolvable!\n");
		}
	}
	
	/**
	 * SOLVE command.
	 * Lets the user set solving options and attempts to solve the maze.
	 * @param maze the Maze to attempt solving
	 */
	private static void setupAndSolve(Maze maze)
	{
		Heuristic heuristic;
		
		while (true)
		{
			console.printf("Choose a Heuristic.\n" +
						   "D(iagonal), M(anhattan) or E(uclidean).\n");
			String distance = console.readLine().toUpperCase();
			
			switch(distance.charAt(0))
			{
				case 'D':
					heuristic = new DiagonalDistance();
					break;
				case 'M':
					heuristic = new ManhattanDistance();
					break;
				case 'E':
					heuristic = new EuclideanDistance();
					break;
					
				default:
					continue;
			}
			break;
		}
		
		while (true)
		{
			console.printf("Choose an Algorithm. A(Star) or D(ijkstras).\n");
			String algorithm = console.readLine().toUpperCase();
			
			switch(algorithm.charAt(0))
			{
				case 'A':
					solver = new AStarAlgorithm(maze, heuristic);
					break;
				case 'D':
					solver = new DijkstrasAlgorithm(maze, heuristic);
					break;
					
				default:
					continue;
			}
			break;
		}
		
		List<Node> result = solver.solveMaze();
		
		if (result != null)
		{
			console.printf("Maze was solved.\n");
			console.printf("Would you like solution output?\n");
			
			String output = console.readLine().toUpperCase();
			
			switch (output.charAt(0))
			{
				case 'Y':
					outputSolution();
					break;
					
				default:
					break;
			}
		}
		else 
		{
			console.printf("Unsolvable maze.\n");
		}
	}
	
	/**
	 * Asks for an input Maze file, then start and end positions.
	 * Checks if everything is valid and parses input to a Maze object.
	 */
	private static void parseFile()
	{
		// Loop until a valid file is given as input
		while (true)
		{	
			console.printf(INPUT_FILE);
			
			String inputString = console.readLine();
			File inputFile = new File(IMAGE_PATH + inputString);
			
			try
			{
				BufferedImage grey = ImageIO.read(inputFile);
				
				image = new BufferedImage(grey.getWidth(), grey.getHeight(), BufferedImage.TYPE_INT_RGB);
				image.getGraphics().drawImage(grey, 0, 0, null);
				
				filename = inputString;
				console.printf("Image dimensions are: %d by %d.\n",
							    image.getWidth(), image.getHeight());
				break;
			}
			catch (Exception e)
			{
				console.printf(ILLEGAl_FILENAME);
			}
		}
		
		// Ask for start and end coordinates
		Position startPosition, endPosition;
		while (true)
		{
			console.printf("Specify START coordinates in x,y format.\n");
			String startString = console.readLine();
			String [] xyString = startString.split(",");
			startPosition = new Position(Integer.parseInt(xyString[0]),
										 Integer.parseInt(xyString[1]));
			
			console.printf("Specify END coordinates in x,y format.\n");
			String endString = console.readLine();
			xyString = endString.split(",");
			endPosition = new Position(Integer.parseInt(xyString[0]),
										 Integer.parseInt(xyString[1]));
			break;
		}
		
		// Parse the image into a Maze with specified start and end
		try
		{
			maze = new Maze(image, startPosition, endPosition);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(INVALID_IMAGE);
		}
		if (maze.getEndNode() == null || maze.getStartNode() == null)
		{
			throw new IllegalArgumentException(INVALID_STARTEND);
		}
		console.printf("Maze has been parsed.\n");
		
	}
	
	/**
	 * OUTPUT command.
	 * Saves and displays the solution image.
	 */
	private static void outputSolution()
	{
		
		File solutionFile = new File(IMAGE_PATH + "soln_" + filename);
		
		BufferedImage solution = solver.outputSolution();
		
		// Write solution image to file
		try 
		{
			ImageIO.write(solution, "bmp", solutionFile);
		} 
		catch (IOException e) 
		{
			console.printf("IO failed!\n");
			return;
		}
		
		// Open the image and display it on Desktop
		try 
		{
			Desktop.getDesktop().open(solutionFile);
		} 
		catch (IOException e) 
		{
			console.printf("File opening failed!\n");
		}
	}
}




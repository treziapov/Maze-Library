package mazelib.data;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Maze GUI Interface. The Controller component of the MVC model.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Saturday, September 22, 2012, 21:00 PM
 */
public class MazeInterfaceControl extends JApplet{

	private MazeInterfaceModel mazeModel;
	private MazeInterfaceView mazeView;
		
	public void initialize() {
		
		// Initialize Interface Components
		mazeModel = new MazeInterfaceModel();
		mazeView = new MazeInterfaceView();
		
		// Set the start state
		mazeView.setNoFileState();
		
		// Set up actions for appropriate listeners
		mazeView.addLoadListener( new ActionListener() {
			
			// Initialize the Maze in Model and display it in View
			public void actionPerformed(ActionEvent e) {
				File file = mazeView.openFile();
				BufferedImage unsolvedImage = mazeModel.initialize(file);
				if (unsolvedImage != null) {
					mazeView.setImage(unsolvedImage);
					mazeView.setFileChosenState();
					mazeView.displayMessage("Loaded a .bmp file!");
				}
				else {
					mazeView.displayMessage("File couldn't be parsed to a Maze!");
				}
			}		
		});
		
		mazeView.addSolvableListener( new ActionListener() {
			
			// Check if the maze is solvable
			public void actionPerformed(ActionEvent e) {
				boolean solvable = mazeModel.testSolvability();
				mazeView.displaySolvable(solvable);
			}
		});
		
		mazeView.addSolveListener( new ActionListener() {
			
			// Solve the maze in Model and display solution in View
			public void actionPerformed(ActionEvent e) {
				if ( mazeModel.solvableHasRan() && !mazeModel.isSolvable() ) {
					mazeView.displayMessage("Maze is unsolvable. Algorithm didn't start!");
					return;
				}
				String h = mazeView.getHeuristic();
				String a = mazeView.getAlgorithm();
				BufferedImage solved = mazeModel.solve(h, a);
				if (solved != null) {
					mazeView.setImage(solved);
					mazeView.setSolvedState();
					mazeView.displayMessage("Solved the maze!");
				}
				else {
					mazeView.displayMessage("Error while solving!");
				}
			}
		});
		
		mazeView.addSaveListener( new ActionListener() {
			
			// Signal the View to let the user save the solution
			public void actionPerformed(ActionEvent e) {
				BufferedImage solved = mazeModel.getSolutionImage();
				mazeView.saveToFile(solved);
				mazeView.displayMessage("Saved the solution!");
			}
		});
		
		mazeView.addStartXListener( new PropertyChangeListener() {

			// Update the startX coordinate in Model
			public void propertyChange(PropertyChangeEvent e) {
				mazeModel.updateStartX(mazeView.getStartX());
			}
		});
		
		mazeView.addStartYListener( new PropertyChangeListener() {

			// Update the startX coordinate in Model
			public void propertyChange(PropertyChangeEvent e) {
				mazeModel.updateStartY(mazeView.getStartY());
			}
		});
		
		mazeView.addEndXListener( new PropertyChangeListener() {

			// Update the startX coordinate in Model
			public void propertyChange(PropertyChangeEvent e) {
				mazeModel.updateEndX(mazeView.getEndX());
			}
		});
		
		mazeView.addEndYListener( new PropertyChangeListener() {

			// Update the startX coordinate in Model
			public void propertyChange(PropertyChangeEvent e) {
				mazeModel.updateEndY(mazeView.getEndY());
			}
		});
	}
	
}

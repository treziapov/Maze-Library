package mazelib.data;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Maze GUI Interface. The View component of the MVC model.
 * @author Timur Reziapov	<reziapo1@illinois.edu>
 * @date Saturday, September 22, 2012, 21:00 PM
 */
public class MazeInterfaceView {

	// Main Components
	private JFrame frame;
	private JPanel guiPanel;
	private JPanel imagePanel;
	private JPanel buttonPanel;
	private JPanel statusPanel;
	private JFileChooser fileChooser;
	
	// Smaller Components
	private JButton loadFileButton;
	private JButton saveFileButton;
	private JButton testSolvableButton;
	private JButton solveButton;
	private JComboBox chooseHeuristicBox;
	private JComboBox chooseAlgorithmBox;
	private JFormattedTextField startXField;
	private JFormattedTextField startYField;
	private JFormattedTextField endXField;
	private JFormattedTextField endYField;
	private JLabel mazeImage = new JLabel("", JLabel.CENTER);
	private JLabel statusLabel = new JLabel("", JLabel.CENTER);
	
	// Combo Boxes' values
	String [] heuristics = { "Manhattan" ,
							 "Diagonal" ,
							 "Euclidean" };
	String [] algorithms = { "A Star", 
							 "Dijkstras" };
	
	// Constructor
	public MazeInterfaceView() {
		
		// Set the native look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) { 
			 return;
		}
		
		// Set up the main frame
		frame = new JFrame("Maze Library GUI");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(650, 500);
	    
	    // Set up the main panel
	    guiPanel = new JPanel( new BorderLayout(5,5) );
	    guiPanel.setBorder( new TitledBorder("Main Panel") );
	    
	    // Set up the panel to display Maze image
	    imagePanel = new JPanel( new FlowLayout() );
	    imagePanel.setBorder( new TitledBorder("Image") );
	    imagePanel.setSize(500,500);
	    imagePanel.setPreferredSize( new Dimension(350,350) );
	    
	    // Set up the panel that will contain all the buttons
	    buttonPanel = new JPanel( new GridLayout(0,2,20,15) );
	    buttonPanel.setBorder( new TitledBorder("Commands"));
	    buttonPanel.setPreferredSize( new Dimension(150,350) );
	    
	    // Set up the panel that will display messages
	    statusPanel = new JPanel();
	    statusPanel.setBorder( new TitledBorder("Status"));
	    statusPanel.setPreferredSize( new Dimension(500, 100) );
	    
	    guiPanel.add(imagePanel, BorderLayout.WEST);
	    guiPanel.add(buttonPanel, BorderLayout.CENTER);
	    guiPanel.add(statusPanel, BorderLayout.SOUTH);
	    
	    // Set up all the small components
		loadFileButton = new JButton("Load");
		saveFileButton = new JButton("Save");
		JLabel startXLabel = new JLabel("Starting X:", JLabel.CENTER);
		JLabel startYLabel = new JLabel("Starting Y:", JLabel.CENTER);
		JLabel endXLabel = new JLabel("Ending X:", JLabel.CENTER);
		JLabel endYLabel = new JLabel("Ending Y:", JLabel.CENTER);
		startXField = new JFormattedTextField();
		startXField.setHorizontalAlignment(JTextField.RIGHT);
		startYField = new JFormattedTextField();
		startYField.setHorizontalAlignment(JTextField.RIGHT);
		endXField = new JFormattedTextField();
		endXField.setHorizontalAlignment(JTextField.RIGHT);
		endYField = new JFormattedTextField();
		endYField.setHorizontalAlignment(JTextField.RIGHT);
		JLabel heuristicLabel = new JLabel("Heuristic:", JLabel.CENTER);
		chooseHeuristicBox = new JComboBox(heuristics);
		chooseHeuristicBox.setSelectedIndex(0);
		JLabel algorithmLabel = new JLabel("Algorithm:", JLabel.CENTER);
		chooseAlgorithmBox = new JComboBox(algorithms);
		chooseAlgorithmBox.setSelectedIndex(0);
		testSolvableButton = new JButton("Solvable?");
		solveButton = new JButton("Solve");
		
		// Group components in the appropriate panels
		buttonPanel.add(loadFileButton);
		buttonPanel.add(saveFileButton);
		buttonPanel.add(startXLabel);
		buttonPanel.add(startXField);
		buttonPanel.add(startYLabel);
		buttonPanel.add(startYField);
		buttonPanel.add(endXLabel);
		buttonPanel.add(endXField);
		buttonPanel.add(endYLabel);
		buttonPanel.add(endYField);
		buttonPanel.add(heuristicLabel);
		buttonPanel.add(chooseHeuristicBox);
		buttonPanel.add(algorithmLabel);
		buttonPanel.add(chooseAlgorithmBox);
		buttonPanel.add(testSolvableButton);
		buttonPanel.add(solveButton);
		
		imagePanel.add(mazeImage);
		
		statusPanel.add(statusLabel);
		
		// Set the contents of the frame and display it
		frame.setContentPane(guiPanel);
	    frame.setVisible(true);
	}

	
	/* 	Listener Adding Methods	*/
	public void addLoadListener(ActionListener a) {
		loadFileButton.addActionListener(a);
	}
	
	public void addSaveListener(ActionListener a) {
		saveFileButton.addActionListener(a);
	}
	
	public void addSolvableListener(ActionListener a) {
		testSolvableButton.addActionListener(a);
	}
	
	public void addSolveListener(ActionListener a) {
		solveButton.addActionListener(a);
	}
	
	public void addStartXListener(PropertyChangeListener p) {
		startXField.addPropertyChangeListener("value", p);
	}
	
	public void addStartYListener(PropertyChangeListener p) {
		startYField.addPropertyChangeListener("value", p);
	}
	
	public void addEndXListener(PropertyChangeListener p) {
		endXField.addPropertyChangeListener("value", p);
	}
	
	public void addEndYListener(PropertyChangeListener p) {
		endYField.addPropertyChangeListener("value", p);
	}
	
	/* 	State Setting methods	*/
	
	/** 
	 * Starting state, use must specify a file.
	 * No other actions are available. 
	 */
	public void setNoFileState() {
		loadFileButton.setEnabled(true);
		saveFileButton.setEnabled(false);
		testSolvableButton.setEnabled(false);
		solveButton.setEnabled(false);
		chooseHeuristicBox.setEnabled(false);
		chooseAlgorithmBox.setEnabled(false);
		startXField.setEnabled(false);
		startYField.setEnabled(false);
		endXField.setEnabled(false);
		endYField.setEnabled(false);
	}
	
	/**
	 * A file has been selected. User is allowed to check if its 
	 * a solvable Maze, or set solving options.
	 */
	public void setFileChosenState() {
		loadFileButton.setEnabled(true);
		saveFileButton.setEnabled(false);
		testSolvableButton.setEnabled(true);
		solveButton.setEnabled(true);
		chooseHeuristicBox.setEnabled(true);
		chooseAlgorithmBox.setEnabled(true);
		startXField.setEnabled(true);
		startYField.setEnabled(true);
		endXField.setEnabled(true);
		endYField.setEnabled(true);
		
		// Display default start and end coordinates
		startXField.setValue(0);
		startYField.setValue(0);
		endXField.setValue(319);
		endYField.setValue(239);
	}
	
	/**
	 * The file has been successfully solved.
	 * User can now select any action and save solution.
	 */
	public void setSolvedState() {
		loadFileButton.setEnabled(true);
		saveFileButton.setEnabled(true);
		testSolvableButton.setEnabled(true);
		solveButton.setEnabled(true);
		chooseHeuristicBox.setEnabled(true);
		chooseAlgorithmBox.setEnabled(true);
		startXField.setEnabled(true);
		startYField.setEnabled(true);
		endXField.setEnabled(true);
		endYField.setEnabled(true);
	}
	
	
	/*	 Controller-called View methods 	*/
	
	/**
	 * This function allows the user to open and choose a maze image file.
	 * @return the File to load
	 */
	public File openFile() {
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP Images", "bmp");
		fileChooser.setFileFilter(filter);
		
		int retval = fileChooser.showOpenDialog(null);
		if (retval == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	/**
	 * This function allows the user to choose where to save the solution image.
	 * @param image the Image to save
	 */
	public void saveToFile(BufferedImage image){
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP Images", "bmp");
		fileChooser.setFileFilter(filter);
		
		int retval = fileChooser.showSaveDialog(null);
		if (retval == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file != null) {
				try {
					// Add the .bmp extension
					File withExt = new File(file.getPath() + ".bmp");
					ImageIO.write(image, "bmp", withExt);
				}
				catch (Exception e) {
					return;
				}
			}
		}
	}
	
	/**
	 * Display the given image in the GUI.
	 * @param image the BufferedImage to view
	 */
	public void setImage(BufferedImage image) {	
		ImageIcon icon = new ImageIcon(image);
		mazeImage.setIcon(icon);
		frame.revalidate();
	}
	
	/**
	 * Display the results of Solvable? command.
	 * @param solvable the solvable flag of the maze
	 */
	public void displaySolvable(boolean solvable) {
		String result = "The maze is ";
		if (solvable) {
			result = result + "SOLVABLE.";
		}
		else {
			result = result + "UNSOLVABLE!";
		}
		statusLabel.setText(result);
		frame.revalidate();
	}
	
	/**
	 * Display a status message.
	 * @param message the String to display in Status panel
	 */
	public void displayMessage(String message) {
		statusLabel.setText(message);
		frame.revalidate();
	}
	
	public String getHeuristic() {
		return (String) chooseHeuristicBox.getSelectedItem();
	}
	
	public String getAlgorithm() {
		return (String) chooseAlgorithmBox.getSelectedItem();
	}
	
	public int getStartX() {
		return ((Number)startXField.getValue()).intValue();
	}
	
	public int getStartY() {
		return ((Number)startYField.getValue()).intValue();
	}
	
	public int getEndX() {
		return ((Number)endXField.getValue()).intValue();
	}
	
	public int getEndY() {
		return ((Number)endYField.getValue()).intValue();
	}
	
}

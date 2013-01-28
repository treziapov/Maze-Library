package mazelib.data;

import javax.swing.*;

public class Glue {

	public Glue() {
		
		MazeInterfaceControl controller = new MazeInterfaceControl();
		controller.initialize();
		
	}
	
	public static void main(String [] args) {
		new Glue();
	}
	
}

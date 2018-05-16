package maze;

import java.awt.*;
import javax.swing.*;

public class Main {
	public static void main (String args[]) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame();
		MazeGUI gui = new MazeGUI(frame);
		
		UnregisteredState unregState = new UnregisteredState();
		unregState.load(gui, null);
		TypeCommand tc = new TypeCommand();
		gui.setStrategy(tc);
		
		frame.setTitle("CS 124 Project");
		frame.getContentPane().setPreferredSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);		
	}
}

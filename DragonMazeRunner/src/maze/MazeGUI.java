package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MazeGUI {
	
	private JFrame frame;
	private JPanel mainPanel, commandPanel;
	private Canvas imageArea;
	private static JTextArea textArea;
	private JTextField txtCommand;
	private JButton btnHelp, btnInventory, btnGo;
	
	public MazeGUI(MazeMaker maze) {
		frame = new JFrame();
		
		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		imageArea = new Canvas();
		imageArea.setPreferredSize(new Dimension(800, 200));
		mainPanel.add(imageArea);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		mainPanel.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		commandPanel = new JPanel();
		frame.getContentPane().add(commandPanel, BorderLayout.SOUTH);
		
		txtCommand = new JTextField();
		txtCommand.setHorizontalAlignment(SwingConstants.LEFT);
		txtCommand.setText("command");
		commandPanel.add(txtCommand);
		txtCommand.setColumns(75);
		
		btnGo = new JButton("Go");
		commandPanel.add(btnGo);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				print(maze.move(txtCommand.getText()));			
			}
		});
		
		btnHelp = new JButton("Help");
		commandPanel.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					print(maze.look());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btnInventory = new JButton("Inventory");
		commandPanel.add(btnInventory);
	}
	
	public static void print(String in) {
		String temp = textArea.getText();
		if (temp.equals("")) textArea.setText(in);
		else textArea.setText(temp + "\n" + in);
	}
	
	public static void main (String args[]) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MazeMaker maze = new MazeMaker();
		MazeGUI gui = new MazeGUI(maze);
		
		print(maze.load());
		gui.frame.setTitle("CS 124 Project");
		gui.frame.getContentPane().setPreferredSize(new Dimension(800, 600));
		gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.frame.pack();
		gui.frame.setLocationRelativeTo(null);
		gui.frame.setResizable(false);
		gui.frame.setVisible(true);		
	}


}

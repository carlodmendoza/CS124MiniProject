package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MazeGUI {
	
	private JFrame frame;
	private JPanel mainPanel, commandPanel;
	public DrawingCanvas imageArea;
	private static JTextArea textArea;
	private JTextField txtCommand;
	private JButton btnHelp, btnInventory, btnGo;
	private static State state;
	private static MazeGUI gui;
	
	public MazeGUI() {
		state = new UnregisteredState();
		frame = new JFrame();

		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		imageArea = new DrawingCanvas();
		imageArea.changeImg("1.png");
		imageArea.setPreferredSize(new Dimension(800, 45));
		mainPanel.add(imageArea);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		mainPanel.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		commandPanel = new JPanel();
		frame.getContentPane().add(commandPanel, BorderLayout.SOUTH);
		
		txtCommand = new JTextField();
		txtCommand.setHorizontalAlignment(SwingConstants.LEFT);
		commandPanel.add(txtCommand);
		txtCommand.setColumns(40);
		
		txtCommand.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				frame.getContentPane().setFocusable(true);
				txtCommand.setText("");
			}
			public void focusLost(FocusEvent e) {
				
			}
		});
		
		txtCommand.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						if (((MazeMaker) state).gameOver) {
							try {
								Thread.sleep(1500);
								System.exit(0);
							} catch (InterruptedException ie) {
								Thread.currentThread().interrupt();
							}
						}
						print(((MazeMaker) state).move(txtCommand.getText()));
						if (((MazeMaker) state).isProxy) imageArea.changeImg(((MazeMaker) state).getMethod(true, "getRoomImg"));
						else imageArea.changeImg(((MazeMaker) state).getMethod(false, "getRoomImg"));
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			}
			public void keyReleased(KeyEvent ke) {
				
			}
			public void keyTyped(KeyEvent ke) {
				
			}
		});
		
		txtCommand.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) txtCommand.setText("");
			}
			public void mouseEntered(MouseEvent e) {
				
			}
			public void mouseExited(MouseEvent e) {
	
			}
			public void mousePressed(MouseEvent e) {

			}
			public void mouseReleased(MouseEvent e) {
				
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
		    public void windowOpened(WindowEvent e){
		        txtCommand.requestFocus();
		    }
		});
		
		btnGo = new JButton("Go");
		commandPanel.add(btnGo);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (((MazeMaker) state).gameOver) {
						try {
							Thread.sleep(1500);
							System.exit(0);
						} catch (InterruptedException ie) {
							Thread.currentThread().interrupt();
						}
					}
					print(((MazeMaker) state).move(txtCommand.getText()));
					if (((MazeMaker) state).isProxy) imageArea.changeImg(((MazeMaker) state).getMethod(true, "getRoomImg"));
					else imageArea.changeImg(((MazeMaker) state).getMethod(false, "getRoomImg"));
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}
		});
				
		btnHelp = new JButton("Help");
		commandPanel.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (((MazeMaker) state).isProxy) {
						if (!(boolean)((MazeMaker) state).getField(true, "isDialogueFinished")) print("You have to finish the conversation first.\n");
						else print(((MazeMaker) state).showCommands());
					}
					else {
						if (!(boolean)((MazeMaker) state).getField(false, "isDialogueFinished")) print("You have to finish the conversation first.\n");
						else print(((MazeMaker) state).showCommands());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btnInventory = new JButton("Court Record");
		commandPanel.add(btnInventory);
		btnInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					print(((MazeMaker) state).showItems());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		
		MazeGUI gui = new MazeGUI();
		state.changeState(gui);
		print(state.load());
		gui.frame.setTitle("CS 124 Project");
		gui.frame.getContentPane().setPreferredSize(new Dimension(800, 600));
		gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.frame.pack();
		gui.frame.setLocationRelativeTo(null);
		gui.frame.setResizable(false);
		gui.frame.setVisible(true);		
	}
	
	public void setState(State state) {
		this.state = state;
	}
}

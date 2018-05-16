package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MazeGUI {
	private JPanel mainPanel, commandPanel;
	private DrawingCanvas imageArea;
	protected JTextArea textArea;
	protected JTextField txtCommand;
	private JButton btnHelp, btnInventory, btnGo;
	private State state;
	private Strategy strategy;
	
	public MazeGUI(JFrame frame) {
		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		imageArea = new DrawingCanvas();
		imageArea.changeImg("0.png");
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
						inputParser();
					} catch (ArrayIndexOutOfBoundsException a) {
						print("Register what?\n");
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
					inputParser();
				} catch (ArrayIndexOutOfBoundsException a) {
					print("Register what?\n");
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
					if (state instanceof UnregisteredState) {
						print("Available commands:\n1. register <user>\n2. quit\n");
					}
					else {
						if (((MazeMaker) state).isProxy) {
							if (!(boolean)((MazeMaker) state).getField(true, "isDialogueFinished")) print("You have to finish the conversation first.\n");
							else print(((MazeMaker) state).showCommands());
						}
						else {
							if (!(boolean)((MazeMaker) state).getField(false, "isDialogueFinished")) print("You have to finish the conversation first.\n");
							else print(((MazeMaker) state).showCommands());
						}
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
				if (state instanceof UnregisteredState) {
					print("Sorry, you need to be registered before you can view the Court Record.\n");
				}
				else {
					try {
						print(((MazeMaker) state).showItems());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public void inputParser() throws Exception {
		if (state instanceof UnregisteredState) {
			if (txtCommand.getText().split(" ")[0].equals("register")) {
				MazeMaker maze = new MazeMaker();
				maze.load(MazeGUI.this, txtCommand.getText().split(" ")[1]);
				if (maze.isProxy) imageArea.changeImg(maze.getMethod(true, "getRoomImg"));
				else imageArea.changeImg(maze.getMethod(false, "getRoomImg"));
			}
			else if (txtCommand.getText().equals("quit")) {
				System.exit(0);
			}
			else {
				print("Invalid command.\nPlease register <name> or quit.\n");
			}
		}
		else {
			if (strategy instanceof TypeCommand) {
				if (((MazeMaker) state).gameOver) {
					try {
						Thread.sleep(1500);
						System.exit(0);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
				}
				String x = (String) strategy.getOperation(txtCommand.getText());
				if (x.equals("run")) {
					ReadFile rf = new ReadFile();
					setStrategy(rf);
					print(strategy.message());
				}
				print(((MazeMaker) state).move(x));
				if (((MazeMaker) state).isProxy) imageArea.changeImg(((MazeMaker) state).getMethod(true, "getRoomImg"));
				else imageArea.changeImg(((MazeMaker) state).getMethod(false, "getRoomImg"));
			}
			else {
				ArrayList<String> sts = (ArrayList<String>) strategy.getOperation("./perfect.txt"); 
				for(String s : sts) { 
					print(((MazeMaker) state).move(s)); 
					if (((MazeMaker) state).isProxy) imageArea.changeImg(((MazeMaker) state).getMethod(true, "getRoomImg")); 
					else imageArea.changeImg(((MazeMaker) state).getMethod(false, "getRoomImg")); 
				}
				TypeCommand tc = new TypeCommand();
				setStrategy(tc);
			}
		}
	}
	
	public void print(String in) {
		String temp = textArea.getText();
		if (temp.equals("")) textArea.setText(in);
		else textArea.setText(temp + "\n" + in);
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
}

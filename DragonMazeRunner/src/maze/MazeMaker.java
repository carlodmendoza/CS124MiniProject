package maze;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import anno.Command;
import anno.Direction;

public class MazeMaker {
	private JFrame frame;
	private JPanel mainPanel, commandPanel;
	private Canvas imageArea;
	private static JTextArea textArea;
	private JTextField txtCommand;
	private JButton btnHelp, btnInventory, btnGo;
	private HashMap<Class, Object> roomMap = new HashMap<Class, Object>();
	private Object currentRoom;
	public boolean inPool, graveFound, tookSword, wordFoundRm2, babyDead, chestFound, wordFoundRm3, wordFoundRm4, isDead;
	
	public MazeMaker() {
		frame = new JFrame();
		
		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		imageArea = new Canvas();
		imageArea.setPreferredSize(new Dimension(800, 200));
		mainPanel.add(imageArea);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
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
		
		btnHelp = new JButton("Help");
		commandPanel.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					look();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btnInventory = new JButton("Inventory");
		commandPanel.add(btnInventory);
		
		frame.setTitle("CS 124 Project");
		frame.getContentPane().setPreferredSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void load() throws Exception {
		FastClasspathScanner scanner = new FastClasspathScanner("room");
		ScanResult result = scanner.scan();
		List<String> allClasses = result.getNamesOfAllStandardClasses();
		for (String className : allClasses) {
			Class clazz = Class.forName(className);
			Object instance = clazz.newInstance();
			if (clazz.isAnnotationPresent(CheckEnter.class)) instance = new MazeIntercept().run(clazz);
			print("Class: " + clazz.getName() + " - Object: " + instance.toString());
			roomMap.put(clazz, instance);
		}
		for (Class roomClazz : roomMap.keySet()) {
			Object currentRoom = roomMap.get(roomClazz);
			for (Field f : roomClazz.getDeclaredFields()) {
				if (f.isAnnotationPresent(Direction.class)) {
					Class fieldClazz = f.getType();
					Object roomInstance = roomMap.get(fieldClazz);
					f.setAccessible(true);
					f.set(currentRoom, roomInstance);
				}
			}
		}
		currentRoom = roomMap.get(room.Room1.class);
		printDescription(false);
	}
	
	public void printDescription(boolean isRoom5) throws Exception {
		Method m;
		if (isRoom5) m = currentRoom.getClass().getSuperclass().getDeclaredMethod("getDescription", MazeMaker.class);
		else m = currentRoom.getClass().getDeclaredMethod("getDescription", MazeMaker.class);
		print(m.invoke(currentRoom, this));
	}
	
	public void move(String action) {
		String[] arr = action.split(" ");
		Class clazz;
		if (EnterCondition.class.isAssignableFrom(currentRoom.getClass())) clazz = currentRoom.getClass().getSuperclass();
		else clazz = currentRoom.getClass();	
		try {
			Field[] fields = clazz.getDeclaredFields();
			Method[] methods = clazz.getDeclaredMethods();
			for (Field f : fields) {
				if (f.isAnnotationPresent(Direction.class)) {
					Direction d = f.getAnnotation(Direction.class);
					if (d.command().equals(action)) {
						Class fieldClass = f.getType();
						Object o  = roomMap.get(fieldClass);
						if (o instanceof EnterCondition) {
							if(((EnterCondition) o).canEnter(this)) {
								print(((EnterCondition) o).enterMessage());
								currentRoom = o;
								printDescription(true);
							}
							else {
								print(((EnterCondition) o).unableToEnterMessage());
								printDescription(false);
							}
						}
						else {
							currentRoom = o;
							printDescription(false);
						}
						break;
					}
				}
			}
			for (Method m : methods) {
				if (m.isAnnotationPresent(Command.class)) {
					Command c = m.getAnnotation(Command.class);	
					if (m.getParameterCount() == 1) {
						if (c.command().equals(action)) {
							print(m.invoke(currentRoom, this));
							break;
						}	
					}
					else {
						if (c.command().equals(arr[0])) {
							try {
								print(m.invoke(currentRoom, this, arr[1]));
							}
							catch (IndexOutOfBoundsException e) {
								print("Retype the method and put your answer after it, separated by a space.");
							}
							break;
						}
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void look() throws Exception {
		Class clazz = currentRoom.getClass();
		if (currentRoom instanceof EnterCondition) clazz = currentRoom.getClass().getSuperclass();
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("What would Jesus do?");
		for (Field f: clazz.getDeclaredFields()) {
		   Direction anno = f.getAnnotation(Direction.class);
		   if (anno != null) pw.println("- " + anno.toString().substring(24, anno.toString().length() - 1));
		}
		for (Method m: clazz.getDeclaredMethods()) {
		   Command anno = m.getAnnotation(Command.class);
		   if (anno != null) pw.println("- " + anno.toString().substring(22, anno.toString().length() - 1));
		}
		print(sw.toString());
	}
	
	public static void print(Object in) {
		String temp = textArea.getText();
		if (temp.equals("")) textArea.setText((String)in);
		else textArea.setText(temp + "\n" + (String)in);
	}
	
	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			print("Error setting a native look and feel.");
		}
		
		MazeMaker maze = new MazeMaker();
		maze.load();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			if (maze.isDead) break;
			String text = scanner.nextLine();
			if (text.equals("exit")) break;
			else if (text.equals("look")) maze.look();
			else maze.move(text);
		}		
	}
}
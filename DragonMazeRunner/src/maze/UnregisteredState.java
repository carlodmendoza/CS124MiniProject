package maze;

import java.io.*;
import java.util.*;

public class UnregisteredState implements State {
	
	@Override
	public void load(MazeGUI gui, String user) throws Exception {
		int count = 0;
		gui.setState(this);
		gui.print("Welcome! Please register.\n");
		File checkfile = new File("users.txt");
		if (checkfile.exists()) {
			try {
				FileInputStream file = new FileInputStream("users.txt");
				ObjectInputStream in = new ObjectInputStream(file);
				ArrayList<String> users = (ArrayList<String>)in.readObject();;
				gui.print("Registered Users:");
				for (String u : users) {
					gui.print(++count + ". " + u);
				}
				gui.print("");
				in.close();
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
		else gui.print("There are no registered users.\n");
	}
}
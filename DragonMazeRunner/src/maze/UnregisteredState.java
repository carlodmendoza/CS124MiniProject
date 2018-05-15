package maze;

import java.io.*;
import java.util.*;

public class UnregisteredState implements State {
	
	@Override
	public void load(MazeGUI gui, String user) throws Exception {
		int count = 0;
		gui.setState(this);
		gui.print("Welcome! Please register.\n");
		try {
			FileInputStream file = new FileInputStream("sessions.txt");
			ObjectInputStream in = new ObjectInputStream(file);
			HashMap<String, Memento> sessions = (HashMap<String, Memento>) in.readObject();
			Set<String> userKeys = (Set<String>) sessions.keySet();
			ArrayList<String> users = new ArrayList<String>(userKeys);
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
}
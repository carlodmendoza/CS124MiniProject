package maze;

import java.io.*;
public class MazeInterceptor {
	
	public static boolean canEnter(MazeMaker maze) {
		if ((maze.items.containsKey("screwdriver") && maze.items.containsKey("bloodstainedBlouse")) || maze.items.containsKey("evidenceKey")) return true;
		return false;
	}
	
	public static String enterMessage(String room) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	if (room.equals("Room8")) {
    		pw.println("Welcome to the Evidence Room!\n");
		}
		else if (room.equals("Room10")) {
			pw.println("Welcome to the District Court!\n");
		}
		return sw.toString();
	}
	
	public static String unableToEnterMessage() {
		return "You cannot enter this room yet.\n";
	}

}

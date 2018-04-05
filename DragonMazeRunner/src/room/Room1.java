package room;

import anno.Direction;
import anno.Command;
import maze.MazeMaker;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.*;

public class Room1 {
	@Direction(command="go north")
	private Room2 room2;
	@Direction(command="test")
	private Room6 room6;
	private int count = 0;
	
	public String getDescription(MazeMaker maze) {	
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	count++;
		pw.println("You are in Room 1 - "+count+" times.");
		pw.println("Click 'Help' to display all the commands available at this room.");
        pw.println("\nYou find yourself inside a dark room. At your north, you see a door marked Room 2.");
        if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) pw.println("\nYou may now access secret Room 5.");
        return sw.toString();
	}
	
	@Command(command="cheat")
	public String cheat(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	maze.wordFoundRm2 = true;
    	maze.wordFoundRm3 = true;
    	maze.wordFoundRm4 = true;
        pw.println("All 3 words found. You may now access secret Room 5.");
        return sw.toString();
    }
}
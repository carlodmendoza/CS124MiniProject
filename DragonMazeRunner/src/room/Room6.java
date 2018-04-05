package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import anno.Command;
import anno.Direction;
import maze.CheckEnter;
import maze.MazeMaker;

@CheckEnter
public class Room6 {
	@Direction(command="go back")
	private Room1 room1;
	private int count = 0;

	public String getDescription(MazeMaker maze) {	
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	count++;
    	pw.println("You are in Test Room - "+count+" times.");
		pw.println("Click 'Help' to display all the commands available at this room.");
        return sw.toString();
	}
	
}

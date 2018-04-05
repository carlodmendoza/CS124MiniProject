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

	public String getDescription(MazeMaker maze) {	
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
		pw.println("Type 'look' to display all the commands allowed at this room.");
        return sw.toString();
	}
	
	public String look(Class clazz) throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
		for (Field f: clazz.getDeclaredFields()) {
		   Direction anno = f.getAnnotation(Direction.class);
		   if (anno != null) pw.println("- " + anno.toString().substring(24, anno.toString().length() - 1));
		}
		return sw.toString();
	}
}

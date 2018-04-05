package room;

import anno.Direction;
import anno.Command;
import maze.MazeMaker;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Room2 {
	@Direction(command="go south")
	private Room1 room1;
	@Direction(command="go east")
	private Room3 room3;
	private int count = 0;
	
	public String getDescription(MazeMaker maze) {	
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	count++;
		pw.println("\nYou are in Room 2 - "+count+" times.");
		pw.println("Type 'look' to display all the commands allowed at this room.");
		pw.println("\nThe door leads down to some steps into an underground cave system. There is a deep pool in the middle of the cave.");
		if (!maze.tookSword) pw.println("You see something shiny at the bottom of the pool.");
		if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) pw.println("\nYou may now access secret Room 5.");
        return sw.toString();
	}
	
	public String look(Class clazz) throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
		for (Field f: clazz.getDeclaredFields()) {
		   Direction anno = f.getAnnotation(Direction.class);
		   if (anno != null) pw.println("- " + anno.toString().substring(24, anno.toString().length() - 1));
		}
		for (Method m: clazz.getDeclaredMethods()) {
		   Command anno = m.getAnnotation(Command.class);
		   if (anno != null) pw.println("- " + anno.toString().substring(22, anno.toString().length() - 1));
		}
		return sw.toString();
	}
	
	@Command(command="swim")
	public String swim(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	maze.inPool = true;
    	if (!maze.tookSword) pw.println("You find a shiny sword at the bottom.");
    	else pw.println("You see nothing else of interest.");
        return sw.toString();
    }
	
	@Command(command="take sword")
	public String takeSword(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	if (!maze.tookSword) {
	        if (maze.inPool) {
	            maze.tookSword = true;
	            pw.println("You take the bright shiny sword."); 
	        }
	        else pw.println("What sword?");
    	} else pw.println("You already took the sword.");
        return sw.toString();
    }
	
	@Command(command="look around")
	public String lookAround(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw); 
    	maze.inPool = false;
        if (!maze.graveFound) {
            maze.graveFound = true;
            pw.println("You find a pile rubble. It looks like a shallow grave.");
        } else {
            pw.println("You see nothing else of interest.");
        }
        return sw.toString();
    }
	
	@Command(command="dig")
	public String dig(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
        if (!maze.graveFound) {
            pw.println("You dig into the ground and disturb the home of a poisonous snake. It bites and you die... The End.");
            maze.isDead = true;
        } else {
        	if (!maze.wordFoundRm2) {
	            maze.wordFoundRm2 = true;
	            pw.println("You dig up the grave and find a skeleton holding a scroll. It contains 3 words but 2 are unreadable. The remaining word says 'Zam'.");
	            if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) pw.println("\nYou may now access secret Room 5.");
        	} else pw.println("You already dug up the grave.");
        }
        return sw.toString();
    }
}
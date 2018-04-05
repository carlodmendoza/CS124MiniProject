package room;

import anno.Direction;
import anno.Command;
import maze.MazeMaker;
import java.util.Random;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Room4 {
	@Direction(command="go north")
	private Room3 room3;
	private int count = 0;
	Random rand = new Random();
	private int x = rand.nextInt(999)+ 100;
	private int y = rand.nextInt(999) + 100;

	public String getDescription(MazeMaker maze) {	
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	count++;
    	pw.println("You are in Room 4 - "+count+" times.");
		pw.println("Click 'Help' to display all the commands available at this room.");
		pw.println("\nYou find yourself in an empty circular room. On the wall opposite, you see '"+ x + " * " + y + " = _'.");
		if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) pw.println("\nYou may now access secret Room 5.");
        return sw.toString();
	}
	
	@Command(command="answer")
	public String answer(MazeMaker maze, String ans) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	if (!maze.wordFoundRm4) {
    		try {
    			Integer finalAns = Integer.parseInt(ans);
    			if (finalAns==x*y) {
    	            maze.wordFoundRm4 = true;
    	            pw.println("A low voice reverberates the word 'Ka' and fades away.");
    	            if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) pw.println("\nYou may now access secret Room 5.");
    	        }
    			else {
    	            pw.println("The door closes behind you and you are trapped here forever to contemplate the value of " + x + " * " + y + "... The End.");
    	            maze.isDead = true;
    	        }
    		}
    		catch (Exception e) {
    			pw.println("Please enter an integer.");
    		}
    	} else pw.println("You already answered the problem on the wall.");
        return sw.toString();
    }
	
	@Command(command="look around")
	public String lookAround(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
        pw.println("You see nothing else of interest.");
        return sw.toString();
    }
}
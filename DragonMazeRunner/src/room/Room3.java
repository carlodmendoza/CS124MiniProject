package room;

import anno.Direction;
import anno.Command;
import maze.MazeMaker;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Room3 {
	@Direction(command="go west")
	private Room2 room2;
	@Direction(command="go south")
	private Room4 room4;
	@Direction(command="go north")
	private Room5 room5;
	private int count = 0;

	public String getDescription(MazeMaker maze) {	
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	count++;
    	pw.println("You are in Room 3 - "+count+" times.");
		pw.println("Click 'Help' to display all the commands available at this room.");
		if (!maze.babyDead) {
			pw.println("\nYou enter a large cavern and hear deep laboured breathing.");
	        pw.println("In the center of the chamber is a small baby dragon sleeping on a big pile of gold coins.");
		} else pw.println("\nYou enter a large cavern and see the dead baby dragon.");	
        if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) pw.println("\nYou may now access secret Room 5 at your north.");
        return sw.toString();
	}
	
	@Command(command="attack")
	public String attack(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	if (!maze.babyDead) {
	    	if (maze.tookSword) {
	            maze.babyDead = true;
	            pw.println("You charge the baby dragon with your bright shiny sword. You cleave its head clean off.");
	    	} else {
	            pw.println("You charge the baby dragon and try to take in on with your bare hands. It wakes and bites your head clean off... The End.");
	            maze.isDead = true;
	        }
    	} else pw.println("You already killed the baby dragon.");
        return sw.toString();
    }
	
	@Command(command="look around")
	public String lookAround(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	if (!maze.chestFound) {
            maze.chestFound = true;
            if (!maze.babyDead) pw.println("You quietly avoid the baby dragon and make your way to the other side of the chamber and find a chest.");
            else pw.println("You make your way to the other side of the chamber and find a chest.");            
        } else {
            if (!maze.babyDead) pw.println("Other than the sleeping baby dragon, there is nothing of interest.");
            else pw.println("There is nothing of interest.");                
        }
        return sw.toString();
    }
	
	@Command(command="open chest")
	public String openChest(MazeMaker maze) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	if (maze.chestFound) {
    		if (!maze.wordFoundRm3) {
	            maze.wordFoundRm3 = true;
	            maze.items.add("secret word 2");
	            pw.println("Inside is a book. A page is ear-marked and the word 'Ala' written in blood.");
	            if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) pw.println("\nYou may now access secret Room 5 at your north.");
    		} else pw.println("You already opened the chest.");
        } else pw.println("What chest?");
        return sw.toString();
    }
}
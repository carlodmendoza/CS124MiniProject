package room;

import anno.Direction;
import anno.Command;
import maze.CheckEnter;
import maze.MazeMaker;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@CheckEnter
public class Room5 {
	
	@Direction(command="go south")
	private Room3 south;
	private int count = 0;
	
	public String getDescription(MazeMaker maze) {	
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	count++;
    	pw.println("You are in Room 5 - "+count+" times.");
		pw.println("Click 'Help' to display all the commands available at this room.");
		if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) {
            pw.println("\nYou enter a long tunnel which opens into a large chamber.");  
            pw.println("You can see an opening to the outside on the other side.");
            pw.println("As you walk towards it, a large dragon head peers from the opening.");
            pw.println("'What is the passphrase?' it asks.");
        } else {
            pw.println("\nYou are not allowed in this room. A ball of fire turns you to ash...");
            maze.isDead = true;
        }
        return sw.toString();
	}
	
	@Command(command="passphrase") 
	public String passhprase(MazeMaker maze, String word) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
        if ((word.equalsIgnoreCase("AlaKaZam"))) {
            if (maze.babyDead) {
                pw.println("That is correct. The dragon breathes fire into the chamber turning you to ash for killing her baby... The End.");
                maze.isDead = true;
            } else {
                pw.println("That is correct. The dragon allows you to pass and you escape... Congratulations on finishing the maze!");
                maze.isDead = true;
            }
        } else {
            pw.println("That is incorrect. The dragon breathes fire into the chamber turning you to ash... The End.");
            maze.isDead = true;
        }
        return sw.toString();
	}
	
	@Command(command="attack")
	public String attack(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
        if (maze.tookSword) {
            pw.println("You charge to attack the dragon brandishing your sword. The dragon breathes fire into the chamber turning you to ash... The End.");
            maze.isDead = true;
        } else pw.println("In a flash of wisdom, you resist. Only a fool would attack such a creature with his bare hands.");
        return sw.toString();
	}
	
	@Command(command="look around")
	public String lookAround(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
        pw.println("There is no way around the dragon.");
        return sw.toString();
	}
}
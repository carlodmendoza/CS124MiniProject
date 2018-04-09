package room;

import anno.Direction;
import anno.Command;
import maze.MazeGUI;
import maze.MazeMaker;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Room2 {
	@Direction(command="goto grossbergOffice")
	private Room2 room2;
	private Scanner sc;
	private String dialog;
	
	public Room2() {
		dialog = "August 3, 09:00 AM - Wright & Co. Law Offices\n"
				+ "Phoenix: (My name is Phoenix Wright. Here’s the story: My first case is a somewhat complicated one. A young bellboy over at the Gatewater Hotel was killed "
				+ "in the very hotel he loyally served. There are way too many suspects, and one of them is an unlucky sap who just happened to be in the wrong place at the"
				+ " wrong time: my best friend since grade school, Larry Butz. It’s been true for that “When something smells, it’s usually the Butz,” but I know better than "
				+ "anyone that he would never commit murder.)\n"
        		+ "The victim was murdered in the police station parking lot. His cause of death was multiple stab wounds to the chest.\n" 
				+ "Anyway… The trial’s tomorrow, I’m still lacking evidence, and Chief’s away on a business trip. Guess I’ll be gathering evidence alone, huh…\n" 
        		+ "Maybe I can go to Chief’s friend, Mr. Grossberg, for advice.\n";
		sc = new Scanner(dialog);
	}
	
	public String getDescription(MazeMaker maze) {	
		if (!sc.hasNext()) {
			return " -- End of conversation --";
		}
		return sc.nextLine();
	}
	
	public String getRoomImg(MazeMaker maze) {
		return "2.png";
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
	            maze.items.add("secret word 1");
	            pw.println("You dig up the grave and find a skeleton holding a scroll. It contains 3 words but 2 are unreadable. The remaining word says 'Zam'.");
	            if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) pw.println("\nYou may now access secret Room 5.");
        	} else pw.println("You already dug up the grave.");
        }
        return sw.toString();
    }
}
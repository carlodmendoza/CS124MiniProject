package room;

import maze.MazeMaker;
import anno.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class Room3 {
	@Direction(command="grossbergOffice")
	private Room2 room2;
	@Direction(command="parkingGarage")
	private Room4 room4;
	private Scanner sc;
	private String dialogue;
	public boolean isDialogueFinished, hasTaken;
	
	public Room3() {
		hasTaken = false;
		dialogue = "Phoenix: (Hmmm... doesn\'t seem like a busy day in the homicide division. Guess Detective Gumshoe isn\'t here.)\n"
				+ "Policeman: Oh, you in blue. Are you Mr. Wright? Gumshoe left you something.\n";
		sc = new Scanner(dialogue);
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 09:25 AM - Police Station");
    	if (!isDialogueFinished) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public String getDialogue() {	
		if (isDialogueFinished)
			return "Please enter a valid command.";
		
		if (!sc.hasNext()) {
			isDialogueFinished = true;
			return "-- End of text --\n\nNow enter a valid command.";
		}
	
		return sc.nextLine();
	}
	
	public String getRoomImg() {
		return "3.png";
	}
	
	@Command(command="take luminol")
	public String takeLuminol(MazeMaker maze) {
		isDialogueFinished = false;
		maze.items.put("luminol", "Spray solution that reveals trace amounts of blood and turns them into a luminous blue."); 
    	dialogue = "Phoenix: Oh, thanks. (Let me read the note...)\n"
    			+ "Gumshoe\'s note: Hey, pal! You better not get in the way of the official police investigation! Just between you and me, pal, here\'s a Luminol kit. "
    			+ "That forensics kid wanted you to take it. Anyway, thank me later! Just make sure not to goto parkingGarage.\n"
    			+ "Phoenix: The back of the note seems to be a scribble of a ghost. Oh, Gumshoe...\n"
    			+ "Looks like I can now find dried-out blood stains with this. I should probably check out that parking garage that the good detective didn\'t want us to go to. "
    			+ "I think that\'s where the murder took place?\n"
    			+ "To think that a murder occured in the police station\'s parking lot...";
    	if (!hasTaken) {
    		sc = new Scanner(dialogue);
    		hasTaken = true;
    	}
    	
    	return getDialogue();
    }
}
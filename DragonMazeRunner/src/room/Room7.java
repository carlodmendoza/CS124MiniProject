package room;

import maze.MazeMaker;
import anno.*;
import java.io.*;

public class Room7 {
	@Direction(command="forestLake")
	private Room6 room6;
	@Direction(command="evidenceRoom")
	private Room8 room8;

	private String[] dialogue;
	private int dialogueCount;
	public boolean isDialogueFinished;
	public String[] items = new String[1];
	
	public Room7() {
		dialogue = new String[2];
		dialogueCount = 0;
		isDialogueFinished = false;
		items[0] = "evidenceKey";
		dialogue[0] = "Phoenix: Oh, looks like Edgeworth isn\'t here. I wanted to see him today. I guess I can check back later--wait, I wonder what\'s that on his desk?";
		dialogue[1] = "It looks like there\'s some sort of key on his desk. It\'s labelled \"Evidence Room.\"";
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 10:45 AM - High Prosecutor Office");
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		return "7.png";
	}
	
	@Command(command="take")
	public String take(MazeMaker maze, String item) {
		if (item.equals("evidenceKey")) {
			if(maze.findItem("evidenceKey")) return "Phoenix: (I already took the key...)";
			else {
				maze.items.put("evidenceKey", "Key to the police station evidence room.");
				return "Phoenix: Sorry, Edgeworth. Looks like I\'ll be borrowing this.";
			}
		}
		else return "Phoenix: (What " + item +"?)";
	}
	
	@Command(command="use")
	public String use(MazeMaker maze, String item) {
		return "Phoenix: (Mia's words echoed... 'Now is not the time to use that, Phoenix!')";
	}
}

package room;

import maze.MazeMaker;
import anno.*;
import java.io.*;

public class Room2 {
	@Direction(command="wrightOffice")
	private Room1 room1;
	@Direction(command="policeStation")
	private Room3 room3;
	private String[] dialogue;
	private int dialogueCount;
	public boolean isDialogueFinished;
	
	public Room2() {
		dialogue = new String[7];
		dialogueCount = 0;
		isDialogueFinished = false;
		dialogue[0] = "Phoenix: This office never ceases to feel so gaudy. Perhaps I should talk to Grossberg first.";
		dialogue[1] = "Grossberg: *Ah-HHHHEM!*";
		dialogue[2] = "Phoenix: Mr. Grossberg! I\'m glad to see you. I need some help finding evidence.";
		dialogue[3] = "Grossberg: Wright, my boy. You don\'t need a veteran like me to tell you that you have to start looking around in the field and not in other people\'s "
					+ "offices. No offense.";
		dialogue[4] = "Well, this does remind me of myself when I was a youth! \"Ah... the days of my youth... like the scent of fresh lemon...\" My boy, I suggest you start by"
					+ " making your way to the police station to get some useful advice.";
		dialogue[5] = "Phoenix: Thanks, sir. I\'ll get back to you if I get more clues.";
		dialogue[6] = "Grossberg: *groan* (My hemorrhoids are beginning to act up...) Hmph, make haste, my boy!";
	}
	
	public String getDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 09:04 AM - Grossberg Law Offices");
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	return sw.toString();
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length || dialogueCount == 1) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nNow enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		if (dialogueCount >= 2)
			return "2-grossberg.png";
		else
			return "2.png";
	}
	
	@Command(command="take")
	public String take(MazeMaker maze, String item) {
		return "Phoenix: (What " + item +"?)";
	}
	
	@Command(command="use")
	public String use(MazeMaker maze, String item) {
		return "Phoenix: (Mia's words echoed... 'Now is not the time to use that, Phoenix!')";
	}
	
	@Command(command="talkto grossberg")
	public String talk(MazeMaker maze) { 
		if(dialogueCount >= dialogue.length) {
			maze.talkedToGrossberg = true;
			return getDialogue();
		}
    	return dialogue[dialogueCount++];
    }
}
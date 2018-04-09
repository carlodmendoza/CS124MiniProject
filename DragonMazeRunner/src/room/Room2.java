package room;

import maze.MazeMaker;
import anno.*;
import java.io.*;
import java.util.Scanner;

public class Room2 {
	@Direction(command="wrightOffice")
	private Room1 room1;
	@Direction(command="policeStation")
	private Room3 room3;
	private Scanner sc;
	private String dialogue;
	private boolean hasTalked;
	public boolean isDialogueFinished;
	
	public Room2() {
		hasTalked = false;
		isDialogueFinished = false;
		dialogue = "Phoenix: This office never ceases to feel so gaudy. Perhaps I should talk to grossberg first.";
		sc = new Scanner(dialogue);
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 09:04 AM - Grossberg Law Offices");
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
		if (!hasTalked)
			return "2.png";
		else
			return "2-grossberg.png";
	}
	
	@Command(command="talkto grossberg")
	public String talk(MazeMaker maze) {
		isDialogueFinished = false;
    	dialogue = "Grossberg: *Ah-HHHHEM!*\n" 
				+ "Phoenix: Mr. Grossberg! I’m glad to see you. I need some help finding evidence.\n" 
				+ "Grossberg: Wright, my boy. You don’t need a veteran like me to tell you that you have to start looking around in the field and not in other people’s offices. " 
				+ "No offense.\n" 
				+ "Well, this does remind me of myself when I was a youth! \"Ah... the days of my youth... like the scent of fresh lemon...\" My boy, I suggest you start by making " 
				+ "your way to the police station to get some useful advice.\n" 
				+ "Phoenix: Thanks, sir. I’ll get back to you if I get more clues.\n" 
				+ "Grossberg: *groan* (My hemorrhoids are beginning to act up…) Hmph, make haste, my boy!\n";
    	if (!hasTalked) {
    		sc = new Scanner(dialogue);
    		hasTalked = true;
    	}
    	
    	return getDialogue();
    }
}
package room;

import maze.MazeMaker;
import anno.*;
import java.io.*;

public class Room6 {
	@Direction(command="sideRoom")
	private Room5 room5;
	@Direction(command="edgeworthOffice")
	private Room7 room7;

	private String[] dialogue;
	private int dialogueCount;
	public boolean isDialogueFinished;
	
	public Room6() {
		dialogue = new String[5];
		dialogueCount = 0;
		isDialogueFinished = false;
		dialogue[0] = "Lotta: Well if it isn\'t that blue hedgehog! What in tarnation are ya doin\' in the middle of nowhere? And what\'s that bag ya got? Hmmm?";
		dialogue[1] = "Phoenix: Hi, Lotta...Hart. Look, I didn\'t steal your camera, I was just told to give it to you.";
		dialogue[2] = "Lotta: Give it to me!! My baby...";
		dialogue[3] = "Lotta: Thanks y\'all. I was just gonna get ready to go over to that red guy\'s office with my camera--wait! Forget I said that! So long, gotta get back to the heart of the heartland!!";
		dialogue[4] = "Phoenix: ...she ran away fast. She mentioned something about, I guess, Edgeworth's Office? Would that be my next lead?";
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 10:20 AM - Gourd Lake Forest");
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length || dialogueCount == 3) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		if (dialogueCount >= 5) return "6.png";
		if (dialogueCount >= 4) return "6-lottaok.png";
		if (dialogueCount >= 0) return "6-lottamad.png";
		else return "6.png";
	}
	
	@Command(command="give camera")
	public String giveCamera(MazeMaker maze) {
		if(!maze.findItem("camera")) return "Phoenix: I don't even have your camera...";
		maze.items.remove("camera");
		maze.gaveCamera = true;
		if(dialogueCount >= dialogue.length) {
			if(!maze.findItem("camera")) return "Phoenix: (I already gave her the camera...)";
			return getDialogue();
		}
    	return dialogue[dialogueCount++];
    }
}
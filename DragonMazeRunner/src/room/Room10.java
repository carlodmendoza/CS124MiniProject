package room;

import maze.CheckEnter;
import maze.MazeMaker;
import java.io.*;
import anno.*;

@CheckEnter
public class Room10 {
	@Direction(command="gatewaterHotel")
	private Room9 room9;
	private String[] dialogue;
	private int dialogueCount;
	public boolean isDialogueFinished;
	private int lives;
	private boolean presentedFirstEvidence;
	public String[] items = new String[7];
	
	public Room10() {
		dialogue = new String[3];
		dialogueCount = 0;
		isDialogueFinished = false;
		lives = 3;
		presentedFirstEvidence = false;
		items[0] = "luminol";
		items[1] = "bloodstainedPhoto";
		items[2] = "button";
		items[3] = "camera";
		items[4] = "bloodyButton";
		items[5] = "bloodstainedBlouse";
		items[6] = "screwdriver";
		dialogue[0] = "Judge: The court is now in session for the trial of Mr. Larry Butz.";
		dialogue[1] = "Payne: The prosecution is ready, Your Honor.";
		dialogue[2] = "Phoenix: (I should probably present some evidence, fast!)";
	}
	
	public String getDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 4, 10:00 AM - District Court -- Courtroom No. 3");
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease present some evidence.";
		else return dialogue[dialogueCount++];
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		return "10.png";
	}
	
	@Command(command="present")
	public String presentEvidence(MazeMaker maze, String item) {
		if (item.equals("bloodstainedBlouse")) {
			if (!presentedFirstEvidence) {
				presentedFirstEvidence = true;
				return "Phoenix: OBJECTION!\nI present this bloodstained blouse belonging to the witness, April May. I found a bloodstained button in the crime scene and it matches this blouse.\n\nJudge: The cross-examination hasn't even started, but I accept that. Can you present another piece of incriminating evidence?";
			}
			else { 
				maze.gameOver = true;
				return "Judge: Sorry, Mr. Wright, but you have made a mockery of the court. \nI now pronounce the defendant, Larry Butz... GUILTY\n\nGAME OVER -- press enter to exit...";
			}
		}
		else if (item.equals("screwdriver")) {
			if (!presentedFirstEvidence) {
				lives--;
				if (lives < 0) {
					maze.gameOver = true;
					return "GAME OVER -- press enter to exit...";
				}
				return "Phoenix: (This isn't right! Think, what's the right evidence!? I only have " + lives + " chance(s) left!)";
			}
			else {
				maze.gameOver = true;
				return "Phoenix: Your Honor. In the witness's hotel room, I found a conspicuous screwdriver in a drawer. It looks clean, but based on the autopsy report the victim died due to multiple stab wounds. Ergo! This screwdriver was the very same murder weapon!\n\nJudge: ...Well then. I still don\'t understand what happened. But considering all the evidence presented I can now provide a verdict.\nI now pronounce the defendant, Larry Butz...\n\nNOT GUILTY\n\nYOU WON THE CASE! -- press enter to exit...";
			}
		}
		else {
			if (!presentedFirstEvidence) {
				lives--;
				if (lives < 0) {
					maze.gameOver = true;
					return "GAME OVER -- press enter to exit...";
				}
				return "Phoenix: (This isn't right! Think, what's the right evidence!? I only have " + lives + " chance(s) left!)";
			}
			else return "Judge: Sorry, Mr. Wright, but you have made a mockery of the court. \nI now pronounce the defendant, Larry Butz...\n\nGUILTY";
		}
	}
}

package room;

import maze.MazeMaker;
import anno.*;
import java.io.*;

public class Room4 {
	@Direction(command="policeStation")
	private Room3 room3;
	@Direction(command="sideRoom")
	private Room5 room5;
	private String[] dialogue;
	private int dialogueCount;
	private boolean usedLuminol;
	public boolean isDialogueFinished;
	
	public Room4() {
		dialogue = new String[2];
		dialogueCount = 0;
		isDialogueFinished = false;
		dialogue[0] = "Phoenix: Okay, so I\'m here. A man was murdered here, his body haphazardly stuffed into an abandoned car. *shivers*";
		dialogue[1] = "I guess I should take look around.";
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 09:32 PM - Police Station -- Parking Garage");
    	if (!isDialogueFinished) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length) {
			isDialogueFinished = true;
			return "-- End of text --\n\nPlease enter a valid command.";
		}
		return dialogue[dialogueCount++];
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		return "4.png";
	}
	
	@Command(command="check car")
	public String checkCar(MazeMaker maze) {
		if (maze.checkedCar) return "Phoenix: (I already checked the car...)";
		else {
			maze.checkedCar = true;
			return "Phoenix: This red-hot car was where the Bellboy\'s body was discovered. The car has been abandoned for quite a while here, so the owner\'s apparently "
				+ "not involved in this case. The trunk has been forced open. It might be worth checking this area for bloodstains.";
		}
    }
	
	@Command(command="use luminol")
	public String useLuminol(MazeMaker maze) {
    	if (!maze.checkedCar) return "Phoenix: (What am I supposed to spray luminol at?)";
    	else {
    		if (!usedLuminol) {
    			usedLuminol = true;
    			maze.items.put("bloodstainedPhoto","Photo of the luminol reaction found in the Parking Area. Shows a suspicious heart-shaped gap in the blood stain.");
    			return "Phoenix: Just as I thought... the entire area\'s reacted blue. There\'s pretty much blood everywhere except--huh!? Why is there a "
    				+ "gap in the blood pool? It seems to be heart-shaped. I should better take note of that.";
    		}
    		else return "Phoenix: (I've already sprayed this area. I don't wanna waste my luminol.)";
    	}
    }	
}

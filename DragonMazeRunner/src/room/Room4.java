package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import anno.Command;
import anno.Direction;
import maze.MazeMaker;

public class Room4 {
	@Direction(command="policeStation")
	private Room3 room3;
	@Direction(command="parkingGarage")
	private Room4 room4;
	private Scanner sc;
	private String dialogue;
	public boolean isDialogueFinished;
	
	public Room4() {
		dialogue = "Phoenix: Okay, so I\'m here. A man was murdered here, his body haphazardly stuffed into an abandoned car. *shivers*\n"
				+ "I guess I should take look around. Maybe this luminol bottle would come in handy.\n";
		sc = new Scanner(dialogue);
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 09:32 PM - Police Station -- Parking Garage");
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
		return "4.png";
	}
}

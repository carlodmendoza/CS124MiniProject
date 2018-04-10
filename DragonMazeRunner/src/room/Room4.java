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
	@Direction(command="sideRoom")
	private Room5 room5;
	private Scanner sc;
	private String dialogue;
	public boolean isDialogueFinished;
	
	public Room4() {
		dialogue = "Phoenix: Okay, so I\'m here. A man was murdered here, his body haphazardly stuffed into an abandoned car. *shivers*\n"
				+ "I guess I should take look around.\n";
		
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
	
	@Command(command="check car")
	public String checkCar(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
		if (maze.checkedCar) pw.print("Phoenix: (I already checked the car...)");
		else {
			pw.println("Phoenix: This red-hot car was where the Bellboy\'s body was discovered. The car has been abandoned for quite a while here, so the owner\'s apparently "
				+ "not involved in this case. The trunk has been forced open. It might be worth checking this area for bloodstains.");
			maze.checkedCar = true;
		}
    	return sw.toString();
    }
	
	@Command(command="use luminol")
	public String useLuminol(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
		if (!maze.findItem("luminol")) pw.print("Phoenix: (I already checked the car...)");
		else {
			pw.println("Phoenix: Just as I thought... the entire area\'s reacted blue. There\'s pretty much blood everywhere except--huh!? Why is there a gap in the blood pool? "
					+ "It seems to be heart-shaped. I should better take note of that.");
			maze.checkedCar = true;
		}
    	return sw.toString();
    }
	
	
}

package room;

import maze.MazeMaker;
import anno.*;

public class Room3 extends Room {
	@Direction(command="grossbergOffice")
	private Room2 room2;
	@Direction(command="parkingGarage")
	private Room4 room4;
	
	public Room3() {
		items = new String[1];
		items[0] = "luminol";
		dialogue = new String[7];
		dialogue[0] = "Phoenix: (Hmmm... doesn\'t seem like a busy day in the homicide division. Guess Detective Gumshoe isn\'t here.)";
		dialogue[1] = "Policeman: Oh, you in blue. Are you Mr. Wright? Gumshoe left you something.";
		dialogue[2] = "Phoenix: Oh, thanks. (Let me read the note...)";
		dialogue[3] = "Gumshoe\'s note: Hey, pal! You better not get in the way of the official police investigation! Just between you and me, pal, here\'s a Luminol kit. "
				+ "That forensics kid wanted you to take it. Anyway, thank me later! Just make sure not to goto parkingGarage.";
		dialogue[4] = "Phoenix: The back of the note seems to be a scribble of a ghost. Oh, Gumshoe...";
		dialogue[5] = "Looks like I can now find dried-out blood stains with this. I should probably check out that parking garage that the good detective didn\'t want us to go "
				+ "to. I think that\'s where the murder took place?";
		dialogue[6] = "To think that a murder occured in the police station\'s parking lot...";
	}
	
	@Override
	public String getDialogue() {
		if (dialogueCount >= dialogue.length || dialogueCount == 2) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	@Command(command="take")
	public String take(MazeMaker maze, String item) {
		if (item.equals("luminol")) {
			maze.items.put("luminol", "Spray solution that reveals trace amounts of blood and turns them into a luminous blue."); 
			if (dialogueCount >= dialogue.length) {
				if (maze.items.containsKey("luminol")) return "Phoenix: (I already took the luminol...)";
				return getDialogue();
			}
	    	return dialogue[dialogueCount++];
		}
		else return "Phoenix: (What " + item +"?)";
    }
	
	@Command(command="use")
	public String use(MazeMaker maze, String item) {
		return "Phoenix: (Mia's words echoed... 'Now is not the time to use that, Phoenix!')";
	}
}
package room;

import java.io.PrintWriter;
import java.io.StringWriter;

import anno.Command;
import anno.Direction;
import maze.MazeMaker;

public class Room9 {
	@Direction(command="edgeworthOffice")
	private Room7 room7;
	@Direction(command="evidenceRoom")
	private Room8 room8;
	@Direction(command="court")
	private Room10 room10;
	
	private String[] dialogue;
	private int dialogueCount;
	public boolean isDialogueFinished;
	public String[] items = new String[1];
	private boolean usedBloodyButton;
	
	public Room9() {
		dialogue = new String[1];
		dialogueCount = 0;
		isDialogueFinished = false;
		items[0] = "screwdriver";

		dialogue[0] = "Phoenix: Huh... the door was left open. Quick, I have to find where this button belongs to. That clothes dresser in the corner seems like a good place to start. Also, there seems to be an open drawer in that cabinet.";
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 11:45 AM - Gatewater Hotel -- Room 303");
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		return "9.png";
	}
	
	@Command(command="use")
	public String use(MazeMaker maze, String item) {
		if (item.equals("bloodyButton")) {
			if (!maze.findItem("bloodyButton") && usedBloodyButton) return "Phoenix: (I've already established that the button belongs to the blouse. What other leads are there..?)";
			else {
				usedBloodyButton = true;
				maze.items.remove("bloodyButton");
				maze.items.put("bloodstainedBlouse", "Pink blouse with a missing button and some bloodstains. Thought to belong to April May.");
				return "Phoenix: The clothes drawer-- aha! A pink blouse with a button missing and a dried red stain! This is all the evidence I need to pin the blame on her.";
			}
		}
		else return "Phoenix: (Mia's words echoed... 'Now is not the time to use that, Phoenix!')";
	}
	

	@Command(command="check drawer")
	public String checkDrawer(MazeMaker maze) {
		if (maze.findItem("screwdriver")) return "The drawer is already empty.";
		if (maze.checkedDrawer) return "Phoenix: (Take the screwdriver, Phoenix!)";
		else {
			maze.checkedDrawer = true;
			return "Phoenix: There\'s a screwdriver inside. I don\'t know how this is related, but I should take screwdriver just in case.";
		}
	}
	
	@Command(command="take")
	public String take(MazeMaker maze, String item) {
		if (item.equals("screwdriver")) {
			if (maze.findItem("screwdriver")) return "Phoenix: (I've already taken the screwdriver.)";
			else {
				maze.items.put("screwdriver", "A standard, flathead screwdriver. Found in April May\'s room.");
				return "(Screwdriver added to Court Record.)";
			}
		}
		else return "Phoenix: (What " + item +"?)";
	}
}

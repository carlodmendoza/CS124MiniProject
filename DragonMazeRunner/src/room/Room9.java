package room;

import maze.MazeMaker;
import anno.*;

public class Room9 extends Room {
	@Direction(command="edgeworthOffice")
	private Room7 room7;
	@Direction(command="evidenceRoom")
	private Room8 room8;
	@Direction(command="court")
	private Room10 room10;
	private boolean usedBloodyButton;
	
	public Room9() {
		items = new String[1];
		items[0] = "screwdriver";
		dialogue = new String[1];
		dialogue[0] = "Phoenix: Huh... the door was left open. Quick, I have to find where this button belongs to. That clothes dresser in the corner seems like a good place to start. Also, there seems to be an open drawer in that cabinet.";
	}
	
	@Command(command="take")
	public String take(MazeMaker maze, String item) {
		if (item.equals("screwdriver")) {
			if (!maze.checkedDrawer) return "What screwdriver?";
			else {
				if (maze.findItem("screwdriver")) return "Phoenix: (I've already taken the screwdriver.)";
				else {
					maze.items.put("screwdriver", "A standard, flathead screwdriver. Found in April May\'s room.");
					return "(Screwdriver added to Court Record.)";
				}
			}
		}
		else return "Phoenix: (What " + item +"?)";
	}
	
	@Command(command="use")
	public String use(MazeMaker maze, String item) {
		if (item.equals("bloodyButton")) {
			if (!maze.findItem("bloodyButton") && usedBloodyButton) return "Phoenix: (I've already established that the button belongs to the blouse. What other leads are there..?)";
			else {
				usedBloodyButton = true;
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
}

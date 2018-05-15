package room;

import maze.MazeMaker;
import anno.*;

public class Room7 extends Room {
	@Direction(command="forestLake")
	private Room6 room6;
	@Direction(command="evidenceRoom")
	private Room8 room8;
	
	public Room7() {
		items = new String[1];
		items[0] = "evidenceKey";
		dialogue = new String[2];
		dialogue[0] = "Phoenix: Oh, looks like Edgeworth isn\'t here. I wanted to see him today. I guess I can check back later--wait, I wonder what\'s that on his desk?";
		dialogue[1] = "It looks like there\'s some sort of key on his desk. It\'s labelled \"Evidence Room.\"";
	}
	
	@Command(command="take")
	public String take(MazeMaker maze, String item) {
		if (item.equals("evidenceKey")) {
			if (maze.items.containsKey("evidenceKey")) return "Phoenix: (I already took the key...)";
			else {
				maze.items.put("evidenceKey", "Key to the police station evidence room.");
				return "Phoenix: Sorry, Edgeworth. Looks like I\'ll be borrowing this.";
			}
		}
		else return "Phoenix: (What " + item +"?)";
	}
	
	@Command(command="use")
	public String use(MazeMaker maze, String item) {
		if (item.equals("evidenceKey")) {
			return "Phoenix: (I should go to the evidence room to use the evidence key.)";
		}
		else return "Phoenix: (Mia's words echoed... 'Now is not the time to use that, Phoenix!')";
	}
}

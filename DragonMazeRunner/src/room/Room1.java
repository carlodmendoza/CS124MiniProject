package room;

import maze.MazeMaker;
import anno.*;

public class Room1 extends Room {
	@Direction(command="grossbergOffice")
	private Room2 room2;
	
	public Room1() {
		dialogue = new String[4];
		dialogue[0] = "Phoenix: (My name is Phoenix Wright. Here\'s the story: My first case is a somewhat complicated one. A young bellboy over at the Gatewater Hotel was killed "
				+ "in the very hotel he loyally served. There are way too many suspects, and one of them is an unlucky sap who just happened to be in the wrong place at the"
				+ " wrong time: my best friend since grade school, Larry Butz. It\'s been true for that \"When something smells, it\'s usually the Butz,\" but I know better than "
				+ "anyone that he would never commit murder.";
		dialogue[1] = "The victim was murdered in the police station parking lot. His cause of death was multiple stab wounds to the chest."; 
		dialogue[2] = "Anyway... The trial\'s tomorrow, I\'m still lacking evidence, and Chief\'s away on a business trip. Guess I\'ll be gathering evidence alone, huh..."; 
        dialogue[3] = "Maybe I can go to Chief\'s friend, Mr. Grossberg, for advice.";
	}
	
	@Command(command="take")
	public String take(MazeMaker maze, String item) {
		return "Phoenix: (What " + item +"?)";
	}
	
	@Command(command="use")
	public String use(MazeMaker maze, String item) {
		return "Phoenix: (Mia's words echoed... 'Now is not the time to use that, Phoenix!')";
	}
}
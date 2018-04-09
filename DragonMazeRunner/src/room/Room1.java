package room;

import maze.MazeMaker;
import anno.*;
import java.util.Scanner;

public class Room1 {
	@Direction(command="goto grossbergOffice")
	private Room2 room2;
	private Scanner sc;
	private String dialog;
	
	public Room1() {
		dialog = "August 3, 09:00 AM - Wright & Co. Law Offices\n"
				+ "Phoenix: (My name is Phoenix Wright. Here’s the story: My first case is a somewhat complicated one. A young bellboy over at the Gatewater Hotel was killed "
				+ "in the very hotel he loyally served. There are way too many suspects, and one of them is an unlucky sap who just happened to be in the wrong place at the"
				+ " wrong time: my best friend since grade school, Larry Butz. It’s been true for that “When something smells, it’s usually the Butz,” but I know better than "
				+ "anyone that he would never commit murder.)\n"
        		+ "The victim was murdered in the police station parking lot. His cause of death was multiple stab wounds to the chest.\n" 
				+ "Anyway… The trial’s tomorrow, I’m still lacking evidence, and Chief’s away on a business trip. Guess I’ll be gathering evidence alone, huh…\n" 
        		+ "Maybe I can go to Chief’s friend, Mr. Grossberg, for advice.\n";
		sc = new Scanner(dialog);
	}
	
	public String getDescription(MazeMaker maze) {	
		if (!sc.hasNext()) {
			return " -- End of conversation --";
		}
		return sc.nextLine();
	}
	
	public String getRoomImg(MazeMaker maze) {
		return "1.png";
	}
}
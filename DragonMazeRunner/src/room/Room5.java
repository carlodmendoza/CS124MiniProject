package room;

import maze.MazeMaker;
import anno.*;
import java.io.*;

public class Room5 {
	@Direction(command="parkingGarage")
	private Room4 room4;
	@Direction(command="forestLake")
	private Room6 room6;
	
	private String[] dialogue;
	private int dialogueCount;
	public boolean isDialogueFinished;
	
	public Room5() {
		dialogue = new String[11];
		dialogueCount = 0;
		isDialogueFinished = false;
		dialogue[0] = "Phoenix: I expected some sort of janitor\'s closet or security office but this looks more like a... dressing room?";
		dialogue[1] = "???: HEY!!";
		dialogue[2] = "Phoenix: Yikes!";
		dialogue[3] = "???: Are you lost? Ha ha ha! Nobody usually visits me here.";
		dialogue[4] = "Phoenix: I, err, I\'m s-sorry for bothering y-you...";
		dialogue[5] = "???: No, it\'s okay. Umm, I think I should be saying sorry because, I know you\'re here to ask me about the murder? Well, I was away that night so I can\'t help you. Sorry...";
		dialogue[6] = "Phoenix: No, actually, I\'m here to ask... why is there a dressing room in the parking area?";
		dialogue[7] = "Will: Oh, well, about that... I-I\'m Will Powers. I used to be an actor for a children\'s show, the Steel Samurai, but I got fired a few days ago. I lost my house too, so I\'m here, a friend hooked me up with this old unused room to live in for a bit.";
		dialogue[8] = "Phoenix: I-I see, sorry for your loss. (Is that how cutthroat the entertainment industry is these days!?) Anyway, I guess I\'ll be on my way...";
		dialogue[9] = "Will: Wait! I forgot! Could you give these items to Mr. Detective if you meet him? You\'re those law people so you\'re friends, right? The day after the murder, I saw a pink button and a camera bag lying around in the parking lot. I didn\'t want them to get lost, so I took them. Well, you can take button or take camera now.";
		dialogue[10] = "Phoenix: Uhh, sure? (These could be my big leads!)";
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 3, 09:50 AM - ???");
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		if (dialogueCount >= 8) return "5-willcute.png";
		if (dialogueCount >= 5) return "5-willfriendly.png";
		if (dialogueCount >= 2) return "5-willshout.png";
		if (dialogueCount >= 0) return "5.png";
		else return "5-willcute.png";
	}
	
	@Command(command="take button")
	public String takeButton(MazeMaker maze) {
		if(maze.findItem("button")) return "Phoenix: (I already took the button...)";
		maze.items.put("button", "Pink and heart-shaped. It doesn\\'t seem remarkable, but..."); 
		return "Phoenix: This button is pink and heart-shaped. It doesn\'t seem remarkable, but...";
	}
	
	@Command(command="take camera")
	public String takeCamera(MazeMaker maze) {
		if(maze.findItem("camera")) return "Phoenix: (I already took the camera...)";
		maze.items.put("camera", "A camera bag containing a high-tech DSLR. There are green fibers and red strands of hair in various parts of the bag."); 
		return "Phoenix: This camera bag... I\'ve got a bad feeling about this... I think I know who owns this...";
	}
}
package room;

import maze.MazeMaker;
import anno.*;

public class Room6 extends Room {
	@Direction(command="sideRoom")
	private Room5 room5;
	@Direction(command="edgeworthOffice")
	private Room7 room7;
	
	public Room6() {
		dialogue = new String[5];
		dialogue[0] = "Lotta: Well if it isn\'t that blue hedgehog! What in tarnation are ya doin\' in the middle of nowhere? And what\'s that bag ya got? Hmmm?";
		dialogue[1] = "Phoenix: Hi, Lotta...Hart. Look, I didn\'t steal your camera, I was just told to give it to you.";
		dialogue[2] = "Lotta: Give it to me!! My baby...";
		dialogue[3] = "Lotta: Thanks y\'all. I was just gonna get ready to go over to that red guy\'s office with my camera--wait! Forget I said that! So long, gotta get back to the heart of the heartland!!";
		dialogue[4] = "Phoenix: ...she ran away fast. She mentioned something about an office...";
	}
	
	@Override
	public String getDialogue() {
		if (dialogueCount >= dialogue.length || dialogueCount == 3) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	@Override
	public String getRoomImg(String room) {
		if (dialogueCount >= 5) return "6.png";
		if (dialogueCount >= 4) return "6-lottaok.png";
		if (dialogueCount >= 0) return "6-lottamad.png";
		else return "6.png";
	}
	
	@Command(command="take")
	public String take(MazeMaker maze, String item) {
		return "Phoenix: (What " + item +"?)";
	}
	
	@Command(command="use")
	public String use(MazeMaker maze, String item) {
		return "Phoenix: (Mia's words echoed... 'Now is not the time to use that, Phoenix!')";
	}
	
	@Command(command="give camera")
	public String giveCamera(MazeMaker maze) {
		if(!maze.findItem("camera") && !maze.gaveCamera) return "Phoenix: I don't even have your camera...";
		maze.items.remove("camera");
		maze.gaveCamera = true;
		if(dialogueCount >= dialogue.length) {
			if(maze.gaveCamera) return "Phoenix: (I already gave her the camera...)";
			return getDialogue();
		}
    	return dialogue[dialogueCount++];
    }
}
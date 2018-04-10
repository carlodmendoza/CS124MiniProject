package room;

import maze.CheckEnter;
import maze.MazeMaker;
import java.io.*;
import anno.*;

@CheckEnter
public class Room10 {
	@Direction(command="gatewaterHotel")
	private Room9 room9;
	private String[] dialogue;
	private int dialogueCount;
	public boolean isDialogueFinished;
	
	public Room10() {
		dialogue = new String[34];
		dialogueCount = 0;
		isDialogueFinished = false;

		dialogue[0] = "Judge: The court is now in session for the trial of Mr. Larry Butz.";
		dialogue[1] = "Payne: The prosecution is ready, Your Honor.";
		dialogue[2] = "Phoenix: (I should probably present some evidence, fast!)";

		// break A
		// [present bloodstainedBlouse]

		dialogue[3] = "Phoenix: OBJECTION!";
		dialogue[4] = "Judge: Mr. Wright! What is the meaning of this? The witness hasn't even been introduced!";
		dialogue[5] = "Phoenix: There\'s no need for testimony when I\'ve got... this bloodstained blouse of hers! The witness, April May, is a murderer!";
		dialogue[6] = "Payne: Wright! Why would you have a woman\'s clothing!?";
		dialogue[7] = "Judge: Order! Order in the court! It is unmanly to steal from a woman, Mr. Wright.\nHowever, I\'ll let you explain yourself: what does this blouse dialogue[] = have to do with the case? So what if it has a bloodstain. Please present evidence.";
		dialogue[8] = "Phoenix: A man found this pink button in the crime scene! The button belongs to the witness\'s blouse! Is that not enough evidence for you, your Honor?";
		dialogue[9] = "Judge: A pink heart-shaped button with luminol reactions!?";
		dialogue[10] = "April: WHERE DID YOU GET THAT!?";
		dialogue[11] = "Judge: ...Well then, Mr. Wright.\nIf you can show me one more piece of evidence against this witness, your client will be acquitted.";
		dialogue[12] = "Payne: HOLD IT! You can\'t be serious, your Honor! This is no court!";
		dialogue[13] = "Judge: The defense presents a rather compelling argument. Overruled. Wright, you have one chance to present one more piece of evidence.";
		dialogue[14] = "Phoenix: (It\'s now or never, Phoenix! Time to present that one last evidence!)";

		// break B
		// [present screwdriver]

		dialogue[15] = "Phoenix: OBJECTION! O B J E C T I O N !";
		dialogue[16] = "Judge: HOLD IT!--er, Hold it. The defense will refrain from destroying my eardrums.";
		dialogue[17] = "Phoenix: Anyway! Here\'s the murder weapon!";
		dialogue[18] = "Payne: The what now";
		dialogue[19] = "Phoenix: I found it in a drawer in the witness\'s room! She\'s definitely guilty!";
		dialogue[20] = "Judge: ...Well then. I still don\'t understand what happened. But considering all the evidence presented I can now provide a verdict.\nI now pronounce the defendant, Larry Butz...";
		dialogue[21] = "NOT GUILTY";
		dialogue[22] = "Judge: That is all. Court is adjourned!";

		// end game

		// [if you present anything other than the bloodstainedBlouse in break A]
		dialogue[23] = "Payne: If you are ready, I'll get to my opening statement...";
		dialogue[24] = "Phoenix: (Wait! This isn\'t the right evidence to show.)";
		dialogue[25] = "Phoenix: HOLD IT! Let me compose myself first, Mr. Payne.\n(Think, Phoenix! What's the right evidence?)";
		// note: this section can only be repeated thrice before jumping to (B)

		// (B) [if you present the wrong evidence]:
		dialogue[26] = "Judge: ...";
		dialogue[27] = "Payne: ...";
		dialogue[28] = "April: ...";
		dialogue[29] = "All: ...";
		dialogue[30] = "Judge: You have made a mockery of the court, Mr. Wright.";
		dialogue[31] = "Phoenix: I, err, umm... I meant to present...";
		dialogue[32] = "Judge: Save your excuses for later. I would also suggest you seek psychiatric help. Please leave the courtroom and let us adults handle this. Bailiff! Take him away!";
		dialogue[33] = "Phoenix: NOOOOOOOOOOOOOOOOOOO!";
		// [game over]

	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println("August 4, 10:00 AM - District Court -- Courtroom No. 3");
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		return "10.png";
	}

}

package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

public class Room {
	protected String[] dialogue;
	protected int dialogueCount;
	public boolean isDialogueFinished;
	public String[] items;
	private HashMap<String, String> roomDesc = new HashMap<String, String>();
	
	public Room() {
		dialogueCount = 0;
		isDialogueFinished = false;
		roomDesc.put("Room1", "1.png>August 3, 09:00 AM - Wright & Co. Law Offices");
		roomDesc.put("Room2", "0>August 3, 09:04 AM - Grossberg Law Offices");
		roomDesc.put("Room3", "3.png>August 3, 09:25 AM - Police Station");
		roomDesc.put("Room4", "4.png>August 3, 09:32 PM - Police Station -- Parking Garage");
		roomDesc.put("Room5", "0>August 3, 09:50 AM - ???");
		roomDesc.put("Room6", "0>August 3, 10:20 AM - Gourd Lake Forest");
		roomDesc.put("Room7", "7.png>August 3, 10:45 AM - High Prosecutor Office");
		roomDesc.put("Room8", "8.png>August 3, 11:00 AM - Police Station -- Evidence Room");
		roomDesc.put("Room9", "9.png>August 3, 11:45 AM - Gatewater Hotel -- Room 303");
		roomDesc.put("Room10", "10.png>August 4, 10:00 AM - District Court -- Courtroom No. 3");
	}
	
	public String getDescription(String room) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	pw.println(roomDesc.get(room).split(">")[1]);
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	return sw.toString();
	}
	
	public String getRoomImg(String room) {
    	return roomDesc.get(room).split(">")[0];
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
}

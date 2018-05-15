package maze;

import java.io.Serializable;
import java.util.HashMap;

public class Memento implements Serializable {
	private HashMap<Class, Object> roomMap = new HashMap<Class, Object>();
	private Object currentRoom;
	private HashMap<String, String> items = new HashMap<String, String>();
	private boolean isProxy, talkedToGrossberg, checkedCar, gaveCamera, checkedDrawer, gameOver;
	
	public Memento(HashMap<Class, Object> roomMap, Object currentRoom, HashMap<String, String> items, boolean isProxy, boolean talkedToGrossberg, boolean checkedCar, boolean gaveCamera, boolean checkedDrawer, boolean gameOver) {
		this.roomMap = roomMap;
		this.currentRoom = currentRoom;
		this.items = items;
		this.isProxy = isProxy;
		this.talkedToGrossberg = talkedToGrossberg;
		this.checkedCar = checkedCar;
		this.gaveCamera = gaveCamera;
		this.checkedDrawer = checkedDrawer;
		this.gameOver = gameOver;
	}

	public HashMap<Class, Object> getRoomMap() {
		return roomMap;
	}

	public Object getCurrentRoom() {
		return currentRoom;
	}

	public HashMap<String, String> getItems() {
		return items;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public boolean hasTalkedToGrossberg() {
		return talkedToGrossberg;
	}

	public boolean hasCheckedCar() {
		return checkedCar;
	}

	public boolean hasGivenCamera() {
		return gaveCamera;
	}

	public boolean hasCheckedDrawer() {
		return checkedDrawer;
	}

	public boolean isGameOver() {
		return gameOver;
	}
}

package maze;

import java.util.HashMap;

public class Originator {
	private HashMap<Class, Object> roomMap = new HashMap<Class, Object>();
	private Object currentRoom;
	private HashMap<String, String> items = new HashMap<String, String>();
	private boolean isProxy, talkedToGrossberg, checkedCar, gaveCamera, checkedDrawer, gameOver;
	
	public void setState(HashMap<Class, Object> roomMap) {
		this.roomMap = roomMap;
	}
	
	public void setState(HashMap<Class, Object> roomMap, Object currentRoom, HashMap<String, String> items, boolean isProxy, boolean talkedToGrossberg, boolean checkedCar, boolean gaveCamera, boolean checkedDrawer, boolean gameOver) {
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
	
	public Memento saveStateToMemento() {
		return new Memento (roomMap, currentRoom, items, isProxy, talkedToGrossberg, checkedCar, gaveCamera, checkedDrawer, gameOver);
	}
	
	public void setStateFromMemento(Memento memento) {
		roomMap = memento.getRoomMap();
		currentRoom = memento.getCurrentRoom();
		items = memento.getItems();
		isProxy = memento.isProxy();
		talkedToGrossberg = memento.hasTalkedToGrossberg();
		checkedCar = memento.hasCheckedCar();
		gaveCamera = memento.hasGivenCamera();
		checkedDrawer = memento.hasCheckedDrawer();
		gameOver = memento.isGameOver();
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

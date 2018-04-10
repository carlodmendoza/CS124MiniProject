package maze;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import java.util.*;
import java.lang.reflect.*;
import java.io.*;
import anno.Command;
import anno.Direction;

public class MazeMaker {
	
	private HashMap<Class, Object> roomMap = new HashMap<Class, Object>();
	public Object currentRoom;
	public HashMap<String, String> items = new HashMap<String, String>();
	public boolean isInRoom10, isInRoom8, talkedToGrossberg, checkedCar, gaveCamera;

	public String load() throws Exception {
		FastClasspathScanner scanner = new FastClasspathScanner("room");
		ScanResult result = scanner.scan();
		List<String> allClasses = result.getNamesOfAllStandardClasses();
		
		for (String className : allClasses) {
			Class clazz = Class.forName(className);
			Object instance = clazz.newInstance();
			if (clazz.isAnnotationPresent(CheckEnter.class)) instance = new MazeIntercept().run(clazz);
			roomMap.put(clazz, instance);
		}
		
		for (Class roomClazz : roomMap.keySet()) {
			Object currentRoom = roomMap.get(roomClazz);
			for (Field f : roomClazz.getDeclaredFields()) {
				if (f.isAnnotationPresent(Direction.class)) {
					Class fieldClazz = f.getType();
					Object roomInstance = roomMap.get(fieldClazz);
					f.setAccessible(true);
					f.set(currentRoom, roomInstance);
				}
			}
		}
		
		currentRoom = roomMap.get(room.Room1.class);
		
		return printDescription(false);
	}
	
	public String printDescription(boolean isProxy) throws Exception {
		Method m;
		if (isProxy) m = currentRoom.getClass().getSuperclass().getDeclaredMethod("getDescription", MazeMaker.class);
		else m = currentRoom.getClass().getDeclaredMethod("getDescription", MazeMaker.class);
		
		return (String) m.invoke(currentRoom, this);
	}
	
	public String printDialogue(boolean isProxy) throws Exception {
		Method m;
		if (isProxy) m = currentRoom.getClass().getSuperclass().getDeclaredMethod("getDialogue");
		else m = currentRoom.getClass().getDeclaredMethod("getDialogue");
		
		return (String) m.invoke(currentRoom);
	}
	
	public boolean isDialogueFinished(boolean isProxy) throws Exception {
		Field f;
		if (isProxy) f = currentRoom.getClass().getSuperclass().getDeclaredField("isDialogueFinished");
		else f = currentRoom.getClass().getDeclaredField("isDialogueFinished");
		
		return f.getBoolean(currentRoom);
	}
	
	public String getRoomImg(boolean isProxy) throws Exception {
		Method m;
		if (isProxy) m = currentRoom.getClass().getSuperclass().getDeclaredMethod("getRoomImg");
		else m = currentRoom.getClass().getDeclaredMethod("getRoomImg");
		
		return (String) m.invoke(currentRoom);
	}
	
	public String move(String action) throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	String arr[] = action.split(" ");
		
		Class clazz;
		if (EnterCondition.class.isAssignableFrom(currentRoom.getClass())) clazz = currentRoom.getClass().getSuperclass();
		else clazz = currentRoom.getClass();	
		
		if (arr[0].equals("goto")) {
			Field[] fields = clazz.getDeclaredFields();
			
			for (Field f : fields) {
				if (f.isAnnotationPresent(Direction.class)) {
					Direction d = f.getAnnotation(Direction.class);
					try {
						if (d.command().equals(arr[1])) {
							Class fieldClass = f.getType();
							Object o  = roomMap.get(fieldClass);
							if (o instanceof EnterCondition) {
								if(((EnterCondition) o).canEnter(this)) {
									pw.println(((EnterCondition) o).enterMessage(this));
									currentRoom = o;
									pw.print(printDescription(true));
									if (currentRoom.getClass().getSuperclass().getSimpleName().equals("Room10")) isInRoom10 = true;
									if (currentRoom.getClass().getSuperclass().getSimpleName().equals("Room8")) isInRoom8 = true;
								}
								else {
									pw.println(((EnterCondition) o).unableToEnterMessage());
									pw.print(printDescription(false));
								}
							}
							else {
								currentRoom = o;
								pw.print(printDescription(false));
								isInRoom10 = false;
								isInRoom8 = false;
							}
							break;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						pw.println("Go to where?");
					}
				}
			}
			try {
				if (sw.toString().equals("")) pw.println("Where is the " + arr[1] + "?");
			} catch (ArrayIndexOutOfBoundsException e) {
				pw.println("Go to where?");
			}
		}
		
		else if (arr[0].equals("take")) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if (m.isAnnotationPresent(Command.class)) {
					Command c = m.getAnnotation(Command.class);
					if (c.command().equals(action)) {
						pw.println(m.invoke(currentRoom, this));
						break;
					}
				}
			}
			try {
				if (sw.toString().equals("")) pw.println("What " + arr[1] + "?");
			} catch (ArrayIndexOutOfBoundsException e) {
				pw.println("Take what?");
			}
		}
		
		else if (arr[0].equals("use")) {
			if (items.isEmpty()) {
				pw.println("Phoenix: (I don't have any items yet.)");
				return sw.toString();
			}
			try {
				if (!findItem(arr[1])) {
					pw.println("Phoenix: (What " + arr[1] +"?)");
					return sw.toString();
				}
			} catch (Exception e1) {
				pw.println("Use what?");
			}
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if (m.isAnnotationPresent(Command.class)) {
					Command c = m.getAnnotation(Command.class);
					try {
						if (c.command().equals(action)) {
							pw.println(m.invoke(currentRoom, this));
							break;
						}
					} catch (Exception e) {
						pw.println("Use what?");
					}
				}
			}
			if (sw.toString().equals("")) pw.println("Phoenix: (Mia's words echoed... 'Now is not the time to use that, Phoenix!')");
		}

		else {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if (m.isAnnotationPresent(Command.class)) {
					Command c = m.getAnnotation(Command.class);
					if (c.command().equals(action)) {
						pw.println(m.invoke(currentRoom, this));
						break;
					} 
				}
			}
		}
			
		if (sw.toString().equals("")) {
			if (currentRoom instanceof EnterCondition) {
				pw.println(printDialogue(true));
			}
			else pw.println(printDialogue(false));
			
		}
		
		return sw.toString();
	}
	
	public String showCommands() throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	int count = 0;
    	
		Class clazz = currentRoom.getClass();
		if (currentRoom instanceof EnterCondition) clazz = currentRoom.getClass().getSuperclass();
		
    	pw.println("Available commands:");
    	
    	if (!isInRoom10) {
	    	for (String item : items.keySet()) {
	    		pw.println(++count + ". use " + item);
	    	}
	    	
			for (Field f : clazz.getDeclaredFields()) {
			   Direction anno = f.getAnnotation(Direction.class);
			   if (anno != null) pw.println(++count + ". goto " + anno.command());
			}
			
			for (Method m : clazz.getDeclaredMethods()) {
			   Command anno = m.getAnnotation(Command.class);
			   if (anno != null) {
				   if (anno.command().split(" ")[0].equals("use")) {
					   continue;
				   }
				   pw.println(++count + ". " + anno.command());
			   }
			}
    	}
    	else {
    		for (String item : items.keySet()) {
	    		pw.println(++count + ". present " + item);
	    	}	
    	}
		
		return sw.toString();
	}
	
	public String showItems() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	int count = 0;
   
    	pw.println("Court Record:");
		for (String item: items.keySet()) {
			pw.println(++count + ". " + item + ": " + items.get(item));
		}
		
		if (items.isEmpty()) pw.println("The court record is empty.");
		
		return sw.toString();
	}
	
	public boolean findItem(String name) {
		for (String s : items.keySet()) {
			if (s.equals(name)) return true;
		}
		
		return false;
	}
}
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
	public boolean isProxy, talkedToGrossberg, checkedCar, gaveCamera, checkedDrawer, gameOver;

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
		if (isProxy) m = currentRoom.getClass().getSuperclass().getDeclaredMethod("getDescription");
		else m = currentRoom.getClass().getDeclaredMethod("getDescription");
		return (String) m.invoke(currentRoom);
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
	
	public String[] getItems(boolean isProxy) throws Exception {
		Field f;
		if (isProxy) f = currentRoom.getClass().getSuperclass().getDeclaredField("items");
		else f = currentRoom.getClass().getDeclaredField("items");
		return (String[]) f.get(currentRoom);
	}
	
	public String move(String action) throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	String arr[] = action.split(" ");
		
		Class clazz = currentRoom.getClass() ;
		if (EnterCondition.class.isAssignableFrom(currentRoom.getClass())) clazz = currentRoom.getClass().getSuperclass();
		
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
									currentRoom = o;
									String className = currentRoom.getClass().getSuperclass().getSimpleName();
									if (className.equals("Room8")) items.remove("evidenceKey");
									pw.print(((EnterCondition) o).enterMessage(className));
									pw.print(printDescription(true));
									isProxy = true;
								}
								else {
									pw.println(((EnterCondition) o).unableToEnterMessage());
									pw.print(printDescription(false));
								}
							}
							else {
								currentRoom = o;
								pw.print(printDescription(false));
								isProxy = false;
							}
							break;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						pw.println("Go to where?");
					}
				}
			}
			try {
				if (sw.toString().equals("")) pw.println("Phoenix: (Where is the " + arr[1] + "?)");
			} catch (ArrayIndexOutOfBoundsException e) {
				pw.println("Go to where?");
			}
		}
		
		else if (arr[0].equals("take")) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if (m.isAnnotationPresent(Command.class)) {
					Command c = m.getAnnotation(Command.class);
					if (c.command().equals("take")) {
						try {
							pw.println(m.invoke(currentRoom, this, arr[1]));
							break;
						} catch (ArrayIndexOutOfBoundsException e) {
							pw.println("Take what?");
						}
					}
				}
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
			} catch (ArrayIndexOutOfBoundsException e) {
				pw.println("Use what?");
			}
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if (m.isAnnotationPresent(Command.class)) {
					Command c = m.getAnnotation(Command.class);
					try {
						if (c.command().equals("use")) {
							pw.println(m.invoke(currentRoom, this, arr[1]));
							break;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						pw.println("Use what?");
					}
				}
			}
		}
		
		else if (arr[0].equals("present")) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if (m.isAnnotationPresent(Command.class)) {
					Command c = m.getAnnotation(Command.class);
					if (c.command().equals("present")) {
						try {
							pw.println(m.invoke(currentRoom, this, arr[1]));
							break;
						} catch (ArrayIndexOutOfBoundsException e) {
							pw.println("Present what?");
						}
					}
				}
			}
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
    	
    	if (clazz.getSimpleName().equals("Room10")) {
    		try {
				for (String s : getItems(true)) {
					if (!findItem(s)) continue;
					pw.println(++count + ". present " + s);
				}
			} catch (Exception e) {
				pw.print("");
			}
    	}
    	else {
    		for (Field f : clazz.getDeclaredFields()) {
 			   Direction anno = f.getAnnotation(Direction.class);
 			   if (anno != null) pw.println(++count + ". goto " + anno.command());
 			}
    		
    		try {
				for (String s : getItems(false)) {
					if (findItem(s)) continue;
					pw.println(++count + ". take " + s);
				}
			} catch (Exception e) {
				pw.print("");
			}
    		
    		for (String item : items.keySet()) {
	    		pw.println(++count + ". use " + item);
	    	}
				
			for (Method m : clazz.getDeclaredMethods()) {
			   Command anno = m.getAnnotation(Command.class);
			   if (anno != null) {
				   if (anno.command().equals("take")) continue;
				   if (anno.command().equals("use")) continue;
				   if (anno.command().equals("talkto grossberg")) {
					   if (talkedToGrossberg) continue;
				   }
				   if (anno.command().equals("check car")) {
					   if (checkedCar) continue;
				   }
				   if (anno.command().equals("give camera")) {
					   if (gaveCamera) continue;
				   }
				   if (anno.command().equals("check drawer")) {
					   if (checkedDrawer) continue;
				   }
				   pw.println(++count + ". " + anno.command());
			   }
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
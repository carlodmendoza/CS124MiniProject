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
	private Object currentRoom;
	public ArrayList<String> items = new ArrayList<String>();
	public boolean inPool, graveFound, tookSword, wordFoundRm2, babyDead, chestFound, wordFoundRm3, wordFoundRm4, isDead;

	public String load() throws Exception {
		FastClasspathScanner scanner = new FastClasspathScanner("room");
		ScanResult result = scanner.scan();
		List<String> allClasses = result.getNamesOfAllStandardClasses();
		
		for (String className : allClasses) {
			Class clazz = Class.forName(className);
			Object instance = clazz.newInstance();
			if (clazz.isAnnotationPresent(CheckEnter.class)) instance = new MazeIntercept().run(clazz);
			//return "Class: " + clazz.getName() + " - Object: " + instance.toString();
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
	
	public String printDescription(boolean isRoom5) throws Exception {
		Method m;
		if (isRoom5) m = currentRoom.getClass().getSuperclass().getDeclaredMethod("getDescription", MazeMaker.class);
		else m = currentRoom.getClass().getDeclaredMethod("getDescription", MazeMaker.class);
		return (String) m.invoke(currentRoom, this);
	}
	
	public String move(String action) throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
		String[] arr = action.split(" ");
		
		Class clazz;
		if (EnterCondition.class.isAssignableFrom(currentRoom.getClass())) clazz = currentRoom.getClass().getSuperclass();
		else clazz = currentRoom.getClass();	
		
		Field[] fields = clazz.getDeclaredFields();
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Field f : fields) {
			if (f.isAnnotationPresent(Direction.class)) {
				Direction d = f.getAnnotation(Direction.class);
				if (d.command().equals(action)) {
					Class fieldClass = f.getType();
					Object o  = roomMap.get(fieldClass);
					if (o instanceof EnterCondition) {
						if(((EnterCondition) o).canEnter(this)) {
							pw.println(((EnterCondition) o).enterMessage());
							currentRoom = o;
							pw.print(printDescription(true));
						}
						else {
							pw.println(((EnterCondition) o).unableToEnterMessage());
							pw.print(printDescription(false));
						}
					}
					else {
						currentRoom = o;
						pw.print(printDescription(false));
					}
					break;
				}
			}
		}
		
		for (Method m : methods) {
			if (m.isAnnotationPresent(Command.class)) {
				Command c = m.getAnnotation(Command.class);	
				if (m.getParameterCount() == 1) {
					if (c.command().equals(action)) {
						pw.print(m.invoke(currentRoom, this));
						break;
					}	
				}
				else {
					if (c.command().equals(arr[0])) {
						try {
							pw.print(m.invoke(currentRoom, this, arr[1]));
						}
						catch (IndexOutOfBoundsException e) {
							pw.println("Retype the method and put your answer after it, separated by a space.");
						}
						break;
					}
				}
			}
		}
		
		if (sw.toString().equals("")) pw.println("Please enter a valid command.");
		return sw.toString();
	}
	
	public String showCommands() throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	int count = 0;
    	
		Class clazz = currentRoom.getClass();
		if (currentRoom instanceof EnterCondition) clazz = currentRoom.getClass().getSuperclass();
		
    	pw.println("Available commands:");
    	
		for (Field f : clazz.getDeclaredFields()) {
		   Direction anno = f.getAnnotation(Direction.class);
		   if (anno != null) pw.println(++count + ". " + anno.command());
		}
		
		for (Method m : clazz.getDeclaredMethods()) {
		   Command anno = m.getAnnotation(Command.class);
		   if (anno != null) {
			   if (clazz.getSimpleName().equals("Room4")) {
				   if (m.getParameterCount() == 2) pw.println(++count + ". " + anno.command() + " <" + m.getParameterTypes()[1] + ">");
				   else pw.println("- " + anno.command());
			   }
			   else if (clazz.getSimpleName().equals("Room5")) {
				   if (m.getParameterCount() == 2) pw.println(++count + ". " + anno.command() + " <String>");
				   else pw.println("- " + anno.command());
			   }
			   else pw.println(++count + ". " + anno.command());
		   }
		}
		
		return sw.toString();
	}
	
	public String showItems() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	int count = 0;
   
    	pw.println("Inventory:");
    	
		for (String item: items) {
			pw.println(++count + ". " + item);
		}
		
		if (items.isEmpty()) pw.println("You currently have no items.");
		return sw.toString();
	}
}
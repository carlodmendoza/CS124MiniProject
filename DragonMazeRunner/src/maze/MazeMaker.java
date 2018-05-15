package maze;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import java.util.*;
import java.lang.reflect.*;
import java.io.*;
import anno.Command;
import anno.Direction;

public class MazeMaker implements State {
	
	private HashMap<Class, Object> roomMap = new HashMap<Class, Object>();
	public Object currentRoom;
	public HashMap<String, String> items = new HashMap<String, String>();
	public boolean isProxy, talkedToGrossberg, checkedCar, gaveCamera, checkedDrawer, gameOver;
	public String user;
		
	public MazeMaker(String user) {
		this.user = user;
	}
	
	@Override
	public void changeState(MazeGUI gui, String user) {
		gui.setState(new UnregisteredState());
	}
	
	@Override
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
		return "Welcome to the game, " + user + "!\n\n" + getMethod(false, "getDescription");
	}
	
	public String getMethod(boolean isProxy, String method) throws Exception {
		Class clazz = currentRoom.getClass();
		Method m;
		if (isProxy) {
			if (method.equals("getDialogue")) {
				m = clazz.getSuperclass().getSuperclass().getDeclaredMethod(method);
				return (String) m.invoke(currentRoom);
			}
			else {
				m = clazz.getSuperclass().getSuperclass().getDeclaredMethod(method, String.class);
				return (String) m.invoke(currentRoom, clazz.getSuperclass().getSimpleName());
			}
		}
		else {
			if (method.equals("getDialogue") && (clazz.getSimpleName().equals("Room2") || clazz.getSimpleName().equals("Room3") || clazz.getSimpleName().equals("Room6") || clazz.getSimpleName().equals("Room10"))) {
				m = clazz.getDeclaredMethod(method);
				return (String) m.invoke(currentRoom);
			}
			else if (method.equals("getDialogue")) {
				m = clazz.getSuperclass().getDeclaredMethod(method);
				return (String) m.invoke(currentRoom);
			}
			else if (method.equals("getRoomImg") && (clazz.getSimpleName().equals("Room2") || clazz.getSimpleName().equals("Room5") || clazz.getSimpleName().equals("Room6"))) {
				m = clazz.getDeclaredMethod(method, String.class);
				return (String) m.invoke(currentRoom, clazz.getSimpleName());
			}
			else {
				m = clazz.getSuperclass().getDeclaredMethod(method, String.class);
				return (String) m.invoke(currentRoom, clazz.getSimpleName());
			}
			
		}
	}
	
	public Object getField(boolean isProxy, String field) throws Exception {
		Field f;
		if (isProxy) f = currentRoom.getClass().getSuperclass().getSuperclass().getDeclaredField(field);
		else f = currentRoom.getClass().getSuperclass().getDeclaredField(field);
		return f.get(currentRoom);
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
									pw.print(getMethod(true, "getDescription"));
									isProxy = true;
								}
								else {
									pw.println(((EnterCondition) o).unableToEnterMessage());
									pw.print(getMethod(false, "getDescription"));
								}
							}
							else {
								currentRoom = o;
								pw.print(getMethod(false, "getDescription"));
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
				pw.println(getMethod(true, "getDialogue"));
			}
			else pw.println(getMethod(false, "getDialogue"));	
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
    	
		for (Field f : clazz.getDeclaredFields()) {
		   Direction anno = f.getAnnotation(Direction.class);
		   if (anno != null) {
			   if (anno.command().equals("policeStation")) {
				   if (!talkedToGrossberg) continue;
			   }
			   pw.println(++count + ". goto " + anno.command());
		   }
		}
		
		try {
			for (String s : (String[]) getField(false, "items")) {
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
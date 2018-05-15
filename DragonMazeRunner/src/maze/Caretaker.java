package maze;

import java.io.Serializable;
import java.util.HashMap;

public class Caretaker implements Serializable {
	
	private HashMap<String, Memento> sessions = new HashMap<String, Memento>();
	
	public void save(String user, Memento session) {
		sessions.put(user, session);
	}
	
	public Memento load(String user){
		return sessions.get(user);
	}

	public boolean exists(String user) {
		if (sessions.containsKey(user)) return true;
		return false;
	}
	
	public int savedSessions() {
		return sessions.size();
	}

//	public ArrayList<String> getUsers() {
//		Set<String> userKeys = sessions.keySet();
//		ArrayList<String> users = new ArrayList<String>(userKeys);
//		return users;
//	}
}

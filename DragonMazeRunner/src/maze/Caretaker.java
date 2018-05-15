package maze;

import java.util.HashMap;

public class Caretaker {
	
	private HashMap<String, Memento> sessions = new HashMap<String, Memento>();
	
	public void save(String user, Memento session) {
		sessions.put(user, session);
	}
	
	public Memento load(String user){
		return sessions.get(user);
	}

	public HashMap<String, Memento> getSessions() {
		return sessions;
	}
	
	public void setSessions(HashMap<String, Memento> sessions) {
		this.sessions = sessions;
	}
}

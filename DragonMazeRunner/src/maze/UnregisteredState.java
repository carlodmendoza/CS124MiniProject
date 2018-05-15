package maze;

public class UnregisteredState implements State {
		
	@Override
	public void changeState(MazeGUI gui, String user) {
		gui.setState(new MazeMaker(user));
		gui.setRegistered();
	}

	@Override
	public String load() throws Exception {
		return "Welcome! Please register.\n";
	}
}
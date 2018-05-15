package maze;

public class UnregisteredState implements State {
	@Override
	public void load(MazeGUI gui, String user) throws Exception {
		gui.setState(this);
		gui.print("Welcome! Please register.\n");
	}
}
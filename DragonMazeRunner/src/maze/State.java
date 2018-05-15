package maze;

public interface State {
	public void changeState(MazeGUI gui, String user);
	public String load() throws Exception;
}
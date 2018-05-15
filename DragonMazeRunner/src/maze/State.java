package maze;

public interface State {
	public void changeState(MazeGUI gui);
	public int stateNumber();
	public String startMe() throws Exception;
}
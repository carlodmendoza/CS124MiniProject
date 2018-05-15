package maze;

public class UnregisteredState implements State {
	private String user;

	public UnregisteredState() {
		System.out.println("Unregistered state object made");
	}
	
	@Override
	public void changeState(MazeGUI gui) {
		gui.setState(new MazeMaker(user));
	}

	@Override
	public int stateNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String load() throws Exception {
		// TODO Auto-generated method stub
		return "Unregistered state object body";
	}
}

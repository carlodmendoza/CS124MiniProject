package maze;

public class Register implements State {
	private String user;

	public Register() {
		System.out.println("a new Register object was made");
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
	public String startMe() throws Exception {
		// TODO Auto-generated method stub
		return "Register startMe ran";
	}
}

package maze;

public class MazeInterceptor {
	
	public static boolean canEnter(MazeMaker maze) {
		if (maze.wordFoundRm2 && maze.wordFoundRm3 && maze.wordFoundRm4) return true;
		else return false;
	}
	
	public static String enterMessage() {
		return "Welcome to the final room!";
	}
	
	public static String unableToEnterMessage() {
		return "You cannot enter this room yet.";
	}

}

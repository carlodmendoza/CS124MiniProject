package maze;

public class MazeInterceptor {
	
	public static boolean canEnter(MazeMaker maze) {
		return true;
	}
	
	public static String enterMessage() {
		return "Welcome to the final room!\n";
	}
	
	public static String unableToEnterMessage() {
		return "You cannot enter this room yet.\n";
	}

}

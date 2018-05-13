package maze;

public interface EnterCondition {
	
	public boolean canEnter(MazeMaker maze);
	
	public String enterMessage(MazeMaker maze, String room);
	
	public String unableToEnterMessage(); 
}

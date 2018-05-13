package maze;

public interface EnterCondition {
	
	public boolean canEnter(MazeMaker maze);
	
	public String enterMessage(String room);
	
	public String unableToEnterMessage(); 
}

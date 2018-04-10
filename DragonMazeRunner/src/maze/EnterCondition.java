package maze;

public interface EnterCondition {
	
	public boolean canEnter(MazeMaker maze);
	
	public String enterMessage(MazeMaker maze);
	
	public String unableToEnterMessage(); 
}

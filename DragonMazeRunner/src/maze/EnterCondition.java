package maze;

public interface EnterCondition {
	
	public boolean canEnter(MazeMaker maze);
	
	public String enterMessage();
	
	public String unableToEnterMessage(); 
}

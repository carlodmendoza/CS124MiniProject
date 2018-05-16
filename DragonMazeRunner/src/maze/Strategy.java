package maze;

public interface Strategy {
	public String message();
	public Object getOperation(String inputText) throws Exception;
}

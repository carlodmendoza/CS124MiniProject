package maze;

public class TypeCommand implements Strategy {

	@Override
	public String message() {
		return "Input command mode";
	}

	@Override
	public Object getOperation(String inputText) throws Exception {
		if(inputText.trim().isEmpty()) return "What are you trying to do?\n";
		else return inputText;
	}

	@Override
	public int getStratNumber() {
		return 0;
	}

}

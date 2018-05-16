package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadFile implements Strategy {
	@Override
	public String message() {
		return "Loading file...";
	}
	@Override
	public Object getOperation(String inputText) throws Exception {
		try(BufferedReader br = new BufferedReader(new FileReader(inputText))) {
			ArrayList<String> commands = new ArrayList<String>();
    		for(String line; (line = br.readLine()) != null;) commands.add(line);
    		br.close();
    		return commands;
		} catch(FileNotFoundException e){
			return null;
		}
	}
}

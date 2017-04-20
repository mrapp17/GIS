package commandFile;

import java.io.BufferedReader;
import java.io.FileReader;

import logFile.LogFileController;

//The purpose of this class is to maintain sole contron and access to the commandFile, and dole out
//individual commands one at a time to the command Processor. 
public class CommandParser {
	private BufferedReader commandFile;
	private LogFileController logFile;
	
	public CommandParser(String fileName, LogFileController logFile) {
		this.logFile = logFile;
		try {
			commandFile = new BufferedReader( new FileReader(fileName));
		}
		catch(Exception e) {
			System.err.println("Command Parser Constructor: " + e.getMessage());
		}
	}
	
	//The logComments flag must be set to true for any line starting with a ; to be logged to the log file
	//If the flag is false, any line which is NOT a command will be simply omitted from the final log. In 
	//the current production state the flag is set to true
	public String getNextCommand(boolean logComments) {
		String commandLine = null;
		try {
			commandLine = commandFile.readLine();
			while(commandLine != null && commandLine.length() > 0 && commandLine.charAt(0) == ';') {
				if(logComments) {
					logFile.writeString(commandLine);
				}
				commandLine = commandFile.readLine();
			}
		}
		catch (Exception e) {
			System.err.println("Command Parser Get Next Command: " + e.getMessage());		
		}
		logFile.writeString(commandLine);
		return commandLine;
	}
}

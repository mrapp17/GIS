package controller;

import java.io.File;
import commandFile.CommandParser;
import commandProcessor.CommandProcessor;
import coordIndex.CoordIndex;
import gisParser.GISParser;
import logFile.LogFileController;
import nameIndex.NameIndex;

//This class is called only once from the main method, it clears all appropriate files
//ensures the commandFile exists then creates the necessary objects, and begins processing 
//in the command processor
public class Controller {
	private LogFileController logFileController; 
	private CommandParser cmdParser;
	private CoordIndex coordIndex;
	private NameIndex nameIndex;
	private GISParser gisParser;
	private CommandProcessor cmdProcessor;
	
	public Controller(String dbName, String commandScriptName, String logFileName) {
		//Delete the dbName and logFileName files
		File dbFile = new File(dbName);
		if(dbFile.exists())
			dbFile.delete();
		File logFile = new File(logFileName);
		if(logFile.exists())
			logFile.delete();
		File commdFile = new File(commandScriptName);
		if(!commdFile.exists())
			System.err.println("The command Script file must be created prior to the invokation of this program\n");
		dbFile = null;
		logFile = null;
		commdFile = null;
		logFileController = new LogFileController(logFileName);
		cmdParser = new CommandParser(commandScriptName, logFileController);
		coordIndex = new CoordIndex(4, logFileController);
		nameIndex = new NameIndex(1024, logFileController);
		gisParser = new GISParser(nameIndex, coordIndex, dbName, logFileController);
		cmdProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		cmdProcessor.begin();
	}
	
}

package commandProcessor;

import org.junit.Test;

import commandFile.CommandParser;
import coordIndex.CoordIndex;
import gisParser.GISParser;
import logFile.LogFileController;
import nameIndex.NameIndex;

public class CommandProcessorTest {

	@Test
	public void test1() {
		LogFileController logFile = new LogFileController("CommandProcessorTest1Log.txt");
		CommandParser cmdParser = new CommandParser("Commands1.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile1.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
	
	@Test
	public void test2() {
		LogFileController logFile = new LogFileController("CommandProcessorTest2Log.txt");
		CommandParser cmdParser = new CommandParser("Commands2.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile2.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
	
	@Test
	public void test3() {
		LogFileController logFile = new LogFileController("CommandProcessorTest3Log.txt");
		CommandParser cmdParser = new CommandParser("Commands3.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile3.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
	
	@Test
	public void test4() {
		LogFileController logFile = new LogFileController("CommandProcessorTest4Log.txt");
		CommandParser cmdParser = new CommandParser("Commands4.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile4.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
	
	@Test
	public void test5() {
		LogFileController logFile = new LogFileController("CommandProcessorTest5Log.txt");
		CommandParser cmdParser = new CommandParser("Commands5.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile5.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
	
	@Test
	public void test6() {
		LogFileController logFile = new LogFileController("CommandProcessorTest6Log.txt");
		CommandParser cmdParser = new CommandParser("Commands6.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile6.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}

	@Test
	public void test7() {
		LogFileController logFile = new LogFileController("CommandProcessorTest7Log.txt");
		CommandParser cmdParser = new CommandParser("Commands7.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile7.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
	@Test
	public void test8() {
		LogFileController logFile = new LogFileController("CommandProcessorTest8Log.txt");
		CommandParser cmdParser = new CommandParser("Commands8.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile8.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
	@Test
	public void test9() {
		LogFileController logFile = new LogFileController("CommandProcessorTest9Log.txt");
		CommandParser cmdParser = new CommandParser("Commands9.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile9.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
	@Test
	public void test10() {
		LogFileController logFile = new LogFileController("CommandProcessorTest10Log.txt");
		CommandParser cmdParser = new CommandParser("Commands10.txt", logFile);
		CoordIndex coordIndex = new CoordIndex(4, logFile);
		NameIndex nameIndex = new NameIndex(1024, logFile);
		GISParser gisParser = new GISParser(nameIndex, coordIndex, "dataFile10.txt", logFile);
		CommandProcessor testProcessor = new CommandProcessor(coordIndex, nameIndex, cmdParser, gisParser);
		testProcessor.begin();
	}
}

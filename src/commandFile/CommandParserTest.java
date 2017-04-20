package commandFile;

import static org.junit.Assert.*;

import org.junit.Test;

import logFile.LogFileController;

public class CommandParserTest {
	
	@Test
	public void testGetNextCommandLogging() {
		LogFileController testLog = new LogFileController("CommandParseTestLog1.txt");
		CommandParser testCommandParser = new CommandParser("Commands1.txt", testLog);
		
		assertEquals("world	0794130W	0792630W	381500N	383000N", testCommandParser.getNextCommand(true));
		assertEquals("import	VA_Monterey.txt", testCommandParser.getNextCommand(true));
		assertEquals("debug	world", testCommandParser.getNextCommand(true));
		assertEquals("debug	quad", testCommandParser.getNextCommand(true));
		assertEquals("debug	hash", testCommandParser.getNextCommand(true));
		assertEquals("what_is	Trimble Knob	VA", testCommandParser.getNextCommand(true));
		assertEquals("debug	pool", testCommandParser.getNextCommand(true));
		assertEquals("what_is_at	382812N	0793156W", testCommandParser.getNextCommand(true));
		assertEquals("debug	pool", testCommandParser.getNextCommand(true));
		assertEquals("what_is	Swope Hollow	VA", testCommandParser.getNextCommand(true));
		assertEquals("what_is	Possum Trot	VA", testCommandParser.getNextCommand(true));
		assertEquals("what_is	Blue Grass	VA", testCommandParser.getNextCommand(true));
		assertEquals("debug	pool", testCommandParser.getNextCommand(true));
		assertEquals("what_is_at	382145N	0793031W", testCommandParser.getNextCommand(true));
		assertEquals("what_is_at	382442N	0793451W", testCommandParser.getNextCommand(true));
		assertEquals("what_is_at	382607N	0793312W", testCommandParser.getNextCommand(true));
		assertEquals("debug	pool", testCommandParser.getNextCommand(true));
		assertEquals("what_is_at	382856N	0793031W", testCommandParser.getNextCommand(true));
		assertEquals("debug	pool", testCommandParser.getNextCommand(true));
		assertEquals("quit\t", testCommandParser.getNextCommand(true));
	}

	@Test
	public void testGetNextCommandNotLogging() {
		LogFileController testLog = new LogFileController("CommandParseTestLog2.txt");
		CommandParser testCommandParser = new CommandParser("Commands1.txt", testLog);
		
		assertEquals("world	0794130W	0792630W	381500N	383000N", testCommandParser.getNextCommand(false));
		assertEquals("import	VA_Monterey.txt", testCommandParser.getNextCommand(false));
		assertEquals("debug	world", testCommandParser.getNextCommand(false));
		assertEquals("debug	quad", testCommandParser.getNextCommand(false));
		assertEquals("debug	hash", testCommandParser.getNextCommand(false));
		assertEquals("what_is	Trimble Knob	VA", testCommandParser.getNextCommand(false));
		assertEquals("debug	pool", testCommandParser.getNextCommand(false));
		assertEquals("what_is_at	382812N	0793156W", testCommandParser.getNextCommand(false));
		assertEquals("debug	pool", testCommandParser.getNextCommand(false));
		assertEquals("what_is	Swope Hollow	VA", testCommandParser.getNextCommand(false));
		assertEquals("what_is	Possum Trot	VA", testCommandParser.getNextCommand(false));
		assertEquals("what_is	Blue Grass	VA", testCommandParser.getNextCommand(false));
		assertEquals("debug	pool", testCommandParser.getNextCommand(false));
		assertEquals("what_is_at	382145N	0793031W", testCommandParser.getNextCommand(false));
		assertEquals("what_is_at	382442N	0793451W", testCommandParser.getNextCommand(false));
		assertEquals("what_is_at	382607N	0793312W", testCommandParser.getNextCommand(false));
		assertEquals("debug	pool", testCommandParser.getNextCommand(false));
		assertEquals("what_is_at	382856N	0793031W", testCommandParser.getNextCommand(false));
		assertEquals("debug	pool", testCommandParser.getNextCommand(false));
		assertEquals("quit\t", testCommandParser.getNextCommand(false));
	}
}

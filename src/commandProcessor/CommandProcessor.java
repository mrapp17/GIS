package commandProcessor;

import java.util.ArrayList;

import commandFile.CommandParser;
import coordIndex.CoordIndex;
import gisParser.Filter;
import gisParser.GISParser;
import nameIndex.NameIndex;

//The purpose of this class is to control the indices and retrieve the offset resulst from them and pass them to the GIS parser
//All string processing of the individual commands is done here
public class CommandProcessor {
	private CoordIndex coordIndex;
	private NameIndex nameIndex;
	private CommandParser cmdParser;
	private GISParser gisParser;
	
	public CommandProcessor(CoordIndex coordIndex, NameIndex nameIndex, CommandParser cmdParser, GISParser gisParser) {
		this.coordIndex = coordIndex;
		this.nameIndex = nameIndex;
		this.cmdParser = cmdParser;
		this.gisParser = gisParser;
	}
	
	//This method is created because I field weird having significant code run in the constructor, this may also allow for a multithreaded
	//approach in the future to be easier to implement.
	public void begin() {
		String currentCommand = cmdParser.getNextCommand(true);
		while(currentCommand != null && !currentCommand.equals("quit\t")) {
			String[] splitCommand = currentCommand.split("\t", -1);
			if(splitCommand[0].equals("world")) {
				coordIndex.setWorld(splitCommand[1], splitCommand[2], splitCommand[3], splitCommand[4]);
			}
			else if(splitCommand[0].equals("import")) {
				gisParser.importGIS(splitCommand[1]);
			}
			else if(splitCommand[0].equals("debug")) {
				if(splitCommand[1].equals("quad")) {
					coordIndex.debug();
				}
				else if(splitCommand[1].equals("hash")) {
					nameIndex.debug();
				}
				else if(splitCommand[1].equals("pool")) {
					gisParser.debug();
				}
				else {
					System.err.println(splitCommand[1] + " is an unrecognized debug command. Accepted commands are : quad hash pool");
				}
			}
			else if(splitCommand[0].equals("quit")) {
				return;
			}
			else if(splitCommand[0].equals("what_is_at")) {
				ArrayList<Integer> resultsList = coordIndex.findAt(splitCommand[1], splitCommand[2]);
				gisParser.whatIsAt(resultsList, splitCommand[1], splitCommand[2]);				
			}
			else if(splitCommand[0].equals("what_is")) {
				ArrayList<Integer> resultsList = nameIndex.getOffset(splitCommand[1], splitCommand[2]);
				gisParser.whatIs(resultsList);
			}
			else if(splitCommand[0].equals("what_is_in")) {
				boolean longFlag;
				Filter currFilter = Filter.NOFILTER;
				int i = 1;
				
				//Determine if long flag is present
				if(splitCommand[i].equals("-long")) {
					longFlag = true;
					++i;
				}
				else 
					longFlag = false;
				
				//Determine if a filter is required and which filter
				if(splitCommand[i].equals("-filter")) {
					if(splitCommand[++i].equals("pop"))
						currFilter = Filter.POP;
					else if(splitCommand[i].equals("water"))
							currFilter = Filter.WATER;
					else if(splitCommand[i].equals("structure"))
						currFilter = Filter.STRUCTURE;
					++i;
				}
				
				//Process the request
				String latitude = splitCommand[i++];
				String longitude = splitCommand[i++];
				int latRange = Integer.parseInt(splitCommand[i++]);
				int longRange = Integer.parseInt(splitCommand[i]);
				ArrayList<Integer> resultsList = coordIndex.findIn(latitude, longitude, latRange, longRange);
				gisParser.whatIsIn(resultsList, latitude, longitude, latRange, longRange, longFlag, currFilter);
			}
			currentCommand = cmdParser.getNextCommand(true);
		}
	}
}

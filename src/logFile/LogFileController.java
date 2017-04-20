package logFile;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Formatter;

import gisParser.GISObject;

public class LogFileController {
	PrintWriter logFile;

	public LogFileController(String logFileName) {
		try {
			logFile = new PrintWriter(logFileName,"UTF-8");
		}
		catch (Exception e) {
			System.err.println("Log Constructor " + e.getMessage());
		}
	}
	
	//Used to write a simple single string to the log file, used to write the commands and comments to the log
	public void writeString(String input) {
		logFile.println(input);
		logFile.flush();
	}
	
	//Writes the header as seen below to the log, called immediately after program is started
//	GIS Program
//
//	dbFile:     db.txt
//	script:     Script01.txt
//	log:        McLog01.txt
//	Start time: Fri Mar 10 21:31:10 EST 2017
//	Quadtree children are printed in the order SW  SE  NE  NW
//	--------------------------------------------------------------------------------
	
	public void writeHeader(String dbFileName, String scriptFileName, String logFileName) {
		logFile.println("GIS Program");
		logFile.println("");
		logFile.println("dbFile:\t\t" + dbFileName);
		logFile.println("scriptFile:\t" + scriptFileName);
		logFile.println("logFile:\t" + logFileName);
		logFile.println("");
		logFile.println("Quadtree children are printed in the order: NE NW SW SE");
		logFile.println("-----------------------------------------------------------------------------------\n");
		logFile.flush();
	}
	
	//Called when the world is assigned to write the size to the log
	public void writeWorld(long xMin, long xMax, long yMin, long yMax) {
		logFile.println("Latitude/longitude values in index entries are shown as signed integers, in total seconds.\n");
		logFile.println("World boundaries are set to:");
		logFile.println("x Range: " + Integer.toString((int) xMin) + " - " + Integer.toString((int) xMax));
		logFile.println("y Range: " + Integer.toString((int) yMin) + " - " + Integer.toString((int) yMax));
		logFile.println("-----------------------------------------------------------------------------------\n");
		logFile.flush();
	}
	
	//Called after import to display the results of import 
	public void writeImport(String fileName, int featuresByName, int longestProbe, int locations) {
		logFile.println("Imported From:\t" + fileName + "\n");
		logFile.println("Imported features by name:\t" + Integer.toString(featuresByName));
		logFile.println("Longest probe sequence:\t" + Integer.toString(longestProbe));
		logFile.println("Imported locations:\t" + Integer.toString(locations));
		logFile.println("-----------------------------------------------------------------------------------\n");
		logFile.flush();
		
	}
		
	//LongFlag log every important non-empty field
	//Else log the offset the feature name state name a pimrary latitude and longitude
	public void writeWhatIsIn(ArrayList<GISObject> output, boolean longFlag, String latitude, String longitude, int latitudeRange, int longitudeRange) {
		String tempLongitude;
		String tempLatitude;
		
		logFile.println("The following " + Integer.toString(output.size()) + " features were found in (" + formatLatitude(latitude) + 
				" +/- " + Integer.toString(latitudeRange) + ", " + formatLongitude(longitude) + " +/- " + Integer.toString(longitudeRange) + ")\n");
		if(longFlag) {
			for(int i = 0; i < output.size(); ++i) {
				logFile.println("Feature ID\t\t:\t" + Integer.toString(output.get(i).FeatureID));
				logFile.println("Feature Name\t:\t" + output.get(i).FeatureName);
				logFile.println("Feature Cat\t\t:\t" + output.get(i).FeatureClass);
				logFile.println("State\t\t\t:\t" + output.get(i).StateAlpha);
				logFile.println("County\t\t\t:\t" + output.get(i).CountyAlpha);
				tempLatitude = (output.get(i).LatitudeAlpha.equals("Unknown")) ? "Unknown" : formatLatitude(output.get(i).LatitudeAlpha);
				tempLongitude = (output.get(i).LongitudeAlpha.equals("Unknown")) ? "Unknown" : formatLongitude(output.get(i).LongitudeAlpha);
				logFile.println("Latitude\t\t:\t" + tempLatitude);
				logFile.println("Longitude\t\t:\t" + tempLongitude);
				if (output.get(i).Elevation != null)
					logFile.println("Elevation(ft)\t:\t" + Integer.toString(output.get(i).Elevation));
				logFile.println("Map Name\t\t:\t" + output.get(i).MapName);
				logFile.println("Date Created\t:\t" + output.get(i).CreationDate);
				logFile.print("\n");
			}
		}
		else {
			for(int i = 0; i < output.size(); ++i) {
				tempLatitude = (output.get(i).LatitudeAlpha.equals("Unknown")) ? "Unknown" : formatLatitude(output.get(i).LatitudeAlpha);
				tempLongitude = (output.get(i).LongitudeAlpha.equals("Unknown")) ? "Unknown" : formatLongitude(output.get(i).LongitudeAlpha);
				logFile.println(Integer.toString(output.get(i).offset) + ": " + output.get(i).FeatureName + "  " + output.get(i).StateAlpha + " (" + tempLatitude + ", " + tempLongitude + ")");
			}
		}
		logFile.println("-----------------------------------------------------------------------------------\n");
		logFile.flush();
	}
	
	//Print out the offset, the feature name, county name, and state abbreviation
	public void writeWhatIsAt(ArrayList<GISObject> output, String latitude, String longitude) {
		logFile.println("The following " + Integer.toString(output.size()) + " features were found at (" + formatLatitude(latitude) + ", " +
				formatLongitude(longitude) + "):\n");
		for(int i = 0; i < output.size(); ++i) {
			logFile.println(Integer.toString(output.get(i).offset) + ": " + output.get(i).FeatureName + "  " + output.get(i).CountyAlpha + "  " + output.get(i).StateAlpha);
		}
		logFile.println("-----------------------------------------------------------------------------------\n");
		logFile.flush();
	}
	
	// log the offset the county name primary latitude and longitude
	public void writeWhatIs(ArrayList<GISObject> output) {
		String tempLongitude;
		String tempLatitude;
		for(int i = 0; i < output.size(); ++i) {
			tempLatitude = (output.get(i).LatitudeAlpha.equals("Unknown")) ? "Unknown" : formatLatitude(output.get(i).LatitudeAlpha);
			tempLongitude = (output.get(i).LongitudeAlpha.equals("Unknown")) ? "Unknown" : formatLongitude(output.get(i).LongitudeAlpha);
			logFile.println(Integer.toString(output.get(i).offset) + ": " + output.get(i).CountyAlpha + " (" + tempLatitude + ", " + tempLongitude + ")");
		}
		logFile.println("-----------------------------------------------------------------------------------\n");
		logFile.flush();
	}
	
	//Write quit header
	public void writeQuit() {
		logFile.println("Terminating execution of commands.");
		logFile.println("-----------------------------------------------------------------------------------\n");
		logFile.flush();
	}
	
	private String formatLongitude(String input) {
		Formatter f = new Formatter();
		
		int day = Integer.parseInt(input.substring(0,3));
		int min = Integer.parseInt(input.substring(3,5));
		int sec = Integer.parseInt(input.substring(5,7));
		String direction = (input.charAt(input.length()-1) == 'W') ? "West" : "East";
		
		f.format("%dd %dm %ds %s", day, min, sec, direction);
		String retval = f.toString();
		f.close();
		return retval;
	}
	
	private String formatLatitude(String input) {
		Formatter f = new Formatter();
		
		int day = Integer.parseInt(input.substring(0,2));
		int min = Integer.parseInt(input.substring(2,4));
		int sec = Integer.parseInt(input.substring(4,6));
		String direction = (input.charAt(input.length()-1) == 'N') ? "North" : "South";
		
		f.format("%dd %dm %ds %s", day, min, sec, direction);
		String retval = f.toString();
		f.close();
		return retval;
	}
}
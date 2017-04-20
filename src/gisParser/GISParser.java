package gisParser;

import java.io.RandomAccessFile;
import java.util.ArrayList;

import coordIndex.CoordIndex;
import logFile.LogFileController;
import nameIndex.NameIndex;

public class GISParser{
	BufferPool bufferPool;
	private NameIndex nameIndex;
	private CoordIndex coordIndex;
	private RandomAccessFile dataFile;
	private LogFileController logFile;
	private RandomAccessFile newGISFile;
	int currentEOFOffset;
	
	
	public GISParser(NameIndex nameIndex, CoordIndex coordIndex, String dataFileName, LogFileController logFile) {
		this.bufferPool = new BufferPool();
		this.nameIndex = nameIndex;
		this.coordIndex = coordIndex;
		try {
			this.dataFile = new RandomAccessFile(dataFileName, "rw");
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		this.logFile = logFile;
		currentEOFOffset = 0;
	}
	
	//imports the data from a GIS file specified by newFileName, stored it into the dataFile
	//and indexes the results into the appropriate indices
	
	//SEE AUTHORS NOTES IN GIS.JAVA FOR NOTES REGARDING ITS BEHAVIOR
	public boolean importGIS(String newFileName) {
		int coordCount = 0;
		int nameCount = 0;
		int probeMax = 0;
		int probeTemp = 0;
		String line;
		String[] splitLine; 
		try {
			dataFile.seek((int) currentEOFOffset);
			newGISFile = new RandomAccessFile(newFileName,"r");
			line = newGISFile.readLine();
			line = newGISFile.readLine();
			while(line != null) {
				splitLine = line.split("[|]");
				if(coordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset))
					++coordCount;
				probeTemp = nameIndex.indexName(splitLine[1], splitLine[3], currentEOFOffset);
				probeMax = (probeTemp > probeMax)? probeTemp : probeMax;
				++nameCount;
				dataFile.writeBytes(line + "\n");			
				currentEOFOffset = (int) dataFile.getFilePointer();
				line = newGISFile.readLine();
			}	
		}
		catch (Exception e) {
			System.err.println("Import " + e.getMessage());
			return false;
		}
		logFile.writeImport(newFileName, nameCount, probeMax, coordCount);
		return true;
	}
	
	//All what is methods operate similarly:
	//Given an input of offsets lists of the gis records which are relevant and the
	//parameters which should be displayed in the log retrieve the records and log them
	//Utilizes the Buffer Pool to minimize file accesses
	public void whatIsAt(ArrayList<Integer> offsetList, String longitude, String latitude) {
		if(offsetList == null)
			return;
		try {
			ArrayList<GISObject> objectList = new ArrayList<GISObject>(offsetList.size());
			for(int i = 0; i < offsetList.size(); ++i) {
				int currentOffset = offsetList.get(i);
				GISObject newGISObject = bufferPool.find(currentOffset);
				if(newGISObject == null) {
					dataFile.seek(currentOffset);
					String data = dataFile.readLine();
					newGISObject = new GISObject(currentOffset, data);
					objectList.add(newGISObject);
					bufferPool.insert(newGISObject);
				}
				else {
					objectList.add(newGISObject);
					bufferPool.insert(newGISObject);
				}
				
			}
			sortList(objectList);
			logFile.writeWhatIsAt(objectList, longitude, latitude);
		}
		catch (Exception e) {
			System.err.println("What is at " + e.getMessage());
		}

	}
	
	public void whatIs(ArrayList<Integer> offsetList) {
		if(offsetList == null)
			return;
		try {
			ArrayList<GISObject> objectList = new ArrayList<GISObject>(offsetList.size());
			for(int i = 0; i < offsetList.size(); ++i) {
				int currentOffset = offsetList.get(i);
				GISObject newGISObject = bufferPool.find(currentOffset);
				if(newGISObject == null) {
					dataFile.seek(currentOffset);
					String data = dataFile.readLine();
					newGISObject = new GISObject(currentOffset, data);
					if(newGISObject != null) {
					objectList.add(newGISObject);
					bufferPool.insert(newGISObject);
					}
				}
				else {
					objectList.add(newGISObject);
					bufferPool.insert(newGISObject);
				}
				dataFile.seek(currentOffset);
			}
			sortList(objectList);
			logFile.writeWhatIs(objectList);
			
		}
		catch (Exception e) {
			System.err.println("What is " + e.getMessage());
		}
	}
	
	public void whatIsIn(ArrayList<Integer> offsetList, String latitude, String longitude, int latRange, int longRange, boolean longFlag, Filter currFilter) {
		if(offsetList == null)
			return;
		try {
			ArrayList<GISObject> objectList = new ArrayList<GISObject>(offsetList.size());
			for(int i = 0; i < offsetList.size(); ++i) {
				int currentOffset = offsetList.get(i);
				GISObject newGISObject = bufferPool.find(currentOffset);
				if(newGISObject == null) {
					dataFile.seek(currentOffset);
					String data = dataFile.readLine();
					newGISObject = new GISObject(currentOffset, data);
				}
				switch(currFilter) {
				case NOFILTER:
					objectList.add(newGISObject);
					bufferPool.insert(newGISObject);
					break;
				case POP:
					if(newGISObject.FeatureClass.equals("Populated Place")) {
						objectList.add(newGISObject);
						bufferPool.insert(newGISObject);
					}
					break;
				case WATER:
					if(newGISObject.FeatureClass.equals("Arroyo") ||
							newGISObject.FeatureClass.equals("Bay") ||
							newGISObject.FeatureClass.equals("Bend") ||
							newGISObject.FeatureClass.equals("Canal") ||
							newGISObject.FeatureClass.equals("Channel") ||
							newGISObject.FeatureClass.equals("Falls") ||
							newGISObject.FeatureClass.equals("Glacier") ||
							newGISObject.FeatureClass.equals("Gut") ||
							newGISObject.FeatureClass.equals("Harbor") ||
							newGISObject.FeatureClass.equals("Lake") ||
							newGISObject.FeatureClass.equals("Rapids") ||
							newGISObject.FeatureClass.equals("Reservoir") ||
							newGISObject.FeatureClass.equals("Sea") ||
							newGISObject.FeatureClass.equals("Spring") ||
							newGISObject.FeatureClass.equals("Stream") ||
							newGISObject.FeatureClass.equals("Swamp") ||
							newGISObject.FeatureClass.equals("Well")) {
						objectList.add(newGISObject);
						bufferPool.insert(newGISObject);
					}
					break;
				case STRUCTURE:
					if(newGISObject.FeatureClass.equals("Airport") ||
							newGISObject.FeatureClass.equals("Bridge") ||
							newGISObject.FeatureClass.equals("Building") ||
							newGISObject.FeatureClass.equals("Church") ||
							newGISObject.FeatureClass.equals("Dam") ||
							newGISObject.FeatureClass.equals("Hospital") ||
							newGISObject.FeatureClass.equals("Levee") ||
							newGISObject.FeatureClass.equals("Park") ||
							newGISObject.FeatureClass.equals("Post Office") ||
							newGISObject.FeatureClass.equals("School") ||
							newGISObject.FeatureClass.equals("Tower") ||
							newGISObject.FeatureClass.equals("Tunnel")) {
						objectList.add(newGISObject);
						bufferPool.insert(newGISObject);
					}
					break;				
				}
			}
			sortList(objectList);
			logFile.writeWhatIsIn(objectList, longFlag, latitude, longitude, latRange, longRange);	
		}
		catch (Exception e) {
			System.err.println("What is in " + e.getMessage());
		}
	}
	
	//Prints the contents of ht buffer pool to the log file
	public void debug() {
		bufferPool.debug(logFile);
	}
	
	//Uses quick sort to organize the list of GIS objects
	private void sortList(ArrayList<GISObject> objectList) {
		if(objectList == null || objectList.size() == 0)
			return;
		else 
			quickSort(objectList, 0, objectList.size() - 1);
	}
	
	private void quickSort(ArrayList<GISObject> objectList, int low, int high) {
		GISObject pivot = objectList.get(low + (high - low)/2);
		int i = low; 
		int j = high;
		while(i <= j) {
			
			//Go from left to right until an element is found which is greater than the pivot
			while(objectList.get(i).compareTo(pivot) < 0)
				++i;
			
			//Go from right to left until an element is found which is less than the pivot
			while(objectList.get(j).compareTo(pivot) > 0)
				--j;
			
			//If the low index is still lower than the high index, swap the two values and move closer to the pivot
			if( i <= j ) {
				GISObject tempObject = objectList.get(i);
				objectList.set(i, objectList.get(j));
				objectList.set(j, tempObject);
				++i;
				--j;
			}
		}
		
		//Recursion calls to perform this on increasingly smaller sets
		if(low < j)
			quickSort(objectList, low, j);
		if(i < high)
			quickSort(objectList, i, high);
	}

}

package coordIndex;

import java.util.ArrayList;

import logFile.LogFileController;

//The coordIndex uses a PR quad tree as its sub data structure to maintain a list of all 
//CoordObjects stored currently. 
public class CoordIndex {	
	private prQuadTree<CoordObject> indexTree;
	private LogFileController logFile;
	private int bucketSize;
	
	public CoordIndex(int bucketSize, LogFileController logFile) {
		this.bucketSize = bucketSize;
		this.logFile = logFile;
		indexTree = null;
	}
	
	//Set create the underlying prQuadTree data structure size
	//This should only be called one during execution!
	public boolean setWorld(String westLong, String eastLong, String southLat, String northLat) {
		long xMin = longStringToInt(westLong);
		long xMax = longStringToInt(eastLong);
		long yMin = latStringToInt(southLat);
		long yMax = latStringToInt(northLat);
		if(xMin > xMax || yMin > yMax) {
			System.err.println("westLong must be less that eastLong, and southLat must be less than northLat");
			return false;
		}
		if(indexTree == null) {
			indexTree = new prQuadTree<CoordObject>(xMin, xMax, yMin, yMax, bucketSize);
			logFile.writeWorld(xMin, xMax, yMin, yMax);
			return true;
		}
		else {
			System.err.println("The world can only be assigned once per program invocation");
			return false;
		}
	}
	
	//Inserts an obejct into the index using x y positions that are related to the underlying pr quad tree borders
	public boolean insert(long x, long y, int offset) {
		if(indexTree == null) {
			System.err.println("The world must be assigned before an element can be inserted");
			return false;
		}
		
		ArrayList<Integer> tempList = findAt(x,y);
		
		
		//Check to see if this element is already contained within the tree, if so just append to its list
		if(tempList != null && !tempList.contains(new Integer(offset))) {
			tempList.add(new Integer(offset));
			return true;
		}
		
		else {
			return indexTree.insert(new CoordObject(x,y,offset));
		}
	}
	
	//An overloaded insert method which takes strings for input, these strings are translated into integers then
	//The original insert is called
	public boolean insert(String longitude, String latitude, int offset) {
		if(longitude.equals("Unknown") || latitude.equals("Unknown")) {
			return false;
		}
		else {
			int longitudeTemp = longStringToInt(longitude);
			int latitudeTemp = latStringToInt(latitude);
			return insert(longitudeTemp, latitudeTemp, offset);
		}
	}
	
	//Returns a list of all offset values which are contained within the boundary specified by the inputs
	//These offsets can come from several different coodObjects provided that their coords are within
	//The bounding box
	public ArrayList<Integer> findIn(String latitude, String longitude, int halfHeight, int halfWidth) {
		long xMid = longStringToInt(longitude);
		long yMid = latStringToInt(latitude);
		if(indexTree == null) {
			System.err.println("The world must be assigned before a search can be performed");
			return null;
		}
		else {
			long xLo = xMid - halfWidth;
			long xHi = xMid + halfWidth;
			long yLo = yMid - halfHeight;
			long yHi = yMid + halfHeight;
			ArrayList<Integer> retList = new ArrayList<Integer>();
			ArrayList<CoordObject> tempList =  indexTree.find(xLo, xHi, yLo, yHi);
			for(int i = 0; i < tempList.size(); ++i) {
				retList.addAll(tempList.get(i).getList());
			}
			return retList;
		}
	}
	
	//Finds all offsets whicha are associated with a CoordOBject at the coordinates x and y 
	public ArrayList<Integer> findAt(long x, long y) {
		if(indexTree == null) {
			System.err.println("The world must be assigned before a search can be performed");
			return null;
		}
		else {
			CoordObject tempCoordObject = indexTree.find(new CoordObject(x,y,-1));
			return 	(tempCoordObject == null)? null: tempCoordObject.getList();		
		}
	}
	
	//an overloaded method which translated strings into integers and calls the original method
	public ArrayList<Integer> findAt(String latitude, String longitude) {
		int x = longStringToInt(longitude);
		int y = latStringToInt(latitude);
		return findAt(x,y);
	}
	
	// When called prints the contents of the underlying tree to the logFile
	public void debug() {
		indexTree.printTree(logFile);
		logFile.writeString("-----------------------------------------------------------------------------------\n");
	}
	
	//Methods which allow any method to translate the longitude string to an integer represent total seconds
	public static int longStringToInt(String longitude) {
		int degrees = Integer.parseInt(longitude.substring(0,3));
		int minutes = Integer.parseInt(longitude.substring(3,5));
		int seconds = Integer.parseInt(longitude.substring(5,7));
		seconds += (degrees * 3600) + (minutes * 60);
		return (longitude.charAt(7) == 'W')? -seconds : seconds;
	}
	
	//Allows an method to translate latitude string to an integer representing total seconds
	public static int latStringToInt(String latitude) {
		int degrees = Integer.parseInt(latitude.substring(0,2));
		int minutes = Integer.parseInt(latitude.substring(2,4));
		int seconds = Integer.parseInt(latitude.substring(4,6));
		seconds += (degrees * 3600) + (minutes * 60);
		return (latitude.charAt(6) == 'S')? -seconds : seconds;
	}
}

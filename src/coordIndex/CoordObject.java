package coordIndex;

import java.util.ArrayList;

//Coord object which is indexed into the coordIndex
//Can contain an infinite number of offsets provided the coords are EXACTLY
//the same
public class CoordObject implements Compare2D<CoordObject> {
	private Point coordinates;
	private ArrayList<Integer> fileOffsets;
	
	public CoordObject(Point coordinates, Integer newOffset) {
		this.coordinates = coordinates;
		fileOffsets = new ArrayList<Integer>(0);
		fileOffsets.add(newOffset);
	}
	
	public CoordObject(long x, long y, Integer newOffset) {
		coordinates = new Point(x,y);
		fileOffsets = new ArrayList<Integer>(0);
		fileOffsets.add(newOffset);
	}
	
	public Point getCorrdinates() {
		return this.coordinates;
	}
	
	
	public boolean addOffset(Integer newOffset) {
		if(this.contains(newOffset))
			return false;
		else {
			fileOffsets.add(newOffset);
			return true;
		}
	}
	
	public ArrayList<Integer> getList() {
		return fileOffsets;
	}
	
	public boolean contains(Integer newOffset) {
		return fileOffsets.contains(newOffset);
	}
	
	//Comapre 2D functionality
	public long getX() {
		return coordinates.getX();
	}
	
	public long getY() {
		return coordinates.getY();
	}
	
	public Direction directionFrom(long x, long y) {
		return coordinates.directionFrom(x, y);
	}
	
	public Direction inQuadrant(double xLo, double xHi, double yLo, double yHi) {
		return coordinates.inQuadrant(xLo, xHi, yLo, yHi);
	}
	
	public boolean inBox(double xLo, double xHi, double yLo, double yHi) {
		return coordinates.inBox(xLo, xHi, yLo, yHi);
	}
	
	public boolean equals(Object o) {
		if (o == this) 
			return true;
		else if (o == null) 
			return false;
		else if(getClass() != o.getClass())
			return false;
		CoordObject otherCoord = (CoordObject) o;
		return coordinates.equals(otherCoord.getCorrdinates());
	}

	//Prints out the entire object into a user readble a string, first printing the coordinates, then the contents
	//of the offsets list
	public String toString() {
		String retval = "[" + coordinates.toString() + " ";
		for(int i = 0; i < fileOffsets.size(); ++i) {
			retval += fileOffsets.get(i);
			if(i < fileOffsets.size() -1)
				retval += ", ";
		}
		retval += "] ";
		return retval;
	}
}

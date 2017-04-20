package coordIndex;

import java.util.ArrayList;

import logFile.LogFileController;

public class prQuadTree< T extends Compare2D<? super T> > {
	abstract class prQuadNode {
	}
	
	class prQuadLeaf extends prQuadNode {
		public ArrayList<T> Elements;
		
		public prQuadLeaf(int size) {
			Elements = new ArrayList<T>(size);
		}
		
		public prQuadLeaf(int size,  T data ) {
			Elements = new ArrayList<T>(size);
			Elements.add(data);
		}
	}
	
	class prQuadInternal extends prQuadNode {
		public prQuadNode NW, SW, SE, NE;
		
		public prQuadInternal() {
			NW = null;
			SW = null;
			SE = null;
			NE = null;
		}
	}
	private LogFileController logFile;
	
	prQuadNode root;
	int bucketSize;
	long xMin, xMax, yMin, yMax;
	
	private boolean insertSuccessStat;
    private boolean deleteSuccessStat;
    
    private ArrayList<T> foundList;
	
    //Initialize a quadtree to an empty state the boundary CANNOT be reassigned once it is created
	public prQuadTree(long xMin, long xMax, long yMin, long yMax, int bucketSize) {
		root = null;
		
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		
		this.bucketSize = bucketSize;
		
		insertSuccessStat = false;
	    deleteSuccessStat = false;
	    
	    foundList = new ArrayList<T>();
	}
	
	// Pre:   elem != null, the element to be inserted is not already present within the tree
	// Post:  If elem lies within the tree's region, and elem is not already 
    //        present in the tree, elem has been inserted into the tree.
    // Return true iff elem is inserted into the tree. 
	public boolean insert(T elem) {
		insertSuccessStat = false;
		if(!elem.inBox(xMin, xMax, yMin, yMax))
			return insertSuccessStat;
		root = insertHelper(root, elem, xMin, xMax, yMin, yMax);		
		return insertSuccessStat;
	}
	
	// Pre:  elem != null
    // Returns reference to an element x within the tree such that 
    // elem.equals(x)is true, provided such a matching element occurs within
    // the tree; returns null otherwise.
	public T find(T elem) {
		if(elem.inBox(xMin, xMax, yMin, yMax))
			return findHelper(root, elem, xMin, xMax, yMin, yMax);
		else 
			return null;
	}
	
    // Pre:  elem != null
    // Post: If elem lies in the tree's region, and a matching element occurs
    //       in the tree, then that element has been removed.
    // Returns true iff a matching element has been removed from the tree. 
	public boolean delete(T elem) {
		deleteSuccessStat = false;
		root = deleteHelper(root, elem, xMin, xMax, yMin, yMax);
		return deleteSuccessStat;
	}
	
	// Pre:  xLo < xHi and yLo < yHi
    // Returns a collection of (references to) all elements x such that x is 
    //in the tree and x lies at coordinates within the defined rectangular 
    // region, including the boundary of the region.
    public ArrayList<T> find(long xLo, long xHi, long yLo, long yHi) {
    	foundList.clear();
    	findListHelper(root, xMin, xMax, yMin, yMax, xLo, xHi, yLo, yHi);
    	return foundList;
    }
	   
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private prQuadNode insertHelper(prQuadNode currNode, T elem, long xLo, long xHi, long yLo, long yHi) {
		//If the currentNode is a null node create new Leaf node with the new element as its element and return it
		if(currNode == null) {
			insertSuccessStat = true;
			return new prQuadLeaf(bucketSize, elem);
		}
		else if(currNode.getClass() == prQuadTree.prQuadLeaf.class) {
			//crate a temporary reference to this leaf node
			prQuadTree.prQuadLeaf tempLeaf = (prQuadTree.prQuadLeaf) currNode;
			//If this leafs bucket is full split the leaf into child leaves
			if(tempLeaf.Elements.size() == bucketSize) {
				prQuadTree.prQuadInternal tempInternal = splitLeaf(tempLeaf,xLo, xHi, yLo, yHi); //Create a new internal node to hosue the now split leaf
				insertHelper(tempInternal, elem, xLo, xHi, yLo, yHi);
				insertSuccessStat = true;
				return tempInternal;
			}
			//A split is not necessary just add it to the list of elements
			else {
				tempLeaf.Elements.add(elem);
				insertSuccessStat = true;
				return tempLeaf;
			}	
		}
		else if(currNode.getClass() == prQuadTree.prQuadInternal.class) { 
			//Calculate middle boundaries
			long xMid = (long) ((float)xHi + xLo) /2;
		    long yMid = (long) ((float)yHi + yLo) /2;
		    //Create a temporary internal node reference
		    prQuadInternal temp = (prQuadInternal) currNode;
		    //Check if new object belongs in NE,NW,SW,or NE quadrant
		    Direction insertQuad = elem.inQuadrant(xLo, xHi, yLo, yHi);
		    if(insertQuad == Direction.NE){
			    temp.NE = insertHelper(temp.NE,elem, xMid, xHi, yMid, yHi);
			    return temp;
		    }
		    else if(insertQuad == Direction.NW){
			    temp.NW = insertHelper(temp.NW,elem, xLo, xMid, yMid, yHi);
			    return temp;
		    }
		    else if(insertQuad == Direction.SW){
			    temp.SW = insertHelper(temp.SW,elem, xLo, xMid, yLo, yMid);
			    return temp;
		    }
		    else if(insertQuad == Direction.SE){
			    temp.SE = insertHelper(temp.SE,elem, xMid, xHi, yLo, yMid);
			    return temp;
		    }
		    else{
			    return null;
		    }
		}
		else {
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private T findHelper(prQuadNode currNode, T elem, long xLo, long xHi, long yLo, long yHi) {
		if(currNode == null)
			return null;
		else if(currNode.getClass() == prQuadTree.prQuadLeaf.class){
			prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNode;
			//Check every element within the list for a match
			for(int i = 0; i < currNodeLeaf.Elements.size(); ++i){
				if(currNodeLeaf.Elements.get(i).equals(elem))
					return (T) currNodeLeaf.Elements.get(i);
			}
			//No match found return null
			return null;
		}
		else if(currNode.getClass() == prQuadTree.prQuadInternal.class){
			long xMid = (long) ((float)xHi + xLo) /2;
		    long yMid = (long) ((float)yHi + yLo) /2;
			
			prQuadInternal temp = (prQuadInternal) currNode;
			//Determine which direction within this node it WOULD go, then call the helper method in that direction
			Direction elemDirection = elem.inQuadrant(xLo, xHi, yLo, yHi);
			if(elemDirection == Direction.NE){
				return findHelper(temp.NE, elem, xMid, xHi, yMid, yHi);
			}
			else if(elemDirection == Direction.NW){
				return findHelper(temp.NW, elem, xLo, xMid, yMid, yHi);
			}
			else if(elemDirection == Direction.SW){
				return findHelper(temp.SW, elem, xLo, xMid, yLo, yMid);
			}
			else if(elemDirection == Direction.SE){
				return findHelper(temp.SE, elem, xMid, xHi, yLo, yMid);
			}
			else{
				System.err.println("ERROR[FINDHELPER]: DIRECTION UNKNOWN");
				return null;
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private prQuadNode deleteHelper(prQuadNode currNode, T elem, long xLo, long xHi, long yLo, long yHi) {
		if(currNode == null || elem == null)
			return null;
		else if(currNode.getClass() == prQuadTree.prQuadLeaf.class){
			prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNode;
			for(int i = 0; i < currNodeLeaf.Elements.size(); ++i) {
				if(currNodeLeaf.Elements.get(i).equals(elem)) {
					currNodeLeaf.Elements.remove(i);
					deleteSuccessStat = true;
				}
			}
			if(currNodeLeaf.Elements.size() == 0)
				return null;
			else
				return currNode;
		}
		else if(currNode.getClass() == prQuadTree.prQuadInternal.class){
			int nonNullCount = 0;
			long xMid = (long) ((float)xHi + xLo) /2;
		    long yMid = (long) ((float)yHi + yLo) /2;
			prQuadTree.prQuadInternal currNodeInt = (prQuadTree.prQuadInternal) currNode;
			//Deterine direction and remove node
			Direction deleteQuad = elem.inQuadrant(xLo, xHi, yLo, yHi);
			if(deleteQuad == Direction.NE){
				currNodeInt.NE = deleteHelper(currNodeInt.NE, elem, xMid, xHi, yMid, yHi);
			}
			else if(deleteQuad == Direction.NW){
				currNodeInt.NW = deleteHelper(currNodeInt.NW, elem, xLo, xMid, yMid, yHi);
			}
			else if(deleteQuad == Direction.SW){
				currNodeInt.SW = deleteHelper(currNodeInt.SW, elem, xLo, xMid, yLo, yMid);
			}
			else if(deleteQuad == Direction.SE){
				currNodeInt.SE = deleteHelper(currNodeInt.SE, elem, xMid, xHi, yLo, yMid);
			}
			//Determine how many null children this node possesses
			if(currNodeInt.NE != null)
				++nonNullCount;
			if(currNodeInt.NW != null)
				++nonNullCount;
			if(currNodeInt.SW != null)
				++nonNullCount;
			if(currNodeInt.SE != null)
				++nonNullCount;
			//If there is greater than 1 non null node return return currNodeInt
			if(nonNullCount > 1){
				return currNodeInt;
			}
			//If there is one non null child return that child and that child is a leaf
			else if(nonNullCount == 1){
				if(currNodeInt.NE != null && currNodeInt.NE.getClass() == prQuadTree.prQuadLeaf.class)
					return currNodeInt.NE;
				if(currNodeInt.NW != null && currNodeInt.NW.getClass() == prQuadTree.prQuadLeaf.class)
					return currNodeInt.NW;
				if(currNodeInt.SW != null && currNodeInt.SW.getClass() == prQuadTree.prQuadLeaf.class)
					return currNodeInt.SW;
				if(currNodeInt.SE != null && currNodeInt.SE.getClass() == prQuadTree.prQuadLeaf.class)
					return currNodeInt.SE;
				return currNodeInt;
			}
			//If there are no nonNull children return null
			else {
				return null;
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void findListHelper(prQuadNode currNode,long xLo1, long xHi1, long yLo1, long yHi1, long xLo2, long xHi2, long yLo2, long yHi2) {
		if(currNode == null)
			return;
		else if(currNode.getClass() == prQuadTree.prQuadLeaf.class){
			prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNode;
			for(int i = 0; i < currNodeLeaf.Elements.size(); ++i) {
				if(currNodeLeaf.Elements.get(i).inBox(xLo2, xHi2, yLo2, yHi2))
					foundList.add(currNodeLeaf.Elements.get(i));
			}
		}
		else if(currNode.getClass() == prQuadTree.prQuadInternal.class){
			prQuadInternal currNodeInt = (prQuadTree.prQuadInternal) currNode;
			long xMid = (long) (xHi1 + xLo1) /2;
		    long yMid = (long) (yHi1 + yLo1) /2;
			if(currNodeInt.NE != null && rectangleIntersection(xMid, xHi1, yMid, yHi1, xLo2, xHi2, yLo2, yHi2))
				findListHelper(currNodeInt.NE,xMid, xHi1, yMid, yHi1, xLo2, xHi2, yLo2, yHi2);
			if(currNodeInt.NW != null && rectangleIntersection(xLo1, xMid, yMid, yHi1, xLo2, xHi2, yLo2, yHi2))
				findListHelper(currNodeInt.NW,xLo1, xMid, yMid, yHi1, xLo2, xHi2, yLo2, yHi2);
			if(currNodeInt.SW != null && rectangleIntersection(xLo1, xMid, yLo1, yMid, xLo2, xHi2, yLo2, yHi2))
				findListHelper(currNodeInt.SW,xLo1, xMid, yLo1, yMid, xLo2, xHi2, yLo2, yHi2);
			if(currNodeInt.SE != null && rectangleIntersection(xMid, xHi1, yLo1, yMid, xLo2, xHi2, yLo2, yHi2))
				findListHelper(currNodeInt.SE,xMid, xHi1, yLo1, yMid, xLo2, xHi2, yLo2, yHi2);
			return;
		}
	}
	
	private boolean rectangleIntersection(long lowerLeftX1, long upperRightX1, long lowerLeftY1, long upperRightY1, long lowerLeftX2, long upperRightX2, long lowerLeftY2, long upperRightY2) {
		if(lowerLeftX1 > upperRightX2 || lowerLeftX2 > upperRightX1)
			return false;
		
		if(lowerLeftY1 > upperRightY1 || lowerLeftY2 > upperRightY1)
			return false;
		
		return true;
	}
	
	private prQuadInternal splitLeaf(prQuadLeaf splitNode, long xHi, long xLo, long yHi, long yLo) {
		prQuadInternal retNode = new prQuadInternal();
		for(int i = 0; i < splitNode.Elements.size(); ++i) {
			insertHelper(retNode, splitNode.Elements.get(i), xHi, xLo, yHi, yLo);
		}
		return retNode;
	}
	
	public void printTree(LogFileController logFile) {
		this.logFile = logFile;
		printTreeHelper(root, "");
	}
	
	@SuppressWarnings("unchecked")
	private void printTreeHelper(prQuadNode currNode, String padding) {
		//Check for empty leaf
		if(currNode == null) {
			logFile.writeString(padding + "*");
			return;
		}
		//Check for and process SW and SE subtrees
		if(currNode.getClass() == prQuadTree.prQuadInternal.class) {
			prQuadInternal tempInternal = (prQuadInternal) currNode;
			printTreeHelper(tempInternal.SW, padding + " \t");
			printTreeHelper(tempInternal.SE, padding + "\t");
		}
		//Process currNode 
		if(currNode.getClass() == prQuadTree.prQuadInternal.class) {
			logFile.writeString(padding + "@");
		}
		else {
			prQuadLeaf tempLeaf = (prQuadLeaf) currNode;
			String tempString = padding;
			for(int i = 0; i < tempLeaf.Elements.size(); ++i) {
				tempString += tempLeaf.Elements.get(i).toString();
			}
			logFile.writeString(tempString);
		}
		//Check for and process NE and NE subtrees
		if(currNode.getClass() == prQuadTree.prQuadInternal.class) {
			prQuadInternal tempInternal = (prQuadInternal) currNode;
			printTreeHelper(tempInternal.NE, padding + "\t");
			printTreeHelper(tempInternal.NW, padding + "\t");
		}
	}
}

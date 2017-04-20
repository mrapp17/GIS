package coordIndex;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class prQuadTreeTest {
	
	@SuppressWarnings("rawtypes")
	@Test
	//Test that the root can hold up to bucketSize objects 
	public void testInsert_NoBucketSplit()  {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100);
		CoordObject testObject_2 = new CoordObject(50,50,200);
		CoordObject testObject_3 = new CoordObject(25,75,300);
		CoordObject testObject_4 = new CoordObject(75,25,400);
		CoordObject testObject_5 = new CoordObject(75,75,500);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		
		assertEquals(testTree.root.getClass(),prQuadTree.prQuadLeaf.class);
		
		prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) testTree.root;
		
		assertEquals(currNodeLeaf.Elements.get(0),testObject_1);
		assertEquals(currNodeLeaf.Elements.get(1),testObject_2);
		assertEquals(currNodeLeaf.Elements.get(2),testObject_3);
		assertEquals(currNodeLeaf.Elements.get(3),testObject_4);
		assertEquals(currNodeLeaf.Elements.get(4),testObject_5);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	//Test that the root when bucketSize + 1 objects are added it splits the root accordingly
	//This one should result in the tree growing by only 1 level
	public void testInsert_BucketSplit_Shallow()  {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100); //SW
		CoordObject testObject_2 = new CoordObject(10,10,200);
		CoordObject testObject_3 = new CoordObject(25,75,300); //NW
		CoordObject testObject_4 = new CoordObject(75,25,400); //SE
		CoordObject testObject_5 = new CoordObject(75,75,500); //NE
		CoordObject testObject_6 = new CoordObject(85,85,600);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		assertTrue(testTree.insert(testObject_6));
		
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		prQuadTree.prQuadInternal currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.NE.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.NW.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.SE.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.SW.getClass());
		prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NE;
		assertEquals(testObject_5, currNodeLeaf.Elements.get(0));
		assertEquals(testObject_6, currNodeLeaf.Elements.get(1));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NW;
		assertEquals(testObject_3, currNodeLeaf.Elements.get(0));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SE;
		assertEquals(testObject_4, currNodeLeaf.Elements.get(0));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SW;
		assertEquals(testObject_1, currNodeLeaf.Elements.get(0));
		assertEquals(testObject_2, currNodeLeaf.Elements.get(1));
		
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	//Test that the root when bucketSize + 1 objects are added it splits the root accordingly
	//This should result in the tree growing an exceptional amount 
	public void testInsert_BucketSplit_Branchy()  {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(1,1,100);
		CoordObject testObject_2 = new CoordObject(1,10,200);
		CoordObject testObject_3 = new CoordObject(10,10,300);
		CoordObject testObject_4 = new CoordObject(10,1,400);
		CoordObject testObject_5 = new CoordObject(11,11,500);
		CoordObject testObject_6 = new CoordObject(9,9,600);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		assertTrue(testTree.insert(testObject_6));
		
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		prQuadTree.prQuadInternal currNodeInternal = (prQuadTree.prQuadInternal) testTree.root; //Center 50,50
		assertEquals(null, currNodeInternal.NE);
		assertEquals(null, currNodeInternal.NW);
		assertEquals(null, currNodeInternal.SE);
		assertEquals(prQuadTree.prQuadInternal.class, currNodeInternal.SW.getClass());
		currNodeInternal = (prQuadTree.prQuadInternal) currNodeInternal.SW; //Center 25,25
		assertEquals(null, currNodeInternal.NE);
		assertEquals(null, currNodeInternal.NW);
		assertEquals(null, currNodeInternal.SE);
		assertEquals(prQuadTree.prQuadInternal.class, currNodeInternal.SW.getClass());
		currNodeInternal = (prQuadTree.prQuadInternal) currNodeInternal.SW; //Center 12,12
		assertEquals(null, currNodeInternal.NE);
		assertEquals(null, currNodeInternal.NW);
		assertEquals(null, currNodeInternal.SE);
		assertEquals(prQuadTree.prQuadInternal.class, currNodeInternal.SW.getClass());
		currNodeInternal = (prQuadTree.prQuadInternal) currNodeInternal.SW; //Center 6,6
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.NE.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.NW.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.SE.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.SW.getClass());
		
		prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NE;
		assertEquals(testObject_3, currNodeLeaf.Elements.get(0));
		assertEquals(testObject_5, currNodeLeaf.Elements.get(1));
		assertEquals(testObject_6, currNodeLeaf.Elements.get(2));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NW;
		assertEquals(testObject_2, currNodeLeaf.Elements.get(0));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SE;
		assertEquals(testObject_4, currNodeLeaf.Elements.get(0));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SW;
		assertEquals(testObject_1, currNodeLeaf.Elements.get(0));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	//Test that objects at the border of regions are inserted correctly
	public void testInsert_Borders()  {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(50,50,100); //NE
		CoordObject testObject_2 = new CoordObject(75,50,200); //NE
		CoordObject testObject_3 = new CoordObject(50,75,300); //NW
		CoordObject testObject_4 = new CoordObject(25,50,400); //SW
		CoordObject testObject_5 = new CoordObject(50,25,500); //SE
		CoordObject testObject_6 = new CoordObject(100,0,600); //SE
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		assertTrue(testTree.insert(testObject_6));
		
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		prQuadTree.prQuadInternal currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.NE.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.NW.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.SE.getClass());
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.SW.getClass());
		prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NE;
		assertEquals(testObject_1, currNodeLeaf.Elements.get(0));
		assertEquals(testObject_2, currNodeLeaf.Elements.get(1));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NW;
		assertEquals(testObject_3, currNodeLeaf.Elements.get(0));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SW;
		assertEquals(testObject_4, currNodeLeaf.Elements.get(0));
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SE;
		assertEquals(testObject_5, currNodeLeaf.Elements.get(0));
		assertEquals(testObject_6, currNodeLeaf.Elements.get(1));
	}
	
	@Test
	public void testFind_Empty() {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100);
		
		assertEquals(null, testTree.find(testObject_1));
	}

	@Test
	public void testFind_LeafRoot() {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100); //SW
		CoordObject testObject_2 = new CoordObject(10,10,200);
		CoordObject testObject_3 = new CoordObject(25,75,300); //NW
		CoordObject testObject_4 = new CoordObject(75,25,400); //SE
		CoordObject testObject_5 = new CoordObject(75,75,500); //NE
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		
		assertEquals(testObject_1, testTree.find(testObject_1));
		assertEquals(testObject_2, testTree.find(testObject_2));
		assertEquals(testObject_3, testTree.find(testObject_3));
		assertEquals(testObject_4, testTree.find(testObject_4));
		assertEquals(testObject_5, testTree.find(testObject_5));
	}
	
	@Test
	public void testFind_InternalRoot() {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100); //SW
		CoordObject testObject_2 = new CoordObject(10,10,200);
		CoordObject testObject_3 = new CoordObject(25,75,300); //NW
		CoordObject testObject_4 = new CoordObject(75,25,400); //SE
		CoordObject testObject_5 = new CoordObject(75,75,500); //NE
		CoordObject testObject_6 = new CoordObject(85,85,600);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		assertTrue(testTree.insert(testObject_6));
		
		assertEquals(testObject_1, testTree.find(testObject_1));
		assertEquals(testObject_2, testTree.find(testObject_2));
		assertEquals(testObject_3, testTree.find(testObject_3));
		assertEquals(testObject_4, testTree.find(testObject_4));
		assertEquals(testObject_5, testTree.find(testObject_5));
		assertEquals(testObject_6, testTree.find(testObject_6));
	}
	
	@Test
	public void testFind_False() {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100); //SW
		CoordObject testObject_2 = new CoordObject(10,10,200);
		CoordObject testObject_3 = new CoordObject(25,75,300); //NW
		CoordObject testObject_4 = new CoordObject(75,25,400); //SE
		CoordObject testObject_5 = new CoordObject(75,75,500); //NE
		CoordObject testObject_6 = new CoordObject(85,85,600);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		
		assertEquals(testObject_1, testTree.find(testObject_1));
		assertEquals(testObject_2, testTree.find(testObject_2));
		assertEquals(testObject_3, testTree.find(testObject_3));
		assertEquals(null, testTree.find(testObject_4));
		assertEquals(null, testTree.find(testObject_5));
		assertEquals(null, testTree.find(testObject_6));
	}
	
	@Test
	public void testDelete_Empty() {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100);
		
		assertFalse(testTree.delete(testObject_1));
	}

	@Test
	public void testDelete_LeafRoot() {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100);
		CoordObject testObject_2 = new CoordObject(50,50,200);
		CoordObject testObject_3 = new CoordObject(25,75,300);
		CoordObject testObject_4 = new CoordObject(75,25,400);
		CoordObject testObject_5 = new CoordObject(75,75,500);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		
		assertEquals(testObject_1, testTree.find(testObject_1));
		assertEquals(testObject_2, testTree.find(testObject_2));
		assertEquals(testObject_3, testTree.find(testObject_3));
		assertEquals(testObject_4, testTree.find(testObject_4));
		assertEquals(testObject_5, testTree.find(testObject_5));
		
		assertTrue(testTree.delete(testObject_1));
		assertTrue(testTree.delete(testObject_2));
		assertTrue(testTree.delete(testObject_3));
		assertTrue(testTree.delete(testObject_4));
		assertTrue(testTree.delete(testObject_5));
		
		assertEquals(null, testTree.find(testObject_1));
		assertEquals(null, testTree.find(testObject_2));
		assertEquals(null, testTree.find(testObject_3));
		assertEquals(null, testTree.find(testObject_4));
		assertEquals(null, testTree.find(testObject_5));
		
		assertFalse(testTree.delete(testObject_1));
		assertFalse(testTree.delete(testObject_2));
		assertFalse(testTree.delete(testObject_3));
		assertFalse(testTree.delete(testObject_4));
		assertFalse(testTree.delete(testObject_5));
	}
	
	@Test
	public void testDelete_Reinsert() {
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100);
		CoordObject testObject_2 = new CoordObject(50,50,200);
		CoordObject testObject_3 = new CoordObject(25,75,300);
		CoordObject testObject_4 = new CoordObject(75,25,400);
		CoordObject testObject_5 = new CoordObject(75,75,500);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		
		assertEquals(testObject_1, testTree.find(testObject_1));
		assertEquals(testObject_2, testTree.find(testObject_2));
		assertEquals(testObject_3, testTree.find(testObject_3));
		assertEquals(testObject_4, testTree.find(testObject_4));
		assertEquals(testObject_5, testTree.find(testObject_5));
		
		assertTrue(testTree.delete(testObject_1));
		assertTrue(testTree.delete(testObject_2));
		assertTrue(testTree.delete(testObject_3));
		assertTrue(testTree.delete(testObject_4));
		assertTrue(testTree.delete(testObject_5));
		
		assertEquals(null, testTree.find(testObject_1));
		assertEquals(null, testTree.find(testObject_2));
		assertEquals(null, testTree.find(testObject_3));
		assertEquals(null, testTree.find(testObject_4));
		assertEquals(null, testTree.find(testObject_5));
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		
		assertEquals(testObject_1, testTree.find(testObject_1));
		assertEquals(testObject_2, testTree.find(testObject_2));
		assertEquals(testObject_3, testTree.find(testObject_3));
		assertEquals(testObject_4, testTree.find(testObject_4));
		assertEquals(testObject_5, testTree.find(testObject_5));
	}
	
	
	@Test
	public void testFindList_World() {
		ArrayList<CoordObject> testArrayList = new ArrayList<CoordObject>();
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100); //SW
		CoordObject testObject_2 = new CoordObject(10,10,200);
		CoordObject testObject_3 = new CoordObject(25,75,300); //NW
		CoordObject testObject_4 = new CoordObject(75,25,400); //SE
		CoordObject testObject_5 = new CoordObject(75,75,500); //NE
		CoordObject testObject_6 = new CoordObject(85,85,600);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		assertTrue(testTree.insert(testObject_6));	
		
		testArrayList = testTree.find(0, 100, 0, 100);
		
		assertTrue(testArrayList.contains(testObject_1));
		assertTrue(testArrayList.contains(testObject_2));
		assertTrue(testArrayList.contains(testObject_3));
		assertTrue(testArrayList.contains(testObject_4));
		assertTrue(testArrayList.contains(testObject_5));
		assertTrue(testArrayList.contains(testObject_6));
		
		testArrayList = testTree.find(0, 1, 0, 1);
		
		assertFalse(testArrayList.contains(testObject_1));
		assertFalse(testArrayList.contains(testObject_2));
		assertFalse(testArrayList.contains(testObject_3));
		assertFalse(testArrayList.contains(testObject_4));
		assertFalse(testArrayList.contains(testObject_5));
		assertFalse(testArrayList.contains(testObject_6));
	}
	
	@Test
	public void testFindList_Border() {
		ArrayList<CoordObject> testArrayList = new ArrayList<CoordObject>();
		prQuadTree<CoordObject> testTree = new prQuadTree<CoordObject>(0, 100, 0, 100, 5);
		CoordObject testObject_1 = new CoordObject(25,25,100); //SW
		CoordObject testObject_2 = new CoordObject(10,10,200);
		CoordObject testObject_3 = new CoordObject(25,75,300); //NW
		CoordObject testObject_4 = new CoordObject(75,25,400); //SE
		CoordObject testObject_5 = new CoordObject(75,75,500); //NE
		CoordObject testObject_6 = new CoordObject(85,85,600);
		
		assertTrue(testTree.insert(testObject_1));
		assertTrue(testTree.insert(testObject_2));
		assertTrue(testTree.insert(testObject_3));
		assertTrue(testTree.insert(testObject_4));
		assertTrue(testTree.insert(testObject_5));
		assertTrue(testTree.insert(testObject_6));	
		
		testArrayList = testTree.find(20, 25, 20, 25);
		assertTrue(testArrayList.contains(testObject_1));
		assertEquals(1, testArrayList.size());
		
		testArrayList = testTree.find(0, 10, 0, 10);
		assertTrue(testArrayList.contains(testObject_2));
		assertEquals(1, testArrayList.size());
		
		testArrayList = testTree.find(24, 25, 74, 75);
		assertTrue(testArrayList.contains(testObject_3));
		assertEquals(1, testArrayList.size());
		
		testArrayList = testTree.find(74, 75, 24, 25);
		assertTrue(testArrayList.contains(testObject_4));
		assertEquals(1, testArrayList.size());
		
		testArrayList = testTree.find(74, 75, 74, 75);
		assertTrue(testArrayList.contains(testObject_5));
		assertEquals(1, testArrayList.size());
		
		testArrayList = testTree.find(84, 85, 84, 85);
		assertTrue(testArrayList.contains(testObject_6));
		assertEquals(1, testArrayList.size());	
	}
}

package nameIndex;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashTableTest {

	@Test
	public void testInsert() {
		HashTable<String,Integer> testHash = new HashTable<String,Integer>(9);
		assertTrue(testHash.insert("Test1", 100));
		assertTrue(testHash.insert("Test2", 200));
		assertTrue(testHash.insert("Test3", 300));
		assertTrue(testHash.insert("Test4", 400));
		assertEquals(4,testHash.currentCount);
		
		assertEquals(100, (int)testHash.find("Test1"));
		assertEquals(200, (int)testHash.find("Test2"));
		assertEquals(300, (int)testHash.find("Test3"));
		assertEquals(400, (int)testHash.find("Test4"));
		assertFalse(testHash.insert("Test1", 100));
		assertFalse(testHash.insert("Test2", 200));
		assertFalse(testHash.insert("Test3", 300));
		assertFalse(testHash.insert("Test4", 400));
		assertEquals(4,testHash.currentCount);
		
		assertEquals(100, (int)testHash.find("Test1"));
		assertEquals(200, (int)testHash.find("Test2"));
		assertEquals(300, (int)testHash.find("Test3"));
		assertEquals(400, (int)testHash.find("Test4"));
	}

	@Test
	public void testRemove() {
		HashTable<String,Integer> testHash = new HashTable<String,Integer>(9);
		testHash.insert("Test1", 100);
		testHash.insert("Test2", 200);
		testHash.insert("Test3", 300);
		testHash.insert("Test4", 400);
		assertEquals(4,testHash.currentCount);
		
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(new Integer(200), testHash.find("Test2"));
		assertEquals(new Integer(300), testHash.find("Test3"));
		assertEquals(new Integer(400), testHash.find("Test4"));
		
		testHash.remove("Test4");
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(new Integer(200), testHash.find("Test2"));
		assertEquals(new Integer(300), testHash.find("Test3"));
		assertEquals(null, testHash.find("Test4"));
		assertEquals(3,testHash.currentCount);
		
		testHash.remove("Test2");
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(null, testHash.find("Test2"));
		assertEquals(new Integer(300), testHash.find("Test3"));
		assertEquals(null, testHash.find("Test4"));
		assertEquals(2,testHash.currentCount);
		
		testHash.remove("Test3");
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(null, testHash.find("Test2"));
		assertEquals(null, testHash.find("Test3"));
		assertEquals(null, testHash.find("Test4"));
		assertEquals(null, testHash.find("Test5"));
		assertEquals(null, testHash.find("Test6"));
		assertEquals(1,testHash.currentCount);
		
		testHash.remove("Test1");
		assertEquals(null, testHash.find("Test1"));
		assertEquals(null, testHash.find("Test2"));
		assertEquals(null, testHash.find("Test3"));
		assertEquals(null, testHash.find("Test4"));
		assertEquals(0,testHash.currentCount);
		
		assertTrue(testHash.insert("Test1", 100));
		assertTrue(testHash.insert("Test2", 200));
		assertTrue(testHash.insert("Test3", 300));
		assertTrue(testHash.insert("Test4", 400));
		assertEquals(4,testHash.currentCount);
	}

	@Test
	public void testFind() {
		HashTable<String,Integer> testHash = new HashTable<String,Integer>(9);
		testHash.insert("Test1", 100);
		testHash.insert("Test2", 200);
		testHash.insert("Test3", 300);
		testHash.insert("Test4", 400);
		
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(new Integer(200), testHash.find("Test2"));
		assertEquals(new Integer(300), testHash.find("Test3"));
		assertEquals(new Integer(400), testHash.find("Test4"));
		assertEquals(null, testHash.find("Test5"));
		assertEquals(null, testHash.find("Test6"));
		
		
		//Add a find check after deleting them one by one 
		testHash.remove("Test4");
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(new Integer(200), testHash.find("Test2"));
		assertEquals(new Integer(300), testHash.find("Test3"));
		assertEquals(null, testHash.find("Test4"));
		assertEquals(null, testHash.find("Test5"));
		assertEquals(null, testHash.find("Test6"));
		
		testHash.remove("Test2");
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(null, testHash.find("Test2"));
		assertEquals(new Integer(300), testHash.find("Test3"));
		assertEquals(null, testHash.find("Test4"));
		assertEquals(null, testHash.find("Test5"));
		assertEquals(null, testHash.find("Test6"));
		
		testHash.remove("Test3");
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(null, testHash.find("Test2"));
		assertEquals(null, testHash.find("Test3"));
		assertEquals(null, testHash.find("Test4"));
		assertEquals(null, testHash.find("Test5"));
		assertEquals(null, testHash.find("Test6"));
		
		testHash.remove("Test1");
		assertEquals(null, testHash.find("Test1"));
		assertEquals(null, testHash.find("Test2"));
		assertEquals(null, testHash.find("Test3"));
		assertEquals(null, testHash.find("Test4"));
		assertEquals(null, testHash.find("Test5"));
		assertEquals(null, testHash.find("Test6"));
	}
	
	@Test
	public void testRehash() {
		HashTable<String,Integer> testHash = new HashTable<String,Integer>(10);
		assertTrue(testHash.insert("Test1", 100));
		assertTrue(testHash.insert("Test2", 200));
		assertTrue(testHash.insert("Test3", 300));
		assertTrue(testHash.insert("Test4", 400));
		assertTrue(testHash.insert("Test5", 500));
		assertTrue(testHash.insert("Test6", 600));
		assertTrue(testHash.insert("Test7", 700));
		assertEquals(7,testHash.currentCount);
		assertEquals(10,testHash.currentSize);
		
		assertTrue(testHash.insert("Test8", 800));
		assertEquals(8,testHash.currentCount);
		assertEquals(20,testHash.currentSize);
		
		//Ensure we can still find the elements
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(new Integer(200), testHash.find("Test2"));
		assertEquals(new Integer(300), testHash.find("Test3"));
		assertEquals(new Integer(400), testHash.find("Test4"));
		assertEquals(new Integer(500), testHash.find("Test5"));
		assertEquals(new Integer(600), testHash.find("Test6"));
		assertEquals(new Integer(700), testHash.find("Test7"));
		assertEquals(new Integer(800), testHash.find("Test8"));
		
		testHash.remove("Test3");
		assertEquals(7,testHash.currentCount);
		assertEquals(20,testHash.currentSize);
		
		//Ensure we can still find the elements
		assertEquals(new Integer(100), testHash.find("Test1"));
		assertEquals(new Integer(200), testHash.find("Test2"));
		assertEquals(null, testHash.find("Test3"));
		assertEquals(new Integer(400), testHash.find("Test4"));
		assertEquals(new Integer(500), testHash.find("Test5"));
		assertEquals(new Integer(600), testHash.find("Test6"));
		assertEquals(new Integer(700), testHash.find("Test7"));
		assertEquals(new Integer(800), testHash.find("Test8"));
	}
}

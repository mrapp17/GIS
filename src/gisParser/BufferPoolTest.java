package gisParser;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class BufferPoolTest {

	@Test
	public void testInsert() {
		Random RNG = new Random();
		GISObject[]  testObjects = new GISObject[20];
		BufferPool testPool = new BufferPool();
		for(int i = 0; i < 20; ++i) {
			testObjects[i] = new GISObject(Math.abs(RNG.nextInt()),null);
		}
		for(int i = 0; i < 15; ++i) {
			testPool.insert(testObjects[i]);
		}
		
		for(int i = 0; i < 15; ++i) {
			assertEquals(testObjects[14 - i], testPool.buffer[i]);
		}
		
		for(int i = 15; i < 20; ++i) {
			testPool.insert(testObjects[i]);
		}
		
		for(int i = 0; i < 15; ++i) {
			assertEquals(testObjects[19 - i], testPool.buffer[i]);
		}
		
		testPool.insert(testObjects[0]);
		assertEquals(testObjects[0],testPool.buffer[0]);
	}

	@Test
	public void testFind() {
		Random RNG = new Random();
		GISObject[]  testObjects = new GISObject[15];
		BufferPool testPool = new BufferPool();
		for(int i = 0; i < 15; ++i) {
			testObjects[i] = new GISObject(Math.abs(RNG.nextInt()),null);
		}
		
		for(int i = 0; i < 15; ++i) {
			testPool.insert(testObjects[i]);
		}
		
		for(int i = 0; i < 15; ++i ) {
			assertEquals(testObjects[i], testPool.find(testObjects[i].offset));
		}
	}
	
	@Test
	public void testFindIndex() {
		Random RNG = new Random();
		GISObject[]  testObjects = new GISObject[15];
		BufferPool testPool = new BufferPool();
		for(int i = 0; i < 15; ++i) {
			testObjects[i] = new GISObject(Math.abs(RNG.nextInt()),null);
		}
		
		for(int i = 0; i < 15; ++i) {
			testPool.insert(testObjects[i]);
		}
		
		for(int i = 0; i < 15; ++i ) {
			assertEquals(14 - i, testPool.findIndex(testObjects[i].offset));
		}
	}

}

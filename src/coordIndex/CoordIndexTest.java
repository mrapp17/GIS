package coordIndex;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.RandomAccessFile;

public class CoordIndexTest {
//
//	@Test
//	public void testLongStringToInt() {
//		String testLong1 = "0794130W";
//		String testLong2 = "0792630W";
//		assertEquals(-286890,CoordIndex.longStringToInt(testLong1));
//		assertEquals(-285990,CoordIndex.longStringToInt(testLong2));
//	}
//	
//	@Test
//	public void testLatStringToInt() {
//		String testLat1 = "381500N";
//		String testLat2 = "383000N";
//		assertEquals(137700,CoordIndex.latStringToInt(testLat1));
//		assertEquals(138600,CoordIndex.latStringToInt(testLat2));
//	}
//	
//	@Test
//	public void testInsertAllElements() {
//		String line;
//		String[] splitLine;
//		int currentEOFOffset = 0;
//		int insertCount = 0;
//		try {
//			RandomAccessFile testDataFile = new RandomAccessFile("CO_All.txt","r");
//			CoordIndex testCoordIndex = new CoordIndex(4);
//			testCoordIndex.setWorld(-3000000, 3000000, -700000, 700000);
//			line = testDataFile.readLine();
//			line = testDataFile.readLine();
//			while(line != null) {
//				splitLine = line.split("[|]");
//				if(splitLine[8].equals("Unknown") || splitLine[7].equals("Unknown"))
//					assertFalse(testCoordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset));
//				else {
//					++insertCount;
//					assertTrue(testCoordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset));
//				}
//				currentEOFOffset = (int) testDataFile.getFilePointer();
//				line = testDataFile.readLine();
//			}
//			assertEquals(49334, insertCount);
//			testDataFile.close();
//		} 
//		catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//	}
//	
//	@Test
//	public void testInsertHalfElements() {
//		String line;
//		String[] splitLine;
//		int currentEOFOffset = 0;
//		int insertCount = 0;
//		try {
//			RandomAccessFile testDataFile = new RandomAccessFile("VA_Monterey.txt","r");
//			CoordIndex testCoordIndex = new CoordIndex(4);
//			assertTrue(testCoordIndex.setWorld(-794130, -793240, 381500, 382250));
//			line = testDataFile.readLine();
//			line = testDataFile.readLine();
//			while(line != null) {
//				splitLine = line.split("[|]");
//				if(splitLine[8].equals("Unknown") || splitLine[7].equals("Unknown"))
//					assertFalse(testCoordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset));
//				else if (testCoordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset)) {
//					++insertCount;
//				}
//				currentEOFOffset = (int) testDataFile.getFilePointer();
//				line = testDataFile.readLine();
//			}
//			assertTrue(insertCount < 63);
//			testDataFile.close();
//		} 
//		catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//	}
//
//	@Test
//	public void testFindIn() {
//		String line;
//		String[] splitLine;
//		int currentEOFOffset = 0;
//		try {
//			RandomAccessFile testDataFile = new RandomAccessFile("VA_Monterey.txt","r");
//			CoordIndex testCoordIndex = new CoordIndex(4);
//			assertTrue(testCoordIndex.setWorld(-794130, -792630, 381500, 383000));
//			line = testDataFile.readLine();
//			line = testDataFile.readLine();
//			while(line != null) {
//				splitLine = line.split("[|]");
//				if(splitLine[8].equals("Unknown") || splitLine[7].equals("Unknown"))
//					assertFalse(testCoordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset));
//				else
//					testCoordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset);
//				currentEOFOffset = (int) testDataFile.getFilePointer();
//				line = testDataFile.readLine();
//			}
//			testDataFile.close();
//			
//			assertEquals(new Integer(3593), testCoordIndex.findIn(-793109, 382148, 60, 60).get(0));
//			
//			assertEquals(4, testCoordIndex.findIn(-793109, 382148, 180, 180).size());
//			assertEquals(new Integer(6693), testCoordIndex.findIn(-793109, 382148, 180, 180).get(0));
//			assertEquals(new Integer(3593), testCoordIndex.findIn(-793109, 382148, 180, 180).get(1));
//			assertEquals(new Integer(4530), testCoordIndex.findIn(-793109, 382148, 180, 180).get(2));
//			assertEquals(new Integer(6434), testCoordIndex.findIn(-793109, 382148, 180, 180).get(3));
//			
//			assertEquals(6, testCoordIndex.findIn(-793109, 382148, 300, 300).size());
//			assertEquals(new Integer(6693), testCoordIndex.findIn(-793109, 382148, 180, 180).get(0));
//			assertEquals(new Integer(3593), testCoordIndex.findIn(-793109, 382148, 180, 180).get(1));
//			assertEquals(new Integer(4530), testCoordIndex.findIn(-793109, 382148, 180, 180).get(2));
//			assertEquals(new Integer(6434), testCoordIndex.findIn(-793109, 382148, 180, 180).get(3));
//		} 
//		catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//	}
//
//	@Test
//	public void testFindAt() {
//		String line;
//		String[] splitLine;
//		int currentEOFOffset = 0;
//		try {
//			RandomAccessFile testDataFile = new RandomAccessFile("VA_Monterey.txt","r");
//			CoordIndex testCoordIndex = new CoordIndex(4);
//			assertTrue(testCoordIndex.setWorld(-794130, -792630, 381500, 383000));
//			line = testDataFile.readLine();
//			line = testDataFile.readLine();
//			while(line != null) {
//				splitLine = line.split("[|]");
//				if(splitLine[8].equals("Unknown") || splitLine[7].equals("Unknown"))
//					assertFalse(testCoordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset));
//				else
//					testCoordIndex.insert(splitLine[8], splitLine[7], currentEOFOffset);
//				currentEOFOffset = (int) testDataFile.getFilePointer();
//				line = testDataFile.readLine();
//			}
//			testDataFile.close();
//			
//			assertEquals(new Integer(6940), testCoordIndex.findAt(-793156, 382812).get(0));
//			assertEquals(new Integer(4530), testCoordIndex.findAt(-793031, 382145).get(0));
//			assertEquals(new Integer(8792), testCoordIndex.findAt(-793451, 382442).get(0));
//			assertEquals(new Integer(0), testCoordIndex.findAt(-793312, 382607).get(0));
//			//Two elements match here
//			assertEquals(new Integer(1822), testCoordIndex.findAt(-793031, 382856).get(0));
//			assertEquals(new Integer(5102), testCoordIndex.findAt(-793031, 382856).get(1));
//		} 
//		catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//	}

}

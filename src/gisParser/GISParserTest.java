package gisParser;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.junit.Test;

import coordIndex.CoordIndex;
import logFile.LogFileController;
import nameIndex.NameIndex;

public class GISParserTest {
//
//	@Test
//	public void testGISParser() throws FileNotFoundException {
//		BufferPool testPool = new BufferPool();
//		NameIndex testNameIndex = new NameIndex(1024);
//		CoordIndex testCoordIndex = new CoordIndex(4);
//		testCoordIndex.setWorld(-286890, -285990, 137700, 138600);
//		RandomAccessFile dataFile = new RandomAccessFile("GISParserTest.txt","rw");
//		LogFileController testLogController = new LogFileController("GISParserTestLog.txt");
//		GISParser testParser = new GISParser(testPool, testNameIndex, testCoordIndex, dataFile, testLogController);
//		assertEquals(true,testParser.importGIS("VA_Monterey.txt"));
//		
//		testNameIndex.debug(testLogController);
//		
//		testCoordIndex.debug(testLogController);
//		
//		ArrayList<Integer> offsetList = new ArrayList<Integer>();
//		offsetList.add(1557);
//		offsetList.add(4837);
//		testParser.whatIsAt(offsetList, "382856N",  "0793031W");
//		
//		testParser.bufferPool.debug(testLogController);
//		
//		offsetList.clear();
//		offsetList.add(2897); //KEY RUN
//		testParser.whatIs(offsetList);
//		
//		testParser.bufferPool.debug(testLogController);
//		
//		offsetList.add(8528); //Town of Monterey
//		testParser.whatIs(offsetList);
//		
//		testParser.bufferPool.debug(testLogController);
//		
//		offsetList.clear();
//		offsetList.add(3328);
//		offsetList.add(4265);
//		offsetList.add(1096);
//		
//		testParser.bufferPool.debug(testLogController);
//		
//		testParser.whatIsIn(offsetList, "0793109W", "382148N", 60, 60, false, Filter.WATER);
//		
//		testParser.bufferPool.debug(testLogController);
//		
//		testParser.whatIsIn(offsetList, "0793109W", "382148N", 60, 60, false, Filter.NOFILTER);
//		
//		testParser.bufferPool.debug(testLogController);
//		
//		testParser.whatIsIn(offsetList, "0793109W", "382148N", 60, 60, true, Filter.NOFILTER);
//		
//		testParser.bufferPool.debug(testLogController);
//		
//		
//	}
}

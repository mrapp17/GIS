package logFile;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import gisParser.GISObject;

public class LogFileControllerTest {
	static LogFileController testController;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testController = new LogFileController("logFileTest.txt");		
	}

	@Test
	public void testWriteHeader() {
		testController.writeString("Header Test\n");
		testController.writeHeader("dbFile.txt", "scriptFile.txt", "logFile.txt");
	}

	@Test
	public void testWriteWorld() {
		testController.writeString("World Test\n");
		testController.writeWorld(-56789, 56789, -98765, 98765);
	}

	@Test
	public void testWriteImport() {
		testController.writeString("Import Test\n");
		testController.writeImport("TestFile.txt", 63, 3, 63);
	}

	@Test
	public void testWriteWhatIsIn() {
		testController.writeString("WhatIsIn Test\n");
		ArrayList<GISObject> testList = new ArrayList<GISObject>(5);
		testList.add(new GISObject(345,"12522|Toh Dahstini Wash|Stream|CO|08|Montezuma|083|370035N|1090235W|37.0097198|-109.0431593|365528N|1091100W|36.9244436|-109.1834384|1410|4626|Yellow Rock Point East|10/13/1978|"));
		testList.add(new GISObject(897,"12550|Tohache Wash|Stream|AZ|04|Apache|001|370002N|1090139W|37.0005556|-109.0275|365523N|1090500W|36.9230542|-109.083436|1408|4619|Yellow Rock Point East|02/08/1980|06/15/2009"));
		testList.add(new GISObject(1054,"169423|Beaver Ponds Picnic Area|Locale|CO|08|Gunnison|051|Unknown|Unknown|0|0|||||||Unknown|09/01/1992|"));
		testList.add(new GISObject(1257,"169432|Meadow Creek Reservoir|Reservoir|CO|08|Grand|049|400318N|1054451W|40.0549592|-105.7474478|||||3049|10003|Monarch Lake|09/01/1992|06/17/2011"));
		testList.add(new GISObject(1458,"169541|Shadow Mountain National Recreation Area|Park|CO|08|Grand|049|401100N|1055102W|40.1833188|-105.8505677|||||2612|8569|Shadow Mountain|10/13/1978|"));
		testController.writeWhatIsIn(testList, true, "382148N", "0793109W", 15, 15);
		testController.writeWhatIsIn(testList, false, "382148N", "0793109W", 15, 15);
	}

	@Test
	public void testWriteWhatIsAt() {
		testController.writeString("WhatIsAt Test\n");
		ArrayList<GISObject> testList = new ArrayList<GISObject>(5);
		testList.add(new GISObject(345,"12522|Toh Dahstini Wash|Stream|CO|08|Montezuma|083|370035N|1090235W|37.0097198|-109.0431593|365528N|1091100W|36.9244436|-109.1834384|1410|4626|Yellow Rock Point East|10/13/1978|"));
		testList.add(new GISObject(897,"12550|Tohache Wash|Stream|AZ|04|Apache|001|370002N|1090139W|37.0005556|-109.0275|365523N|1090500W|36.9230542|-109.083436|1408|4619|Yellow Rock Point East|02/08/1980|06/15/2009"));
		testList.add(new GISObject(1054,"169423|Beaver Ponds Picnic Area|Locale|CO|08|Gunnison|051|Unknown|Unknown|0|0|||||||Unknown|09/01/1992|"));
		testList.add(new GISObject(1257,"169432|Meadow Creek Reservoir|Reservoir|CO|08|Grand|049|400318N|1054451W|40.0549592|-105.7474478|||||3049|10003|Monarch Lake|09/01/1992|06/17/2011"));
		testList.add(new GISObject(1458,"169541|Shadow Mountain National Recreation Area|Park|CO|08|Grand|049|401100N|1055102W|40.1833188|-105.8505677|||||2612|8569|Shadow Mountain|10/13/1978|"));
		testController.writeWhatIsAt(testList, "382148N", "0793109W");
	}

	@Test
	public void testWriteWhatIs() {
		testController.writeString("WhatIs Test\n");
		ArrayList<GISObject> testList = new ArrayList<GISObject>(5);
		testList.add(new GISObject(345,"12522|Toh Dahstini Wash|Stream|CO|08|Montezuma|083|370035N|1090235W|37.0097198|-109.0431593|365528N|1091100W|36.9244436|-109.1834384|1410|4626|Yellow Rock Point East|10/13/1978|"));
		testList.add(new GISObject(897,"12550|Tohache Wash|Stream|AZ|04|Apache|001|370002N|1090139W|37.0005556|-109.0275|365523N|1090500W|36.9230542|-109.083436|1408|4619|Yellow Rock Point East|02/08/1980|06/15/2009"));
		testList.add(new GISObject(1054,"169423|Beaver Ponds Picnic Area|Locale|CO|08|Gunnison|051|Unknown|Unknown|0|0|||||||Unknown|09/01/1992|"));
		testList.add(new GISObject(1257,"169432|Meadow Creek Reservoir|Reservoir|CO|08|Grand|049|400318N|1054451W|40.0549592|-105.7474478|||||3049|10003|Monarch Lake|09/01/1992|06/17/2011"));
		testList.add(new GISObject(1458,"169541|Shadow Mountain National Recreation Area|Park|CO|08|Grand|049|401100N|1055102W|40.1833188|-105.8505677|||||2612|8569|Shadow Mountain|10/13/1978|"));
		testController.writeWhatIs(testList);	
	}

	@Test
	public void testWriteQuit() {
		testController.writeString("Quit Test\n");
		testController.writeQuit();
	}

}

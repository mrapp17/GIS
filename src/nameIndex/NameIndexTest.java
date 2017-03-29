package nameIndex;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class NameIndexTest {

	@Test
	public void testIndexName() {
		String[] testKeys1 = new String[5];
		String[] testKeys2 = new String[5];
		int[] testValues = new int[20];

		testKeys1[0] = "River";
		testKeys1[1] = "Mountain";
		testKeys1[2] = "Lake";
		testKeys1[3] = "Hill";
		testKeys1[4] = "City";
		
		testKeys2[0] = "VA";
		testKeys2[1] = "WV";
		testKeys2[2] = "WA";
		testKeys2[3] = "NJ";
		testKeys2[4] = "TX";
		
		testValues[0] = 95;
		testValues[1] = 195;
		testValues[2] = 295;
		testValues[3] = 395;
		testValues[4] = 495;
		testValues[5] = 595;
		testValues[6] = 695;
		testValues[7] = 795;
		testValues[8] = 895;
		testValues[9] = 995;
		testValues[10] = 174;
		testValues[11] = 274;
		testValues[12] = 374;
		testValues[13] = 474;
		testValues[14] = 574;
		testValues[15] = 674;
		testValues[16] = 774;
		testValues[17] = 874;
		testValues[18] = 974;
		testValues[19] = 222;
		NameIndex testIndex = new NameIndex();
		int numIndex = 0;
		for(int i = 0; i < 5; ++i){
			for(int j = 0; j < 5; ++j) {
				assertTrue(testIndex.indexName(testKeys1[i], testKeys2[j], numIndex++));
			}
		}
	}

	@Test
	public void testGetOffset() {
		String[] testKeys1 = new String[5];
		String[] testKeys2 = new String[5];
		int[] testValues = new int[25];

		Random randNumGen = new Random();
		testKeys1[0] = "River";
		testKeys1[1] = "Mountain";
		testKeys1[2] = "Lake";
		testKeys1[3] = "Hill";
		testKeys1[4] = "City";
		
		testKeys2[0] = "VA";
		testKeys2[1] = "WV";
		testKeys2[2] = "WA";
		testKeys2[3] = "NJ";
		testKeys2[4] = "TX";
		
		for(int i = 0; i < 25; ++i) {
			testValues[i] = randNumGen.nextInt();
		}

		NameIndex testIndex = new NameIndex();
		int numIndex = 0;
		for(int i = 0; i < 5; ++i){
			for(int j = 0; j < 5; ++j) {
				assertTrue(testIndex.indexName(testKeys1[i], testKeys2[j], testValues[numIndex++]));
			}
		}
		
		numIndex = 0;
		for(int i = 0; i < 5; ++i){
			for(int j = 0; j < 5; ++j) {
				assertEquals(testValues[numIndex++], testIndex.getOffset(testKeys1[i], testKeys2[j]));
			}
		}
	}

}

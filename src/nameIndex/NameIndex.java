package nameIndex;

import java.util.ArrayList;

import logFile.LogFileController;

//Has a data member which is a hash table of all the elements that have been indexed into it

public class NameIndex {
	private LogFileController logFile;
	HashTable<String, Integer> nameTable;

	public NameIndex(int size, LogFileController logFile) {
		nameTable = new HashTable<String, Integer>(size);
		this.logFile = logFile;
	}
	
	public int indexName(String feature, String state, int offset) {
		return nameTable.insert(feature+ " " +state,offset);
	}
	
	public ArrayList<Integer> getOffset(String feature, String state) {
		ArrayList<Integer> retval = nameTable.find(feature+":"+state);
		if(retval == null)
			return null;
		
		return retval;
	}
	
	public void debug() {
		nameTable.displayTable(logFile);
	}
}

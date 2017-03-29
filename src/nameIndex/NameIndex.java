package nameIndex;

//Has a data member which is a hash table of all the elements that have been indexed into it

public class NameIndex {
	HashTable<String, Integer> nameTable;

	public NameIndex() {
		nameTable = new HashTable<String, Integer>(10);
	}
	
	public boolean indexName(String feature, String state, int offset) {
		return nameTable.insert(feature+state,offset);
	}
	
	public int getOffset(String feature, String state) {
		Integer retval = (Integer) nameTable.find(feature+state);
		if(retval == null)
			return -1;
		
		return retval.intValue();
	}
	
	//IMPLEMENT DEBUG 
	public void debug() {
		
	}
}

package nameIndex;

import java.io.RandomAccessFile;

//Given a string this table should use the elfHash algorithm to hash the string into a key and insert it into the table
//The table should rehash when its 70% occupied
public class HashTable<K,V> {
	
	@SuppressWarnings("hiding")
	//Internal class that represents the elements contained within the table
	class HashElement<K,V> {
		K key;
		V value;
		boolean isGrave;
		
		public HashElement(K k, V v) {
			key = k;
			value = v;
		}
	}
	
	HashElement<K,V>[] dataList;
	int currentSize;
	int currentCount;
	
	@SuppressWarnings("unchecked")
	public HashTable(int size) {
		dataList = new HashElement[size];
		currentSize = size;
		currentCount = 0;
	}
	
	public boolean insert(K key, V value) {
		//If the key is within the list return false
		if(find(key) != null)
			return false;

		//Create new HashElement to insert into table
		HashElement<K,V> elem = new HashElement<K,V>(key, value);
		
		//Determine if the table must resize
		if(currentCount >= currentSize * 0.7)
			rehash();

		//Calculate the insert index
		long hash = elfHash(key);
		int index = (int) (hash % currentSize);
		
		//Iterate through up to the size of the table + 1
		for(int i = 0; i < currentCount + 1; ++i) {
			//If the currently examine position is a null or is a grave insert here
			if(dataList[index] == null || dataList[index].isGrave) {
				dataList[index] = elem;
				++currentCount;
				return true;
			}
			else {
				index += ((i*i) + i)/2;
				index = index % currentSize;
			}
		}
		//This should never happen
		return false;
	}
	
	public boolean remove(K key) {
		long hash = elfHash(key);
		int index = (int) (hash % currentSize);
		for(int i = 0; i < currentCount; ++i) {
			if(dataList[index] == null)
				return false;
			else if(dataList[index].key == key && !dataList[index].isGrave) {
				dataList[index].isGrave = true;
				--currentCount;
				return true;
			}
			else {
				index += ((i*i) + i)/2;
				index = index % currentSize;
			}
		}
		return false;
	}
	
	
	public V find(K key) {
		long hash = elfHash(key);
		int index = (int) (hash % currentSize);
		
		for(int i = 0; i < currentCount; ++i) {
			if(dataList[index] == null)
				return null;
			else if(dataList[index].key.equals(key) && !dataList[index].isGrave)
				return dataList[index].value;
			else {
				index += ((i*i) + i)/2;
				index = index % currentSize;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	void rehash() {
		
		int tempSize = currentSize;
		currentSize = currentSize * 2;
		currentCount = 0;
		HashElement<K,V>[] oldDataList = dataList;
		dataList = new HashElement[currentSize * 2];
		
		for(int i = 0; i < tempSize; ++i) {
			if(oldDataList[i] != null && !oldDataList[i].isGrave)
				insert(oldDataList[i].key, oldDataList[i].value);
		}		
	}
	
	private long elfHash(K key) {
		long hashValue = 0;
		String tempKey = (String) key;
	  	for (int Pos = 0; Pos < tempKey.length(); Pos++) {
		     hashValue = (hashValue << 4) + tempKey.charAt(Pos);
		     long hiBits = hashValue & 0xF000000000000000L;
		         if (hiBits != 0)
		            hashValue ^= hiBits >> 56;
		     hashValue &= ~hiBits;
		}		
	  return ( hashValue & 0x0FFFFFFFFFFFFFFFL );
	}

	public void displayTable(RandomAccessFile logFile) {
		
	}
}


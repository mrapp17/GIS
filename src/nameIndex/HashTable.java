package nameIndex;

import java.util.ArrayList;

import logFile.LogFileController;

//Given a string this table should use the elfHash algorithm to hash the string into a key and insert it into the table
//The table should rehash when its 70% occupied
public class HashTable<K,V> {
	
	@SuppressWarnings("hiding")
	//Internal class that represents the elements contained within the table
	class HashElement<K,V> {
		K key;
		ArrayList<V> valueList;
		boolean isGrave;
		
		public HashElement(K k, V v) {
			key = k;
			valueList = new ArrayList<V>(0);
			valueList.add(v);
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
	
	public int insert(K key, V value) {
		//Determine if the table must resize
		if(currentCount >= currentSize * 0.7)
			rehash();

		//Calculate the insert index
		long hash = elfHash(key);
		int index = (int) (hash % currentSize);
		
		for(int i = 1; i <= currentCount + 1; ++i) {
			//If the currently polled element matches the key append 
			if(dataList[index] != null && dataList[index].key.equals(key)) {
				dataList[index].valueList.add(value);
				++currentCount;
				return i;
			}
			//If the currently examine position is a null or is a grave insert here
			if(dataList[index] == null || dataList[index].isGrave) {
				HashElement<K,V> elem = new HashElement<K,V>(key, value);
				dataList[index] = elem;
				++currentCount;
				return i;
			}
			else {
				index += ((i*i) + i)/2;
				index = index % currentSize;
			}
		}
		//This should never happen
		return -1;
	}
	
	public boolean remove(K key) {
		long hash = elfHash(key);
		int index = (int) (hash % currentSize);
		for(int i = 1; i <= currentCount; ++i) {
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
	
	
	public ArrayList<V> find(K key) {
		long hash = elfHash(key);
		int index = (int) (hash % currentSize);
		
		for(int i = 1; i <= currentCount; ++i) {
			if(dataList[index] == null)
				return null;
			else if(dataList[index].key.equals(key) && !dataList[index].isGrave)
				return dataList[index].valueList;
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
				for(int j =0; j < oldDataList[i].valueList.size(); ++j)
					insert(oldDataList[i].key, oldDataList[i].valueList.get(j)); //This could probably be considerably improved by just copy the entire list
		}		
	}
	
	private long elfHash(K key) {
		long hashValue = 0;
		String toHash = (String) key;
	  	for (int Pos = 0; Pos < toHash.length(); Pos++) {
		     hashValue = (hashValue << 4) + toHash.charAt(Pos);
		     long hiBits = hashValue & 0xF000000000000000L;
		         if (hiBits != 0)
		            hashValue ^= hiBits >> 56;
		     hashValue &= ~hiBits;
		}		
	  return hashValue;
	}

	public void displayTable(LogFileController logFile) {
		logFile.writeString("Format of display is");
		logFile.writeString("Slot Number:\t [RecordKey, [List of offsets]]");
		logFile.writeString("Current table size: " + currentSize);
		logFile.writeString("Number of elements in table: " + currentCount + "\n");
		for(int i = 0; i < currentSize; ++i) {
			if(dataList[i] != null && !dataList[i].isGrave) {
				String writeValue = i + ":\t[" + dataList[i].key + ", [";
				for(int j = 0; j < dataList[i].valueList.size(); ++j) {
					writeValue += dataList[i].valueList.get(j);
					if(j < dataList[i].valueList.size()-1)
						writeValue += ", ";
				}
				writeValue += "]]";
				logFile.writeString(writeValue);
			}
		}
		logFile.writeString("-----------------------------------------------------------------------------------\n");
	}
}


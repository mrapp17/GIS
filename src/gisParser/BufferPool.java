package gisParser;

import logFile.LogFileController;

public class BufferPool {
	GISObject[] buffer;
	private final int bufferSize = 15;
	
	
	public BufferPool() {
		buffer = new GISObject[bufferSize];
		for(int i = 0; i < bufferSize; ++i)
			buffer[i] = null;
	}
	
	//Searches the list to determine if the sought after object is already in the list, if so 
	//it inserts it at the top, if not the entirety of the list is shift down 1 then the new object
	//is inserted
	public void insert(GISObject newObject) {
		for (int i = findIndex(newObject.offset); i > 0; --i) {
			buffer[i] = buffer[i - 1];
		}
		buffer[0] = newObject;
	}

	//Returns the object reference to by the input offset, if the object is not found returns null
	public GISObject find(int offset) {
		for(int i = 0; i < bufferSize; ++i) {
			if(buffer[i] == null)
				return null;
			else if(buffer[i].offset == offset) {
				return buffer[i];
			}
		}
		return null;
	}
	
	//Searches the buffer pool for the index of the object
	int findIndex(int offset) {
		for(int i = 0; i < bufferSize; ++i) {
			if(buffer[i] == null || buffer[i].offset == offset) {
				return i;
			}	
		}
		return 14;
	}
	
	//Returns an array list of strings that represent all the contents of the buffer pool
	public void debug(LogFileController logFile) {
		logFile.writeString("MRU");
		for(int i = 0; i < bufferSize && buffer[i] != null; ++i) {
			logFile.writeString("\t" + buffer[i].offset + ":\t" + buffer[i].getString());
		}
		logFile.writeString("LRU");
		logFile.writeString("-----------------------------------------------------------------------------------\n");
	}
}

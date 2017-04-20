package gisParser;

//Object representation of a GIS record
public class GISObject {
	public final Integer offset;
	public final Integer FeatureID;
	public final String FeatureName;
	public final String FeatureClass;
	public final String StateAlpha;
	public final Integer StateNum;
	public final String CountyAlpha;
	public final Integer CountyNum;
	public final String LongitudeAlpha;
	public final String LatitudeAlpha;
	public final Integer Elevation;
	public final String MapName;
	public final String CreationDate;
	
	public GISObject(Integer offset, String rawData) {
		this.offset = offset;
		String[] splitData = rawData.split("[|]"); 
		FeatureID = Integer.parseInt(splitData[0]);
		FeatureName = splitData[1];
		FeatureClass = splitData[2];
		StateAlpha = splitData[3];
		StateNum = Integer.parseInt(splitData[4]);
		CountyAlpha = splitData[5];
		CountyNum = Integer.parseInt(splitData[6]);
		LatitudeAlpha = splitData[7];
		LongitudeAlpha = splitData[8];	
		Elevation = splitData[16].equals("") ? null : Integer.parseInt(splitData[16]);
		MapName = splitData[17]; 
		CreationDate = splitData[18];
	}

	//Returns a string of the entire GISObject
	public String getString() {
		String retval =  FeatureID + "|" +
				FeatureName + "|" +
				FeatureClass + "|" +
				StateAlpha + "|" +
				StateNum + "|" +
				CountyAlpha + "|" +
				CountyNum + "|" +
				LongitudeAlpha + "|" +
				LatitudeAlpha + "|";
		retval += (Elevation == null) ? "" : Elevation;
		retval +=  "|" + MapName + "|" + CreationDate;
		return retval;
	}
	
	//Compare two GISObjects, used for sorting them in the correct order
	public int compareTo(GISObject comparedObject) {
		int compareValue = FeatureName.compareTo(comparedObject.FeatureName);
		if(compareValue != 0)
			return compareValue;
		
		compareValue = CountyAlpha.compareTo(comparedObject.CountyAlpha);
		if(compareValue != 0)
			return compareValue;
		
		compareValue = StateAlpha.compareTo(comparedObject.StateAlpha);
		if(compareValue != 0)
			return compareValue;
		
		return 0;
	}
	
}

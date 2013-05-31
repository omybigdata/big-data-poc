package platform.data.file.layout;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class CsvFileLayout extends FileLayout {
	private String delimiter;
	
	public CsvFileLayout(String layoutFile, String delimeter) 
			throws NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException{
		super(layoutFile);
		this.delimiter = delimeter;
	}
	
	public String[] parse(String record){		
		return record.split(this.delimiter);
	}
	
	public String unParse(String [] recordValues){
		if(recordValues == null || recordValues.length < 1) return null;
		
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(; i<recordValues.length-1; i++){
			sb.append(recordValues[i]); 
			sb.append(this.delimiter);
		}		
		sb.append(recordValues[i]);
		
		return sb.toString();
	}
}

package platform.data.file.layout;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.regex.Pattern;

public class CsvFileLayout extends FileLayout {
	private String delimiter;
	private String patern;
	
	public CsvFileLayout(String layoutFile, String delimiter) 
			throws NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException{
		super(layoutFile);
		this.delimiter = delimiter;
		this.patern = Pattern.quote(delimiter);
	}
	
	public String[] parse(String record){
		return record.split(this.patern);
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

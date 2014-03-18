package platform.data.file.layout;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

public class FileLayoutFactory {

	public static final String DELIMITER = "delimiter";

	public static FileLayout getFileLayout(String fileName, LayoutType layoutType, Map<String,String> options) 
			throws NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException{
		
		FileLayout fileLayout;
		String delimiter = ",";
		
		if(options !=null){
			delimiter = options.get(DELIMITER) == null ? delimiter : options.get(DELIMITER);
		}	
		
		if(layoutType == LayoutType.DELIMITED){			
			fileLayout = new CsvFileLayout(fileName, delimiter);
		}else if(layoutType == LayoutType.FIXED_WIDTH){
			fileLayout = new FixedFileLayout(fileName);
		}else{
			throw new RuntimeException("Layout: "+layoutType+" is not supported!");
		}
		
		return fileLayout;
	}
}

package platform.data.file.layout;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import org.junit.Test;

public class CsvFileLayoutTest {
	
	CsvFileLayout cfl;
	
	private void init(){
		try {
			cfl = new CsvFileLayout("src/test/resources/SampleDelimitedLayout.xml", ",");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFieldLayoutException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testParse() {
		init();
		String record = "100000001,DEBA SAHA,2519 Honeysuckle ln,Rolling Meadows,IL,60008" ;
		String [] fieldValues = cfl.parse(record );
		assertTrue(fieldValues.length==6);
		assertEquals(fieldValues[2], "2519 Honeysuckle ln");		
	}

	@Test
	public void testUnParse() {
		init();
		String [] recValues = {"100000001","DEBA SAHA","2519 Honeysuckle ln","Rolling Meadows","IL","60008"};
		String record = cfl.unParse(recValues);
		assertEquals(record, "100000001,DEBA SAHA,2519 Honeysuckle ln,Rolling Meadows,IL,60008");
	}

	@Test
	public void testGetFields() {
		init();
		Field[] fields = cfl.getFields();
		assertTrue(fields.length==6);
		assertEquals(fields[0].getFieldName(), "id");
		assertEquals(fields[1].getFieldName(), "name");
		assertEquals(fields[2].getFieldName(), "address");
		assertEquals(fields[3].getFieldName(), "city");
		assertEquals(fields[4].getFieldName(), "state");
		assertEquals(fields[5].getFieldName(), "zip");		
	}

}

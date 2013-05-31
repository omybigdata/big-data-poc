/**
 * 
 */
package platform.data.file.layout;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import org.junit.Test;

/**
 * @author dsaha1
 *
 */
public class FixedFileLayoutTest {
	FixedFileLayout ffl;
	
	private void init() throws NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException{
		ffl = new FixedFileLayout("src/test/resources/SampleFixedLayout.xml");
	}

	/**
	 * Test method for {@link platform.data.file.layout.FixedFileLayout#parse(java.lang.String)}.
	 * @throws InvalidFieldLayoutException 
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 * @throws NumberFormatException 
	 */
	@Test
	public void testParse() throws NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException {
		init();
		String record = "1000000001DEBA SAHA                2519 Honeysuckle ln                                                                                 Rolling Meadows                                                                                     IL60008      " ;
		String [] fieldValues = ffl.parse(record );
		assertTrue(fieldValues.length==6);
		assertEquals("2519 Honeysuckle ln",fieldValues[2]);
	}

	/**
	 * Test method for {@link platform.data.file.layout.FixedFileLayout#unParse(java.lang.String[])}.
	 * @throws InvalidFieldLayoutException 
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 * @throws NumberFormatException 
	 */
	@Test
	public void testUnParse() throws NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException {
		init();
		String [] recValues = {"1000000001","DEBA SAHA","2519 Honeysuckle ln","Rolling Meadows","IL","60008"};
		String record = ffl.unParse(recValues);
		assertEquals(record, "1000000001DEBA SAHA                2519 Honeysuckle ln                                                                                 Rolling Meadows                                                                                     IL60008     ");

	}

	/**
	 * Test method for {@link platform.data.file.layout.FixedFileLayout#getRecordByteLength()}.
	 * @throws InvalidFieldLayoutException 
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 * @throws NumberFormatException 
	 */
	@Test
	public void testGetRecordByteLength() throws NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException {
		init();
		Field[] fields = ffl.getFields();
		assertTrue(fields.length==6);
		assertEquals(fields[0].getFieldName(), "id");
		assertEquals(fields[1].getFieldName(), "name");
		assertEquals(fields[2].getFieldName(), "address");
		assertEquals(fields[3].getFieldName(), "city");
		assertEquals(fields[4].getFieldName(), "state");
		assertEquals(fields[5].getFieldName(), "zip");	
	}

}

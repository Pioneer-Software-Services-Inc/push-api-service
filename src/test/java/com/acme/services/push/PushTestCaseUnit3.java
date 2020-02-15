/**
 * 
 */
package com.acme.services.push;

import org.apache.commons.io.FileUtils;

/**
 * @author cgordon
 * @created 10/24/2017 
 * @version 1.0
 *
 * Unit test cases using annotations
 *  
 * fail(message) - Let the method fail. Might be used to check that a certain part of the code is not reached or to have a failing test before the test code is implemented. The message parameter is optional.
 * assertTrue([message,] boolean condition) - Checks that the boolean condition is true.
 * assertFalse([message,] boolean condition) - Checks that the boolean condition is false.
 * assertEquals([message,] expected, actual) - Tests that two values are the same. Note: for arrays the reference is checked not the content of the arrays.
 * assertEquals([message,] expected, actual, tolerance) - Test that float or double values match. The tolerance is the number of decimals which must be the same.
 * assertNull([message,] object) - Checks that the object is null.
 * assertNotNull([message,] object) - Checks that the object is not null.
 * assertSame([message,] expected, actual) - Checks that both variables refer to the same object.
 * assertNotSame([message,] expected, actual) - Checks that both variables refer to different objects.
 * 
 */

import org.junit.Before;
/**
 * @author cgordon
 *
 */
import org.junit.Test;

import com.acme.services.push.file.CharacterSet;
import static com.acme.services.push.file.FileUtil.*;
import com.acme.services.push.thread.tasks.ProcessOrderFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

public class PushTestCaseUnit3 {

	private final byte ref = 3;
	
	@Test
	public void testExecution() {	

		System.out.printf(" Test no: %d execution starts...", ref);    	
		int exitVal = 0;

		try {
			Process.main(new String[] {TestConstants.configFileLocation});
		} catch (IOException e) {
			exitVal = -1;
			e.printStackTrace();
		}

		assertEquals("Assert Push job executed successfully.", 0, exitVal);
	}
	
    @Before
    public void setUp() {
    	System.out.printf("Run @Before"); 
    	PushTestUtils.createTestEmailOptInData(4);
    	PushTestUtils.createOptOrderProcessData(2);    	
    }
    
	@Test
	public void testProcessDirectory() {	
    
		System.out.printf(" Test no: %d execution starts: bad dir...", ref);
	
		boolean success= true;
		Queue<File> queue = getPendingFiles();
		
		for(File f : queue) {
			ProcessOrderFile task = new ProcessOrderFile(f);
			success = task.execute();
		}

		assertTrue("Assert Push job process ", success);
		
		System.out.printf(" Test no: %d execution ends: bad dir...", ref);
	}

	@Test
	public void testBadEmailFile() {	
    
		System.out.printf(" Test no: %d execution starts: bad email file...", ref);
		
		PushTestUtils.createTestEmailOptInData();
	
		boolean success= true;
		Queue<File> queue = getPendingFiles();

		File file = queue.peek();

		try {
			StringBuilder contents = new StringBuilder(FileUtils.readFileToString(file, CharacterSet.UTF_8));
			contents.append("<JUNK>invalid node email data trailer</JUNK><BAD>more bad stuff<BAD>");
			
			FileUtils.writeStringToFile(file, contents.toString(), CharacterSet.UTF_8);
			ProcessOrderFile task = new ProcessOrderFile(file);
			success = task.execute();
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}

		assertFalse("Assert Push job process ", success);
		
		System.out.printf(" Test no: %d execution ends: bad file...", ref);
	}

	@Test
	public void testBadOrderFile() {	
    
		System.out.printf(" Test no: %d execution starts: bad order file...", ref);
		
		PushTestUtils.createOptOrderProcessData();
	
		boolean success= true;
		Queue<File> queue = getPendingFiles();

		File file = queue.peek();

		try {
			StringBuilder contents = new StringBuilder(FileUtils.readFileToString(file, CharacterSet.UTF_8));
			contents.append("<JUNK>invalid node order data trailer</JUNK><BAD>more bad stuff<BAD>");
			
			FileUtils.writeStringToFile(file, contents.toString(), CharacterSet.UTF_8);
			ProcessOrderFile task = new ProcessOrderFile(file);
			success = task.execute();
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}

		assertFalse("Assert Push job process ", success);
		
		System.out.printf(" Test no: %d execution ends: bad file...", ref);
	}
	
}
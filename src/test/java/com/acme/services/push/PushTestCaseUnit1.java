/**
 * 
 */
package com.acme.services.push;

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

import com.acme.services.push.command.Command;
import com.acme.services.push.command.SendGetCommand;
import com.acme.services.push.command.SendPostCommand;
import com.acme.services.push.config.Configs;
import com.acme.services.push.model.beans.activity.Activity;
import com.acme.services.push.model.beans.activity.TriggerSend;
import com.acme.services.push.network.Communicate;
import com.acme.services.push.request.Request;
import com.acme.services.push.service.Producer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import java.io.IOException;


public class PushTestCaseUnit1 {

	private final byte ref = 1;
	

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
    	PushTestUtils.createTestData();
    }
	
	@Test
	public void testRequestConstructs() {	
    
		System.out.printf(" Test no: %d execution starts: request constructs...", ref);
	
		Activity activity = PushTestUtils.buildActivity();
		
		assertNotNull("Assert Push build activity.", activity);		
		
		Request request = PushTestUtils.buildRequest(activity, "POST");
		
		assertNotNull("Assert Push build request.", request);
		
		assertNotNull("Assert Push check request payload.", request.getPayload());
		
		assertEquals("Assert Push check request payload.", activity, request.getPayload());
		
		assertEquals("Assert Push job process ", "ajohgf890q3204fjw-i3tj23if", ((TriggerSend) activity).getApp_group_id());
		
		System.out.printf(" Test no: %d execution ends: request constructs...", ref);
	}

	@Test
	public void testRequestSend() {	
    
		System.out.printf(" Test no: %d execution starts: request send...", ref);
	
		Configs.configure(TestConstants.configFileLocation);
		Configs.clearCache();
		
		Activity activity = PushTestUtils.buildActivity();
		
		assertNotNull("Assert Push build activity.", activity);		
		
		Request request = PushTestUtils.buildRequest(activity, "POST");
		
		assertNotNull("Assert Push build request (POST).", request);
		
		Communicate comm = new Communicate();
		boolean success = comm.AsyncPost(request);
		
		assertTrue("Assert Push send POST request.", success);
		
		try {
			success = comm.AsyncGet(request);
		}catch(Exception e) {
			fail("failure to send GET request with POST method configured");
			success = false;
		}
		assertFalse("Assert Push send GET request (POST-data).", success);
		
		request = PushTestUtils.buildRequest(activity, "GET");
		
		assertNotNull("Assert Push build request again (GET).", request);
		
		comm = new Communicate();		
		success = comm.AsyncGet(request);
		
		assertTrue("Assert Push send GET request again (GET-data).", success);
		
		
		System.out.printf(" Test no: %d execution ends: request send...", ref);
	}
	
	@Test
	public void testRequestProduce() {	
    
		System.out.printf(" Test no: %d execution starts: request produce...", ref);
	
		Configs.configure(TestConstants.configFileLocation);
		Configs.clearCache();
		
		Activity activity = PushTestUtils.buildActivity();
		
		assertNotNull("Assert Push build activity.", activity);		
		
		Request request = PushTestUtils.buildRequest(activity, "POST");
		
		assertNotNull("Assert Push build request (POST).", request);
		
		boolean success = true;
		
		try {
			Producer.produce(request);
		}catch(Exception e) {
			fail("failure to produce request POST configured");
			success = false;
		}
		
		assertTrue("Assert Push produce POST request.", success);
		
		System.out.printf(" Test no: %d execution ends: request produce...", ref);
		
	}

	@Test
	public void testCommandInvokePost() {	
    
		System.out.printf(" Test no: %d execution starts: request command invoke...", ref);
		
		Configs.configure(TestConstants.configFileLocation);
		Configs.clearCache();
		
		Activity activity = PushTestUtils.buildActivity();
		
		assertNotNull("Assert Push build activity.", activity);		
		
		Request request = PushTestUtils.buildRequest(activity, "POST");
		
		assertNotNull("Assert Push build request (POST).", request);
		
		Command command = new SendPostCommand(request);
		boolean success = true;
		
		try {
			 command.execute();
		}catch(Exception e) {
			fail("failure to execute POST command request POST configured");
			success = false;
		}
	
		assertTrue("Assert Push execute command POST request w/ POST.", success);
		
		command = new SendGetCommand(request);
		success = true;
		
		try {
			 command.execute();
		}catch(Exception e) {
			fail("failure to execute GET command request POST configured");
			success = false;
		}
			
		assertTrue("Assert Push execute command GET request w/ POST data.", success);
		
		System.out.printf(" Test no: %d execution ends: request command invoke...", ref);
	}

	@Test
	public void testCommandInvokeGet() {	
    
		System.out.printf(" Test no: %d execution starts: request command invoke...", ref);
		
		Configs.configure(TestConstants.configFileLocation);
		Configs.clearCache();
		
		Activity activity = PushTestUtils.buildActivity();
		
		assertNotNull("Assert Push build activity.", activity);		
		
		Request request = PushTestUtils.buildRequest(activity, "GET");
		
		assertNotNull("Assert Push build request (POST).", request);
		
		Command command = new SendPostCommand(request);
		boolean success = true;
		
		try {
			 command.execute();
		}catch(Exception e) {
			fail("failure to execute POST command request GET configured");
			success = false;
		}
	
		assertTrue("Assert Push execute command POST request w/ GET.", success);
		
		command = new SendGetCommand(request);
		success = true;
		
		try {
			 command.execute();
		}catch(Exception e) {
			fail("failure to execute GET command request GET configured");
			success = false;
		}
			
		assertTrue("Assert Push execute command GET request w/ GET data.", success);
		
		System.out.printf(" Test no: %d execution ends: request command invoke...", ref);
	}
	
}
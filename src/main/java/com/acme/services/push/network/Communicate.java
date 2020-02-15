package com.acme.services.push.network;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.validation.constraints.NotNull;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.constants.FeedType;
import com.acme.services.push.facade.ParserFacade;

import static com.acme.services.push.facade.FunctionFacade.*;
import com.acme.services.push.file.CharacterSet;
import static com.acme.services.push.logger.Status.*;
import com.acme.services.push.request.Request;
import com.acme.services.push.response.Response;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 *  <p>
 *  
 *  This Java class contains the http GET and POST methods that handle communication with the 
 *  push REST web service
 *  <p>
 */
public final class Communicate {

	private static transient final Logger logger = LoggerFactory.getLogger(Communicate.class);

	/**
	 * This performs a composite GET operation using the <code>Request</code> objects payload data.
	 * 
	 * @param Data in the <code>Request</code> is used to configure and send a http GET request.
	 * @returns boolean primitive indicating the success of the operation
	 */	
	public boolean AsyncGet(@NotNull Request request) {

		logger.debug("perform Async GET method, {}", request);
		if(!Optional.of(request).isPresent()) return false;

		try {
			HttpGet getRequest = new HttpGet(request.getUrl());
			getRequest.addHeader("content-type", request.getContentType());
			getRequest.addHeader("accept",  request.getContentType());

			asyncClientExecute(getRequest, Constants._EMPTY);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException | KeyManagementException | NoSuchAlgorithmException | 
				KeyStoreException | InterruptedException | ExecutionException | NoSuchProviderException e) {
			logger.error(e.getMessage());
		}
		
		logger.debug("perform Async GET method complete");		
		return true;
	}

	/**
	 * This performs a composite POST operation using the <code>Request</code> objects payload data.
	 * 
	 * @param Data in the <code>Request</code> is used to configure and send a http POST request.
	 * @return boolean primitive indicating the success of the operation
	 */	
	public boolean AsyncPost(@NotNull Request request) {

		logger.debug("perform Async POST method, {}", request);
		if(!Optional.of(request).isPresent()) return false;

		String payload = getJsonModeltoString(request.getPayload());
		logger.debug("perform Async POST method, payload: {}, formatted: {}", payload);

		try {
			HttpPost postRequest = new HttpPost(request.getUrl());

			StringEntity input = new StringEntity(payload, CharacterSet.UTF_8);
			input.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, request.getContentType()));
			postRequest.setEntity(input);

			asyncClientExecute(postRequest, payload);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException | KeyManagementException | NoSuchAlgorithmException | 
				KeyStoreException | InterruptedException | ExecutionException | NoSuchProviderException e) {
			logger.error(e.getMessage());
		}
		
		logger.debug("perform Async POST method complete");

		return true;
	}

	/**
	 * This performs a asynchronous Http Method execute operation using the <code>HttpRequestBase</code> objects.
	 * 
	 * @param request object <code>HttpRequestBase</code> is used to configure and send a http asynchronous POST request.
	 * @param payload object <code>String</code> detail tracked for production debug / status email 
	 * @throws <code>KeyManagementException</code>
	 * @throws <code>NoSuchAlgorithmException</code>
	 * @throws <code>KeyStoreException</code>
	 * @throws <code>InterruptedException</code>
	 * @throws <code>ExecutionException</code> 
	 * @throws <code>IOException</code> 
	 */	
	private void asyncClientExecute(@NotNull final HttpRequestBase request, final String payload) 
						throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, 
						InterruptedException, ExecutionException, IOException, NoSuchProviderException {

		logger.info("\n*****\nperform Async Execute method, {} \n******\n", request);
		if(!Optional.of(request).isPresent()) return;

		CustomHttpAsyncClient client = new CustomHttpAsyncClient();
		Future<HttpResponse> future = client.execute(request);

		logger.debug("perform Async Execute future callback method, {}", request);

		HttpEntity entity=null;
		Short statusCode= (short)Constants.ZERO;
		String statusMessage=Constants._BLANK;
		
		String path = request.getURI().getPath();
		String type = path.substring(path.indexOf("/"));
		
		final String descriptor = ParserFacade.getJsonIdentifier(payload, type.contains("track")? FeedType.TRACK : FeedType.ORDER);
	
		try {
			HttpResponse response= future.get(Integer.parseInt(getResourceProperty(SERVICE_MAX_ASYNC_FUTURE_MAX_WAIT)),
					TimeUnit.MILLISECONDS);

			synchronized(response){ 
				if(!future.isDone()){
					logger.debug(" async future response is incomplete due to cancel, exception or terminated: {} | descriptor: {}", response, descriptor);
					return;
				}
				logger.debug(" async response received, {}", response);
				statusCode = (short)response.getStatusLine().getStatusCode();
				statusMessage = response.getStatusLine().getReasonPhrase();
				entity = response.getEntity();
			}
		} catch (TimeoutException e1) {
			logger.error(" async future response exception: {} | descriptor: {}", e1.getMessage());
			trace(String.format(" async future response exception: %s, descriptor: %s", e1.getMessage(), descriptor));
			return;
		} 

		logger.debug("Execute Request returned status : code: {} | reason: {} | ref: {}\n", statusCode, statusMessage, descriptor);
		
		String msgType = Response.successCodes.contains(statusCode)? Response.STATUS_CONTENT : Response.ERROR_CONTENT;
		String jsonOutput = String.format(msgType, statusCode, statusMessage);

		logger.info("Server response: {} , {}", jsonOutput, descriptor);
		if ( !(statusCode.equals(Response.OK) || statusCode.equals(Response.CREATED)) ) {

			logger.info("Execute Request FAILED : HTTP error details : code: {} | reason: {} | descriptor: {}\n", statusCode, statusMessage, descriptor);
			      
			trace(String.format("Execute Request FAILED : HTTP error details : code: %s | reason: %s | request: %s | descriptor: %s\n", statusCode, statusMessage, request, 
					descriptor));
		}

		logger.info("\nOutput from server ....");
		if (!(Response.hasNoContentCodes.contains(statusCode) )) {
			
			jsonOutput = EntityUtils.toString(entity, CharacterSet.UTF_8);
			
			logger.debug("\n stream response content: {} | descriptor: {}\n", jsonOutput, descriptor);
			trace(String.format("\n stream response content: %s, descriptor: %s \n", jsonOutput, descriptor));
		}
		logger.info("perform Async Execute method SUCCESS, request: {} | message: {} | descriptor: {}\n", request, statusMessage, descriptor);
	}
}
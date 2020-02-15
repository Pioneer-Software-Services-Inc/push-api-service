/**
 * 
 */
package com.acme.services.push.network;

import java.io.IOException;
import java.nio.CharBuffer;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.validation.constraints.NotNull;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.constants.Constants;

/**
 * @author cgordon
 * @created 08/16/2017
 * @version 1.0
 * 
 * Custom wrapper class for the <code>CloseableHttpAsyncClient</code> class.
 * Needed so as to have the capability to leave the connection open for multi data burst
 * needed for push request to Push REST service endpoint.
 */
public class CustomHttpAsyncClient {

	private static transient final Logger logger = LoggerFactory.getLogger(CustomHttpAsyncClient.class);
	private static CloseableHttpAsyncClient client = null;

	/**
	 *  No argument default constructor
	 */
	public CustomHttpAsyncClient(){

		client = client==null? getCloseableHttpAsyncClient() : client;
	}

	/**
	 * Starts the (asynchronous) http service. First verifies that the service instance
	 *  is initialized. 
	 */
	public static void start(){

		client = client==null? getCloseableHttpAsyncClient() : client;	
		client.start();
	}

	/**
	 * Closes the (asynchronous) http service connection. First verifies that the 
	 * service instance is initialized. 
	 */
	public static void close(){

		client = client==null? getCloseableHttpAsyncClient() : client;	
		try {
			client.close();
			client = null;
		} catch (IOException e) {
			logger.error(e.getMessage());		
		}

	}

	/**
	 * Method retrieves the <code>CloseableHttpAsyncClient</code> member object
	 * 
	 * @return <code>CloseableHttpAsyncClient</code>
	 */
	public static final CloseableHttpAsyncClient getHttpAsyncClient(){

		return client==null? getCloseableHttpAsyncClient() : client;
	}

	/**
	 * calls the <code>CloseableHttpAsyncClient</code> execute() method. Uses a
	 * <code>FutureCallback</code> type which maintains the response information
	 * as the execute method is called asynchronously.  
	 * 
	 * @param request <code>HttpRequestBase</code> such as HttpGet | HttpPost etc
	 * @return <code>Future<HttpResponse></code> when response objects becomes available 
	 * @throws <code>InterruptedException</code> 
	 */
	public Future<HttpResponse> execute(@NotNull final HttpRequestBase request) throws InterruptedException{

		HttpAsyncRequestProducer producer = HttpAsyncMethods.create(request);

		AsyncCharConsumer<HttpResponse> consumer = new AsyncCharConsumer<HttpResponse>() {

			HttpResponse response;

			@Override
			protected void onResponseReceived(final HttpResponse response) {
				this.response = response;
			}

			@Override
			protected void onCharReceived(CharBuffer buf, IOControl ioctrl) throws IOException {
				/** Do something useful */
			}

			@Override
			protected void releaseResources() {
			}

			@Override
			protected HttpResponse buildResult(final HttpContext context) {
				return this.response;
			}
		};
		
		if(client==null || !client.isRunning()) client.start();
		
		Future<HttpResponse> future = client.execute(producer, consumer, new FutureCallback<HttpResponse>() {

			public void completed(final HttpResponse response) {
				logger.debug(" successful execution {} -> {}", request.getRequestLine(), response.getStatusLine());
			}

			public void failed(final Exception ex) {
				logger.error("failed with exception {} -> {}", request.getRequestLine(), ex);
			}

			public void cancelled() {
				logger.error("{} cancelled", request.getRequestLine());
			}

		});
		return future;
	}

	/**
	 * This is a convenience method that constructs a <code>CloseableHttpAsyncClient</code> object
	 * 
	 * @return <code>CloseableHttpAsyncClient</code> object
	 */	
	private static CloseableHttpAsyncClient getCloseableHttpAsyncClient(){

		CloseableHttpAsyncClient client=null;

		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] certificate,  String authType) {
				return true;
			}
		};

		try{
			final KeyStore keystore = KeyStore.getInstance("JKS");

			SSLContextBuilder builder = SSLContexts.custom()
			.setSecureRandom( SecureRandom.getInstance("SHA1PRNG"))
			.loadTrustMaterial(keystore, acceptingTrustStrategy);

			SSLContext sslContext = builder.build();

			sslContext.init(null,
					new TrustManager[]{new X509TrustManager() {
						public X509Certificate[] getAcceptedIssuers() {

							return null;
						}

						public void checkClientTrusted(
								X509Certificate[] certs, String authType) {

						}

						public void checkServerTrusted(
								X509Certificate[] certs, String authType) {

						}
					}}, new SecureRandom());

			IOReactorConfig config = IOReactorConfig.custom()
	        .setTcpNoDelay(true)
	        .setSoTimeout(Constants.HTTP_SO_TIMEOUT)
	        .setSoReuseAddress(true)
	        .setConnectTimeout(Constants.HTTP_CONNECTION_TIME_OUT)
	        .setSoKeepAlive(true)
	        .build();
	
			ConnectionKeepAliveStrategy keepAlive = new ConnectionKeepAliveStrategy() {

		          public long getKeepAliveDuration(HttpResponse response,
		                                           HttpContext context) {
		            return Constants.HTTP_KEEP_ALIVE_TIME; /** 30 seconds in millis */
		          }
		        };
			
			int maxAsyncClients = Integer.parseInt(getResourceProperty(SERVICE_MAX_ASYNC_CLIENTS));
			client = HttpAsyncClients.custom().setDefaultIOReactorConfig(config)
			.setSSLContext(sslContext).setMaxConnTotal(maxAsyncClients)
			.setKeepAliveStrategy(keepAlive).build();
			
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error(e.getMessage());
		}
		
		return client;
	}

}



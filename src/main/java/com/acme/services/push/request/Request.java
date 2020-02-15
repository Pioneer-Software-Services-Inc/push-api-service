/**
 * 
 */
package com.acme.services.push.request;

import javax.validation.constraints.NotNull;

import com.acme.services.push.constants.Constants;
import static com.acme.services.push.facade.FunctionFacade.*;
import com.acme.services.push.model.beans.Model;
import com.acme.services.push.model.beans.NullObject;
import com.acme.services.push.network.ContentType;
import com.acme.services.push.network.HttpMethod;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 *  <p>
 *  
 *  This Java class is effectively a Data transfer Object that contains the information 
 *  needed to configure and the payload data for sending requests to the push REST 
 *  recipient service. 
 *  <p>
 */
public class Request {
	
	private String endpoint = Constants.DEFAULT_ENDPOINT;
	private String method = HttpMethod.DEFAULT_METHOD;
	private String contentType = ContentType.DEFAULT_CONTENTTYPE;
	@NotNull private Model payload = new NullObject();
	
	/**
	 * No param constructor for request object
	 */
	public Request(){
	}
	
	/**
	 * Convenience constructor for request object
	 *  
	 * @param endpoint Push destination endpoint
	 * @param method Push
	 * @param contenttype Push
	 * @param <code>Activity</code> object with data payload information as well as other info needed
	 * for constructing the http request object (<code>HttpRequestBase</code> subclass HttpGet | HttpPost)
	 */
	public Request(String endpoint, String method, String contenttype, Model data){
		
		this.endpoint = endpoint;
		this.method = method;
		this.contentType = contenttype;
		this.payload = data;
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return endpoint;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.endpoint = url;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the payload
	 */
	public Model getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(Model payload) {
		this.payload = payload;
	};
	
	@Override
	public String toString(){
		return String.format(" Request: [endpoint: %s | method: %s | content-type: %s | data: %s]", 
				this.endpoint, this.method, this.contentType, getJsonModeltoString(this.payload));
	}

}

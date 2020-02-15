/**
 * 
 */
package com.acme.services.push.file.locator;

import java.util.WeakHashMap;

import com.acme.services.push.constants.Constants;
import static com.acme.services.push.facade.ParserFacade.*;

/**
 * @author cgordon
 * @created 10/17/2017
 * @version 1.0
 *
 * Cache class for service locator design pattern
 *
 */
public class Cache {

	/** Uses <code>WeakHashMap</code> ADT to implement basic cache-ing. <code>WeakHashMap</code> will be garbage collected by the JVM
	 * when the object essentially becomes stale or no longer referenced. 
	 * 
	 *  * * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/WeakHashMap.html">Java SE 1.8 Document WeakHashMap Class</a>
	 * 
	 * */
	private WeakHashMap<String, ResourceInterface> resources;

	public Cache(){

		resources = fetchCache();
	}

	/**
	 * gets the requested resource
	 * 
	 * @param resourceName
	 * @return <code>ResourceInterface</code> resource requested
	 */
	public ResourceInterface getResource(String resourceName){

		if(resources==null || resources.isEmpty()) resources = fetchCache();
		
		return resources.get(resourceName);
	}

	/**
	 * performs basic logical cache pre-fetch
	 * 
	 * @return <code>WeakHashMap</code> type with map of Resources
	 */
	private WeakHashMap<String, ResourceInterface> fetchCache() {

		WeakHashMap<String, ResourceInterface> cdata = new WeakHashMap<String, ResourceInterface>();
		
		Constants.factoryTypes.stream().map(f -> {
			WeakHashMap<String, ResourceInterface> values = new WeakHashMap<String, ResourceInterface> ();
			values.put(f, getFactory(f));
			return values ;
		}).forEach(map -> cdata.putAll(map)); 		
		
		return cdata;
	}
}
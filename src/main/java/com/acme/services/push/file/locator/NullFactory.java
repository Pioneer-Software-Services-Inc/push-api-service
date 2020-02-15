/**
 * 
 */
package com.acme.services.push.file.locator;

/**
 * @author cgordon
 * @created 10/17/2017
 * @version 1.0
 *
 * Null Object design pattern as safety against passing around and handling null values.
 * 
 */
public enum NullFactory implements ResourceInterface{

	/** modern method for implementing singleton design pattern */
	INSTANCE;

	public static NullFactory getInstance(){
    	return INSTANCE;
    }
}

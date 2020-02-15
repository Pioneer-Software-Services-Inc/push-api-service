/**
 * 
 */
package com.acme.services.push.response;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Error response type data
 */
@JsonRootName(value = "error")
public class Error extends Response{
	
	@Override
	public String toString(){
		return String.format("[label:%s,code:%s,message:%s,errors:%s.]","Error",code,message,Arrays.toString(errors.toArray())); 
	}	
}

/**
 * 
 */
package com.acme.services.push.command;

import javax.validation.constraints.NotNull;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 *  <p>
 *  
 *  This Java class defines Command Invoker class component of the Command design pattern.
 * 
 *  <p> 
 */
public class CommandInvoker {

	@NotNull private Command command;
	
	public void setCommand(@NotNull Command command){
		this.command = command;
	}
	
	public void executeCommand(){
		command.execute();
	}

}

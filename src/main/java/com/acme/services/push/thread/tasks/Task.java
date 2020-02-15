/**
 * 
 */
package com.acme.services.push.thread.tasks;

/**
 * @author cgordon
 * @created 08/016/2017
 * @version 1.0
 *
 * Task type definition interface for tasks to be executed inside the <code>WorkerTheadPool</code> class.
 * This approach is an implementation of the Inversion of Control (IoC) design pattern which only requires the 
 * interface implementations provide a execute() method. Each file processing type is expected to implement this interface. 
 *  
 */
public interface Task {

	public Boolean execute();
}

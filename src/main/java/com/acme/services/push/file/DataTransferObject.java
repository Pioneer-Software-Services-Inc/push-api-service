/**
 * 
 */
package com.acme.services.push.file;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.acme.services.push.model.beans.Attributes;

/**
 * @author cgordon
 * @created 08/24/2017
 * @version 1.0
 * 
 * This Data Transfer Object design pattern class' purpose is to transfer data from the file processing 
 * to the function that creates the request object that is used to populate the payload data for 
 * the Push request. 
 *
 */
public class DataTransferObject implements Cloneable {
	
	private Hashtable<String, String> fields =  new Hashtable<String, String>();
	private Hashtable<String, String> trigger_properties =  new Hashtable<String, String>();
	private List<Attributes> attributes = new ArrayList<Attributes>();
	
	public DataTransferObject() {};
	
	/**
	 * @return the fields
	 */
	public Hashtable<String, String> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(Hashtable<String, String> fields) {
		this.fields.putAll(fields);
	}
	/**
	 * @return the trigger_properties
	 */
	public Hashtable<String, String> getTrig_props() {
		return trigger_properties;
	}
	/**
	 * @param trigger_properties the trigger_properties to set
	 */
	public void setTrig_props(Hashtable<String, String> trig_props) {
		this.trigger_properties.putAll(trig_props);
	}

	@Override
	public String toString(){
		
		return String.format("DTO payload: field hash: %s | trigger properties hash: %s | attributes hash: %s ", fields.toString(), trigger_properties.toString(), attributes.toString());
	}

	public List<Attributes> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attributes> attributes) {
		this.attributes.addAll(attributes);
	}
	
    public DataTransferObject clone() {
        try {
            return (DataTransferObject)super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new DataTransferObject();
        }
    }	
	
	
}

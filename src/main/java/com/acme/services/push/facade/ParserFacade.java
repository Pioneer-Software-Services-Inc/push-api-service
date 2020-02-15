/**
 * 
 */
package com.acme.services.push.facade;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import static com.acme.services.push.constants.Constants.*;
import com.acme.services.push.constants.FeedType;
import com.acme.services.push.exception.ServiceException;
import static com.acme.services.push.file.FileUtil.*;
import static com.acme.services.push.logger.Status.trace;

import com.acme.services.push.file.locator.NullFactory;
import com.acme.services.push.file.locator.ParserFactory;
import com.acme.services.push.file.locator.ProcessFactory;
import com.acme.services.push.file.locator.ResourceInterface;
import com.acme.services.push.file.locator.ServiceLocator;
import com.acme.services.push.file.xml.FileParser;
import com.acme.services.push.thread.tasks.NullProcessFile;
import com.acme.services.push.thread.tasks.Task;

/**
 * @author cgordon
 * @created 10/16/2017
 * @version 1.0
 * 
 * The purpose of this data facade class is to house various xml parse related method operations. 
 *
 */
public class ParserFacade {

	private static transient final Logger logger = LoggerFactory.getLogger(ParserFacade.class);

	/**
	 * This method inspects the input xml data file and returns an enum value <code>FeedType</code> enum type
	 *  identifying the type of file xml feed.
	 * 
	 * @param xmlFile input <code>File</code> file.
	 * @return <code>FeedType</code> enum type identifying the type of feed
	 * @throws ServiceException 
	 */
	public static FeedType getFeedType(@NotNull final File xmlFile) throws ServiceException {

		logger.debug("calculate feed type inspect file content. ({})", xmlFile);

		FeedType type = null;
		final String xmlData = readEncodeXmlFile(xmlFile);
		DocumentBuilderFactory dbFactory = getOwaspDocumentBuilderFactory();
		String action= _BLANK;

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
			doc.getDocumentElement().normalize();

			action = doc.getDocumentElement().getTagName();			
			logger.info("detected feed type root node: {}", action);			
		} catch (SAXException | ParserConfigurationException | IOException e) {
			logger.error("error parseing xml file. invalid data: {}.", e);
			trace(String.format("error parseing xml file. invalid data: %s", e));
			throw new ServiceException(String.format("error parseing xml file. invalid data: %s", e));
		}

		final List<String> feed_types = getResourcePropertyList(DATA_FEED_REFERENCE_TYPE_LIST);

		if(feed_types.contains(action)) {

			switch(action) {

				case LABEL_TRACK_TYPE:
	
					type = FeedType.TRACK;
					break;
				case LABEL_ORDER_TYPE:
	
					type = FeedType.ORDER;
					break;
				default:
					type = FeedType.INVALID;
			}
		}else {
			logger.error("Feed type specified in xml imput file is invalid: {}", action);
		}

		return type;
	}

	/**
	 * This method accepts the type of feed <code>FeedType</code> enum type and determines the type of 
	 * xml file parser to use for the feed type.
	 * 
	 * @param type feed input <code>FeedType</code> type.
	 * @return <code>FileParser</code> type of xml processor/parser
	 * @throws <code>InstantiationException</code>
	 * @throws <code>IllegalAccessException</code>
	 * @throws <code>ServiceException</code> 
	 */
	public static FileParser getDocumentParser(@NotNull FeedType type) throws InstantiationException, IllegalAccessException, ServiceException {

		ParserFactory service = (ParserFactory)ServiceLocator.getResource(FACTORY_PARSE_TYPE);

		return service.getXmlParser(type);
	}

	/**
	 * This method inspects the input xml data file and returns the type class <code>AbstractProcessFile</code> concrete class instance to 
	 * be used to process the feed enum type.
	 * 
	 * @param file input <code>File</code> file.
	 * @return <code>Task</code> object document processor
	 * @throws <code>InstantiationException</code>
	 * @throws <code>IllegalAccessException</code>
	 * @throws <code>ServiceException</code> 
	 */
	public static Task getDocumentProcesser(@NotNull File file ) throws InstantiationException, IllegalAccessException, ServiceException {

		Task task = null;
		try {
			FeedType type = getFeedType(file);
			switch(type) {
			
				case INVALID:
					task = new NullProcessFile(file);
					
					break;
				
				default:	
					ResourceInterface service = ServiceLocator.getResource(FACTORY_PROCESS_TYPE);
					task = ((ProcessFactory)service).getFileProcessor(type, file);
			}
		}catch(ServiceException e) {
			logger.error("Feed type specified in xml imput file is invalid: error: {}", e);			
		}
		
		return task;
	}

	/**
	 * Uses reflections to determine the subclasses of a given input class <code>Class</code> type.
	 * (Generalized Target-Type Inference <T> is used)
	 * 
	 * @param klass generic type <code>Class</code> top determine its declared subclasses.
	 * @param _package name in the java classpath to search for sub classes
	 * @return <code>Set</code> of generic classes <code>Class</code> that are subclasses of input klass type
	 */
	public static <T> Set<Class<? extends T>> getSetOfSubClasses(@NotNull Class<T> klass, String _package) {

		logger.info("package reference: {}", _package);

		Reflections reflections = new Reflections(_package.trim()); 

		return reflections.getSubTypesOf(klass);
	}

	/**
	 * Calculate the type of class factory based on defined input type <code>String</code>
	 * 
	 * @param type <code>String</code> of factory reference key.
	 * @return <code>ResourceInterface</code> interface concrete class instance object.
	 */
	public static ResourceInterface getFactory(@NotNull String type){

		ResourceInterface factory=null;

		if(!factoryTypes.contains(type)) return null;

		switch(type){
	
			case FACTORY_PARSE_TYPE:
	
				factory = ParserFactory.getInstance();
				break;
	
			case FACTORY_PROCESS_TYPE:
	
				factory = ProcessFactory.getInstance();
				break;
	
			default:
	
				factory = NullFactory.getInstance();
		}

		return factory;
	}

	/**
	 * This method implements the recommendations of OWASP for how to configure a 
	 * <code>DocumentBuilderFactory</code> xml reader for best security against malicious actors.
	 * 
	 * method declared private final to prevent extension and other modification 
	 * 
	 * @see <a https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Prevention_Cheat_Sheet#JAXP_DocumentBuilderFactory.2C_SAXParserFactory_and_DOM4J>OWASP recommended XML Parse configuration</a>
	 * 
	 * @return <code>DocumentBuilderFactory</code>
	 */
	public static final DocumentBuilderFactory getOwaspDocumentBuilderFactory(){

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		String FEATURE = null;

		try {
			FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
			dbf.setFeature(FEATURE, true);

			FEATURE = "http://xml.org/sax/features/external-general-entities";
			dbf.setFeature(FEATURE, false);

			FEATURE = "http://xml.org/sax/features/external-parameter-entities";
			dbf.setFeature(FEATURE, false);

			FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
			dbf.setFeature(FEATURE, false);

			dbf.setXIncludeAware(false);
			dbf.setExpandEntityReferences(false);

		} catch (ParserConfigurationException e) {
			logger.error("ParserConfigurationException was thrown. The feature '" +
					FEATURE + "' is probably not supported by your XML processor.");
		}
		
		dbf = DocumentBuilderFactory.newInstance();
		
		return dbf;
	}

	/**
	 * This method searches a json string for the referenced value identified by the reference feed type.
	 * Used to populate the log with [configurable] important details for troubleshooting etc
	 * 
	 * @param content <code>String</code> json content to search for value identified by the reference feed type
	 * @param reference <code>String</code> feed type id used to locate json value
	 * @return <code>String</code> json value
	 */
	public static String getJsonIdentifier(@NotNull final String content, @NotNull final FeedType type) {
		
		logger.info("\ndebug inspect json content: {}\n", content );
		
		final String key = getResourceProperty(String.format(DATA_RECORD_DESCRIPTOR_ID, 
				type.equals(FeedType.TRACK)? LABEL_KEY_TRACK: LABEL_KEY_ORDER));

		if(!content.contains(key)) return String.format("{ key: %s , value: %s }", key, "not available");;
		
		String value = null;
		final JSONObject jsonObj = new JSONObject(content);	
		
		final String jsonNode = type.equals(FeedType.TRACK)? LABEL_ATTRIBUTES : LABEL_TRIGGER_PROPERTIES;
		logger.debug("Content Identifier: node: {} key: {} type: {}", jsonNode, key, type);
		
		try {
			value = type.equals(FeedType.TRACK)? String.valueOf(jsonObj.getJSONArray(jsonNode).getJSONObject(ZERO).getString(key)) : 
				String.valueOf(jsonObj.getJSONObject(jsonNode).getString(key));
		}catch(JSONException e) {
			logger.warn(" unable to locate value referenced by key: {} | exception: {}", key,  e);			
		}

		return String.format("{ key: %s , value: %s }", key, value);		
	}
}

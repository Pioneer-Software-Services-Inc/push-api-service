/**
 * 
 */
package com.acme.services.push.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.validation.constraints.NotNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.lang.StringEscapeUtils;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.exception.Handler;
import com.acme.services.push.exception.ServiceException;
import com.acme.services.push.facade.FunctionFacade;
import com.acme.services.push.file.CharacterSet;
import com.acme.services.push.file.xml.validate.Validate;
import static com.acme.services.push.logger.Status.*;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * This class provides functionalities need for file manipulation
 */
public class FileUtil {

	private static transient final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * This method scans the pending folder and queues up all the fileUtil it finds. 
	 * 
	 * @returns <code>Queue<File></code> queue of fileUtil present inn the configured pending folder
	 */	
	public static Queue<File> getPendingFiles(){

		String pending_folder = getResourceProperty(PROCESSING_FOLDER);

		logger.debug(" pending folder:, {}", pending_folder);

		List<File> files = Arrays.asList(new File(!pending_folder.isEmpty()? pending_folder : Constants.DEFAULT_PROCESSING_FOLDER).listFiles());
		
		logger.debug(" fileUtil:, {}", files);

		Queue<File> queue = (LinkedList<File>)files.stream()
				.filter(f -> (f.isFile() && !f.getName().contains(Constants.LOCK)))
				.collect(Collectors.toCollection(LinkedList<File>::new));

		logger.info("QUEUE: , {}", queue);
		return queue;
	}

	/**
	 * This method performs a logical lock of the specified file by renaming it to 
	 * [filename.xml].lck during processing.   
	 * 
	 * @param accepts a <code>File</code> object to perform the lock operation 
	 * @returns The new file <code>File</code> name is returned by he method to be used for further processing.
	 */	
	public static File lockFile(@NotNull File file){
		logger.debug("file to be locked, {}", file);

		File f = file;
		StringBuffer tmp = new StringBuffer(f.getName()).append(Constants.LOCK);

		return renameFile(f, tmp.toString());
	}

	/**
	 * This method performs a logical unlock of the specified file by renaming it to 
	 * [filename.xml]   
	 * 
	 * @param accepts a <code>File</code> object to perform the unlock operation 
	 * @returns The new file <code>File</code> name is returned by he method to be used for further processing.
	 */	
	public static File unlockFile(@NotNull File file){

		logger.debug(" file to be unlocked, {}", file);
		StringBuffer tmp = new StringBuffer(file.getAbsolutePath());
		
		if(tmp != null && !String.valueOf(tmp).contains(Constants.LOCK)) {
			
			logger.warn(" file is not locked, {}", file);
			return file;
		}
		
		return renameFile(file, String.valueOf(tmp).replaceAll(Constants.LOCK, Constants._BLANK));	
	}
	/**
	 * This method performs moves the specified file to the configured processed folder.
	 * It achieves this by renaming the file to be on the processed folder. 
	 * 
	 * @param accepts a <code>File</code> object to perform the file move operation 
	 * @returns The new file <code>File</code> name is returned by he method to be used for further processing.
	 */	
	public static File moveToProcessed(@NotNull File file){

		logger.debug("file to be moved to processed folder, {}", file);
		File f = file;
		StringBuffer tmp = new StringBuffer(getResourceProperty(PROCESSED_FOLDER));
		verifyFolder(tmp.toString());		
		tmp.append(f.getName()).append(Constants._UNDERSCORE).append(new Date().getTime());

		logger.debug(" to be : processed renamed;, {}", tmp);

		f = renameFile(f, tmp.toString());		
		logger.debug("file moved to processed folder, {}", f);    	

		return f;
	}

	/**
	 * This method performs moves the specified file to the configured failed folder.
	 * It achieves this by renaming the file to be on the failed folder. 
	 * 
	 * @param accepts a <code>File</code> object to perform the file move operation 
	 * @returns The new file <code>File</code> name is returned by he method to be used for further processing.
	 */	
	public static File moveToFailed(@NotNull File file){

		logger.debug("file to be moved to failed folder, {}", file);
		File f = file;
		StringBuffer tmp = new StringBuffer(getResourceProperty(FAILED_FOLDER));
		verifyFolder(tmp.toString());
		tmp.append(f.getName()).append(Constants._UNDERSCORE).append(new Date().getTime());

		logger.debug(" to be : failed renamed;, {}", tmp);

		f = renameFile(f, tmp.toString());

		logger.debug("file moved to failed folder, {}", f);    	

		return f;
	}

	/**
	 * This method performs read operation and returns he contents of the file as a <code>String</code> object. 
	 * Method also uses owasp encoder to encode input data to mitigate potential {various security} data problems.
	 * 
	 * @param accepts a <code>File</code> object to perform the file move operation 
	 * @returns The contents as a <code>String</code> object.
	 */	
	public static String readEncodeXmlFile(@NotNull File file) {

		logger.debug("file to be read encoded, {}", file);
		
		if(!file.exists()) return Constants._WELL_FORMED_EMPTY_DOCLOCK;
		String contents = Constants._BLANK;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), CharacterSet.UTF_8))) {

			contents = br.lines().collect(Collectors.joining(""));

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			logger.error(" fio read file not found error, {}", e);
		} catch (IOException e) {
			logger.error(" fio read i/o error, {}", e);
		}
		
		logger.info("\n stream file read contents: {}\n", contents);
		
		return StringEscapeUtils.unescapeXml(Encode.forXmlContent(contents.toString()));
	}

	/**
	 * This method verifies that the folder passed to it exists. If not then the folder is created.
	 * 
	 * @param folder name as a <code>String</code>
	 * @return boolean flag success
	 */
	private static boolean verifyFolder(@NotNull String folder){
		logger.debug("folder to be verified, {}", folder);
		
		File dir = new File(folder.toString());
		if(!dir.exists()){
			if (dir.mkdir()) {
				logger.debug(" created destination folder {} successfully", folder);
			} else {
				logger.error(" unable to create destination folder {} FAILED", folder);
				return false;
			}
		}

		return true;
	}

	/**
	 * This method performs SAX xml verification 
	 * 
	 * @param input xml <code>File</code> to validate via SAX validation
	 * @return boolean flag success
	 * @throws <code>IOException</code> 
	 */
	public static boolean validateXmlFile(@NotNull File file) throws IOException{
		logger.debug("xml file to be validated, {}", file);
		if(!file.exists()) return false;

		Validate validate = new Validate();
		logger.debug(" attempting xml validation shunt: {}", file);
		return Handler.unchecked(() -> validate.isValidXmlFile(file)).get();
	}
	
	/**
	 * Purpose of method is to standardize the file name call so it can be thread safe
	 * 
	 * @param file <code>File</code> to be renamed
	 * @param new file name <code>String</code> 
	 * @return file <code>File</code> that has been renamed
	 */
	private static File renameFile(@NotNull File file, String newFilename){
		logger.debug("file to be renamed, from {} to {}", file, newFilename);
		File f = file;
		if(f.renameTo(new File(newFilename)))
			f = new File(newFilename);
		
		f.setLastModified(new Date().getTime());
		
		return f;	
	}

	/** 
	 * Archive oldest fileUtil with a given suffix, if more than configured max 
	 * 
	 * @param folderName: specify which folder to purge
	 * @throws IOException 
	 */
	public static void purgeTooManyFiles(@NotNull String folderName, @NotNull String archiveName) throws IOException{
		logger.debug(" service checking folder for too many ..., folder: {} ", folderName);
		
		File file = new File(folderName);
		if(!file.exists() || !file.isDirectory()) return;

		AtomicInteger num_files = new AtomicInteger(0);		
		int maxFiles = Integer.parseInt(getResourceProperty(SERVICE_FOLDER_MAX_FILES));		
		
		File[] files=(new File(folderName)).listFiles();
		logger.debug(" found {} fileUtil in {} folder.", files.length, folderName);
				
		if(maxFiles > files.length) return;		

		File tempDir = Files.createTempDirectory(Constants.LABEL_TEMP_FOLDER).toFile();
		tempDir.deleteOnExit();
		
		Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		Arrays.asList(files).stream().forEach(f -> {
				
				f.renameTo(new File(tempDir.getAbsolutePath().concat(File.pathSeparator).concat(f.getName())));
				num_files.getAndIncrement();	
				logger.info(" # File to Delete: name: {} | modified: {} ##", f.getName(), new Date(f.lastModified()*0L * 1000L));
		});
		
		StringBuffer zipFileName = new StringBuffer(getResourceProperty(ARCHIVE_FOLDER))
				.append(archiveName).append(Constants._UNDERSCORE).append(new Date().getTime());
		
		logger.debug(" folder compress to archive. {}", zipFileName);
		FileUtil.pack(tempDir.getAbsolutePath(), String.valueOf(zipFileName));
		
		logger.debug(" folder purge completed. {} archived", num_files);
	}

	
	/**
	 * Utility function to determine the current directory regardless of the OS Type... 
	 * 
	 * @return string value of the current directory
	 */
	public static String getCurrentDirectory(){

		String currentDir = Constants._BLANK;
		OsUtils.OS ostype=OsUtils.getOs();
		
		logger.info(" operating system detected is: {}", ostype);
		switch (ostype) {
		    case WINDOWS: 
		    	
		    	logger.debug(" operating system detected is WINDOWS");
		    	currentDir = System.getProperty("user.dir").concat(File.separator);
		    	
		    	break;
		    case MAC: 
		    case UNIX: 
		    	logger.debug(" operating system detected is UNIX");
		    	currentDir = (new File(".").getAbsolutePath()).concat(File.separator);
		    	
		    	break;
		    case OTHER: 
		    	logger.debug(" operating system detected is OTHER");
		    	currentDir = File.pathSeparator;
		    	
		    	break;
		default:
			break;
		}
		
		logger.info(" current directory: {}", currentDir);
		return currentDir;
	}

	/** 
	 * Initialize files for processing by moving them to an intermediate / in-progress folder.
	 * Mitigates infinite processing issue if file cannot be parsed and other IO issues.
	 * 
	 * @param srcFolderName: specify which folder to copy pending files from
	 * @param destFolderName: specify which folder to put files prepared for processing 
	 * @throws IOException 
	 * @throws ServiceException 
	 */
	public static void initialiseFiles(@NotNull String srcFolderName, @NotNull String destFolderName) throws IOException, ServiceException{
		logger.debug("files to be moved from {} to processing folder {}", srcFolderName, destFolderName);
		
		final File srcDir = new File(srcFolderName);
		final File destDir = new File(destFolderName);
		if(!srcDir.exists() || !srcDir.isDirectory()) throw new ServiceException("Service Error: Configured Source folder does not exist");
		if(!destDir.exists() || !destDir.isDirectory()) throw new ServiceException("Service Error: Configured Intermediate processing folder does not exist");
		
		File[] files=(new File(srcFolderName)).listFiles();
		logger.info("file list to be moved from {} to processing folder {}, files: %s\n", srcFolderName, destFolderName, FunctionFacade.printList(Arrays.asList(files)));
		
		Arrays.asList(files).stream().forEach( f -> {
			try {
				FileUtils.moveFileToDirectory(f, destDir, true);
			} catch (IOException e) {
				logger.error("exception thrown moving files, msg: {} ",e);
			}
		});
		
		logger.debug("files were moved from {} to processing folder {}", srcFolderName, destFolderName);    	
	}	
	
	/**
	 * Checks for any file that remain after processing of files. These files usually have parse or IO exception issues
	 * this is to prevent situation where application tries to process files in infinite loop...  
	 * 
	 * @param folderName location of intermediate files that are being processed
	 */
	public static void cleanupFiles(@NotNull String folderName){

		logger.debug(" cleanup procesing folder:, {}", folderName);

		File folder = new File(folderName);
		if(!(folder.exists() && folder.isDirectory())) logger.warn(" config error cleaning processing folder. folder: {}", folderName);
			
		List<File> files = Arrays.asList(folder.listFiles());
		
		logger.debug(" fileUtil:, {}", files);
			
		files.stream().forEach(f -> {
			
			logger.warn(" unprocessed files found in processing folder: file: {}", f);
			trace(String.format("unprocessed files found in processing folder: file: %s", f));
			moveToFailed(f);	
		});


	}
	
	/**
	 * Method compresses files in specified folder to the requested zip file.
	 * 
	 * @param sourceDirPath source folder where files are to be compressed
	 * @param zipFilePath zip file name (path)
	 * @throws IOException IOException exception  
	 */
	public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
		
	    Path p = Files.createFile(Paths.get(zipFilePath));
	    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
	        Path pp = Paths.get(sourceDirPath);
	        Files.walk(pp)
	          .filter(path -> !Files.isDirectory(path))
	          .forEach(path -> {
	              ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
	              try {
	                  zs.putNextEntry(zipEntry);
	                  Files.copy(path, zs);
	                  zs.closeEntry();
	            } catch (IOException e) {
	                System.err.println(e);
	            }
	          });
	    }
	}

	
}

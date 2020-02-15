/**
 * 
 */
package com.acme.services.push.file;

/**
 * @author cgordon
 * @created 08/18/2017
 * @version 1.0
 *
 * This class defines the standard constants for character sets
 *
 * Charset 		Description
 * US-ASCII		Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set
 * ISO-8859-1  	ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
 * UTF-8			Eight-bit UCS Transformation Format
 * UTF-16BE		Sixteen-bit UCS Transformation Format, big-endian byte order
 * UTF-16LE		Sixteen-bit UCS Transformation Format, little-endian byte order
 * UTF-16		Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark
 */
public final class CharacterSet {

	public final static String UTF_8 = "UTF-8";
	public final static String UTF_16 = "UTF-16";
	public final static String UTF_16BE = "UTF-16BE";
	public final static String UTF_16LE = "UTF-16LE";
	public final static String US_ASCII = "US-ASCII";
	public final static String ISO_8859_1  = "ISO-8859-1 ";

	private CharacterSet(){};
}

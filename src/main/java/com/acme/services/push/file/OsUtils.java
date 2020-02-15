/**
 * 
 */
package com.acme.services.push.file;

/**
 * 
 * @author cgordon
 * @created 09/05/2017
 * @version 1.0
 *
 * helper class to check the operating system this Java VM runs in
 *
 */
import java.io.IOException;
import java.util.Locale;

public class OsUtils{

	public enum OS {
		WINDOWS,
		UNIX,
		POSIX_UNIX,
		MAC,
		OTHER;

		private String version;

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
	}

	private static OS os = OS.OTHER;

	static {
		try {
			String osName = System.getProperty("os.name");
			if (osName == null) {
				throw new IOException("os.name not found");
			}
			osName = osName.toLowerCase(Locale.ENGLISH);
			if (osName.contains("windows")) {
				os = OS.WINDOWS;
			} else if (osName.contains("linux")
					|| osName.contains("mpe/ix")
					|| osName.contains("freebsd")
					|| osName.contains("irix")
					|| osName.contains("digital unix")
					|| osName.contains("unix")) {
				os = OS.UNIX;
			} else if (osName.contains("mac os")) {
				os = OS.MAC;
			} else if (osName.contains("sun os")
					|| osName.contains("sunos")
					|| osName.contains("solaris")) {
				os = OS.POSIX_UNIX;
			} else if (osName.contains("hp-ux") 
					|| osName.contains("aix")) {
				os = OS.POSIX_UNIX;
			} else {
				os = OS.OTHER;
			}

		} catch (Exception ex) {
			os = OS.OTHER;
		} finally {
			os.setVersion(System.getProperty("os.version"));
		}
	}

	public static final OS getOs() {

		return os;
	}
}
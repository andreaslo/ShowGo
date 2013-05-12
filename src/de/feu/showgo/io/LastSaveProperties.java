package de.feu.showgo.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * A utility class for writing the .properties file that stores the last used
 * save file. The information is written to .showgo.properties in the users home
 * directory. It is used to load the last save file during application startup.
 */
public class LastSaveProperties {

	private static final String FILE_NAME = ".showgo.properties";
	private static final String LAST_SAVE_FILE_ATTR_NAME = "lastSaveFile";
	private static final Logger log = Logger.getLogger(LastSaveProperties.class);

	/**
	 * 
	 * @return The full path to the last save file
	 */
	public static String getLastSaveFile() {
		String userHome = System.getProperty("user.home");
		File f = new File(userHome, FILE_NAME);
		log.debug("Loading Properties file " + f);
		if (!f.exists()) {
			log.debug("properties file does not exists");
			return null;
		}
		log.debug("properties file exists");

		Properties defaultProps = new Properties();
		FileInputStream in;

		try {
			in = new FileInputStream(f);
			defaultProps.load(in);
			String lastSave = defaultProps.getProperty(LAST_SAVE_FILE_ATTR_NAME);
			if (lastSave != null) {
				log.debug("last save file: " + lastSave);
				return lastSave;
			}
		} catch (FileNotFoundException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}

		return null;
	}

	/**
	 * Writes the full path of the provided file to .showgo.properties.
	 * 
	 * @param saveFile
	 */
	public static void writeLastSaveFile(File saveFile) {
		Properties prop = new Properties();
		try {
			log.debug("writing .showgo.properties");
			log.debug("last saved file: " + saveFile.getCanonicalPath());
			prop.setProperty(LAST_SAVE_FILE_ATTR_NAME, saveFile.getCanonicalPath());

			String userHome = System.getProperty("user.home");
			File f = new File(userHome, FILE_NAME);
			log.debug("Saving to file " + f);
			prop.store(new FileOutputStream(f), null);
		} catch (IOException e) {
			log.error("", e);
		}
	}

}

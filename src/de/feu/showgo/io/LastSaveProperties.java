package de.feu.showgo.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LastSaveProperties {

	private static final Logger log = Logger.getLogger(LastSaveProperties.class);
	
	public static String getLastSaveFile(){
		String userHome = System.getProperty("user.home");
		File f = new File(userHome, ".showgo.properties");
		log.debug("Loading Properties file " + f);
		if(!f.exists()){
			log.debug("properties file does not exists");
			return null;
		}
		log.debug("properties file exists");
		
		Properties defaultProps = new Properties();
		FileInputStream in;
		
		try {
			in = new FileInputStream(f);
			defaultProps.load(in);
			String lastSave = defaultProps.getProperty("lastSaveFile");
			if(lastSave != null){
				log.debug("last save file: "+lastSave);
				return lastSave;
			}
		} catch (FileNotFoundException e) {
			log.error("",e);
		} catch (IOException e) {
			log.error("",e);
		}
		
		return null;
	}

	
}

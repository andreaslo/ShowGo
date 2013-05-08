package de.feu.showgo;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import de.feu.showgo.io.LastSaveProperties;
import de.feu.showgo.io.ShowGoIO;
import de.feu.showgo.model.ShowGo;

public class ShowGoDAO {

	private static final Logger log = Logger.getLogger(ShowGoDAO.class);
	private static final String AUTOSAVE_FILENAME = ".autosave.showgo";
	private static File saveFile;
	private static ShowGo showGo = new ShowGo();
	
	public static ShowGo getShowGo() {
		return showGo;
	}

	public static File getSaveFile() {
		return saveFile;
	}

	public static void setSaveFile(File saveFile) {
		ShowGoDAO.saveFile = saveFile;
	}

	public static void setShowGo(ShowGo showGo) {
		ShowGoDAO.showGo = showGo;
	}

	public static void saveToDisc(File file) throws JAXBException{
		ShowGoIO.saveShowGo(showGo, file);
	}
	
	public static void autosave() {
		log.debug("autosaving");
		String userHome = System.getProperty("user.home");
		File f = new File(userHome, AUTOSAVE_FILENAME);
		try {
			saveToDisc(f);
			LastSaveProperties.writeLastSaveFile(f);
		} catch (JAXBException e) {
			log.error("",e);
		}
		
	}
	
	
}

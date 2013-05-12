package de.feu.showgo;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import de.feu.showgo.io.LastSaveProperties;
import de.feu.showgo.io.ShowGoIO;
import de.feu.showgo.model.ShowGo;

/**
 * This class holds one object of the ShowGo class that is used throughout the
 * whole application. It also provides methods for saving the object to disc.
 */
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

	/**
	 * Setting the file to which the ShowGo singleton was saved last. It is NOT
	 * set automatically by saveToDisc. The information is used by the save
	 * action in the menu bar. If the save file exists, the user may save into
	 * it. If not, a file select dialog is displayed.
	 * 
	 * @param saveFile
	 */
	public static void setSaveFile(File saveFile) {
		ShowGoDAO.saveFile = saveFile;
	}

	public static void setShowGo(ShowGo showGo) {
		ShowGoDAO.showGo = showGo;
	}

	/**
	 * This method saves the ShowGo singleton to the provided file.
	 * 
	 * @param file
	 * @throws JAXBException
	 */
	public static void saveToDisc(File file) throws JAXBException {
		ShowGoIO.saveShowGo(showGo, file);
	}

	/**
	 * This method saves the ShowGo singleton to .autosave.showgo in the users
	 * home directory.
	 */
	public static void autosave() {
		log.debug("autosaving");
		String userHome = System.getProperty("user.home");
		File f = new File(userHome, AUTOSAVE_FILENAME);
		try {
			saveToDisc(f);
			LastSaveProperties.writeLastSaveFile(f);
		} catch (JAXBException e) {
			log.error("", e);
		}

	}

}

package de.feu.showgo;

import java.io.File;

import de.feu.showgo.model.ShowGo;

public class ShowGoDAO {

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
	
	
	
}

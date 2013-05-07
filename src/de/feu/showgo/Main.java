package de.feu.showgo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.swing.UIManager;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;

import de.feu.showgo.io.LastSaveProperties;
import de.feu.showgo.io.ShowGoIO;
import de.feu.showgo.model.ShowGo;
import de.feu.showgo.ui.MainWindow;

public class Main {

	private static final Logger log = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {

		BasicConfigurator.configure();

		
		PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {
		}
		Locale.setDefault(Locale.GERMAN);
		
		String lastSaved = LastSaveProperties.getLastSaveFile();
		String windowTitle = null;
		if(lastSaved != null){
			try{
				File lastSavedFile = new File(lastSaved);
				windowTitle = lastSavedFile.getName();
				ShowGo loaded = ShowGoIO.loadShowGo(lastSavedFile);
				ShowGoDAO.setShowGo(loaded);
				ShowGoDAO.setSaveFile(lastSavedFile);
			}catch (Exception e){
				log.error("",e);
			}
		}
		
		MainWindow window = new MainWindow();
		window.init();
		window.getNavTree().refreshTree();
		window.setTitleFilename(windowTitle);
	}
		

}

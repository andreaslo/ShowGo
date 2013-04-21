package de.feu.showgo;

import java.util.Locale;

import javax.swing.UIManager;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;

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

		MainWindow window = new MainWindow();
		window.init();
	}

	

}

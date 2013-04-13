package de.feu.showgo;

import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;

import de.feu.showgo.model.ShowGo;
import de.feu.showgo.ui.MainWindow;

public class Main {

	private static final Logger log = Logger.getLogger(Main.class);
	private static ShowGo showGo = new ShowGo();
	
	public static void main(String[] args) {

		PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {
		}

		MainWindow window = new MainWindow();
		window.init();
	}

	public static ShowGo getShowGo() {
		return showGo;
	}

	public static void setShowGo(ShowGo showGo) {
		Main.showGo = showGo;
	}

	

}

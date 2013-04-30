package de.feu.showgo.ui.views;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.feu.showgo.ui.MainWindow;

public class EnsembleView extends JPanel {
	
	private final static Logger log = Logger.getLogger(EnsembleView.class);
	private MainWindow mainWindow;
	
	
	public EnsembleView(MainWindow mainWindow) {
		log.debug("showing ensemble view");
		this.mainWindow = mainWindow;
		setName("Ensemble anlegen");
	}


}
package de.feu.showgo.ui.views;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.feu.showgo.ui.MainWindow;

public class ProductionView extends JPanel {

	private final static Logger log = Logger.getLogger(ProductionView.class);
	private MainWindow mainWindow;
	
	public ProductionView(MainWindow mainWindow) {
		log.debug("showing production view");
		this.mainWindow = mainWindow;
		setName("Neue Inszenierung anlegen");
		createComponent();
	}
	
	private void createComponent(){
		
	}
	
}

package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;

public class PlaybillView extends JPanel {

	private final static Logger log = Logger.getLogger(EnsembleView.class);
	private MainWindow mainWindow;
	private Production production;
	
	public PlaybillView(MainWindow mainWindow, Production production) {
		log.debug("showing playbill view for " + production);
		this.mainWindow = mainWindow;
		this.production = production;
		setName("Programmheft");
		createComponent();
	}
	
	private void createComponent(){
		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, 60, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30, 30 } };
		setLayout(new TableLayout(size));
		
		
		revalidate();
		repaint();
	}
	
}

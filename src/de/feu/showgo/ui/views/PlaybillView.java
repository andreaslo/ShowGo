package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
				{ 20, TableLayout.PREFERRED, 10, TableLayout.PREFERRED } };
		setLayout(new TableLayout(size));
		
		JButton saveButton = new JButton("In Datei speichern");
		
		
		JTextArea playbillArea = new JTextArea();
		playbillArea.setBorder(BorderFactory.createEtchedBorder());
		
		add(saveButton,"1,1,l,c");
		add(playbillArea, "1,3");
		
		revalidate();
		repaint();
	}
	
}

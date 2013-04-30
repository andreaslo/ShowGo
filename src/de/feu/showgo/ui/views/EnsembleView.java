package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Act;
import de.feu.showgo.model.Scene;
import de.feu.showgo.ui.MainWindow;

public class EnsembleView extends JPanel {
	
	private final static Logger log = Logger.getLogger(EnsembleView.class);
	private MainWindow mainWindow;
	
	
	public EnsembleView(MainWindow mainWindow) {
		log.debug("showing ensemble view");
		this.mainWindow = mainWindow;
		setName("Ensemble anlegen");
		createComponent();
	}


	private void createComponent() {
		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30 } };
		setLayout(new TableLayout(size));
	
		JPanel ensembleNamePanel = createEnsembleNamePanel();
		PersonsTable availablePersonsTable = createAvailablePersonsPanel();
	
		add(ensembleNamePanel, "1,1");
		add(availablePersonsTable, "1,2");
	}
	
	private JPanel createEnsembleNamePanel(){
		JPanel ensembleNamePanel = new JPanel();
		double size[][] = { { 120, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		ensembleNamePanel.setLayout(layout);
		
		ensembleNamePanel.add(new JLabel("Ensemble Name:"), "0,0");
		
		JTextField ensembleNameInput = new JTextField();
		ensembleNamePanel.add(ensembleNameInput, "1,0,f,c");

		return ensembleNamePanel;
	}

	private PersonsTable createAvailablePersonsPanel(){
		PersonsTable availablePersonsTable = new PersonsTable(this, ShowGoDAO.getShowGo().getPersons());
		
		return availablePersonsTable;		
	}
	
	

}
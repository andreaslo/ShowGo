package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.ui.MainWindow;

public class EditEnsembleAction implements ActionListener {
	
	private Ensemble ensemble;
	private MainWindow mainWindow;

	public EditEnsembleAction(MainWindow mainWindow, Ensemble ensemble) {
		this.mainWindow = mainWindow;
		this.ensemble = ensemble;
	 }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showEditEnsemble(ensemble);
	}

	
	
}

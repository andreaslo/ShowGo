package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.ui.MainWindow;

/**
 * This action displays the edit ensemble view in read only mode.
 */
public class ViewEnsembleAction implements ActionListener {
	
	private Ensemble ensemble;
	private MainWindow mainWindow;

	/**
	 * Instantiates a new view ensemble action.
	 *
	 * @param mainWindow the main window
	 * @param ensemble the ensemble
	 */
	public ViewEnsembleAction(MainWindow mainWindow, Ensemble ensemble) {
		this.mainWindow = mainWindow;
		this.ensemble = ensemble;
	 }
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showEditEnsemble(ensemble, false);
	}

	
	
}

package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.ui.MainWindow;

/**
 * This action executes the copy ensemble action. It creates a copy of the
 * provided ensemble and opens the edit ensemble view with it.
 */
public class CopyEnsembleAction implements ActionListener {

	private Ensemble ensemble;
	private MainWindow mainWindow;

	/**
	 * Instantiates a new copy ensemble action.
	 *
	 * @param mainWindow the main window
	 * @param ensemble the ensemble
	 */
	public CopyEnsembleAction(MainWindow mainWindow, Ensemble ensemble) {
		this.mainWindow = mainWindow;
		this.ensemble = ensemble;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Ensemble copiedEnsemble = new Ensemble(ensemble);
		copiedEnsemble.setName("Kopie von " + ensemble.getName());
		mainWindow.showEditEnsemble(copiedEnsemble, true);
	}

}

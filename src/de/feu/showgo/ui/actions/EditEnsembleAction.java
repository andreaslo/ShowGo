package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;

/**
 * This action opens the edit ensemble view with the provided ensemble. Only
 * ensembles that are not used in a production can be edited.
 * 
 */
public class EditEnsembleAction implements ActionListener {

	private Ensemble ensemble;
	private MainWindow mainWindow;

	/**
	 * Instantiates a new edit ensemble action.
	 *
	 * @param mainWindow the main window
	 * @param ensemble the ensemble
	 */
	public EditEnsembleAction(MainWindow mainWindow, Ensemble ensemble) {
		this.mainWindow = mainWindow;
		this.ensemble = ensemble;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Production production : ShowGoDAO.getShowGo().getProductions()) {
			if (production.getEnsemble() == ensemble) {
				JOptionPane.showMessageDialog(mainWindow, "Das Ensemble " + ensemble.getName() + " ist der Inszenierung " + production.getName()
						+ " zugeordnet und kann daher nicht bearbeitet werden.", "Ensemble Inszenierung zugeordnet", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		mainWindow.showEditEnsemble(ensemble, true);
	}

}

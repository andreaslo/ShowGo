package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.views.EnsembleView;

/**
 * This action deletes the provided ensemble from the showgo singleton. Only ensembles that are not used
 * in a production can be deleted.
 * 
 */
public class DeleteEnsembleAction implements ActionListener {

	private Ensemble ensemble;
	private MainWindow mainWindow;

	/**
	 * Instantiates a new delete ensemble action.
	 *
	 * @param mainWindow the main window
	 * @param ensemble the ensemble
	 */
	public DeleteEnsembleAction(MainWindow mainWindow, Ensemble ensemble) {
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
						+ " zugeordnet und kann daher nicht gelöscht werden.", "Ensemble Inszenierung zugeordnet", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		int choice = JOptionPane.showConfirmDialog(mainWindow, "Möchten Sie wirklich das Ensemble " + ensemble.getName() + " löschen?",
				ensemble.getName() + " löschen", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {

			// If the deleted ensemble is currently being edited, switch to the
			// startup view.
			JPanel currentView = mainWindow.getCurrentView();
			if (currentView instanceof EnsembleView) {
				Ensemble editedEnsemble = ((EnsembleView) currentView).getModel();
				if (editedEnsemble == ensemble) {
					mainWindow.showStartupView();
				}
			}

			ShowGoDAO.getShowGo().delteEnsemble(ensemble);
			mainWindow.getNavTree().refreshEnsembles();
		}
	}

}

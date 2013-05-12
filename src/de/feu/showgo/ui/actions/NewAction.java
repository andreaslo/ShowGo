package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.ShowGo;
import de.feu.showgo.ui.MainWindow;

/**
 * This action creates a new showgo singleton, discarding the current one.
 */
public class NewAction implements ActionListener {

	private MainWindow mainWindow;

	/**
	 * Instantiates a new new action.
	 *
	 * @param mainWindow the main window
	 */
	public NewAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(mainWindow,
				"Sind Sie sicher, dass Sie ein neues Theater anlegen wollen? Alle nicht gespeicherten Daten gehen verloren.", "Neues Theater",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			ShowGoDAO.setShowGo(new ShowGo());
			ShowGoDAO.setSaveFile(null);
			mainWindow.setTitleFilename(null);
			mainWindow.getNavTree().refreshTree();
			mainWindow.showStartupView();
		}

	}

}

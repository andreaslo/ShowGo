package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;

/**
 * This action opens the edit production view with the provided production.
 */
public class EditProductionAction implements ActionListener {
	
	private Production production;
	private MainWindow mainWindow;

	/**
	 * Instantiates a new edit production action.
	 *
	 * @param mainWindow the main window
	 * @param production the production
	 */
	public EditProductionAction(MainWindow mainWindow, Production production) {
		this.mainWindow = mainWindow;
		this.production = production;
	 }
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showEditProduction(production);
	}

	
	
}

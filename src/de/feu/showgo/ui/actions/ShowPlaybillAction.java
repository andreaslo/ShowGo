package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;

/**
 * This action displays the playbill view for a provided production.
 */
public class ShowPlaybillAction implements ActionListener {

	private MainWindow mainWindow;
	private Production production;
	
	/**
	 * Instantiates a new show playbill action.
	 *
	 * @param mainWindow the main window
	 * @param production the production
	 */
	public ShowPlaybillAction(MainWindow mainWindow, Production production){
		this.mainWindow = mainWindow;
		this.production = production;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showPlaybillView(production);
	}

}

package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.ui.MainWindow;

/**
 * This action displays the production view.
 */
public class ShowProductionViewAction implements ActionListener {

	private MainWindow mainWindow;
	
	/**
	 * Instantiates a new show production view action.
	 *
	 * @param mainWindow the main window
	 */
	public ShowProductionViewAction(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showProductionView();
	}

}

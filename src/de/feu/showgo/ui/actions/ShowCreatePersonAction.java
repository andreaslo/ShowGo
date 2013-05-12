package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.ui.MainWindow;

/**
 * This action displays the CreatePerson view.
 */
public class ShowCreatePersonAction implements ActionListener {

	private MainWindow mainWindow;
	
	/**
	 * Instantiates a new show create person action.
	 *
	 * @param mainWindow the main window
	 */
	public ShowCreatePersonAction(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showCreatePersonView();
	}

}

package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.ui.MainWindow;

/**
 * This action displays to the ensemble view.
 */
public class ShowEnsembleViewAction  implements ActionListener {

	private MainWindow mainWindow;
	
	public ShowEnsembleViewAction(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showEnsembleView();
	}

}

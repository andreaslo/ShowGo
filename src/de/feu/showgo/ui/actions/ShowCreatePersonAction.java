package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.ui.MainWindow;

public class ShowCreatePersonAction implements ActionListener {

	private MainWindow mainWindow;
	
	public ShowCreatePersonAction(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showCreatePersonView();
	}

}

package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;

public class ShowPlaybillAction implements ActionListener {

	private MainWindow mainWindow;
	private Production production;
	
	public ShowPlaybillAction(MainWindow mainWindow, Production production){
		this.mainWindow = mainWindow;
		this.production = production;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showPlaybillView(production);
	}

}

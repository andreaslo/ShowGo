package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.ShowGo;
import de.feu.showgo.ui.MainWindow;

public class NewAction implements ActionListener {
	
	private MainWindow mainWindow;

	public NewAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	 }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ShowGoDAO.setShowGo(new ShowGo());
		ShowGoDAO.setSaveFile(null);
		mainWindow.setTitleFilename(null);
		mainWindow.getNavTree().refreshTree();
		mainWindow.showStartupView();
	}

	
	
}

package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.ShowGo;
import de.feu.showgo.ui.MainWindow;

public class LoadAction implements ActionListener {

	private MainWindow mainWindow;

	public LoadAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int result = JOptionPane.showConfirmDialog(mainWindow,
				"Sind Sie sicher, dass Sie ein neues Theater anlegen wollen? Alle nicht gespeicherten Daten gehen verloren.", "Neues Theater",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			
		}
		
		
		ShowGoDAO.setShowGo(new ShowGo());
		ShowGoDAO.setSaveFile(null);
		mainWindow.setTitleFilename(null);
		mainWindow.getNavTree().refreshTree();
		mainWindow.showStartupView();
	}



}

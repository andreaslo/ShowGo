package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.io.ShowGoIO;
import de.feu.showgo.model.ShowGo;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.dialogs.ShowGoLoadFile;

public class LoadAction implements ActionListener {

	private MainWindow mainWindow;

	public LoadAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ShowGoLoadFile dialog = new ShowGoLoadFile(mainWindow);
		dialog.showDialog();
		File selectedFile = dialog.getSelectedFile();
		
		if(dialog.isApproved()){
			ShowGo loaded = ShowGoIO.loadShowGo(selectedFile);
			
			ShowGoDAO.setShowGo(loaded);
			ShowGoDAO.setSaveFile(selectedFile);
			mainWindow.setTitleFilename(selectedFile.getName());
			mainWindow.getNavTree().refreshTree();
			mainWindow.showStartupView();
		}
		

	}



}

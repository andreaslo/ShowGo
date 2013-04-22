package de.feu.showgo.ui.dialogs;

import javax.swing.JDialog;

import de.feu.showgo.model.Role;
import de.feu.showgo.ui.MainWindow;

public class RolesSelectDialog {

	private JDialog dialog;
	
	public RolesSelectDialog(Role role, MainWindow mainWindow) {
		dialog = new JDialog(mainWindow);
		dialog.setSize(400,600);
		dialog.setTitle("Rollenzurodnung für " + role.getName());
		dialog.setModal(true);
		dialog.setLocationRelativeTo(mainWindow);
		
		
	}
	
	
	public void showDialog(){
		dialog.setVisible(true);
	}

}

package de.feu.showgo.ui.views;

import java.util.List;

import javax.swing.JPanel;

import de.feu.showgo.model.Role;
import de.feu.showgo.ui.MainWindow;

public class RolePanel extends JPanel {

	private MainWindow mainWindow;
	private List<Role> roles;
	private String roleActionName;

	public RolePanel(MainWindow mainWindow, List<Role> roles) {
		this.mainWindow = mainWindow;
		this.roles = roles;
		createComponent();
	}
	
	private void createComponent(){
		
	}
	
}

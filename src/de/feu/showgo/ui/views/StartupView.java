package de.feu.showgo.ui.views;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartupView extends JPanel {

	private static final long serialVersionUID = 1L;

	public StartupView() {
		createComponent();
	}
	
	private void createComponent(){
		JLabel welcomeLabel = new JLabel("Willkommen bei ShowGo!");
		this.add(welcomeLabel);
	}
	
}

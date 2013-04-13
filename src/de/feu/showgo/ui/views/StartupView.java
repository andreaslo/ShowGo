package de.feu.showgo.ui.views;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StartupView extends JPanel {

	public StartupView() {
		createComponent();
	}
	
	private void createComponent(){
		JButton foobar = new JButton("startup view");
		
		
		this.add(foobar);
	}
	
}

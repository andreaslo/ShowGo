package de.feu.showgo.ui.views;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ReadPlayView extends JPanel {

	public ReadPlayView() {
		createComponent();
	}
	
	private void createComponent(){
		JButton foobar = new JButton("read play view");
		
		
		this.add(foobar);
	}
	
}

package de.feu.showgo.ui.views;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import info.clearthought.layout.TableLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReadPlayView extends JPanel {

	public ReadPlayView() {
		createComponent();
	}

	private void createComponent() {

//		double size[][] = { { 20, 150, 20, TableLayout.FILL, 20 }, { 20, 30, 10, TableLayout.PREFERRED, 10, 30, 10, 30, 10, 30, 10, 30 } };
//		this.setLayout(new TableLayout(size));
		
		setLayout(new GridLayout(1,1));

		
		JPanel fileSelectPanel = createFileSelectPanel();
		
		
		add(fileSelectPanel);

	}
	
	private JPanel createFileSelectPanel(){
		JPanel fileSelectPanel = new JPanel();
		double size[][] = { { TableLayout.FILL, TableLayout.PREFERRED }, { 30, 30 } };
		fileSelectPanel.setLayout(new TableLayout(size));
		
		JTextField fileInput = new JTextField();
		JButton selectFileButton = new JButton("Durchsuchen");
		JButton doReadPlayButton = new JButton("Stück einlesen");
		
		fileSelectPanel.add(fileInput, "0,0,f,c");
		fileSelectPanel.add(selectFileButton, "1,0,r,c");
		fileSelectPanel.add(doReadPlayButton, "0,1,l,c");
		
		return fileSelectPanel;
	}

}

package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Act;
import de.feu.showgo.model.Paragraph;
import de.feu.showgo.model.Passage;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.StageDirection;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;

public class EditTheaterPlayPanel extends JPanel{

	private MainWindow mainWindow;
	private TheaterPlay play;
	private static final Logger log = Logger.getLogger(EditTheaterPlayPanel.class);

	public EditTheaterPlayPanel(MainWindow mainWindow, TheaterPlay play){
		this.mainWindow = mainWindow;
		this.play = play;
		createComponent();
	}
	
	
	
	private void createComponent(){
		setLayout(new TableLayout(generateLayoutSize(play)));
		int row = 0;
		
		for(Act act : play.getActs()){
			add(createActNamePanel(act), "0," + row);
			row++;
			
			for(Scene scene : act.getScenes()){
				add(createSceneNamePanel(scene), "0," + row);
				row++;
				
				for(Paragraph paragraph : scene.getParagraphs()){
					add(createParagraphPanel(paragraph), "0," + row);
					row++;
				}
			}
		}
	}
	
	private JPanel createActNamePanel(Act act){
		JPanel namePanel = new JPanel();
		double size[][] = { { 80, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Act:"), "0,0");

		JTextField actText = new JTextField(act.getName());
		namePanel.add(actText, "1,0,f,c");

		return namePanel;
	}
	
	private JPanel createSceneNamePanel(Scene scene){
		JPanel namePanel = new JPanel();
		double size[][] = { { 20, 80, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Szene:"), "1,0");

		JTextField actText = new JTextField(scene.getName());
		namePanel.add(actText, "2,0,f,c");

		return namePanel;
	}
	
	private JPanel createParagraphPanel(Paragraph paragraph){
		if(paragraph instanceof StageDirection){
			return createStageDiection((StageDirection) paragraph);
		}else if(paragraph instanceof Passage){
			return createPassagePanel((Passage) paragraph);
		}else{
			return null;
		}
	}

	private JPanel createPassagePanel(Passage passage) {
		JPanel namePanel = new JPanel();
		double size[][] = { { 40, 120, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel(passage.getRole().getName()), "1,0");

		JTextArea actText = new JTextArea(passage.getText());

		namePanel.add(actText, "2,0,f,c");

		return namePanel;
	}



	private JPanel createStageDiection(StageDirection stageDirection) {
		JPanel namePanel = new JPanel();
		double size[][] = { { 40, 120, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Regieanweisung:"), "1,0");

		JTextArea actText = new JTextArea(stageDirection.getText());
		namePanel.add(actText, "2,0,f,c");

		return namePanel;
	}
	
	private double[][] generateLayoutSize(TheaterPlay play){
		int numRows = 0;
		
		for(Act act : play.getActs()){
			numRows++;
			for(Scene scene : act.getScenes()){
				numRows++;
				for(Paragraph paragraph : scene.getParagraphs()){
					numRows++;
				}
			}
		}
		
		double[][] size = new double[2][];
		double[] width = {TableLayout.PREFERRED};
		size[0] = width;
		double[] height = new double[numRows];
		size[1] = height;
				
		for(int i = 0; i < height.length; i++){
			height[i] = TableLayout.PREFERRED;
		}
		
		return size;
	}

}

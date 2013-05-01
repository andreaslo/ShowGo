package de.feu.showgo.ui.views;

import java.awt.Component;

import info.clearthought.layout.TableLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Act;
import de.feu.showgo.model.Paragraph;
import de.feu.showgo.model.Passage;
import de.feu.showgo.model.Role;
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
					add(createParagraphPanel(paragraph, scene.getAllRole()), "0," + row);
					row++;
				}
			}
		}
	}
	
	private JPanel createActNamePanel(Act act){
		JPanel namePanel = new JPanel();
		double size[][] = { { 80, 80, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Akt:"), "1,0");

		JTextField actText = new JTextField(act.getName());
		namePanel.add(actText, "2,0,f,c");
		
		JButton delete = new JButton("Löschen");
		namePanel.add(delete, "0,0,l,c");

		return namePanel;
	}
	
	private JPanel createSceneNamePanel(Scene scene){
		JPanel namePanel = new JPanel();
		double size[][] = { { 20, 80, 80, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Szene:"), "2,0");

		JTextField actText = new JTextField(scene.getName());
		namePanel.add(actText, "3,0,f,c");

		JButton delete = new JButton("Löschen");
		namePanel.add(delete, "1,0,l,c");
		
		return namePanel;
	}
	
	private JPanel createParagraphPanel(Paragraph paragraph, Role allRole){
		if(paragraph instanceof StageDirection){
			return createStageDiection((StageDirection) paragraph);
		}else if(paragraph instanceof Passage){
			return createPassagePanel((Passage) paragraph, allRole);
		}else{
			return null;
		}
	}

	private JPanel createPassagePanel(Passage passage, Role allRole) {
		JPanel namePanel = new JPanel();
		double size[][] = { { 40, 80, 200, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);
		
		JComboBox<Role> roleSelect = new JComboBox<Role>();
		roleSelect.setRenderer(new RoleComboRederer());
		for(Role role : play.getRoles()){
			roleSelect.addItem(role);
		}
		if(allRole != null){
			roleSelect.addItem(allRole);
		}
		roleSelect.setSelectedItem(passage.getRole());
		namePanel.add(roleSelect, "2,0,l,c");

		JTextArea actText = new JTextArea(passage.getText());
		
		Border border = BorderFactory.createEtchedBorder();
		actText.setBorder(border);

		namePanel.add(actText, "3,0,f,c");

		JButton delete = new JButton("Löschen");
		namePanel.add(delete, "1,0,l,c");
		
		return namePanel;
	}



	private JPanel createStageDiection(StageDirection stageDirection) {
		JPanel namePanel = new JPanel();
		double size[][] = { { 40, 80, 200, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Regieanweisung:"), "2,0");

		JTextArea stageDirectionText = new JTextArea(stageDirection.getText());
		Border border = BorderFactory.createEtchedBorder();
		stageDirectionText.setBorder(border);
		namePanel.add(stageDirectionText, "3,0,f,c");

		JButton delete = new JButton("Löschen");
		namePanel.add(delete, "1,0,l,c");
		
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
	
	
	private class RoleComboRederer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				Role role = (Role) value;
				setText(role.getName());
			}

			return this;
		}
	}

}

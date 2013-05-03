package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import de.feu.showgo.ui.listener.RoleDeleteListener;

public class EditTheaterPlayPanel extends JPanel {

	private MainWindow mainWindow;
	private TheaterPlay play;
	private static final Logger log = Logger.getLogger(EditTheaterPlayPanel.class);
	private RolePanel roleDisplay;
	private List<ActPanelWrapper> actWrapperList;

	public EditTheaterPlayPanel(MainWindow mainWindow, TheaterPlay play){
		this.mainWindow = mainWindow;
		this.play = play;
		createComponent();
	}
	
	private void createComponent(){		
		JPanel theaterDataPanel = new JPanel();
		theaterDataPanel.setLayout(new TableLayout(generateLayoutSize(play)));
		int row = 0;
		
		actWrapperList = new ArrayList<EditTheaterPlayPanel.ActPanelWrapper>();
		for(Act act : play.getActs()){
			JPanel actPanel = new JPanel();
			actPanel.setLayout(new TableLayout(generateLayoutSize(act)));
			actPanel.add(createActNamePanel(act), "0,0");
			
			ActPanelWrapper actWrapper = new ActPanelWrapper();
			actWrapper.act = act;
			actWrapper.panel = actPanel;		
			
			int actRow = 1;
			for(Scene scene : act.getScenes()){
				log.debug("adding scene " + scene.getName());
				JPanel scenePanel = new JPanel();
				scenePanel.setLayout(new TableLayout(generateLayoutSize(scene)));
				scenePanel.add(createSceneNamePanel(scene, act, scenePanel), "0,0");
				
				ScenePanelWrapper sceneWrapper = new ScenePanelWrapper();
				sceneWrapper.panel = scenePanel;
				sceneWrapper.scene = scene;
				
				int sceneRow = 1;
				for(Paragraph paragraph : scene.getParagraphs()){
					JPanel paragraphPanel = createParagraphPanel(paragraph, scene.getAllRole());
					
					ParagraphPanelWrapper panelWrapper = new ParagraphPanelWrapper();
					panelWrapper.panel = paragraphPanel;
					panelWrapper.paragraph = paragraph;
					sceneWrapper.children.add(panelWrapper);
					
					scenePanel.add(paragraphPanel, "0," + sceneRow);
					sceneRow++;
				}
				
				actPanel.add(scenePanel, "0," + actRow);
				actRow++;
				
				actWrapper.children.add(sceneWrapper);
			}

			theaterDataPanel.add(actPanel, "0," + row);
			row++;
			
			actWrapperList.add(actWrapper);
		}
		
		double size[][] = { { TableLayout.FILL }, { TableLayout.PREFERRED, TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		setLayout(layout);
		
		roleDisplay = new RolePanel(mainWindow, play);
		roleDisplay.setShowDeleteButton(true);
		roleDisplay.setChangePseudoEnabled(false);
		roleDisplay.addRoleDeleteEventListener(new RoleDeleteListener() {
			
			@Override
			public void deleteRole(Role role) {
				log.debug("event deleting role: " + role);
				removeRole(role);
			}
		});
		add(roleDisplay, "0,0");
		add(theaterDataPanel, "0,1");
		
	}
	
	private JPanel createActNamePanel(final Act act){
		final JPanel namePanel = new JPanel();
		double size[][] = { { 80, 80, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Akt:"), "1,0");

		JTextField actText = new JTextField(act.getName());
		namePanel.add(actText, "2,0,f,c");
		
		JButton delete = new JButton("Löschen");
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAct(act);
			}
		});
		
		namePanel.add(delete, "0,0,l,c");

		return namePanel;
	}
	
	private JPanel createSceneNamePanel(final Scene scene, final Act act, final JPanel scenePanel){
		final JPanel namePanel = new JPanel();
		double size[][] = { { 20, 80, 80, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Szene:"), "2,0");

		JTextField actText = new JTextField(scene.getName());
		namePanel.add(actText, "3,0,f,c");

		JButton delete = new JButton("Löschen");
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				removeScene(scene);
			}
		});
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

	private JPanel createPassagePanel(final Passage passage, Role allRole) {
		final JPanel namePanel = new JPanel();
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
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeParagraph(passage);
			}
		});
		namePanel.add(delete, "1,0,l,c");
		
		return namePanel;
	}



	private JPanel createStageDiection(final StageDirection stageDirection) {
		final JPanel namePanel = new JPanel();
		double size[][] = { { 40, 80, 200, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Regieanweisung:"), "2,0");

		JTextArea stageDirectionText = new JTextArea(stageDirection.getText());
		Border border = BorderFactory.createEtchedBorder();
		stageDirectionText.setBorder(border);
		namePanel.add(stageDirectionText, "3,0,f,c");

		JButton delete = new JButton("Löschen");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeParagraph(stageDirection);
			}
		});
		namePanel.add(delete, "1,0,l,c");
		
		return namePanel;
	}
	
	private double[][] generateLayoutSize(TheaterPlay play){
		return generateLayoutSize(play.getActs().size());
	}
	
	private double[][] generateLayoutSize(Scene scene){
		return generateLayoutSize(scene.getParagraphs().size() + 1);
	}
	
	private double[][] generateLayoutSize(Act act){
		return generateLayoutSize(act.getScenes().size() + 1);
	}
	
	private double[][] generateLayoutSize(int numRows){		
		double[][] size = new double[2][];
		double[] width = {TableLayout.FILL};
		size[0] = width;
		double[] height = new double[numRows];
		size[1] = height;
				
		for(int i = 0; i < height.length; i++){
			height[i] = TableLayout.PREFERRED;
		}
		
		return size;
	}
	
	
	private void checkRolesNecessary(){
		List<Role> toBeDeleted = new LinkedList<Role>();
		
		for(Role role : play.getRoles()){
			if(role.getWords() == 0  && !role.isPseudoRole()){
				int result = JOptionPane.showConfirmDialog(mainWindow, "Die Rolle \"" + role.getName() + "\" hat keine Sprachtexte mehr. Soll Sie gelöscht werden?", "Rolle löschen", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.OK_OPTION){
					toBeDeleted.add(role);
				}
			}
		}
		
		log.debug("roles queued for deletion: " + toBeDeleted);
		
		for(Role role : toBeDeleted){
			removeRole(role);
		}
	}
	
	private void removeRole(Role toBeDeleted){
		log.debug("delete role " + toBeDeleted);
		play.deleteRole(toBeDeleted);
		roleDisplay.removeRole(toBeDeleted);
		roleDisplay.refreshPseudoRoles();
		
		List<Paragraph> queuedForDeletion = new LinkedList<Paragraph>();
		
		for(ActPanelWrapper actWrapper : actWrapperList){
			for(ScenePanelWrapper sceneWrapper : actWrapper.children){
				for(ParagraphPanelWrapper paragraphWrapper : sceneWrapper.children){
					if(paragraphWrapper.paragraph instanceof Passage){
						Passage passage = (Passage) paragraphWrapper.paragraph;
						if(passage.getRole() == toBeDeleted){
							queuedForDeletion.add(passage);
						}
					}
				}
			}
		}
		
		for(Paragraph paragraph : queuedForDeletion){
			removeParagraph(paragraph);
		}
		
		
	}
	
	
	private void removeAct(Act act){
		ActPanelWrapper toBeDeleted = null;
		for(ActPanelWrapper wrapper : actWrapperList){
			if(wrapper.act == act){
				toBeDeleted = wrapper;
				break;
			}
		}
		
		toBeDeleted.panel.removeAll();
		actWrapperList.remove(toBeDeleted);
		play.deleteAct(act);
		
		genericAfterDelete();
	}
	
	private void removeScene(Scene scene){
		ScenePanelWrapper toBeDeleted = null;
		ActPanelWrapper surroundingActWrapper = null;
		for(ActPanelWrapper actWrapper : actWrapperList){
			for(ScenePanelWrapper sceneWrapper : actWrapper.children){
				if(sceneWrapper.scene == scene){
					toBeDeleted = sceneWrapper;
					surroundingActWrapper = actWrapper;
					break;
				}
			}
		}
		
		toBeDeleted.panel.removeAll();
		surroundingActWrapper.act.deleteScene(scene);			
		surroundingActWrapper.children.remove(toBeDeleted);
		if(surroundingActWrapper.children.isEmpty()){
			removeAct(surroundingActWrapper.act);
		}
		
		genericAfterDelete();
	}
	
	private void removeParagraph(Paragraph paragraph){
		ScenePanelWrapper surroundingSceneWrapper = null;
		ParagraphPanelWrapper toBeDeleted = null;
		for(ActPanelWrapper actWrapper : actWrapperList){
			for(ScenePanelWrapper sceneWrapper : actWrapper.children){
				for(ParagraphPanelWrapper paragraphWrapper : sceneWrapper.children){
					if(paragraphWrapper.paragraph == paragraph){
						toBeDeleted = paragraphWrapper;
						surroundingSceneWrapper = sceneWrapper;
						break;
					}
				}
			}
		}
		
		toBeDeleted.panel.removeAll();
		surroundingSceneWrapper.scene.deleteParagraph(paragraph);			
		surroundingSceneWrapper.children.remove(toBeDeleted);
		if(surroundingSceneWrapper.children.isEmpty()){
			removeScene(surroundingSceneWrapper.scene);
		}
		
		genericAfterDelete();
	}
	
	private void genericAfterDelete(){
		roleDisplay.updateWordCounter();
		roleDisplay.refreshPseudoRoles();
		revalidate();
		repaint();
		checkRolesNecessary();
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
	
	private class ActPanelWrapper {
		JPanel panel;
		Act act;
		List<ScenePanelWrapper> children = new ArrayList<EditTheaterPlayPanel.ScenePanelWrapper>();
	}
	
	private class ScenePanelWrapper {
		JPanel panel;
		Scene scene;
		List<ParagraphPanelWrapper> children = new ArrayList<EditTheaterPlayPanel.ParagraphPanelWrapper>();
	}
	
	private class ParagraphPanelWrapper {
		JPanel panel;
		Paragraph paragraph;
	}

}

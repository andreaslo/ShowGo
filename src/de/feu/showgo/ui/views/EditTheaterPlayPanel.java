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

/**
 * This view displays a theater play to the users and provides ui elements to
 * edit it. It the acts, scenes and paragraphs. The user can delete those,
 * change the text of a paragraph or assign a different role to a text. Also all
 * roles are displayed and the user can assign a cast or delete roles.
 */
public class EditTheaterPlayPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	private TheaterPlay play;
	private static final Logger log = Logger.getLogger(EditTheaterPlayPanel.class);
	private RolePanel roleDisplay;
	private List<ActPanelWrapper> actWrapperList;
	private JTextField playNameInput;
	private List<RoleDeleteListener> roleDeleteListener = new LinkedList<RoleDeleteListener>();

	/**
	 * Instantiates a new edits the theater play panel.
	 * 
	 * @param mainWindow
	 *            the main window
	 * @param play
	 *            the play
	 */
	public EditTheaterPlayPanel(MainWindow mainWindow, TheaterPlay play) {
		this.mainWindow = mainWindow;
		this.play = play;
		createComponent();
	}

	private void createComponent() {
		JPanel theaterDataPanel = new JPanel();
		theaterDataPanel.setLayout(new TableLayout(generateLayoutSize(play)));

		int row = 0;
		actWrapperList = new ArrayList<EditTheaterPlayPanel.ActPanelWrapper>();
		for (Act act : play.getActs()) {
			JPanel actPanel = new JPanel();
			actPanel.setLayout(new TableLayout(generateLayoutSize(act)));
			actPanel.add(createActNamePanel(act), "0,0");

			ActPanelWrapper actWrapper = new ActPanelWrapper();
			actWrapper.act = act;
			actWrapper.panel = actPanel;

			int actRow = 1;
			for (Scene scene : act.getScenes()) {
				log.debug("adding scene " + scene.getName());
				JPanel scenePanel = new JPanel();
				scenePanel.setLayout(new TableLayout(generateLayoutSize(scene)));
				scenePanel.add(createSceneNamePanel(scene, act, scenePanel), "0,0");

				ScenePanelWrapper sceneWrapper = new ScenePanelWrapper();
				sceneWrapper.panel = scenePanel;
				sceneWrapper.scene = scene;

				int sceneRow = 1;
				for (Paragraph paragraph : scene.getParagraphs()) {
					ParagraphPanelWrapper panelWrapper = new ParagraphPanelWrapper();
					JPanel paragraphPanel = createParagraphPanel(paragraph, scene.getAllRole(), panelWrapper);
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

		double size[][] = { { TableLayout.FILL }, { TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED } };
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

		JPanel playNamePanel = createPlayNamePanel();

		add(playNamePanel, "0,0");
		add(roleDisplay, "0,1");
		add(theaterDataPanel, "0,2");
	}

	private JPanel createActNamePanel(final Act act) {
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

	private JPanel createSceneNamePanel(final Scene scene, final Act act, final JPanel scenePanel) {
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

	private JPanel createParagraphPanel(Paragraph paragraph, Role allRole, ParagraphPanelWrapper panelWrapper) {
		if (paragraph instanceof StageDirection) {
			return createStageDiection((StageDirection) paragraph, panelWrapper);
		} else if (paragraph instanceof Passage) {
			return createPassagePanel((Passage) paragraph, allRole, panelWrapper);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private JPanel createPassagePanel(final Passage passage, Role allRole, ParagraphPanelWrapper panelWrapper) {
		final JPanel namePanel = new JPanel();
		double size[][] = { { 40, 80, 200, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		JComboBox<Role> roleSelect = new JComboBox<Role>();
		panelWrapper.roleSelect = roleSelect;

		roleSelect.setRenderer(new RoleComboRederer());
		for (Role role : play.getRoles()) {
			roleSelect.addItem(role);
		}
		if (allRole != null) {
			roleSelect.addItem(allRole);
		}

		roleSelect.setSelectedItem(passage.getRole());
		namePanel.add(roleSelect, "2,0,l,c");

		JTextArea paragraphText = new JTextArea(passage.getText());
		panelWrapper.text = paragraphText;

		Border border = BorderFactory.createEtchedBorder();
		paragraphText.setBorder(border);

		namePanel.add(paragraphText, "3,0,f,c");

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

	private JPanel createStageDiection(final StageDirection stageDirection, ParagraphPanelWrapper panelWrapper) {
		final JPanel namePanel = new JPanel();
		double size[][] = { { 40, 80, 200, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		namePanel.setLayout(layout);

		namePanel.add(new JLabel("Regieanweisung:"), "2,0");

		JTextArea stageDirectionText = new JTextArea(stageDirection.getText());
		panelWrapper.text = stageDirectionText;
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

	private JPanel createPlayNamePanel() {
		log.debug("creating play name panel");

		JPanel playNamePanel = new JPanel();
		double size[][] = { { 180, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		playNamePanel.setLayout(layout);

		playNamePanel.add(new JLabel("Name des Stückes:"), "0,0");

		playNameInput = new JTextField(play.getName());
		playNamePanel.add(playNameInput, "1,0,f,c");

		return playNamePanel;
	}

	private double[][] generateLayoutSize(TheaterPlay play) {
		return generateLayoutSize(play.getActs().size());
	}

	private double[][] generateLayoutSize(Scene scene) {
		return generateLayoutSize(scene.getParagraphs().size() + 1);
	}

	private double[][] generateLayoutSize(Act act) {
		return generateLayoutSize(act.getScenes().size() + 1);
	}

	private double[][] generateLayoutSize(int numRows) {
		double[][] size = new double[2][];
		double[] width = { TableLayout.FILL };
		size[0] = width;
		double[] height = new double[numRows];
		size[1] = height;

		for (int i = 0; i < height.length; i++) {
			height[i] = TableLayout.PREFERRED;
		}

		return size;
	}

	private void checkRolesNecessary() {
		List<Role> toBeDeleted = new LinkedList<Role>();

		for (Role role : play.getRoles()) {
			if (role.getWords() == 0 && !role.isPseudoRole()) {
				int result = JOptionPane.showConfirmDialog(mainWindow, "Die Rolle \"" + role.getName()
						+ "\" hat keine Sprachtexte mehr. Soll Sie gelöscht werden?", "Rolle löschen", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					toBeDeleted.add(role);
				}
			}
		}

		log.debug("roles queued for deletion: " + toBeDeleted);

		for (Role role : toBeDeleted) {
			removeRole(role);
			log.debug("deleted: " + role);
			for (RoleDeleteListener listener : roleDeleteListener) {
				log.debug("calling listener");
				listener.deleteRole(role);
			}
		}
	}

	private void removeRole(Role toBeDeleted) {
		log.debug("delete role " + toBeDeleted);
		play.deleteRole(toBeDeleted);
		roleDisplay.removeRole(toBeDeleted);
		roleDisplay.refreshPseudoRoles();

		List<Paragraph> queuedForDeletion = new LinkedList<Paragraph>();

		for (ActPanelWrapper actWrapper : actWrapperList) {
			for (ScenePanelWrapper sceneWrapper : actWrapper.children) {
				for (ParagraphPanelWrapper paragraphWrapper : sceneWrapper.children) {
					if (paragraphWrapper.paragraph instanceof Passage) {
						Passage passage = (Passage) paragraphWrapper.paragraph;
						if (passage.getRole() == toBeDeleted) {
							queuedForDeletion.add(passage);
						}
						paragraphWrapper.roleSelect.removeItem(toBeDeleted);
					}
				}
			}
		}

		for (Paragraph paragraph : queuedForDeletion) {
			removeParagraph(paragraph);
		}

		// Check whether pseudo roles became empty
		List<Role> queuePseudoRolesForDeletion = new LinkedList<Role>();
		for (Role role : play.getRoles()) {
			if (role.isPseudoRole()) {
				if (role.getAssigendRoles().isEmpty()) {
					int result = JOptionPane.showConfirmDialog(mainWindow, "Der Pseudorolle \"" + role.getName()
							+ "\" sind keine Rollen mehr zugewiesen. Soll diese gelöscht werden?", "Rolle löschen", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						queuePseudoRolesForDeletion.add(role);
					}
				}
			}
		}
		for (Role role : queuePseudoRolesForDeletion) {
			removeRole(role);
		}

	}

	private void removeAct(Act act) {
		ActPanelWrapper toBeDeleted = null;
		for (ActPanelWrapper wrapper : actWrapperList) {
			if (wrapper.act == act) {
				toBeDeleted = wrapper;
				break;
			}
		}

		toBeDeleted.panel.removeAll();
		actWrapperList.remove(toBeDeleted);
		play.deleteAct(act);

		genericAfterDelete();
	}

	private void removeScene(Scene scene) {
		ScenePanelWrapper toBeDeleted = null;
		ActPanelWrapper surroundingActWrapper = null;
		for (ActPanelWrapper actWrapper : actWrapperList) {
			for (ScenePanelWrapper sceneWrapper : actWrapper.children) {
				if (sceneWrapper.scene == scene) {
					toBeDeleted = sceneWrapper;
					surroundingActWrapper = actWrapper;
					break;
				}
			}
		}

		toBeDeleted.panel.removeAll();
		surroundingActWrapper.act.deleteScene(scene);
		surroundingActWrapper.children.remove(toBeDeleted);
		if (surroundingActWrapper.children.isEmpty()) {
			removeAct(surroundingActWrapper.act);
		}

		genericAfterDelete();
	}

	private void removeParagraph(Paragraph paragraph) {
		ScenePanelWrapper surroundingSceneWrapper = null;
		ParagraphPanelWrapper toBeDeleted = null;
		for (ActPanelWrapper actWrapper : actWrapperList) {
			for (ScenePanelWrapper sceneWrapper : actWrapper.children) {
				for (ParagraphPanelWrapper paragraphWrapper : sceneWrapper.children) {
					if (paragraphWrapper.paragraph == paragraph) {
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
		if (surroundingSceneWrapper.children.isEmpty()) {
			removeScene(surroundingSceneWrapper.scene);
		}

		genericAfterDelete();
	}

	private void genericAfterDelete() {
		roleDisplay.updateWordCounter();
		roleDisplay.refreshPseudoRoles();
		revalidate();
		repaint();
		checkRolesNecessary();
	}

	private class RoleComboRederer extends BasicComboBoxRenderer {
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("rawtypes")
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
		JComboBox<Role> roleSelect;
		JTextArea text;
	}

	/**
	 * Gets the role display.
	 * 
	 * @return the role display
	 */
	public RolePanel getRoleDisplay() {
		return roleDisplay;
	}

	/**
	 * Gets the play.
	 * 
	 * @return the play
	 */
	public TheaterPlay getPlay() {
		return play;
	}

	/**
	 * Adds a role delete listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addRoleDeleteListener(RoleDeleteListener listener) {
		roleDeleteListener.add(listener);
	}

	/**
	 * Save play to backing model. Changed texts and role/text assignments are
	 * saved.
	 */
	public void saveToBackingModel() {
		play.setName(playNameInput.getText());

		for (ActPanelWrapper actWrapper : actWrapperList) {
			for (ScenePanelWrapper sceneWrapper : actWrapper.children) {
				for (ParagraphPanelWrapper paragraphWrapper : sceneWrapper.children) {
					if (paragraphWrapper.paragraph instanceof Passage) {
						Passage passage = (Passage) paragraphWrapper.paragraph;
						passage.setRole((Role) paragraphWrapper.roleSelect.getSelectedItem());
						passage.setText(paragraphWrapper.text.getText());
					}

					if (paragraphWrapper.paragraph instanceof StageDirection) {
						StageDirection direction = (StageDirection) paragraphWrapper.paragraph;
						direction.setText(paragraphWrapper.text.getText());
					}
				}
			}
		}
	}

}

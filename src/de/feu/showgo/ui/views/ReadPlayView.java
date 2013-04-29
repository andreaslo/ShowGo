package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.io.ParsingException;
import de.feu.showgo.io.PlayParser;
import de.feu.showgo.io.RoleWordCounter;
import de.feu.showgo.model.Act;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.WindowColors;

public class ReadPlayView extends JPanel {

	private TheaterPlay model;
	private MainWindow mainWindow;
	private static final Logger log = Logger.getLogger(ReadPlayView.class);
	private List<RolePanel> rolePanels;
	private JLabel currentMessage;

	public ReadPlayView(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createComponent();
		setName("Bühnenstück einlesen");
	}

	private void createComponent() {
		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30 } };
		setLayout(new TableLayout(size));

		JPanel fileSelectPanel = createFileSelectPanel();

		add(fileSelectPanel, "1,1");
	}

	private JPanel createFileSelectPanel() {
		JPanel fileSelectPanel = new JPanel();
		double size[][] = { { TableLayout.FILL, 20, TableLayout.PREFERRED }, { 30, 30 } };
		fileSelectPanel.setLayout(new TableLayout(size));

		final JTextField fileInput = new JTextField("/home/andreas/Macshort.html");
		JButton selectFileButton = new JButton("Durchsuchen");

		selectFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogType(JFileChooser.OPEN_DIALOG);
				fc.setAcceptAllFileFilterUsed(true);

				FileFilter filter = new FileNameExtensionFilter("HTML-Datei", "html");
				fc.addChoosableFileFilter(filter);
				int result = fc.showOpenDialog(mainWindow);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					try {
						fileInput.setText(selectedFile.getCanonicalPath());
					} catch (IOException e) {
						log.error("", e);
						JOptionPane.showMessageDialog(mainWindow, "Es ist ein Problem beim Lesen der Datei aufgetreten", "Fehler",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JButton doReadPlayButton = new JButton("Stück einlesen");

		doReadPlayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File selectedFile = new File(fileInput.getText());

				PlayParser parser = new PlayParser();
				TheaterPlay parsedPlay = null;
				try {
					parsedPlay = parser.generatePlay(selectedFile);
				} catch (IOException e) {
					log.error("", e);
					JOptionPane.showMessageDialog(mainWindow, "Die Datei \"" + selectedFile.getName() + "\" konnte nicht gelesen werden.", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					return;
				} catch (ParsingException e) {
					log.error("", e);
					JOptionPane.showMessageDialog(mainWindow, "Die Datei \"" + selectedFile.getName()
							+ "\" konnte nicht geparst werden. Es ist folgender Fehler aufgetreten: \n" + e.getMessage(), "Fehler",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				model = parsedPlay;
				JOptionPane.showMessageDialog(mainWindow, "Das Stück " + parsedPlay.getName() + " wurde erfolgreich eingelesen.",
						parsedPlay.getName() + " erfoglreich eingelesen", JOptionPane.INFORMATION_MESSAGE);

				createAndShowRoleSelect();
			}
		});

		fileSelectPanel.add(fileInput, "0,0,f,c");
		fileSelectPanel.add(selectFileButton, "2,0,r,c");
		fileSelectPanel.add(doReadPlayButton, "0,1,l,c");

		return fileSelectPanel;
	}

	private void createAndShowRoleSelect() {
		rolePanels = new ArrayList<RolePanel>();

		JPanel rolePanel = createRoleSelectTable();
		add(rolePanel, "1,2");

		JPanel roleAllPanel = createSpecialRoleAllSelectPanel();
		add(roleAllPanel, "1,3");

		JPanel submitPanel = createSubmitPanel();
		add(submitPanel, "1,4");

		revalidate();
		repaint();
	}

	private JPanel createSpecialRoleAllSelectPanel() {
		JPanel roleSelectPanel = new JPanel();
		double size[][] = { { TableLayout.FILL }, { 25 } };
		TableLayout layout = new TableLayout(size);
		roleSelectPanel.setLayout(layout);

		for (Act act : model.getActs()) {
			for (Scene scene : act.getScenes()) {
				if (scene.getAllRole() != null) {
					JPanel rolePanel = createRolePanel(scene.getAllRole());
					layout.insertRow(1, TableLayout.PREFERRED);
					roleSelectPanel.add(rolePanel, "0,1");
					layout.insertRow(1, TableLayout.PREFERRED);
					roleSelectPanel.add(new JLabel(scene.getName()), "0,1");
				}
			}
		}

		return roleSelectPanel;
	}

	private JPanel createRoleSelectTable() {
		JPanel roleSelectPanel = new JPanel();
		double size[][] = { { TableLayout.FILL }, { 25 } };
		TableLayout layout = new TableLayout(size);
		roleSelectPanel.setLayout(layout);

		JPanel header = new JPanel();
		double sizeHeader[][] = { { 85, 270, 100, 10, 80, 10, 110 }, { TableLayout.PREFERRED } };
		header.setLayout(new TableLayout(sizeHeader));
		header.add(new JLabel("Pseudorolle"), "0,0");
		header.add(new JLabel("Name"), "1,0");
		header.add(new JLabel("Geschlecht"), "2,0");
		header.add(new JLabel("Wörter"), "4,0");
		header.add(new JLabel("Alter von bis"), "6,0");

		roleSelectPanel.add(header, "0,0");

		for (Role role : model.getRoles()) {
			layout.insertRow(1, TableLayout.PREFERRED);
			JPanel rolePanel = createRolePanel(role);
			roleSelectPanel.add(rolePanel, "0,1");
		}

		return roleSelectPanel;
	}

	private JPanel createRolePanel(final Role role) {
		log.debug("creating role panel for " + role);

		final JPanel rolePanel = new JPanel();
		RolePanel rolePanelWrapper = new RolePanel(mainWindow, this, model, role, rolePanel);
		rolePanels.add(rolePanelWrapper);

		if (role.isPseudoRole()) {
			rolePanelWrapper.setToPseudeRole();
		} else {
			rolePanelWrapper.setToNormalRole();
		}

		return rolePanel;
	}

	/**
	 * Recalculates the words per role and updates the UI. Should be called
	 * after changing a role to a pseudo role or back.
	 */
	public void updateWordCounter() {
		RoleWordCounter counter = new RoleWordCounter();
		counter.updateRoleWords(model);

		for (RolePanel panel : rolePanels) {
			if (!panel.getRole().isPseudoRole()) {
				panel.setToNormalRole();
			}
		}
	}

	private JPanel createSubmitPanel() {
		JPanel submitPanel = new JPanel();
		submitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton saveButton = new JButton("Speichern");
		submitPanel.add(saveButton);

		final JPanel viewPanel = this;
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.debug("saving roles to backing model");
				for (RolePanel panel : rolePanels) {
					panel.saveRole();
				}

				ShowGoDAO.getShowGo().addPlay(model);
				
				log.debug("disabeling all components");
				enableComponents(viewPanel, false);
				
				mainWindow.getNavTree().refreshPlays();
				
				showMessage("Das Stück " + model.getName() + " wurde erfolgreich angelegt.", WindowColors.SUCCESS);
			}
		});

		return submitPanel;
	}

	/**
	 * This method recursively sets the enabled state for a container.
	 * 
	 * @param container
	 * @param enable
	 */
	private void enableComponents(Container container, boolean enable) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			component.setEnabled(enable);
			if (component instanceof Container) {
				enableComponents((Container) component, enable);
			}
		}
	}
	
	private void showMessage(String message, Color background){
		removeMessage();
		
		log.debug("showing message " + message);
		currentMessage = new JLabel(message);
		currentMessage.setBorder(BorderFactory.createEtchedBorder());
		currentMessage.setHorizontalAlignment( SwingConstants.CENTER );
		currentMessage.setBackground(background);
		currentMessage.setOpaque(true);
		this.add(currentMessage, "1,5");
		this.revalidate();
		this.repaint();
	}

	private void removeMessage(){
		if(currentMessage != null){
			this.remove(currentMessage);
			this.revalidate();
			this.repaint();
		}
	}
	
}

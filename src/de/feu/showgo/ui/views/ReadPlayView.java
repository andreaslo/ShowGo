package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import de.feu.showgo.io.ParsingException;
import de.feu.showgo.io.PlayParser;
import de.feu.showgo.model.Act;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.dialogs.RolesSelectDialog;

public class ReadPlayView extends JPanel {

	private TheaterPlay model;
	private MainWindow mainWindow;
	private static final Logger log = Logger.getLogger(ReadPlayView.class);

	public ReadPlayView(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createComponent();
		setName("Bühnenstück einlesen");
	}

	private void createComponent() {
		double size[][] = { { 20, TableLayout.FILL, 20 }, { 20, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, } };
		setLayout(new TableLayout(size));

		JPanel fileSelectPanel = createFileSelectPanel();

		add(fileSelectPanel, "1,1");
	}

	private JPanel createFileSelectPanel() {
		JPanel fileSelectPanel = new JPanel();
		double size[][] = { { TableLayout.FILL, 20, TableLayout.PREFERRED }, { 30, 30 } };
		fileSelectPanel.setLayout(new TableLayout(size));

		final JTextField fileInput = new JTextField("/home/andreas/Macbeth.html");
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

				showRoleSelectPanel();
			}
		});

		fileSelectPanel.add(fileInput, "0,0,f,c");
		fileSelectPanel.add(selectFileButton, "2,0,r,c");
		fileSelectPanel.add(doReadPlayButton, "0,1,l,c");

		return fileSelectPanel;
	}

	private void showRoleSelectPanel() {
		JPanel rolePanel = createRoleSelectPanel();
		add(rolePanel, "1,2");

		JPanel roleAllPanel = createSpecialRoleAllSelectPanel();
		add(roleAllPanel, "1,3");

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
					JPanel rolePanel = new JPanel();
					setPseude(scene.getAllRole(), rolePanel);
					layout.insertRow(1, TableLayout.PREFERRED);
					roleSelectPanel.add(rolePanel, "0,1");
					layout.insertRow(1, TableLayout.PREFERRED);
					roleSelectPanel.add(new JLabel(scene.getName()), "0,1");
				}
			}
		}

		return roleSelectPanel;
	}

	private JPanel createRoleSelectPanel() {
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
		if(role.isPseudoRole()){
			setPseude(role, rolePanel);
		}else{
			fillRolePanel(role, rolePanel);
		}


		return rolePanel;
	}

	private void fillRolePanel(final Role role, final JPanel rolePanel) {
		JCheckBox pseudoSelect = new JCheckBox();

		pseudoSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.debug("pseudo action " + role);
				setPseude(role, rolePanel);
			}
		});

		JLabel nameLabel = new JLabel(role.getName());
		String[] genders = { "Männlich", "Weiblich" };
		JComboBox<String> genderSelect = new JComboBox<String>(genders);
		JTextField requiredWords = new JTextField(role.getWords()+"");
		requiredWords.setEnabled(false);
		JTextField ageFrom = new JTextField();
		JTextField ageTo = new JTextField();

		double size[][] = { { 85, 270, 100, 10, 80, 10, 50, 10, 50 }, { TableLayout.PREFERRED } };
		rolePanel.setLayout(new TableLayout(size));

		rolePanel.add(pseudoSelect, "0,0");
		rolePanel.add(nameLabel, "1,0");
		rolePanel.add(genderSelect, "2,0");
		rolePanel.add(requiredWords, "4,0");
		rolePanel.add(ageFrom, "6,0");
		rolePanel.add(ageTo, "8,0");
	}

	private void unsetPseudo(final Role role, JPanel rolePanel) {
		log.debug("unset pseudo");
		role.setPseudoRole(false);
		role.setAssigendRoles(null);
		rolePanel.removeAll();
		fillRolePanel(role, rolePanel);
		revalidate();
		repaint();
	}

	private void setPseude(final Role role, final JPanel rolePanel) {
		role.setPseudoRole(true);
		
		rolePanel.removeAll();

		double size[][] = { { 85, 270, 190 }, { 10, TableLayout.PREFERRED, TableLayout.PREFERRED, 10 } };
		rolePanel.setLayout(new TableLayout(size));

		JCheckBox pseudoSelect = new JCheckBox();
		pseudoSelect.setSelected(true);
		pseudoSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				unsetPseudo(role, rolePanel);
			}
		});

		JLabel roleLabel = new JLabel("Rollen: ");
		final JLabel assignedRolesDisplay = new JLabel("");
		JLabel nameLabel = new JLabel(role.getName());
		JButton assignRoles = new JButton("Rollen zuordnen");

		assignRoles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RolesSelectDialog dialog = new RolesSelectDialog(role, mainWindow, model,role.getAssigendRoles());
				dialog.showDialog();
				log.debug("dialog closed");
				log.debug("Selected roles: " + dialog.getSelectedRoles());

				if (dialog.isApproved()) {
					role.setAssigendRoles(dialog.getSelectedRoles());
					setAssignedLabelTest(assignedRolesDisplay, role);
				}
			}
		});

		JPanel roleDisplayPanel = new JPanel();
		double roleDisplayPanelSize[][] = { { TableLayout.PREFERRED, TableLayout.FILL }, { TableLayout.PREFERRED } };
		roleDisplayPanel.setLayout(new TableLayout(roleDisplayPanelSize));
		roleDisplayPanel.add(roleLabel, "0,0,l,t");
		roleDisplayPanel.add(assignedRolesDisplay, "1,0");

		rolePanel.add(pseudoSelect, "0,1");
		rolePanel.add(nameLabel, "1,1");
		rolePanel.add(assignRoles, "2,1,l,f");
		rolePanel.add(roleDisplayPanel, "1,2,2,2");

		setAssignedLabelTest(assignedRolesDisplay, role);
		
		revalidate();
		repaint();
	}
	
	private void setAssignedLabelTest(JLabel roleDisplay, Role pseudoRole){
		StringBuilder roleLabelBuilder = new StringBuilder();
		for (Role selectedRole : pseudoRole.getAssigendRoles()) {
			roleLabelBuilder.append(selectedRole.getName() + ", ");
		}
		// remove last ,
		String roleText = roleLabelBuilder.toString();
		if (roleText.length() > 0) {
			roleText = roleText.substring(0, roleText.length() - 2);
		}
		roleDisplay.setText("<html>" + roleText); // the html-tag enables line-breaks
	}

}

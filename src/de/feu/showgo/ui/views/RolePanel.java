package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.dialogs.RolesSelectDialog;

public class RolePanel {

	private MainWindow mainWindow;
	private TheaterPlay model;
	private Role role;
	private JPanel rolePanel;
	private static final Logger log = Logger.getLogger(RolePanel.class);
	
	public RolePanel(MainWindow mainWindow, TheaterPlay model, Role role, JPanel rolePanel){
		this.role = role;
		this.rolePanel = rolePanel;
		this.mainWindow = mainWindow;
		this.model = model;
	}
	
	
	public void setToNormalRole() {
		rolePanel.removeAll();
		JCheckBox pseudoSelect = new JCheckBox();

		pseudoSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.debug("pseudo action " + role);
				setToPseudeRole();
			}
		});

		JLabel nameLabel = new JLabel(role.getName());
		String[] genders = { "Männlich", "Weiblich" };
		JComboBox<String> genderSelect = new JComboBox<String>(genders);
		JTextField requiredWords = new JTextField(role.getWords() + "");
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
		
		rolePanel.revalidate();
		rolePanel.repaint();
	}
	
	
	private void unsetPseudo(final Role role, JPanel rolePanel) {
		log.debug("unset pseudo");
		role.setPseudoRole(false);
		setToNormalRole();
	}

	public void setToPseudeRole() {
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
				RolesSelectDialog dialog = new RolesSelectDialog(role, mainWindow, model, role.getAssigendRoles());
				dialog.showDialog();
				log.debug("dialog closed");
				log.debug("Selected roles: " + dialog.getSelectedRoles());

				if (dialog.isApproved()) {
					role.setAssigendRoles(dialog.getSelectedRoles());
					setAssignedLabelText(assignedRolesDisplay, role);
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

		setAssignedLabelText(assignedRolesDisplay, role);

		rolePanel.revalidate();
		rolePanel.repaint();
	}

	private void setAssignedLabelText(JLabel roleDisplay, Role pseudoRole) {
		StringBuilder roleLabelBuilder = new StringBuilder();
		for (Role selectedRole : pseudoRole.getAssigendRoles()) {
			roleLabelBuilder.append(selectedRole.getName() + ", ");
		}
		// remove last ,
		String roleText = roleLabelBuilder.toString();
		if (roleText.length() > 0) {
			roleText = roleText.substring(0, roleText.length() - 2);
		}
		roleDisplay.setText("<html>" + roleText); // the html-tag enables
													// line-breaks
	}

	
}

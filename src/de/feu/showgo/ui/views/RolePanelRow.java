package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Gender;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.dialogs.RolesSelectDialog;
import de.feu.showgo.ui.listener.RoleDeleteListener;

/**
 * This class represents a single row in the RowPanel table. A user may change
 * the gender, age from and age to requirements or switch to pseudo role and
 * back. The switch pseudo checkbox can be disabled. Optionally a delete button
 * may be displayed, firing a RoleDeleteListener event.
 */
public class RolePanelRow {

	private MainWindow mainWindow;
	private RolePanel parentView;
	private TheaterPlay model;
	private Role role;
	private JPanel rowPanel;
	private static final Logger log = Logger.getLogger(RolePanelRow.class);
	private JTextField requiredWords;
	private JTextField ageFrom;
	private JTextField ageTo;
	private JComboBox<String> genderSelect;
	private String errorMessage;
	private JCheckBox pseudoSelect;
	private boolean changePseudoEnabled;
	private boolean showDeleteButton;
	private List<RoleDeleteListener> roleDeleteListener;

	/**
	 * Instantiates a new role panel row.
	 * 
	 * @param mainWindow
	 *            the main window
	 * @param rolePanel
	 *            the role panel
	 * @param model
	 *            the model
	 * @param role
	 *            the role
	 * @param rowContent
	 *            the row content
	 */
	public RolePanelRow(MainWindow mainWindow, RolePanel rolePanel, TheaterPlay model, Role role, JPanel rowContent) {
		this.role = role;
		this.rowPanel = rowContent;
		this.mainWindow = mainWindow;
		this.model = model;
		this.parentView = rolePanel;
		this.changePseudoEnabled = true;
		roleDeleteListener = new ArrayList<RoleDeleteListener>();
	}

	/**
	 * Sets the role to normale role, creating all necessary components. Calling
	 * this method on a normale role updates it.
	 */
	public void setToNormalRole() {
		rowPanel.removeAll();
		pseudoSelect = new JCheckBox();
		pseudoSelect.setEnabled(changePseudoEnabled);
		pseudoSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.debug("pseudo action " + role);
				setToPseudeRole();
			}
		});

		JLabel nameLabel = new JLabel(role.getName());
		String[] genders = { "Männlich", "Weiblich" };
		genderSelect = new JComboBox<String>(genders);

		if (role.getGender() == Gender.MALE) {
			genderSelect.setSelectedIndex(0);
		} else {
			genderSelect.setSelectedIndex(1);
		}

		requiredWords = new JTextField(role.getWords() + "");
		requiredWords.setEnabled(false);
		ageFrom = new JTextField(role.getAgeFrom() + "");
		ageTo = new JTextField(role.getAgeTo() + "");

		double size[][] = { { 85, 270, 100, 10, 80, 10, 50, 10, 50, 80 }, { TableLayout.PREFERRED } };
		rowPanel.setLayout(new TableLayout(size));

		rowPanel.add(pseudoSelect, "0,0");
		rowPanel.add(nameLabel, "1,0");
		rowPanel.add(genderSelect, "2,0");
		rowPanel.add(requiredWords, "4,0");
		rowPanel.add(ageFrom, "6,0");
		rowPanel.add(ageTo, "8,0");

		JButton deleteButton = new JButton("Löschen");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteButtonAction();
			}
		});
		if (showDeleteButton) {
			rowPanel.add(deleteButton, "9,0,r,c");
		}

		rowPanel.revalidate();
		rowPanel.repaint();
	}

	private void unsetPseudo(final Role role, JPanel rolePanel) {
		log.debug("unset pseudo");
		role.setPseudoRole(false);
		parentView.updateWordCounter();
		setToNormalRole();
	}

	/**
	 * Sets the to pseude role, creating all necessary components. Calling this
	 * method on a pseudo role updates it.
	 */
	public void setToPseudeRole() {
		role.setPseudoRole(true);

		rowPanel.removeAll();

		double size[][] = { { 85, 270, 310, 80 }, { 10, TableLayout.PREFERRED, TableLayout.PREFERRED, 10 } };
		rowPanel.setLayout(new TableLayout(size));

		pseudoSelect = new JCheckBox();
		pseudoSelect.setEnabled(changePseudoEnabled);
		pseudoSelect.setSelected(true);
		pseudoSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				unsetPseudo(role, rowPanel);
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
					parentView.updateWordCounter();
				}
			}
		});

		JPanel roleDisplayPanel = new JPanel();
		double roleDisplayPanelSize[][] = { { TableLayout.PREFERRED, TableLayout.FILL }, { TableLayout.PREFERRED } };
		roleDisplayPanel.setLayout(new TableLayout(roleDisplayPanelSize));
		roleDisplayPanel.add(roleLabel, "0,0,l,t");
		roleDisplayPanel.add(assignedRolesDisplay, "1,0");

		rowPanel.add(pseudoSelect, "0,1");
		rowPanel.add(nameLabel, "1,1");
		rowPanel.add(assignRoles, "2,1,l,f");
		rowPanel.add(roleDisplayPanel, "1,2,2,2");

		setAssignedLabelText(assignedRolesDisplay, role);

		JButton deleteButton = new JButton("Löschen");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteButtonAction();
			}
		});

		if (showDeleteButton) {
			rowPanel.add(deleteButton, "3,1,r,c");
		}

		rowPanel.revalidate();
		rowPanel.repaint();
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

	/**
	 * Gets the role.
	 * 
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * This methods stores the ui fields into the backing model. The method is
	 * only applicable for non-pseudo roles. It will do nothing called on a
	 * pseudo-role.
	 */
	public void saveRole() {
		if (!role.isPseudoRole()) {
			role.setAgeFrom(Integer.valueOf(ageFrom.getText()));
			role.setAgeTo(Integer.valueOf(ageTo.getText()));
			if (genderSelect.getSelectedIndex() == 0) {
				role.setGender(Gender.MALE);
			} else if (genderSelect.getSelectedIndex() == 1) {
				role.setGender(Gender.FEMALE);
			} else {
				log.error("unknown gender");
			}
		}
	}

	/**
	 * This methode validates the role panel. For regular roles both age fields
	 * must be set, contain an integer and the from age must not be higher than
	 * the to age. Pseudo Roles need a least one child role assigned.
	 * 
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return performValidation();
	}

	private boolean performValidation() {
		if (role.isPseudoRole()) {
			if (role.getAssigendRoles().isEmpty()) {
				errorMessage = "Der Pseudorolle " + role.getName() + " sind keine Rollen zugewiesen.";
				return false;
			}
		} else {
			if (ageFrom.getText().equals("")) {
				errorMessage = "Das \"Alter von\" Feld ist nicht gesetzt für Rolle " + role.getName();
				return false;
			}
			if (ageTo.getText().equals("")) {
				errorMessage = "Das \"Alter bis\" Feld ist nicht gesetzt für Rolle " + role.getName();
				return false;
			}

			int ageFromInt = 0;
			int ageToInt = 0;
			try {
				ageFromInt = Integer.valueOf(ageFrom.getText());
			} catch (NumberFormatException notUsed) {
				errorMessage = "Das \"Alter von\" Feld der Rolle " + role.getName() + " enthält keine ganze Zahl.";
				return false;
			}

			try {
				ageToInt = Integer.valueOf(ageTo.getText());
			} catch (NumberFormatException notUsed) {
				errorMessage = "Das \"Alter bis\" Feld der Rolle " + role.getName() + " enthält keine ganze Zahl.";
				return false;
			}

			if (ageFromInt > ageToInt) {
				errorMessage = "Das \"Alter von\" Feld der Rolle " + role.getName() + " ist größer als das \"Alter bis\" Feld.";
				return false;
			}
		}

		return true;
	}

	/**
	 * This method returns the validation error message. If there is no error it
	 * returns null. The method internally performs a validation.
	 * 
	 * @return the validation error message
	 */
	public String getValidationErrorMessage() {
		errorMessage = null;
		performValidation();
		return errorMessage;
	}

	/**
	 * If true a the change pseudo checkbox is enabled.
	 * 
	 * @param changePseudoEnabled
	 */
	public void setChangePseudoEnabled(boolean changePseudoEnabled) {
		if (pseudoSelect != null) {
			pseudoSelect.setEnabled(changePseudoEnabled);
		}
		this.changePseudoEnabled = changePseudoEnabled;
	}

	/**
	 * Gets the row panel.
	 * 
	 * @return the row panel
	 */
	public JPanel getRowPanel() {
		return rowPanel;
	}

	/**
	 * If true, a delete button is displayed next to the role.
	 * 
	 * @param showDeleteButton
	 */
	public void setShowDeleteButton(boolean showDeleteButton) {
		this.showDeleteButton = showDeleteButton;
		if (role.isPseudoRole()) {
			setToPseudeRole();
		} else {
			setToNormalRole();
		}
	}

	/**
	 * Adds a role delete event listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addRoleDeleteEventListener(RoleDeleteListener listener) {
		roleDeleteListener.add(listener);
	}

	private void deleteButtonAction() {
		for (RoleDeleteListener listener : roleDeleteListener) {
			listener.deleteRole(role);
		}
	}

}

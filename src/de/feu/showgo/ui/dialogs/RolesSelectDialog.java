package de.feu.showgo.ui.dialogs;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;

/**
 * This class contains a dialog for selecting roles. A list of roles is
 * displayed to the user. He can select roles using checkboxes and confirm or
 * cancle his selection. It is used for assigning pseudo roles to a role.
 * 
 */
public class RolesSelectDialog {

	private JDialog dialog;
	private TheaterPlay play;
	private boolean approved;
	private List<Role> selectedRoles;
	private static final Logger log = Logger.getLogger(RolesSelectDialog.class);

	/**
	 * Instantiates a new roles select dialog for assigning roles to a pseudo role.
	 * 
	 * @param role
	 *            the role
	 * @param mainWindow
	 *            the main window
	 * @param play
	 *            the play
	 * @param assignedRoles
	 *            the pre selected roles
	 */
	public RolesSelectDialog(Role role, MainWindow mainWindow, TheaterPlay play, List<Role> assignedRoles) {
		this.play = play;
		log.debug("Assigned roles: " + assignedRoles);
		if (assignedRoles == null) {
			selectedRoles = new ArrayList<Role>();
		} else {
			selectedRoles = assignedRoles;
		}

		dialog = new JDialog(mainWindow);
		dialog.setSize(400, 600);
		dialog.setTitle("Rollenzurodnung für " + role.getName());
		dialog.setModal(true);
		dialog.setLocationRelativeTo(mainWindow);

		createDialogContent();
	}

	private void createDialogContent() {
		JPanel contentPanel = new JPanel();

		double size[][] = { { 10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, TableLayout.FILL }, { TableLayout.PREFERRED, 50 } };
		TableLayout layout = new TableLayout(size);
		contentPanel.setLayout(layout);

		JPanel selectTable = createSelectTable();

		JButton saveButton = new JButton("Speichern");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				approved = true;
				dialog.dispose();
			}
		});

		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});

		contentPanel.add(selectTable, "1,0,4,0");
		contentPanel.add(saveButton, "1,1,l,c");
		contentPanel.add(cancelButton, "3,1,l,c");

		dialog.add(new JScrollPane(contentPanel));
	}

	private JPanel createSelectTable() {
		JPanel selectTable = new JPanel();

		double sizeSelectTable[][] = { { 50, TableLayout.FILL }, { TableLayout.PREFERRED } };
		TableLayout selectTableLayout = new TableLayout(sizeSelectTable);
		selectTable.setLayout(selectTableLayout);

		for (final Role curRole : play.getRegularRoles()) {
			selectTableLayout.insertRow(1, TableLayout.PREFERRED);
			JCheckBox roleSelected = new JCheckBox();
			if (selectedRoles.contains(curRole)) {
				roleSelected.setSelected(true);
			}

			roleSelected.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (selectedRoles.contains(curRole)) {
						log.debug("removing selection for: " + curRole);
						selectedRoles.remove(curRole);
					} else {
						log.debug("Set as selected: " + curRole);
						selectedRoles.add(curRole);
					}
					log.debug("There are " + selectedRoles.size() + " roles selected");
				}
			});

			JLabel roleDisplay = new JLabel(curRole.getName());
			selectTable.add(roleSelected, "0,1");
			selectTable.add(roleDisplay, "1,1");
		}

		return selectTable;
	}

	/**
	 * Opens the dialog.
	 */
	public void showDialog() {
		dialog.setVisible(true);
	}

	/**
	 * Checks if the ok button was pressed.
	 * 
	 * @return true, if approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * Gets the selected roles.
	 * 
	 * @return the selected roles
	 */
	public List<Role> getSelectedRoles() {
		return selectedRoles;
	}

}

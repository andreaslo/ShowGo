package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.feu.showgo.io.RoleWordCounter;
import de.feu.showgo.model.Act;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.listener.RoleDeleteListener;

/**
 * This panel displays a list of roles. The user may alter the role requirements
 * and switch between a pseudo and non-pseudo role. Optionally a delete button
 * may be displayed next to the roles notifying all registered
 * RoleDeleteListener. Each role is displayed in a RolePanelRow.
 */
public class RolePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	private static final Logger log = Logger.getLogger(RolePanel.class);
	private List<RolePanelRow> rolePanelRows;
	private TheaterPlay model;
	private JPanel roleSelectPanel;
	private JPanel specialRoleSelectPanel;
	private boolean changePseudoEnabled;
	private boolean showDeleteButton;
	private List<RoleDeleteListener> roleDeleteListener;

	/**
	 * Instantiates a new role panel.
	 * 
	 * @param mainWindow
	 *            the main window
	 * @param model
	 *            the model
	 */
	public RolePanel(MainWindow mainWindow, TheaterPlay model) {
		this.mainWindow = mainWindow;
		this.model = model;
		rolePanelRows = new ArrayList<RolePanelRow>();
		roleDeleteListener = new ArrayList<RoleDeleteListener>();
		createComponent();
	}

	private void createComponent() {
		double size[][] = { { TableLayout.FILL }, { TableLayout.PREFERRED, TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		setLayout(layout);

		roleSelectPanel = createRoleSelectTable();
		add(roleSelectPanel, "0,0");

		specialRoleSelectPanel = createSpecialRoleAllSelectPanel();
		add(specialRoleSelectPanel, "0,1");
	}

	private JPanel createSpecialRoleAllSelectPanel() {
		JPanel specialRoleSelectPanel = new JPanel();
		double size[][] = { { TableLayout.FILL }, { 25 } };
		TableLayout layout = new TableLayout(size);
		specialRoleSelectPanel.setLayout(layout);

		int rowCounter = 1;
		for (Act act : model.getActs()) {
			for (Scene scene : act.getScenes()) {
				if (scene.getAllRole() != null) {
					layout.insertRow(rowCounter, TableLayout.PREFERRED);
					specialRoleSelectPanel.add(new JLabel(act.getName() + " / " + scene.getName()), "0," + rowCounter);
					rowCounter++;

					JPanel rolePanel = createRolePanel(scene.getAllRole());
					layout.insertRow(rowCounter, TableLayout.PREFERRED);
					specialRoleSelectPanel.add(rolePanel, "0," + rowCounter);
					rowCounter++;
				}
			}
		}

		return specialRoleSelectPanel;
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

		int rowCounter = 1;
		for (Role role : model.getRoles()) {
			layout.insertRow(rowCounter, TableLayout.PREFERRED);
			JPanel rolePanel = createRolePanel(role);
			roleSelectPanel.add(rolePanel, "0," + rowCounter);
			rowCounter++;
		}

		return roleSelectPanel;
	}

	private JPanel createRolePanel(final Role role) {
		log.debug("creating role panel for " + role);

		final JPanel rolePanel = new JPanel();
		RolePanelRow rolePanelWrapper = new RolePanelRow(mainWindow, this, model, role, rolePanel);
		rolePanelWrapper.setChangePseudoEnabled(changePseudoEnabled);
		rolePanelWrapper.setShowDeleteButton(showDeleteButton);
		for (RoleDeleteListener listener : roleDeleteListener) {
			rolePanelWrapper.addRoleDeleteEventListener(listener);
		}
		rolePanelRows.add(rolePanelWrapper);

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

		for (RolePanelRow panel : rolePanelRows) {
			if (!panel.getRole().isPseudoRole()) {
				panel.setToNormalRole();
			}
		}
	}

	/**
	 * Gets the role panel rows.
	 * 
	 * @return the role panel rows
	 */
	public List<RolePanelRow> getRolePanelRows() {
		return rolePanelRows;
	}

	/**
	 * Sets the change pseudo enabled. If true, the set pseudo checkboxes are
	 * enabled. This method updates all rows.
	 * 
	 * @param changePseudoEnabled
	 *            If true, the set pseudo checkboxes are enabled.
	 */
	public void setChangePseudoEnabled(boolean changePseudoEnabled) {
		for (RolePanelRow panel : rolePanelRows) {
			panel.setChangePseudoEnabled(changePseudoEnabled);
		}
		this.changePseudoEnabled = changePseudoEnabled;
	}

	/**
	 * Removes a role and updates the table.
	 * 
	 * @param role
	 *            the role
	 */
	public void removeRole(Role role) {
		for (RolePanelRow row : rolePanelRows) {
			if (row.getRole() == role) {
				log.debug("role panel row found");
				roleSelectPanel.remove(row.getRowPanel());
				repaint();
				revalidate();
			}
		}
	}

	/**
	 * Refresh pseudo roles after changing the cast.
	 */
	public void refreshPseudoRoles() {
		for (RolePanelRow row : rolePanelRows) {
			if (row.getRole().isPseudoRole()) {
				log.debug("refreshing pseudo role: " + row.getRole().getName());
				row.setToPseudeRole();
			}
		}

		remove(specialRoleSelectPanel);
		specialRoleSelectPanel = createSpecialRoleAllSelectPanel();
		add(specialRoleSelectPanel, "0,1");

		repaint();
		revalidate();
	}

	/**
	 * Sets the show delete button flag. If true, a delete button is displayed
	 * for each role. On button press, all assigned RoleDeleteListener are
	 * notified.
	 * 
	 * @param showDeleteButton
	 *            if true, a delete button is displayed next to each role.
	 */
	public void setShowDeleteButton(boolean showDeleteButton) {
		log.debug("updateing show delete button: " + showDeleteButton);

		for (RolePanelRow panel : rolePanelRows) {
			panel.setShowDeleteButton(showDeleteButton);
		}
		this.showDeleteButton = showDeleteButton;
	}

	/**
	 * Adds a role delete event listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addRoleDeleteEventListener(RoleDeleteListener listener) {
		roleDeleteListener.add(listener);
		for (RolePanelRow panel : rolePanelRows) {
			panel.addRoleDeleteEventListener(listener);
		}
	}

}

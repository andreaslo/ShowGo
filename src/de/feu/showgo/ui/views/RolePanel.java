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

public class RolePanel extends JPanel {

	private MainWindow mainWindow;
	private static final Logger log = Logger.getLogger(RolePanel.class);
	private List<RolePanelRow> rolePanelRows;
	private TheaterPlay model;

	public RolePanel(MainWindow mainWindow, TheaterPlay model) {
		this.mainWindow = mainWindow;
		this.model = model;
		rolePanelRows = new ArrayList<RolePanelRow>();
		createComponent();
	}
	
	private void createComponent(){
		double size[][] = { { TableLayout.FILL }, { TableLayout.PREFERRED, TableLayout.PREFERRED } };
		TableLayout layout = new TableLayout(size);
		setLayout(layout);
		
		JPanel rolePanel = createRoleSelectTable();
		add(rolePanel, "0,0");

		JPanel roleAllPanel = createSpecialRoleAllSelectPanel();
		add(roleAllPanel, "0,1");
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
		RolePanelRow rolePanelWrapper = new RolePanelRow(mainWindow, this, model, role, rolePanel);
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

	public List<RolePanelRow> getRolePanelRows() {
		return rolePanelRows;
	}
	
}

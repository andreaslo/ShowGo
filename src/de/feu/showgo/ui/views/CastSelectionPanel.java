package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Person;
import de.feu.showgo.model.Role;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.listener.RoleDeleteListener;

/**
 * This class is a JPanel for assigning a list of people to a list of roles. For
 * each role a CastRowPanel is created that shows the actual select.
 */
public class CastSelectionPanel extends JPanel implements RoleDeleteListener {

	private static final long serialVersionUID = -4563301479407385455L;
	@SuppressWarnings("unused")
	private MainWindow mainWindow;
	private List<Role> roles;
	private List<Person> availablePersons;
	private List<CastRowPanel> castRows;
	private String borderTitle;
	private boolean mayBeEmpty;
	private static final Logger log = Logger.getLogger(CastSelectionPanel.class);

	/**
	 * Instantiates a new cast selection panel.
	 *
	 * @param mainWindow the main window
	 * @param roles the roles
	 * @param availablePersons the available persons
	 * @param borderTitle The title of the panel
	 * @param mayBeEmpty If true, an empty person selection is available.
	 */
	public CastSelectionPanel(MainWindow mainWindow, List<Role> roles, List<Person> availablePersons, String borderTitle, boolean mayBeEmpty) {
		log.debug("creating cast selection panel");
		this.mainWindow = mainWindow;
		this.roles = roles;
		this.availablePersons = availablePersons;
		this.borderTitle = borderTitle;
		this.mayBeEmpty = mayBeEmpty;
		createComponent();
	}

	private void createComponent() {
		castRows = new ArrayList<CastRowPanel>();

		if (borderTitle != null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), borderTitle);
			setBorder(titledBorder);
		}

		setLayout(new TableLayout(generateLayoutSize(roles.size())));
		int rowCounter = 0;
		for (Role role : roles) {
			if (!role.isPseudoRole()) {
				CastRowPanel row = new CastRowPanel(role, availablePersons, mayBeEmpty);
				add(row, "0," + rowCounter);
				rowCounter++;
				castRows.add(row);
			}
		}

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

	/* (non-Javadoc)
	 * @see de.feu.showgo.ui.listener.RoleDeleteListener#deleteRole(de.feu.showgo.model.Role)
	 */
	@Override
	public void deleteRole(Role role) {
		log.debug("cast panel notified abour deleted role: " + role);
		CastRowPanel toBeRemoved = null;
		for (CastRowPanel rowPanel : castRows) {
			if (rowPanel.getRole() == role) {
				remove(rowPanel);
				toBeRemoved = rowPanel;
			}
		}

		castRows.remove(toBeRemoved);
	}

	/**
	 * Save cast to backing model.
	 */
	public void saveCastToBackingModel() {
		log.debug("saving cast");
		for (CastRowPanel row : castRows) {
			List<Person> selectedPersons = row.getSelectedPersons();
			row.getRole().setCast(selectedPersons);
			log.debug("setting cast of " + row.getRole().getName() + " to: " + selectedPersons);
		}
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

}

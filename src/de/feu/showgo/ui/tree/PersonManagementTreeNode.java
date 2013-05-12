package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.ShowCreatePersonAction;

/**
 * This class represents the person root node in the navigation tree. All
 * persons are children of it. The menu pop shows the create person action.
 */
public class PersonManagementTreeNode extends TreeElement {

	private MainWindow mainWindow;

	public PersonManagementTreeNode(Object customObject, MainWindow mainWindow) {
		super(customObject);
		this.mainWindow = mainWindow;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();

		JMenuItem createPerson = new JMenuItem("Neue Person");
		createPerson.addActionListener(new ShowCreatePersonAction(mainWindow));

		popup.add(createPerson);
		return popup;
	}

}

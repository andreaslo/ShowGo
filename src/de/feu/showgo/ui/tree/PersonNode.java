package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.DeletePersonAction;
import de.feu.showgo.ui.actions.EditPersonAction;

/**
 * This class represents a person in the navigation tree. The menu pop shows the
 * edit and delete person actions.
 */
public class PersonNode extends TreeElement {

	private MainWindow mainWindow;
	private Person person;

	/**
	 * Instantiates a new person node.
	 *
	 * @param person the person
	 * @param mainWindow the main window
	 */
	public PersonNode(Person person, MainWindow mainWindow) {
		super(person);
		this.person = person;
		this.mainWindow = mainWindow;
	}

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see de.feu.showgo.ui.tree.TreeElement#getPopupMenu()
	 */
	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();

		JMenuItem editPerson = new JMenuItem("Bearbeiten");
		editPerson.addActionListener(new EditPersonAction(mainWindow, person));

		JMenuItem deletePerson = new JMenuItem("Löschen");
		deletePerson.addActionListener(new DeletePersonAction(mainWindow, person));

		popup.add(editPerson);
		popup.addSeparator();
		popup.add(deletePerson);
		return popup;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString() {
		return person.getName();
	}

}

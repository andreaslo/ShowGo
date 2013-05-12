package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;

/**
 * This action open the edit person view with the provided person.
 */
public class EditPersonAction implements ActionListener {

	private Person person;
	private MainWindow mainWindow;

	/**
	 * Instantiates a new edit person action.
	 *
	 * @param mainWindow the main window
	 * @param person the person
	 */
	public EditPersonAction(MainWindow mainWindow, Person person) {
		this.mainWindow = mainWindow;
		this.person = person;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showEditPersonView(person);
	}

}

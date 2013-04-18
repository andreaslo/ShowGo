package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;

public class EditPersonAction implements ActionListener {
	
	private Person person;
	private MainWindow mainWindow;

	public EditPersonAction(MainWindow mainWindow, Person person) {
		this.mainWindow = mainWindow;
		this.person = person;
	 }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showEditPerson(person);
	}

	
	
}

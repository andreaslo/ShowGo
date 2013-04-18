package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;

public class DeletePersonAction implements ActionListener {
	
	private Person person;
	private MainWindow mainWindow;

	public DeletePersonAction(MainWindow mainWindow, Person person) {
		this.mainWindow = mainWindow;
		this.person = person;
	 }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(mainWindow, "Möchten Sie wirklich die Person " + person.getName() + " löschen?", person.getName() + " löschen", JOptionPane.YES_NO_OPTION);
		if(choice == JOptionPane.YES_OPTION){
			// TODO: Only delete users if they are not part of an ensemble
			// TODO: Change view if this user is opened in edit mode
			
			ShowGoDAO.getShowGo().deltePerson(person);
			mainWindow.getNavTree().refreshPersons();
		}
	}

	
	
}

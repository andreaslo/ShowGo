package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.views.PersonManagementView;

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

			// If the deleted user is currently being edited, switch to the startup view.
			JPanel currentView = mainWindow.getCurrentView();
			if(currentView instanceof PersonManagementView){
				Person editedPerson = ((PersonManagementView) currentView).getModel();
				if(editedPerson == person){
					mainWindow.showStartupView();
				}
			}
			
			ShowGoDAO.getShowGo().deltePerson(person);
			mainWindow.getNavTree().refreshPersons();
		}
	}

	
	
}

package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Ensemble;
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
		for (Ensemble ensemble : ShowGoDAO.getShowGo().getEnsembles()) {
			for (Person p : ensemble.getMembers()) {
				if (person == p) {
					JOptionPane.showMessageDialog(mainWindow, "Die Person " + person.getName() + " ist dem ensemble " + ensemble.getName()
							+ " zugeordnet und kann daher nicht gelöscht werden.", "Person Ensemble zugeordnet", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		}
		
		int choice = JOptionPane.showConfirmDialog(mainWindow, "Möchten Sie wirklich die Person " + person.getName() + " löschen?", person.getName()
				+ " löschen", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {

			// If the deleted user is currently being edited, switch to the
			// startup view.
			JPanel currentView = mainWindow.getCurrentView();
			if (currentView instanceof PersonManagementView) {
				Person editedPerson = ((PersonManagementView) currentView).getModel();
				if (editedPerson == person) {
					mainWindow.showStartupView();
				}
			}

			ShowGoDAO.getShowGo().deltePerson(person);
			mainWindow.getNavTree().refreshPersons();
		}
	}

}

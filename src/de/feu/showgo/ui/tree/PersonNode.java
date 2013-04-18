package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.DeletePersonAction;
import de.feu.showgo.ui.actions.EditPersonAction;
import de.feu.showgo.ui.actions.ShowCreatePersonAction;

public class PersonNode extends TreeElement{

		private MainWindow mainWindow;
		private Person person;
		
		public PersonNode(Person person, MainWindow mainWindow) {
			super(person);
			this.person = person;
			this.mainWindow = mainWindow;
		}

		private static final long serialVersionUID = 1L;
		
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

		@Override
		public String toString() {
			return person.getName();
		}
		
		
}

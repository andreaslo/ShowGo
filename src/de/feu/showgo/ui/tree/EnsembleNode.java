package de.feu.showgo.ui.tree;

import javax.swing.JPopupMenu;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.ui.MainWindow;

public class EnsembleNode extends TreeElement{

		private MainWindow mainWindow;
		private Ensemble ensemble;
		
		public EnsembleNode(Ensemble ensemble, MainWindow mainWindow) {
			super(ensemble);
			this.ensemble = ensemble;
			this.mainWindow = mainWindow;
		}

		private static final long serialVersionUID = 1L;
		
		@Override
		public JPopupMenu getPopupMenu() {
			JPopupMenu popup = new JPopupMenu();
			
//			JMenuItem editPerson = new JMenuItem("Bearbeiten");
//			editPerson.addActionListener(new EditPersonAction(mainWindow, person));
//			
//			JMenuItem deletePerson = new JMenuItem("Löschen");
//			deletePerson.addActionListener(new DeletePersonAction(mainWindow, person));
//			
//			popup.add(editPerson);
//			popup.addSeparator();
//			popup.add(deletePerson);
			return popup;
		}

		@Override
		public String toString() {
			return ensemble.getName();
		}
		
		
}

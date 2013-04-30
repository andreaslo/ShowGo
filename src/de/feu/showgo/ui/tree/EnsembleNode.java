package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.DeleteEnsembleAction;
import de.feu.showgo.ui.actions.EditEnsembleAction;

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
			
			JMenuItem editEnsemble = new JMenuItem("Bearbeiten");
			editEnsemble.addActionListener(new EditEnsembleAction(mainWindow, ensemble));
			
			JMenuItem deleteEnsemble = new JMenuItem("Löschen");
			deleteEnsemble.addActionListener(new DeleteEnsembleAction(mainWindow, ensemble));
			
			popup.add(editEnsemble);
			popup.addSeparator();
			popup.add(deleteEnsemble);
			return popup;
		}

		@Override
		public String toString() {
			return ensemble.getName();
		}
		
		
}

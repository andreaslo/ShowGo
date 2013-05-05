package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.DeleteProductionAction;

public class ProductionNode extends TreeElement{

		private MainWindow mainWindow;
		private Production production;
		
		public ProductionNode(Production production, MainWindow mainWindow) {
			super(production);
			this.production = production;
			this.mainWindow = mainWindow;
		}

		private static final long serialVersionUID = 1L;
		
		@Override
		public JPopupMenu getPopupMenu() {
			JPopupMenu popup = new JPopupMenu();
			
			JMenuItem showEnsemble = new JMenuItem("Anzeigen");
			
			JMenuItem editEnsemble = new JMenuItem("Bearbeiten");
			
			JMenuItem deleteEnsemble = new JMenuItem("Löschen");
			deleteEnsemble.addActionListener(new DeleteProductionAction(mainWindow, production));
			
			popup.add(showEnsemble);
			popup.add(editEnsemble);
			popup.addSeparator();
			popup.add(deleteEnsemble);
			return popup;
		}

		@Override
		public String toString() {
			return production.getName();
		}
		
		
}

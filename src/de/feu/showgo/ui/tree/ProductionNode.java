package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.DeleteProductionAction;
import de.feu.showgo.ui.actions.EditProductionAction;

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
			
			JMenuItem editProduction = new JMenuItem("Bearbeiten");
			editProduction.addActionListener(new EditProductionAction(mainWindow, production));
			
			JMenuItem deleteProduction = new JMenuItem("Löschen");
			deleteProduction.addActionListener(new DeleteProductionAction(mainWindow, production));
			
			popup.add(editProduction);
			popup.addSeparator();
			popup.add(deleteProduction);
			return popup;
		}

		@Override
		public String toString() {
			return production.getName();
		}
		
		
}

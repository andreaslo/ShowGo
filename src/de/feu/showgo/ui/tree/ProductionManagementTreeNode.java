package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.ShowProductionViewAction;


public class ProductionManagementTreeNode extends TreeElement{

	private MainWindow mainWindow;
	
	public ProductionManagementTreeNode(Object customObject, MainWindow mainWindow) {
		super(customObject);
		this.mainWindow = mainWindow;
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		
		JMenuItem createProduction = new JMenuItem("Neue Inszenierung anlegen");
		createProduction.addActionListener(new ShowProductionViewAction(mainWindow));
		
		popup.add(createProduction);
		return popup;
	}

}
